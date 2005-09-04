package org.iris.server.archivehandler;

/**
 * Responsible for archiving files on a scheduled basis. Uses JCrontab for scheduling.
 *
 * Information about JCrontab follows (http://www.jcrontab.org):
 *
 * Cron Expressions
 * Cron-Expressions are used to configure instances of CronTrigger. Cron-Expressions are
 * strings that are actually made up of six sub-expressions, that describe individual details
 * of the schedule. These sub-expression are separated with white-space, and represent:
    * Seconds
    * Minutes
    * Hours
    * Day-of-Month
    * Month
    * Day-of-Week
 * An example of a complete cron-expression is the string "0 0 12 ? * WED" - which means
 * "every Wednesday at 12:00 pm".
 *
 * Individual sub-expressions can contain ranges and/or lists. For example, the day of week
 * field in the previous (which reads "WED") example could be replaces with "MON-FRI", "MON, WED, FRI",
 * or even "MON-WED,SAT".
 *
 * Wild-cards (the '*' character) can be used to say "every" possible value of this field. Therefore
 * the '*' character in the "Month" field of the previous example simply means "every month". A '*' in
 * the Day-Of-Week field would obviously mean "every day of the week".
 *
 * All of the fields have a set of valid values that can be specified. These values should be fairly
 * obvious - such as the numbers 0 to 59 for seconds and minutes, and the values 0 to 23 for hours.
 * Day-of-Month can be any value 0-31, but you need to be careful about how many days are in a given
 * month! Months can be specified as values between 0 and 11, or by using the strings JAN, FEB, MAR, APR,
 * MAY, JUN, JUL, AUG, SEP, OCT, NOV and DEC. Days-of-Week can be specified as vaules between 1 and 7
 * (1 = Sunday) or by using the strings SUN, MON, TUE, WED, THU, FRI and SAT.
 *
 * The '/' character can be used to specify increments to values. For example, if you put '0/15' in the
 * Minutes field, it means 'every 15 minutes, starting at minute zero'. If you used '3/20' in the Minutes
 * field, it would mean 'every 20 minutes during the hour, starting at minute three' - or in other words
 * it is the same as specifying '3,23,43' in the Minutes field.
 *
 * The '?' character is allowed for the day-of-month and day-of-week fields. It is used to specify "no
 * specific value". This is useful when you need to specify something in one of the two fields, but not
 * the other. See the examples below (and CronTrigger JavaDOC) for clarification.
 *
 * The 'L' character is allowed for the day-of-month and day-of-week fields. This character is short-hand
 * for "last", but it has different meaning in each of the two fields. For example, the value "L" in the
 * day-of-month field means "the last day of the month" - day 31 for January, day 28 for February on non-leap
 * years. If used in the day-of-week field by itself, it simply means "7" or "SAT". But if used in the
 * day-of-week field after another value, it means "the last xxx day of the month" - for example "6L" or
 * "FRIL" both mean "the last friday of the month". When using the 'L' option, it is important not to
 * specify lists, or ranges of values, as you'll get confusing results.
 *
 * Here are a few more examples of expressions and their meanings - you can find even more in the JavaDOC
 * for CronTrigger
 *
 * CronTrigger Example 1 - an expression to create a trigger that simply fires every 5 minutes
 *   "0 0/5 * * * ?"
 *
 * CronTrigger Example 2 - an expression to create a trigger that fires every 5 minutes, at 10 seconds
 * after the minute (i.e. 10:00:10 am, 10:05:10 am, etc.).
 *  "10 0/5 * * * ?"
 *
 * CronTrigger Example 3 - an expression to create a trigger that fires at 10:30, 11:30, 12:30, and 13:30,
 * on every Wednesday and Friday.
 *  "0 30 10-13 ? * WED,FRI"
 *
 * CronTrigger Example 4 - an expression to create a trigger that fires every half hour between the hours
 * of 8 am and 10 am on the 5th and 20th of every month. Note that the trigger will NOT fire at 10:00 am,
 * just at 8:00, 8:30, 9:00 and 9:30
 *
 * "0 0/30 8-9 * 5,20 ?"
 *
 *
 * Note that some scheduling requirements are too complicated to express with a single trigger -
 * such as "every 5 minutes between 9:00 am and 10:00 am, and every 20 minutes between 1:00 pm
 * and 10:00 pm". The solution in this scenario is to simply create two triggers, and register
 * both of them to run the same job.
 *
 * @author Georges Polyzois
 * @version 1.0
 */

