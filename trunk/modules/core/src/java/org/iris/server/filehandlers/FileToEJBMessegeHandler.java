package org.iris.server.filehandlers;

import org.iris.server.errorhandler.ErrorMessageVO;
import org.iris.server.util.config.Service;
import org.iris.server.util.config.ServiceFileEJB;
import org.apache.log4j.NDC;

import javax.ejb.EJBHome;
import javax.ejb.EJBMetaData;
import javax.ejb.EJBObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * All files in infolder are read and sent to ejb for this service. If bind
 * fails or a subsequent call to ejb fails a connector thread is launched and
 * tries to reconnect.
 *
 */
public class FileToEJBMessegeHandler
    extends FileMessageHandler
{
    protected Method methodToInvoke;
    protected EJBObject bean;
    protected String serviceMethod;
    private boolean isConnected = false;
    private long reconnectDelay = 600000;
    private Connector connector;
    private boolean reconnectForever;

    
    public FileToEJBMessegeHandler(ServiceFileEJB service)
    {
        super.init(service);
        setCorbaSettings();
        NDC.push( serviceId );
        ior_file = service.getNameService();
        jndiname = service.getServiceName();
        serviceMethod = service.getServiceMethod();
        reconnectDelay = service.getReconnectToObjectInMilliSeconds();
        reconnectToObjectPolicy = service.getReconnectToObjectPolicy();
        myApplicationLogger.info( "Reconnect policy : " + reconnectToObjectPolicy );
        if ( reconnectToObjectPolicy.equalsIgnoreCase( ServiceFileEJB.RECONNECT_FOREVER ) )
        {
            reconnectForever = true;
        }
        NDC.remove();

        setUp( service );
    }

    /**
     * Some init parameters to inform wm we are using visibroker orb
     *
     */
    private void setCorbaSettings()
    {
        if (System.getProperties().get("org.omg.CORBA.ORBClass") == null)
        {
            System.getProperties().put("org.omg.CORBA.ORBClass", "com.inprise.vbroker.orb.ORB");
        }
        if (System.getProperties().get("org.omg.CORBA.ORBSingletonClass") == null)
        {
            System.getProperties().put("org.omg.CORBA.ORBSingletonClass", "com.inprise.vbroker.orb.ORB");
        }
    }

    /**
     * Bind to ejb and start error handler if it has been configured in service settings.
     *
     * @param service
     */
    protected void setUp( Service service )
    {
        NDC.push( serviceId );
        bindToEJB();
        NDC.remove();
        startErrorHandler(service);
        if ( ! isConnected ) fireConnector();
    }

    /**
     * Lookup and bind to ejb and set connected flag to true if sucessfull. Signal the connector thread
     * to stop if it is running.
     */
    private void bindToEJB()
    {
        try
        {
            Hashtable env = new Hashtable();
            env.put(Context.PROVIDER_URL, getIorFromFile());
            Context ctx = new InitialContext(env);
            Object objRef = ctx.lookup(jndiname);
            EJBHome home = (EJBHome) PortableRemoteObject.narrow(objRef, EJBHome.class);
            EJBMetaData ejbMetadata = home.getEJBMetaData();
            home = (EJBHome) PortableRemoteObject.narrow(objRef, ejbMetadata.getHomeInterfaceClass());
            Class remoteClass = ejbMetadata.getRemoteInterfaceClass();
            Class classData = home.getClass();
            methodToInvoke = classData.getMethod("create", null);
            bean = (EJBObject) methodToInvoke.invoke(home, null);
            methodToInvoke = remoteClass.getMethod(this.serviceMethod, new Class[]{String.class});
            myApplicationLogger.info( "Connected to ejb" );
            mySocketLogger.info( "Connected to ejb" );
            isConnected = true;
            stopConnector();

        }
        catch (Exception e)
        {
            myApplicationLogger.fatal("Could not connect to ejb. Failed connecting to naming service in app server.", e);
            isConnected = false;
        }
    }

    /**
     * Schedule method runs on configured intervall for service.
     */
    public void run()
    {
        NDC.push( serviceId );
        readSendFiles();
        NDC.remove();
    }

    /**
     * Actaul work takes place here. Read files from infolder and call send.
     */
    synchronized private void readSendFiles()
    {
        readFrom = new File(readFromDir);
        File[] curFiles = readFrom.listFiles();

        int fileCollectionSize = getFileCollectionSize(curFiles);

        logStatistics( fileCollectionSize );

        if ( !isMessagesReceived(curFiles) ) return;

        if (sortalpha)
        {
            Arrays.sort(curFiles);
        }

        String fileName = "";
        for (int i = 0; i < fileCollectionSize; i++)
        {
            fileName = curFiles[i].getName();
            try
            {
                if (createuniquename)
                {
                    fileName = createUniqueFileName(curFiles[i].getName());
                }
                send(curFiles[i], fileName);
            }
            catch (Exception ex)
            {
                myApplicationLogger.info(ex.getMessage());
            }
            curFiles[i].delete();
        }

        // this enforces a status file written to error info folder if any errors
        handleStatusFileForErrors();
        //NDC.remove();
    }

    /**
     * Send current file contents to ejb. If ok sotre backup if configured to do so. If failure handle error
     * and fire new connetor thread if none started.
     *
     * @param file
     * @param name
     */
    protected void send(File file, String name)
    {
        try
        {
            String message = getMessageAsString(file);
            if (isConnected && methodToInvoke != null)
            {
                methodToInvoke.invoke(bean, new String[]{message});
                //myApplicationLogger.debug("New message sent storing file for backup");
                handleBackup(file, file.getName());
            }
            else
            {
                throw new Exception("Not connected to ejb, message not sent");
            }
        }
        catch (Exception ex)
        {
            myApplicationLogger.error("Could not send file to ejb");
            ErrorMessageVO error = new ErrorMessageVO(errorfoldername, "Could not send file ejb " + bean, ex, "ERROR", file.getName());
            handleError(file, error);
            fireConnector();
        }
    }

    /**
     * Stopping the file to ejb message handler thread.
     */
    public void stopThread()
    {
        myApplicationLogger.info("Stopping messagehandler : " + serviceId);
        if (myTimer != null)
        {
            stopConnector();
            stopErrorHandler();
            myTimer.cancel();
            myApplicationLogger.info("Stopped messagehandler : " + serviceId);
        }
        myTimer = null;
    }

    /**
     * Fire of a new connector thread for reconnecting to the namining service of the app server.
     */
    void fireConnector()
    {
        if ( connector == null )
        {
            mySocketLogger.info("Starting a new connector thread since we lost connection to ejb.");
            myApplicationLogger.info("Starting a new connector thread since we lost connection to ejb.");
            connector = new Connector();
            connector.setDaemon(true);
            connector.setPriority(Thread.MIN_PRIORITY);
            connector.start();
        }
    }

   /**
    * Stops the connector thread by signalling an interrupt
     */
   private void stopConnector()
   {
       if (connector != null )
       {
           myApplicationLogger.debug("Interrupted connector and setting to null since we are connected");
           connector.interrupted = true;
           connector = null; // allow gc
       }
   }

    /**
     * Thread for reconnecting to the application server and binding to the ejb.
     *
     */
    class Connector extends Thread
    {
        boolean interrupted = false;
        public void run()
        {
            while (!interrupted)
            {
                NDC.push( serviceId );
                try
                {
                    myApplicationLogger.debug("Reconnecting in [ms] : " + reconnectDelay);
                    sleep(reconnectDelay);
                    synchronized (this)
                    {
                        bindToEJB();
                        myApplicationLogger.debug("Connector tried connecting to ejb exiting loop. isConnected : " + isConnected);
                        if ( !reconnectForever )
                        {
                            myApplicationLogger.debug( "Not reconnecting" );
                            connector = null;
                            break;
                        }
                    }
                }
                catch (InterruptedException e)
                {
                    myApplicationLogger.debug("Connector interrupted. Leaving loop.", e);
                    return;
                }
                finally
                {
                    NDC.remove();

                }
            }
        }

    }

}
