package org.iris.server.filehandlers;

import org.iris.server.errorhandler.ErrorMessageVO;
import org.iris.server.errorhandler.ErrorMessages;
import org.iris.server.messagehandlers.email.EmailQueueHandler;
import org.iris.server.messagehandlers.email.EmailQueueItem;
import org.iris.server.util.config.ServiceFileEmail;
import org.iris.server.util.filehelpers.FileUtils;
import org.apache.log4j.NDC;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Timer;

public class FileToEmailMessegeHandler
    extends FileMessageHandler
{
    protected EmailQueueHandler myEmailHandler;
    protected int emailBatchsize = 10;
    protected String emailServer;
    protected long emailsendintervall = 10000;
    Logger myLogger = Logger.getLogger(FileToEmailMessegeHandler.class.getName());

    public FileToEmailMessegeHandler(ServiceFileEmail service) throws Exception
    {
        super.init(service);
      //  myMessageLogger = Logger.getLogger( BUSINESS + service.getId() );
        emailServer = service.getEmailServer();
        emailBatchsize = service.getEmailbatchsize();
        emailsendintervall = service.getEmailsendintervall();
        myEmailHandler = new EmailQueueHandler( errorhandleron ,errorfoldername , getServiceId(), emailBatchsize,
                myMessageLogger, myApplicationLogger);
        setUp( service );
    }

    /**
     * Starts the email handler thread
     */
    public void startEmailThread()
    {
        Timer myTimer = new Timer();
        myApplicationLogger.info("Starting emailhandler thread...");
        myTimer.schedule(myEmailHandler, 0, emailsendintervall);
    }

    /**
     * Start up emailqueue handler thread to pop emails and send on this intervall
     */
    protected void setUp( ServiceFileEmail service)
    {
        startErrorHandler(service);
        startEmailThread();
    }

    /**
     *
     * @param file
     * @param newName
     */
    protected void send(File file, String newName)
    {
        try
        {
            String message = getMessageAsString(file);
            EmailQueueItem item = new EmailQueueItem(message, errorfoldername, file.getName() , errorInfolderName );
            myEmailHandler.getEmailQueue().push(item);
            myApplicationLogger.info( "New message put on queue for email" );
        }
        catch (Exception ex)
        {
            ErrorMessageVO error = new ErrorMessageVO(errorfoldername, ErrorMessages.FILECOPYERROR,ex, "ERROR", file.getName());
            handleError(  file, error );
        }
    }

    public void run()
    {
        NDC.push( serviceId );
        //mySocketLogger.error("Logging event");   //remove
        myApplicationLogger.debug( "LOG EVENT" );
        myLogger.debug( "LOG EVENT" );
        System.out.println("Logging event");
        readSendFiles();
        NDC.remove();
    }

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
                if (backuphandleron)
                {
                    FileUtils.copy(readFromDir + curFiles[i].getName(), backupDir + fileName);
                }
                send(curFiles[i], fileName);
            }
            catch (FileNotFoundException exc)
            {
                myApplicationLogger.debug(exc.getMessage(), exc);
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


}