import org.apache.log4j.Logger;
import org.quartz.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CrontriggerArchiveHandler
    implements Job
{                
    static Logger myLogger = Logger.getLogger(CrontriggerArchiveHandler.class.getName());
    private static ArchiveVO[] myArchiveVOs;
    private static String myCronparameters;
    private static String myJobName;
    private static String myTriggerName;
    private static String myGroupName;
    // format of zip file created uses this date format
    protected SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HHmmssSS");

    public CrontriggerArchiveHandler()
    {
    }


    /**
     * Starts up the the thread using cron trigger parameters
     *
     * @param backupFolders
     * @param jobName
     * @param groupname
     * @param triggername
     * @param cronparams
     */
    public static void startSchedulerThread(ArrayList backupFolders, String jobName, String groupname,
        String triggername, String cronparams)
    {
        try
        {
            myJobName = jobName;
            myTriggerName = triggername;
            myCronparameters = cronparams;
            myGroupName = groupname;

            myArchiveVOs = new ArchiveVO[backupFolders.size()];
            backupFolders.toArray(myArchiveVOs);

            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            sched.start();

            JobDetail jobDetail = new JobDetail(myJobName, sched.DEFAULT_GROUP, CrontriggerArchiveHandler.class);
            jobDetail.getJobDataMap().put( "backupfolders", myArchiveVOs  );
            CronTrigger trigger = new CronTrigger(myTriggerName, myGroupName, myCronparameters);
            sched.scheduleJob(jobDetail, trigger);
        }
        catch (Exception ex)
        {
            myLogger.error("Scheduling archiving failed", ex);
        }

    }

    /**
     * Called by cron scheduleer to execute job
     *
     * @param context
     */
    public void execute(JobExecutionContext context)
    {
        myLogger.info("Archiving backup folders : " + new Date(System.currentTimeMillis()));
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        myArchiveVOs = (ArchiveVO[])dataMap.get("backupfolders");
        if (myArchiveVOs != null)
        {
            for (int i = 0; i < myArchiveVOs.length; i++)
            {
                File readFromFolder = new File(myArchiveVOs[i].getBackupFromFolderPath());
                File[] curFiles = readFromFolder.listFiles();
                if (curFiles == null || curFiles.length == 0)
                {
                    myLogger.debug("No files for archiving in folder : " + myArchiveVOs[i].getBackupFromFolderPath());
                    return;
                }
                else
                {
                    try
                    {
                        createZipFile(myArchiveVOs[i].getArchiveToFolderPath() + getDate() + "_" +
                            myArchiveVOs[i].getServiceName() + ".zip", curFiles);
                        deleteFiles(curFiles);
                    }
                    catch (IOException ex)
                    {
                        myLogger.error("Could not archive folder..." + myArchiveVOs[i].getBackupFromFolderPath());
                    }
                }
            }
        }
    }

    /**
     * Creates a zip file for a collection of files
     *
     * @param zipFilePathName
     * @param files
     * @throws IOException
     */
    public synchronized void createZipFile(String zipFilePathName, File[] files) throws IOException
    {
        myLogger.info("Creating archive for : " + zipFilePathName);
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipFilePathName));
        try
        {
            byte[] buffer = new byte[1024];
            int bytesRead;
            for (int i = 0; i < files.length; i++)
            {
                File fileName = files[i];
                try
                {
                    FileInputStream file = new FileInputStream(fileName);
                    try
                    {
                        ZipEntry entry = new ZipEntry(fileName.getName());
                        zip.putNextEntry(entry);
                        while ( (bytesRead = file.read(buffer)) != -1)
                        {
                            zip.write(buffer, 0, bytesRead);
                        }
                    }
                    catch (Exception ex)
                    {
                        myLogger.error("Failed creating zip entry", ex);
                    }
                    finally
                    {
                        file.close();
                    }
                }
                catch (IOException ex)
                {
                    myLogger.error("Failed reading file for archiving", ex);
                }
            }
        }
        finally
        {
            zip.close();
        }
    }

    /**
     * Gets the date using the format specified
     *
     * @return
     */
    private String getDate()
    {
        return new String(format.format(new Date(System.currentTimeMillis())));
    }

    /**
     * When zip file is ready this util method is called to remove all zipped files
     *
     * @param curFiles
     */
    private void deleteFiles(File[] curFiles)
    {
        for (int i = 0; i < curFiles.length; i++)
        {
            curFiles[i].delete();
        }
    }

}
