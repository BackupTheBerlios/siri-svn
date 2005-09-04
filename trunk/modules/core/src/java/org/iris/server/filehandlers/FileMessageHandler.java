package org.iris.server.filehandlers;

import org.apache.log4j.*;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.iris.server.errorhandler.ErrorMessageVO;
import org.iris.server.errorhandler.FileErrorMessageHandler;
import org.iris.server.messagehandlers.InstantEmailMessageHandler;
import org.iris.server.messagehandlers.email.EmailQueueItem;
import org.iris.server.statistics.StatisticsHandler;
import org.iris.server.statistics.StatisticsVO;
import org.iris.server.util.DateUtil;
import org.iris.server.util.config.Service;
import org.iris.server.util.filehelpers.FileUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class FileMessageHandler
        extends TimerTask {

    private org.iris.server.util.config.Service service;

    public static String SERVICE_APPLICATION_ROOT_CATEGORY = "application.";
    public static String SERVICE_BUSINESS_ROOT_CATEGORY = "business.";
    // This logger will log to a sockethubappender
    Logger mySocketLogger = Logger.getLogger( "socketlogger" );
    // This logger will log successfully sent messages
    protected Logger myMessageLogger;
    // This logger will log all application messages for this service
    protected Logger myApplicationLogger;

    // Statistics
    protected long dumpStatisticsRecordEveryMS = 20000;
    protected StatisticsHandler myStatisticsHandler = StatisticsHandler.getInstance();
    protected int stattCounter = 0;
    protected int filesQueued;
    //

    //
    protected String readFromDir;
    protected boolean sendinfolderchunkson;
    protected int infolderchunksize;
    protected int infolderchunkthreshold;


    // Used to notify of incativity in service node
    protected Date lastDateInfilesNotEmpty = new Date( System.currentTimeMillis() );
    protected Date lastDateOfNotification;



    protected String archivefoldername;

    protected String jndiname;
    protected String reconnectToObjectPolicy;
    protected String backupDir;
    protected String serviceId;
    protected File readFrom;
    protected long start;
    protected long intervall;
    protected String ior_file;
    protected SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HHmmssSS");
    protected boolean skipfirstblankline;
    protected String cronintervall;
    protected String errorfoldername;
    protected String errorInfolderName;
    protected long numberOfErrorMessages;
    protected long resendintervall;
    protected boolean sortonresend;
    protected boolean errorhandleron;
    protected boolean backuphandleron;
    protected boolean transformon;
    protected boolean isTransformOn;
    protected HashMap transformMatrix;
    protected boolean sortalpha;
    protected long inactivitytime;

    protected boolean sendchunkson;
    protected int chunksize;
    protected int chunkthreshold;

    protected boolean createuniquename;

    // Every handler holds a timer reference so it can be scheduled for its operations - stop/start
    public Timer myTimer;

    protected String primaryEmailserver;


    public final static int DOESNOTEXIST = 0;
    public final static int NOTSTARTED = 3;
    public final static int RUNNING = 9;
    public final static int STOPPED = 12;
    protected int serviceStatus = DOESNOTEXIST;


    protected FileErrorMessageHandler errorHandler;

    protected List filesToSort = new ArrayList();

    static int counter = 0;


    public int getServiceStatus()
    {
        return serviceStatus;
    }

    public void setServiceStatus(int serviceStatus)
    {
        this.serviceStatus = serviceStatus;
    }

    abstract protected void send(File file, String newName);

    /**
     *  Used by filehandlers to log an error message in the info folder for error and also to store the
     * file failed to be sent in the error folder. The error files will be sent again by the scheduled
     * error handler (global error service).
     *
     * @param file
     * @param errormessageVO
     */
    protected void saveFileToErrorFolder(File file,  ErrorMessageVO errormessageVO)
    {
        try
        {
            FileUtils.copy(readFromDir + file.getName(), errorfoldername  + file.getName());
            mySocketLogger.error("Storing message in error folder");
        }
        catch (IOException ex1)
        {
            myApplicationLogger.error("Failed storing file in error info folder : " + errorfoldername + file.getName());
        }
    }

    /**
     * Saves a file with status of errors for the last batch of files handled.
     *
     */
    protected void handleStatusFileForErrors()
    {
        if ( numberOfErrorMessages == 0 ) return;
        try
        {
            FileUtils.createFile( "timestamp=" + String.valueOf( System.currentTimeMillis()) +
                    "\n" + "numberOfErrors=" + numberOfErrorMessages ,errorInfolderName  + "status.txt");
        }
        catch (IOException ex1)
        {
            myApplicationLogger.error("Failed status file in error info folder ");
        }
        numberOfErrorMessages=0;
    }







    /**
     * Called by all filehandlers to set common attributes from settings.
     *
     * @param service holds settings
     */
    protected void init(Service service)
    {
        //Thread.currentThread().setName(service.getId());
        this.service = service;

        serviceId = service.getId();
        readFromDir = service.getInfolderPath();
        sendinfolderchunkson = service.isSendinfolderchunksOn();
        infolderchunksize = service.getInFolderchunkSize();

        inactivitytime = service.getMaxInactivitytime();

        archivefoldername = service.getArchivefoldername();

        infolderchunkthreshold = service.getInFolderchunkThreshold();

        backupDir = service.getBackupfolder();
        intervall = service.getInFolderIntervall();
        skipfirstblankline = service.isSkipfirstblankLine();
        errorfoldername = service.getErrorfoldername();

        errorhandleron = service.isErrorhandlerOn();
        errorInfolderName =  service.getErrorInfolderName();

        resendintervall = service.getResendIntervall();
        sortonresend = service.isSortOnResend();


        backuphandleron = service.isBackuphandlerOn();
        transformon = service.isIsTransformOn();
        if (transformon)
        {
            transformMatrix = service.getTransformFiles();

        }
        createuniquename = service.isCreateUniqueName();

        sortalpha = service.isSortAlphaOn();

        cronintervall =  service.getCronintervall();

        // set loggers
        setApplicationLogger(service);

        setMessageLogger(service);




    }

    /**
     * Initialize the application logger. This logger will log all service activities according
     * to config settings in Iris config file. The application logger is responsible for logging
     * errors and information about this service.
     *
     * @param service   config settings goes here
     */
    private void setApplicationLogger(Service service)
    {
        try
        {
            Layout layoutPattern = new PatternLayout(service.getApplicationlogpattern());
            //ServiceFilter filter = new ServiceFilter(APPLICATION + service.getId() );
            if ( service.getApplicationappendertype().equalsIgnoreCase( "RollingFileAppender" ) )
            {
                RollingFileAppender appender = new RollingFileAppender(layoutPattern, service.getApplicationlogfolder() + "applicationlog.txt");
                //appender.addFilter(filter);
                appender.setMaxBackupIndex(  service.getApplicationmaxBackupIndex() );
                appender.setMaxFileSize( service.getApplicationappendermaxfilesize() );
                myApplicationLogger = Logger.getLogger( SERVICE_APPLICATION_ROOT_CATEGORY + service.getId() );
                myApplicationLogger.addAppender(appender);
                myApplicationLogger.setLevel( Level.toLevel( service.getApplicationLogLevel() ) );
                myApplicationLogger.setAdditivity(  false );

            }
            else if ( service.getApplicationappendertype().equalsIgnoreCase( "DailyRollingFileAppender" ) )
            {
                DailyRollingFileAppender dailyAppender = new DailyRollingFileAppender(layoutPattern, service.getApplicationlogfolder() + "applicationlog.txt", service.getApplicationappenderrollingschedule());
                //dailyAppender.addFilter(filter);
                myApplicationLogger = Logger.getLogger( SERVICE_APPLICATION_ROOT_CATEGORY + service.getId() );
                myApplicationLogger.addAppender(dailyAppender);
                myApplicationLogger.setLevel( Level.toLevel( service.getApplicationLogLevel() ) );
                myApplicationLogger.setAdditivity( false );  //if logger business.other.level logs we do not want it to log there
            }
        }
        catch (IOException ex)
        {
            mySocketLogger.error("Parameter parse error from xml config file. Logging parametes are incorrect for service  " + getServiceId(), ex);
        }
        //myApplicationLogger = Logger.getLogger( FileMover.SERVICE_APPLICATION_ROOT_CATEGORY + service.getId() );
    }

    /**
     * Initialize the message logger. This logger will log all service messages sent according
     * to config settings in Iris config file. Logs actual message sent.
     *
     * @param service   config settings goes here
     */
    private void setMessageLogger( Service service)
    {
        try
        {
            Layout layoutPattern = new PatternLayout(service.getMessagelogpattern());
            //ServiceFilter filter = new ServiceFilter(getName());
            if ( service.getMessageappendertype().equalsIgnoreCase( "RollingFileAppender" ) )
            {
                RollingFileAppender appender = new RollingFileAppender(layoutPattern, service.getMessagelogfolder() + "messagelog.txt");
                //appender.addFilter(filter);
                appender.setMaxBackupIndex(  service.getMessagemaxBackupIndex() );
                appender.setMaxFileSize( service.getMessageappendermaxfilesize() );
                Logger businessRoot = Logger.getLogger( SERVICE_BUSINESS_ROOT_CATEGORY + service.getId() );
                businessRoot.addAppender(appender);
                businessRoot.setLevel( Level.toLevel( service.getMessageLogLevel() ) );
                // we do not want the log messages on this logger show up
                businessRoot.setAdditivity(  false );
            }
            else if ( service.getMessageappendertype().equalsIgnoreCase( "DailyRollingFileAppender" ) )
            {
                DailyRollingFileAppender dailyAppender =  new DailyRollingFileAppender(layoutPattern, service.getMessagelogfolder() + "messagelog.txt", service.getMessageappenderrollingschedule());
                //dailyAppender.addFilter(filter);
                Logger businessRoot = Logger.getLogger( SERVICE_BUSINESS_ROOT_CATEGORY + service.getId() );
                businessRoot.addAppender(dailyAppender);
                businessRoot.setLevel( Level.toLevel( service.getMessageLogLevel() ) );
                businessRoot.setAdditivity( false );  //if logger business.other.level logs we do not want it to log there
            }
        }
        catch (IOException ex)
        {
            mySocketLogger.error("Parameter parse error from xml config file. Logging parametes are incorrect for service  " + getServiceId(), ex);
        }
        myMessageLogger = Logger.getLogger( SERVICE_BUSINESS_ROOT_CATEGORY + service.getId() );
    }

    /**
     * Converts file to string.
     *
     * @param file
     * @return
     */
    protected String getMessageAsString(File file)
    {
        FileReader filereader = null;
        String returnval = null;
        BufferedReader inputBuffer =  null;
        try
        {
            filereader =  new FileReader(file);
            inputBuffer = new BufferedReader(filereader);
            StringBuffer buffer = new StringBuffer();
            String line = inputBuffer.readLine();
            if (skipfirstblankline)
            {
                line = inputBuffer.readLine();
            }

            while (line != null)
            {
                buffer.append(line);
                buffer.append("\n");

                line = inputBuffer.readLine();
            }
            returnval = buffer.toString();
            filereader.close();
        }
        catch (Exception pce)
        {
            mySocketLogger.warn("File could not be read, probably the process copying files to the infolder is to slow.");
        }
        finally
        {
            try
            {
                if ( filereader != null )
                    filereader.close();
            }
            catch (IOException e)
            {
                try
                {
                    if ( inputBuffer != null )
                        inputBuffer.close();
                }
                catch (IOException e1)
                {
                }
            }
        }

        return returnval;
    }

    protected String createUniqueFileName(String filename)
    {
        SecureRandom seeder;
        StringBuffer result = new StringBuffer(filename);
        try
        {
            seeder = new SecureRandom();
            int i = seeder.nextInt();
            result.append(System.currentTimeMillis()).append("_");
            result.append(Math.abs(i)).append(".txt");
        }
        catch (Exception exception)
        {
        }

        return result.toString();

    }

    /**
     * The ior file holds a reference to the name service where we want to look up the ejbs, queues etc
     * @return
     * @throws IOException
     * @throws Exception
     */
    protected String getIorFromFile() throws IOException,Exception
    {
        return FileUtils.getIORFileContent( ior_file );
    }

    protected void sortFilesAlphabetically()
    {
        Collections.sort(filesToSort);
    }

    public long getStart()
    {
        return start;
    }

    public long getIntervall()
    {
        return intervall;
    }

    public String getServiceId()
    {
        return serviceId;
    }

    public Timer getMyTimer()
    {
        return myTimer;
    }

    /**
     * A timer is asscociated to every messagehandler. It knows how often the service thread should run
     *
     * @param myTimer
     */
    public void setTimer(Timer myTimer)
    {
        this.myTimer = myTimer;
    }

    public org.iris.server.util.config.Service getService()
    {
        return service;
    }

    public void setService(org.iris.server.util.config.Service service)
    {
        this.service = service;
    }

    /**
     * If sendinfolderchunkson is on then we should not try sending the whole colletion at once. We will
     * then instead try sedning files in batches using  infolderchunkthreshold as batch size.
     * If file colletion is empty return null. If not return the collection size unless sendinfolderchunkson
     * is on, then
     *
     * return  infolderchunkthreshold if file colletion is greater than infolderchunkthreshold.
     *
     * @param curFiles  the colletion of files currently residing in infolder
     * @return          size of colletion we should send
     */
    public int getFileCollectionSize(File[] curFiles)
    {
        if (curFiles == null)
        {
            return 0;
        }
        int size = curFiles.length;
        if (sendinfolderchunkson && (curFiles.length > infolderchunkthreshold))
        {
            size = infolderchunkthreshold;
        }
        return size;
    }


    /**
     * Determine ir enough time has elapsed between inactivity notifications from incativity in
     * infolder. If so the we should send a new inactivity notification alert.
     */
    protected boolean sendNotificationIfIncativytTimeExceeded()
    {
        boolean sendNotification = false;
        // when did we send the last error notification? we need to ensure that xxx amount of time has elapse
        // so we do not send a notification every time this thread runs
        if ( lastDateOfNotification != null )
        {
            // should we send an email of notification? If last time an error notification was sent is more than
            // xxx amount of time and the error notification for this service is switched on the we should send
            // out an error notification
            if ( service.isInacativityHandlerOn() &&  DateUtil.isDateOlderThanXnumberOfMillisec( service.getMaxInactivitytime(), lastDateOfNotification ))
            {
                sendNotification =  true;
                lastDateOfNotification =  new java.util.Date( System.currentTimeMillis()  );
            }
            else
            {
                // we probably sent an error message to soon to send a new one again or the error notficiation
                // is not on for this service
                //myApplicationLogger.debug( "Do not send any INACTIVITY notification - to early or not ON. lastDateOfNotification = " + lastDateOfNotification  );
                sendNotification = false;
            }
        }
        else
        {
            lastDateOfNotification =  new java.util.Date( System.currentTimeMillis()  );
            sendNotification = false;
        }
        return sendNotification;
    }

    /**
     * Logs a statement to inform nothing has happend in a certain amount of time - incativity time - and send an email
     * if it has been setup to do so.
     *
     * @param curFiles
     * @return
     */
    protected boolean isMessagesReceived( File[] curFiles )
    {
        if (curFiles == null || curFiles.length == 0  )
        {
            if ( sendNotificationIfIncativytTimeExceeded() )
            {
                myApplicationLogger.info("Service idle for to long according to conf parameters. Preparing to send email.");
                sendInactivityNotification();
            }
            return false;
        }
        else
        {
            lastDateOfNotification =  null;
            return true;
        }
    }

    /**
     * Use InstantEmailMessageHandler to send a notification mail to objects specified in the email template file of
     * this service.
     *
     */
    protected void sendInactivityNotification()
    {
        EmailQueueItem mailItem = new EmailQueueItem( service.getId() );
        myApplicationLogger.debug( "Trying to send an email alert using file : " + service.getEmailonincativity() );
        try
        {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            Document document;
            factory.setNamespaceAware(true);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource source = new InputSource(new FileReader(service.getEmailonincativity()));
            document = builder.parse(source);
            myApplicationLogger.debug( "Calling emailhandler to process and send email" );
            InstantEmailMessageHandler.getInstance().handleMessage(document, mailItem, myApplicationLogger);
        }
        catch (Exception ex)
        {
            myApplicationLogger.error( "Could not send an email alert " + ex.getMessage(), ex );
        }
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString( this );
    }

    /* public String getBackupDir()
    {
    return backupDir;
    }*/

    public String getErrorfoldername()
    {
        return errorfoldername;
    }

    public String getReadFromDir() {
        return readFromDir;
    }

    public String getArchivefoldername() {
        return archivefoldername;
    }


    /**
     *  Logs a statistic record on how many files are processed when this thread is run. It should only log if
     * we pass dumpStatisticsRecordEveryMS, else a counter is increased. The stattCounter keeps track on how many
     * times we have run the thread without doing a log dump.
     */
    protected void logStatistics(  int fileCollectionSize)
    {
        filesQueued = filesQueued + fileCollectionSize;
        if ( stattCounter * intervall > dumpStatisticsRecordEveryMS )
        {
            myApplicationLogger.debug( "Creating log statt item and pushin on queue setting stattCounter to 0. Size : "  + filesQueued);
            StatisticsVO item = new StatisticsVO();
            item.setServiceName( this.serviceId );
            item.setTimestamp( System.currentTimeMillis() );
            item.setValue( filesQueued );
            myStatisticsHandler.push( item );
            //myApplicationLogger.debug( myStatisticsHandler.toString() );
            filesQueued = 0;
            stattCounter = 0;
        }
        else
        {
            stattCounter++;
        }
    }

    /**
     * Save contents of file to the message log so we can search it later if necessairy.
     *
     * @param ex    the exception raised when we failed sending the contents of the file to the receiver
     * @param file  messagelog for storing information about message fialed to be sent
     */
    private void saveToLogOnFailure(Exception ex, File file)
    {
        try
        {
            myMessageLogger.info( "Failed sending file " + ex.getMessage() + "\n" + FileUtils.getMessageContent( new File( readFromDir + file.getName()), false) );
        }
        catch (Exception e)
        {
        }
    }

    /**
     * If message sent sucessfully store it in the messagelog for easy searching.
     *
     * @param file
     */
    private void saveToMessageLogOnSuccess( File file)
    {
        // send output to socket to inform client
        mySocketLogger.info( "Sucessfully sent message : " +  FileUtils.getMessageContent( file,false ) );
        // log to file
        myMessageLogger.info( "Sucessfully sent message : " +  FileUtils.getMessageContent( file,false ) );
    }

    /**
     * Method executed if backup on and message sent sucessfully, if not sucessfully sent store in error folder
     * instead (if error handler is on it should copy files to infolder for resending).
     *
     * @param curFile
     * @param fileNameOut
     * @throws IOException
     */
    protected void handleBackup(File curFile,  String fileNameOut)
            throws IOException
    {
        if (backuphandleron)
        {
            FileUtils.copy(readFromDir + curFile.getName(), backupDir + fileNameOut);
        }
        saveToMessageLogOnSuccess( curFile );
    }

    /**
     * Save the current file to the error folder of the service and increas errorcounter.
     *
     * @param curFile
     * @param error
     */
    protected void handleError(File curFile, ErrorMessageVO error)
    {
        numberOfErrorMessages++;
        if ( errorhandleron )
        {
            saveFileToErrorFolder(curFile, error);
        }
        saveToLogOnFailure((Exception)error.getThrowable(), curFile);
    }


    /**
     * Sort files in the collection
     *
     * @param curFiles    the file collection to be sorted
     */
    protected void getSortedFiles(File[] curFiles)
    {
        if (sortalpha)
        {
            Arrays.sort(curFiles);
        }
    }

    /**
     * Start error handler if configured for this service. Error handler is a cron
     * scheduled job taking care of copying error files back to the in folder for
     * the service.
     *
     * @param service  the iris service which has declared it wants error handling
     */
    protected void startErrorHandler(Service service)
    {
        if ( errorhandleron )
        {
            errorHandler = new FileErrorMessageHandler( service , myApplicationLogger);
            errorHandler.startSchedulerThread();
        }
    }

    /**
     * Stop error handler if configured for this service. Error handler is a cron scheduled job.
     *
     */
    protected void stopErrorHandler()
    {
        if ( errorhandleron )
        {
            errorHandler.stopSchedulerThread();
        }
    }


    public void stopThread()
    {
        myApplicationLogger.info("Stopping messagehandler : " + serviceId);
        if (myTimer != null)
        {
            stopErrorHandler();
            myTimer.cancel();
            myApplicationLogger.info("Stopped messagehandler : " + serviceId);
        }
        myTimer = null;
    }
}
