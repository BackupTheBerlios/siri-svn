package org.iris.server.errorhandler;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.iris.server.messagehandlers.InstantEmailMessageHandler;
import org.iris.server.messagehandlers.email.EmailQueueItem;
import org.iris.server.util.DateUtil;
import org.iris.server.util.config.ServerSystemConfigHandler;
import org.iris.server.util.config.Service;
import org.iris.server.util.filehelpers.FileUtils;
import org.iris.server.filehandlers.FileMessageHandler;
import org.quartz.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;

/**
 * Abstract super class to all errro handlers. Contains common methods for all error message handlers.
 *
 * User: gepo
 * Date: 2004-apr-05
 * Time: 16:37:13
 */
public class FileErrorMessageHandler  implements Job
{
    protected Date lastDateOfNotification;
    protected final String groupname = "GroupErrorHandlers";
    protected final String triggername = "crontrigger";
    protected String jobName;
    protected Logger myApplicationLogger;
    private Service service;
    SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
    Scheduler sched;

    public FileErrorMessageHandler()
    {
    }

    public FileErrorMessageHandler( Service service, Logger serviceLogger)
    {
        this.myApplicationLogger =  serviceLogger; //Logger.getLogger( FileMessageHandler.SERVICE_APPLICATION_ROOT_CATEGORY + service.getId() );
        this.service = service;
        this.jobName = service.getId();
    }

    /**
     * Determine ir enough time has elapsed between error notifications. If so the we should send a new error notification
     * alert to operations.
     *
     * @param jobExecutionContext    quartz context map with execution params needed
     * @param myApplicationLogger    logger for service node
     * @param service                the service param holder
     * @param curFiles               current error files
     * @return  true if we should send notification and false if we should not send notification
     */
    protected boolean getSendNotificationOn(JobExecutionContext jobExecutionContext, Logger myApplicationLogger, Service service, File[] curFiles)
    {
        long timeBetweenErrorNotificationsMilliseconds =  service.getNotifyeveryintervallInMilliSeconds();
        boolean sendNotification = false;
        // when did we send the last error notification? we need to ensure that timeBetweenErrorNotificationsMilliseconds
        // amount of time has elapse so we do not send a error notification every time this thread runs
        Object obj = jobExecutionContext.getJobDetail().getJobDataMap().get( "lastnotificationsent" );
        if ( obj != null )
        {
            lastDateOfNotification = (Date) obj;
            // should we send an email of notification? If last time an error notification was sent is more than
            // xxx amount of time and the error notification for this service is switched on the we should send
            // out an error notification
           if ( service.isErrornotificationOn() &&  DateUtil.isDateOlderThanXnumberOfMillisec( timeBetweenErrorNotificationsMilliseconds, lastDateOfNotification )
                && (service.getNumberoferrors() <= curFiles.length ) )
            {
                myApplicationLogger.debug( "It is ok to send an error notification since time between errors : " + timeBetweenErrorNotificationsMilliseconds +
                        " is larger than lastDateOfNotification : " + lastDateOfNotification + " and number of error >  " + curFiles.length);
                sendNotification =  true;
                lastDateOfNotification =  new Date( System.currentTimeMillis()  );
                jobExecutionContext.getJobDetail().getJobDataMap().put( "lastnotificationsent", lastDateOfNotification );
            }
            else
            {
                // we probably sent an error message to soon to send a new one again or the error notficiation
                // is not on for this service
                myApplicationLogger.debug( "Do not send any error notification - to early or not ON. lastDateOfNotification = " + lastDateOfNotification+  " timeBetweenErrorNotificationsMilliseconds : " + timeBetweenErrorNotificationsMilliseconds );
                sendNotification = false;
            }
        }
        else
        {
            // first time we got an error so create and store current time in job context
            // as the last error notificaton sent also set flag to send an error notification
            myApplicationLogger.debug("First time we handle an error we send a error notification");
            lastDateOfNotification =  new Date( System.currentTimeMillis()  );
            jobExecutionContext.getJobDetail().getJobDataMap().put( "lastnotificationsent", lastDateOfNotification );
            sendNotification = true;
        }
        return sendNotification;
    }

    /**
     * Use InstantEmailMessageHandler to send a notification mail to objects specified in the email template file of
     * this service.
     *
     * @param service
     * @param myApplicationLogger
     */
    protected void sendErrorNotification(Service service, Logger myApplicationLogger)
    {
        EmailQueueItem mailItem = new EmailQueueItem( service.getId() );
        myApplicationLogger.info( "Trying to send an email alert using file : " + service.getEmailonfailure());
        try
        {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            Document document;
            factory.setNamespaceAware(true);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource source = new InputSource(new FileReader(service.getEmailonfailure()));
            document = builder.parse(source);
            InstantEmailMessageHandler.getInstance().handleMessage(document, mailItem, myApplicationLogger );
        }
        catch (Exception ex)
        {
            myApplicationLogger.error(service.getId() + " - could not send an email alert!! Is file in place and correct? " + ex.getMessage() );
        }
    }

