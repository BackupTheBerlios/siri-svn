package org.iris.server.filehandlers;

//import net.tradevision.cargo2000.corba.servers.C2KFeeder;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.iris.server.errorhandler.ErrorMessageVO;
import org.iris.server.errorhandler.ErrorMessages;
import org.iris.server.util.config.ServiceFileCORBA;
import org.iris.server.util.filehelpers.FileUtils;

import javax.ejb.EJBObject;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class FileToCORBAMessageHandler
    extends FileMessageHandler

{
//    private C2KFeeder myCargo2000Interface;

    Class remoteClass;
    Class helperClass;

    private Method methodToInvoke;
    private EJBObject bean;

    Logger myLogger = Logger.getLogger(FileToCORBAMessageHandler.class.getName());

    private String ior_file;
    private String serviceMethod;
    java.util.Timer myTimer = new java.util.Timer();

    public FileToCORBAMessageHandler(ServiceFileCORBA service) throws Exception
    {
        super.init(service);
        //    NDC.push(name);
        myMessageLogger = Logger.getLogger( FileMessageHandler.SERVICE_BUSINESS_ROOT_CATEGORY + service.getId() );

        if (System.getProperties().get("org.omg.CORBA.ORBClass") == null)
        {
            System.getProperties().put("org.omg.CORBA.ORBClass", "com.inprise.vbroker.orb.ORB");
        }

        if (System.getProperties().get("org.omg.CORBA.ORBSingletonClass") == null)
        {
            System.getProperties().put("org.omg.CORBA.ORBSingletonClass", "com.inprise.vbroker.orb.ORB");
        }
        ior_file = service.getNameService();
        jndiname = service.getServiceName();
        serviceMethod = service.getServiceMethod();

        /*        try
                {
             remoteClass = Class.forName("net.tradevision.cargo2000.corba.servers.C2KFeeder myCargo2000Interface");
                    helperClass = Class.forName("net.tradevision.cargo2000.corba.servers.C2KFeederHelper");
                }
                catch (ClassNotFoundException ex)
                {
                    myApplicationLogger.fatal( "",ex );
                }
         */try
        {
            setUp();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    protected void setUp() throws Exception
    {

        if (System.getProperties().get("org.omg.CORBA.ORBClass") == null)
        {
            System.getProperties().put("org.omg.CORBA.ORBClass", "com.inprise.vbroker.orb.ORB");
        }

        if (System.getProperties().get("org.omg.CORBA.ORBSingletonClass") == null)
        {
            System.getProperties().put("org.omg.CORBA.ORBSingletonClass", "com.inprise.vbroker.orb.ORB");
        }

        try
        {
            org.omg.CORBA.ORB orb = null;
            if (orb == null)
            {
                orb = org.omg.CORBA.ORB.init( (String[])null, System.getProperties());

            }

 //           myCargo2000Interface = net.tradevision.cargo2000.corba.servers.C2KFeederHelper.bind(orb,
 //                                  "/" + jndiname + "_poa", jndiname.getBytes());

//            Method methodToInvoke = helperClass.getMethod( "helperClass", new Class[] { org.omg.CORBA.ORB.class, String.class, Byte[].class   } );
            //          methodToInvoke.invoke( methodToInvoke, new Object[] { orb, "/" + jndiname + "_poa", jndiname.getBytes() }  );
            //          bean =  (EJBObject) methodToInvoke.invoke( home, null );
            //        methodToInvoke  = remoteClass.getMethod( this.serviceMethod , new Class[] {String.class} );

        }
        catch (Exception ex)
        {
            myLogger.fatal("CORBA service could not be contacted. Is corba service " + jndiname + " alive");

            throw new Exception("Could not contact or establish connection with CORBA server");

        }

        /*        try
                 {
                     Hashtable env = new Hashtable();
                     env.put(javax.naming.Context.PROVIDER_URL, getIorFromFile());
                     Context ctx = new InitialContext(env);
                     Object objRef = ctx.lookup( jndiname );
                     EJBHome  home = ( EJBHome ) PortableRemoteObject.narrow( objRef, EJBHome.class   );
                     EJBMetaData ejbMetadata  = home.getEJBMetaData();
                     home = (EJBHome) PortableRemoteObject.narrow( objRef, ejbMetadata.getHomeInterfaceClass() );
                     Class remoteClass = ejbMetadata.getRemoteInterfaceClass();
                     Class classData = home.getClass();
                     methodToInvoke = classData.getMethod( "create", null );
                     bean =  (EJBObject) methodToInvoke.invoke( home, null );
                     methodToInvoke  = remoteClass.getMethod( this.serviceMethod , new Class[] {String.class} );
                 }
                 catch (Exception e)
                 {
                     myApplicationLogger.fatal("Could not get remote reference ", e);
                 }
         */
    }

    protected void send(File file, String newName)
    {
        try
        {
            String message = getMessageAsString(file);
     //       myCargo2000Interface.handleMessage(message);
            //methodToInvoke.invoke( bean, new String[] { message }  );
            myLogger.info("New message sent");
        }
        catch (Exception ex)
        {
            if (errorhandleron)
            {
                saveFileToErrorFolder(file,  new ErrorMessageVO(errorfoldername, ErrorMessages.FILECOPYERROR,
                    ex, "ERROR", file.getName()));
            }
        }
    }

    public void run()
    {
        readSendFiles();
    }

    synchronized private void readSendFiles()
    {
        NDC.push(getServiceId());

        readFrom = new File(readFromDir);
        File[] curFiles = readFrom.listFiles();
        int fileCollectionSize = getFileCollectionSize(curFiles);


        if ( isMessagesReceived(curFiles) ) return;


        if (sortalpha)
        {
            Arrays.sort(curFiles);
        }

        FileReader filin = null;

        String fileName = "";
        for (int i = 0; i < fileCollectionSize; i++)
        {
            fileName = curFiles[i].getName();
            try
            {
                if (createuniquename)
                {
                    myLogger.debug("Creating unique name");
                    fileName = createUniqueFileName(curFiles[i].getName());
                }

                if (backuphandleron)
                {
                    myLogger.debug("Backing up file  " + backupDir + fileName);
                    FileUtils.copy(readFromDir + curFiles[i].getName(), backupDir + fileName);
                }

                send(curFiles[i], fileName);
            }
            catch (Exception ex)
            {
                myLogger.info("Exception caught" + ex.getMessage(), ex);
            }

            if (filin != null)
            {
                try
                {
                    filin.close();
                }
                catch (IOException e)
                {
                    ;
                }
            }
            curFiles[i].delete();
        }

        NDC.remove();

    }

}
