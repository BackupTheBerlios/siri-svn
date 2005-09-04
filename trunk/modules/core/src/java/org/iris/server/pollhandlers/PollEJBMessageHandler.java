package org.iris.server.pollhandlers;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.iris.server.filehandlers.FileMessageHandler;
import org.iris.server.util.config.ServicePollEJB;
import org.iris.server.util.filehelpers.FileCopyHelper;

import javax.ejb.EJBHome;
import javax.ejb.EJBMetaData;
import javax.ejb.EJBObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

public class PollEJBMessageHandler
    extends FileMessageHandler
{
    private Method methodToInvoke;
    private EJBObject bean;
    private javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
    Logger myLogger = Logger.getLogger(PollEJBMessageHandler.class.getName());
    private String serviceMethod;
    private static String messageToSend;
    private String emailtosendonfailure;
    private String emailservice;
    private String message;

    public PollEJBMessageHandler(ServicePollEJB service) throws Exception
    {
        super.init(service);

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
        message = service.getMessage();
        emailservice = service.getEmailservice();
        emailtosendonfailure = service.getEmailtosendonfailure();
        intervall = Long.parseLong(service.getPollintervall());

        setUp();

        try
        {
            methodToInvoke.invoke(bean, new String[]
                {
                ""});
        }
        catch (Exception ex)
        {
            throw new Exception("EJB could not be contacted. Failed invoiking " + jndiname, ex);
        }
    }

    public void startThread()
    {
//        myTimer = new Timer();
        //      myTimer.schedule(this, this.start, this.intervall);
    }

    /**
     * Look up ejb and do create
     *
     *
     * @throws Exception
     */
    protected void setUp() throws Exception
    {
        try
        {

            //myLogger.debug( "Trying to get remote reference...." );
            Hashtable env = new Hashtable();
            env.put(javax.naming.Context.PROVIDER_URL, getIorFromFile());
            Context ctx = new InitialContext(env);
            Object objRef = ctx.lookup(jndiname);
            EJBHome home = (EJBHome) PortableRemoteObject.narrow(objRef, EJBHome.class);
            EJBMetaData ejbMetadata = home.getEJBMetaData();
            home = (EJBHome) PortableRemoteObject.narrow(objRef, ejbMetadata.getHomeInterfaceClass());
            Class remoteClass = ejbMetadata.getRemoteInterfaceClass();
            Class classData = home.getClass();
            methodToInvoke = classData.getMethod("create", null);
            bean = (EJBObject) methodToInvoke.invoke(home, null);
            methodToInvoke = remoteClass.getMethod(this.serviceMethod, new Class[]
                             {String.class});
        }
        catch (Exception e)
        {
            throw new Exception("Could not get remote reference, failed binding to service.", e);
        }

    }

    protected void send()
    {
        try
        {
            NDC.push(getServiceId());

            methodToInvoke.invoke(bean, new String[]
                {
                messageToSend});
            myLogger.info("Polling ok!");
        }
        catch (Exception ex)
        {
            myLogger.error(
                "Polling error. Could not send message to ejb, probably communication failure. Verify if alive!");
            sendEmailOnFailure();
        }
        NDC.remove();

    }

    public void run()
    {
        readSendFiles();
    }

    synchronized private void readSendFiles()
    {
        send();
    }

    public void stopThread()
    {
        myLogger.info("Stopping messagehandler : " + serviceId);
        if (myTimer != null)
        {
            myTimer.cancel();
            myLogger.info("Stopped messagehandler : " + serviceId);
        }
        myTimer = null;
        NDC.remove();
    }

    protected void send(File file, String newName)
    {
    }

    private void sendEmailOnFailure()
    {
        myLogger.info("Sending email using file  " + emailtosendonfailure);
        try
        {
            String uniqueFileName = createUniqueFileName(getServiceId());
            FileCopyHelper.copy(emailtosendonfailure, emailservice + uniqueFileName);
        }
        catch (IOException ex)
        {
            myLogger.error("Failed copying file for sending as email", ex);
        }
    }

}
