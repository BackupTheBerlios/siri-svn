package org.iris.server.filehandlers;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.iris.server.errorhandler.ErrorMessageVO;
import org.iris.server.errorhandler.ErrorMessages;
import org.iris.server.util.config.ServiceFileQueue;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.util.Arrays;
import java.util.Hashtable;

public class FileToQueueMessageHandler
        extends FileMessageHandler

{
    private QueueConnectionFactory myFactory;
    private QueueConnection myConnection;
    private QueueSession mySession;
    private Queue myQueue;
    private QueueSender mySender;
    private String factoryName;
    java.util.Timer myTimer;
    Logger myApplicationLogger;

    public FileToQueueMessageHandler(ServiceFileQueue service)
    {
        super.init(service);
//    NDC.push(name);
      //  myMessageLogger = Logger.getLogger( BUSINESS + service.getId() );

        if (System.getProperties().get("org.omg.CORBA.ORBClass") == null)
        {
            System.getProperties().put("org.omg.CORBA.ORBClass", "com.inprise.vbroker.orb.ORB");
        }

        if (System.getProperties().get("org.omg.CORBA.ORBSingletonClass") == null)
        {
            System.getProperties().put("org.omg.CORBA.ORBSingletonClass", "com.inprise.vbroker.orb.ORB");
        }
        ior_file = service.getNameService();
        jndiname = service.getQueueName();
        factoryName = service.getFactoryname();

        myApplicationLogger.debug(serviceId + " is starting to handle messages. Configuration for service:");
        myApplicationLogger.info(service);

        setUp();

    }

    private void setUp()
    {
        try
        {
            myApplicationLogger.debug("Creating context");
            Hashtable env = new Hashtable();
            env.put(javax.naming.Context.PROVIDER_URL, getIorFromFile());
            Context cont = new InitialContext(env);

            myApplicationLogger.debug("Looking up jndi : " + factoryName);
            myFactory = (QueueConnectionFactory) cont.lookup(factoryName);
            myConnection = myFactory.createQueueConnection();
            mySession = myConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            myApplicationLogger.debug("Looking up jndi : " + jndiname);
            myQueue = (Queue) cont.lookup(jndiname);
            mySender = mySession.createSender(myQueue);
            myApplicationLogger.debug("Look up and create was ok. Message bean created for queue : " + jndiname);
        }
        catch (NamingException ex)
        {
            myApplicationLogger.fatal("Could not look up : " + jndiname, ex);
        }
        catch (Exception ex)
        {
            myApplicationLogger.fatal("Could not create message bean for queue: " + jndiname, ex);
        }
    }

    synchronized private void readSendFiles()
    {
        readFrom = new File(readFromDir);
        File[] curFiles = readFrom.listFiles();
        int fileCollectionSize = getFileCollectionSize(curFiles);

        if (curFiles == null || curFiles.length == 0)
        {
            myApplicationLogger.debug("no files");
            NDC.remove();
            return;
        }

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
                myApplicationLogger.info("Exception caught" + ex.getMessage(), ex);
            }
            curFiles[i].delete();
        }
    }

    protected void send(File file, String newName)
    {
        try
        {
            String message = getMessageAsString(file);
            TextMessage tcxt = mySession.createTextMessage();
            tcxt.setText(message);
            mySender.send(tcxt);
            handleBackup(file, newName);
        }
        catch (Exception ex)
        {
            ErrorMessageVO error = new ErrorMessageVO(errorfoldername, ErrorMessages.FILECOPYERROR, ex, "ERROR", file.getName());
            handleError( file, error );
        }
    }


    public void run()
    {
        readSendFiles();
    }



}