    /**
     * Sort file if sort is on for service.
     *
     * @param curFiles
     * @param isSortAlphaOn
     */
    protected void getSortedFiles(File[] curFiles, boolean isSortAlphaOn)
    {
        if (isSortAlphaOn)
        {
            Arrays.sort(curFiles);
        }
    }

/**
     * Method called be quartz job scheduler to execute this method on every cron intervall as specified in
     * config file for this service
     *
     * @param jobExecutionContext
     * @throws org.quartz.JobExecutionException
     */
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        service = (Service )jobExecutionContext.getJobDetail().getJobDataMap().get( "service" );
        // we are running a different thread so we need to push it again!!!
        NDC.push( service.getId() );
        try
        {
            myApplicationLogger = (Logger)jobExecutionContext.getJobDetail().getJobDataMap().get( "logger" );
            // Check if we have any errors
            File readFromFolderPath = new File(service.getErrorfoldername());
            final File[] curFiles = readFromFolderPath.listFiles();
            if ( curFiles.length == 0 )
            {
                myApplicationLogger.debug( "No files in error folder for resending" );
                return;
            }
            else
            {
                myApplicationLogger.debug("We got errors");
                // send error message notification
                if ( getSendNotificationOn(jobExecutionContext, myApplicationLogger, service, curFiles ) )
                {
                    sendErrorNotification( service, myApplicationLogger);
                }

                // handle error files
                moveErrorFilesToInfolder( service.getErrorfoldername(),service.getInfolderPath(), myApplicationLogger );
            }
        }
        catch ( Exception e)
        {}
        finally
        {
            // makes sure we remove it
            NDC.remove();
        }
    }

    /**
     * Start error handler thread using quartz scheduler framework
     */
    public  void startSchedulerThread()
    {
        try
        {
            myApplicationLogger.info( "Starting error handler thread for service : " + getErrorHandlerrCronIntervall());
            sched = schedFact.getScheduler();
            sched.start();
            JobDetail jobDetail = new JobDetail(this.jobName, Scheduler.DEFAULT_GROUP, FileErrorMessageHandler.class);
            jobDetail.getJobDataMap().put( "service",service );
            jobDetail.getJobDataMap().put( "logger",myApplicationLogger);
            jobDetail.getJobDataMap().put( "lastnotificationsent", null );
            CronTrigger trigger = new CronTrigger( this.triggername +"_" + this.jobName , this.groupname, getErrorHandlerrCronIntervall() );
            sched.scheduleJob(jobDetail, trigger);
        }
        catch (Exception ex)
        {
            myApplicationLogger.error("Scheduling error handling failed", ex);
        }
    }

    /**
     * Start error handler thread using quartz scheduler framework
     */
    public  void stopSchedulerThread()
    {
        try
        {
            myApplicationLogger.info( "Stopping scheduler for service");
            sched.shutdown(true);
        }
        catch (Exception ex)
        {
            myApplicationLogger.error("Failed shutting down scheduler", ex);
        }
    }


    /**
     * Extract cron intervall after @ symbol in inparameter
     * @return
     */
    private String getErrorHandlerrCronIntervall()                    
    {
        return ServerSystemConfigHandler.extractCronintervall( service.getCronintervall() );
    }

    /**
     * Copy files from errorfolder to infolder.
     *
     * @param fromErrorFolder
     * @param toInfolder
     * @param myApplicationLogger
     */
    protected void moveErrorFilesToInfolder( String fromErrorFolder, String toInfolder, Logger myApplicationLogger)
    {
        File readFromFolderPath = new File(fromErrorFolder);
        File[] curFiles = readFromFolderPath.listFiles();
        myApplicationLogger.info( "Trying to copy files from error folder. Size : " + curFiles.length );
        for (int fileNr = 0; fileNr < curFiles.length; fileNr++)
        {
            if ( curFiles[fileNr].length() == 0 )
            {
                return;
            }
            else if ( !curFiles[fileNr].isDirectory() )
            {
                File sendToInfolderPath =  new File(toInfolder + curFiles[fileNr].getName());
                try
                {
                    FileUtils.copy(curFiles[fileNr], sendToInfolderPath);
                    curFiles[fileNr].delete();
                }
                catch (IOException e)
                {
                    myApplicationLogger.warn( "Could not copy error file to infolder" );
                }
            }
        }

        myApplicationLogger.info( "Copied number of error files to infolder. Size : " + curFiles.length );
    }


}
