package org.iris.server.util.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.HashMap;


/**
 * Abstract super class for all setting services.
 *
 * @author Georges Polyzois
 */
public abstract class Service
    implements Comparable

{
    public transient static boolean sortAscending = true;
    public transient static int SORTONNAME = 0;
    public transient static int columnToSort = 0;

    protected String id;
    protected boolean createUniqueName;

    //infolder
    protected String infolderIntervall;
    protected String infolderPath;
    protected boolean skipfirstblankLineOn;
    protected boolean sortOnResendOn;
    //protected String sendinfolderchunkson;
    protected boolean sendinfolderchunksOn;

    protected String infolderchunksize;
    protected String infolderchunkthreshold;
    protected int inFolderchunkThreshold;
    protected int inFolderchunkSize;
    protected long inFolderIntervall;

    //

    //error
    protected boolean errorhandlerOn;
    protected String errorfoldername;
    protected String errorInfolderName;
    protected String sendonstart;
    protected String resendintervall;
    protected String cronintervall;
    protected int resendIntervall;
    protected boolean isErrornotificationOn;
    protected long notifyeveryintervallInMilliSeconds;
    protected long numberoferrors;  // if errors are larger than this then we should send notification of error
    protected long sendintervall;   // how often to send error notifications
    protected String emailonfailure;
    protected String emailservice;
    protected String smsonfailure;
    protected String smsservice;
//    protected String errornotificationon = "true";  // remove in future
    //

    // incativity options
    protected long maxInactivitytime;
    protected String emailonincativity;
    protected boolean isInacativityHandlerOn;
    //


    //
    protected String backupfolder;
    protected boolean archiverOn;
    protected boolean backuphandlerOn;
    protected String archivefoldername;
    //

    // transform
    protected String transformon;
    protected boolean isTransformOn;
    protected String transformfile;
    protected String transformmessage;
    protected HashMap transformFiles = new HashMap();
    //

    protected String sortalpha;
    protected boolean isSortAlphaOn;

    // logging
    protected String applicationlogfolder;
    protected String applicationappendertype;
    protected String applicationappendermaxfilesize;
    protected String applicationappenderrollingschedule;
    protected String applicationlogpattern;
    protected String applicationLogLevel;
    protected int applicationmaxBackupIndex;
    protected String messagelogfolder;
    protected String messageappendertype;
    protected String messageappendermaxfilesize;
    protected String messageappenderrollingschedule;
    protected String messagelogpattern;
    protected int messagemaxBackupIndex;
    protected String messageLogLevel;


    //


    protected String sendonthreshold;
    private String type;
    private boolean usesrelativerootpath;

    public Service()
    {
    }

    public boolean isSortAlphaOn() {
        return isSortAlphaOn;
    }

    public void setSortAlphaOn(boolean sortAlphaOn) {
        isSortAlphaOn = sortAlphaOn;
    }
    public String getBackupfolder()
    {
        return backupfolder;
    }

    public String getInfolderIntervall()
    {
        return infolderIntervall;
    }

    public String getInfolderPath()
    {
        return infolderPath;
    }

    public String getId()
    {
        return id;
    }

    public void setBackupfolder(String backupfolder)
    {
        this.backupfolder = backupfolder;
    }

    public void setInfolderIntervall(String infolderIntervall)
    {
        this.infolderIntervall = infolderIntervall;
    }

    public void setInfolderPath(String infolderPath)
    {
        this.infolderPath = infolderPath;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getApplicationlogfolder()
    {
        return applicationlogfolder;
    }

    public void setApplicationlogfolder(String applicationlogfolder)
    {
        this.applicationlogfolder = applicationlogfolder;
    }

    public String getApplicationlogpattern()
    {
        return applicationlogpattern;
    }

    public String getApplicationappendermaxfilesize()
    {
        return applicationappendermaxfilesize;
    }

    public void setApplicationappendermaxfilesize(String applicationappendermaxfilesize)
    {
        this.applicationappendermaxfilesize = applicationappendermaxfilesize;
    }

    /*public void setMaxbackupindex(String maxbackupindex)
    {
        this.maxbackupindex = maxbackupindex;
    } */

    public void setApplicationlogpattern(String applicationlogpattern)
    {
        this.applicationlogpattern = applicationlogpattern;
    }

    public String getApplicationappendertype()
    {
        return applicationappendertype;
    }

    public void setApplicationappendertype(String applicationappendertype)
    {
        this.applicationappendertype = applicationappendertype;
    }

    /*public String getMaxbackupindex()
    {
        return maxbackupindex;
    } */

    public String getResendintervall()
    {
        return resendintervall;
    }

    public void setResendintervall(String resendintervall)
    {
        this.resendintervall = resendintervall;
    }

    public void setErrorfoldername(String errorfoldername)
    {
        this.errorfoldername = errorfoldername;
    }

    public String getErrorfoldername()
    {
        return errorfoldername;
    }

    public String getTransformon()
    {
        return transformon;
    }

    public void setTransformon(String transformon)
    {
        this.transformon = transformon;
    }

    public String getTransformfile()
    {
        return transformfile;
    }

    public void setTransformfile(String transformfile)
    {
        transformFiles.put(transformmessage, transformfile);
        this.transformfile = transformfile;
    }

    public String getTransformmessage()
    {
        return transformmessage;
    }

    public void setTransformmessage(String transformmessage)
    {
        this.transformmessage = transformmessage;
    }

    public java.util.HashMap getTransformFiles()
    {
        return transformFiles;
    }

    public void setTransformFiles(java.util.HashMap transformFiles)
    {
        this.transformFiles = transformFiles;
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getArchivefoldername()
    {
        return archivefoldername;
    }

    public void setArchivefoldername(String archivefoldername)
    {
        this.archivefoldername = archivefoldername;
    }

    public String getSortalpha()
    {
        return sortalpha;
    }

    public void setSortalpha(String sortalpha)
    {
        this.sortalpha = sortalpha;
    }

    public int compareTo(Object o1)
    {
        int signum = 1;
        if (sortAscending)
        {
            signum = -1;
        }
        if (SORTONNAME == columnToSort)
        {
            return signum * (compare(getId(), ( (Service) o1).getId()));
        }
        return -1;
    }

    public int compare(String str1, String str2)
    {

        return str1.compareTo(str2);
    }

    /*    public int compare(BigDecimal str1, BigDecimal str2)
        {
            return str1.compareTo(str2);
        }
     */
    public int compare(long str1, long str2)
    {

        if (str1 < str2)
        {

            return -1;
        }
        else if (str1 > str2)
        {

            return 1;
        }
        else
        {

            return 0;
        }
    }

    public int compare(double str1, double str2)
    {

        if (str1 < str2)
        {

            return -1;
        }
        else if (str1 > str2)
        {

            return 1;
        }
        else
        {

            return 0;
        }
    }

    public String getSendonthreshold()
    {
        return sendonthreshold;
    }

    public void setSendonthreshold(String sendonthreshold)
    {
        this.sendonthreshold = sendonthreshold;
    }

    public String getSendonstart()
    {
        return sendonstart;
    }

    public void setSendonstart(String sendonstart)
    {
        this.sendonstart = sendonstart;
    }

    public String getInfolderchunksize()
    {
        return infolderchunksize;
    }

    public String getInfolderchunkthreshold()
    {
        return infolderchunkthreshold;
    }

    public void setInfolderchunksize(String infolderchunksize)
    {
        this.infolderchunksize = infolderchunksize;
    }

    public void setInfolderchunkthreshold(String infolderchunkthreshold)
    {
        this.infolderchunkthreshold = infolderchunkthreshold;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getInFolderchunkThreshold()
    {
        return inFolderchunkThreshold;
    }

    public void setInFolderchunkThreshold(int inFolderchunkThreshold)
    {
        this.inFolderchunkThreshold = inFolderchunkThreshold;
    }

    public int getInFolderchunkSize()
    {
        return inFolderchunkSize;
    }

    public void setInFolderchunkSize(int inFolderchunkSize)
    {
        this.inFolderchunkSize = inFolderchunkSize;
    }

    public long getInFolderIntervall()
    {
        return inFolderIntervall;
    }

    public void setInFolderIntervall(long inFolderIntervall)
    {
        this.inFolderIntervall = inFolderIntervall;
    }

    /*public long getMaxFileSize()
    {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize)
    {
        this.maxFileSize = maxFileSize;
    }                        */

    public int getApplicationmaxBackupIndex()
    {
        return applicationmaxBackupIndex;
    }

    public void setApplicationmaxBackupIndex(int applicationmaxBackupIndex)
    {
        this.applicationmaxBackupIndex = applicationmaxBackupIndex;
    }

    public boolean isBackuphandlerOn()
    {
        return backuphandlerOn;
    }

    public void setBackuphandlerOn(boolean backuphandlerOn)
    {
        this.backuphandlerOn = backuphandlerOn;
    }

    public boolean isArchiverOn()
    {
        return archiverOn;
    }

    public void setArchiverOn(boolean archiverOn)
    {
        this.archiverOn = archiverOn;
    }

    public boolean isSkipfirstblankLine()
    {
        return skipfirstblankLineOn;
    }

    public void setSkipfirstblankLine(boolean skipfirstblankLineOn)
    {
        this.skipfirstblankLineOn = skipfirstblankLineOn;
    }

    public int getResendIntervall()
    {
        return resendIntervall;
    }

    public void setResendIntervall(int resendIntervall)
    {
        this.resendIntervall = resendIntervall;
    }

    public boolean isSendinfolderchunksOn()
    {
        return sendinfolderchunksOn;
    }

    public void setSendinfolderchunksOn(boolean sendinfolderchunksOn)
    {
        this.sendinfolderchunksOn = sendinfolderchunksOn;
    }

    public boolean isSortOnResend()
    {
        return sortOnResendOn;
    }

    public void setSortOnResend(boolean sortOnResendOn)
    {
        this.sortOnResendOn = sortOnResendOn;
    }

    public boolean isIsTransformOn()
    {
        return isTransformOn;
    }

    public void setIsTransformOn(boolean isTransformOn)
    {
        this.isTransformOn = isTransformOn;
    }

    public boolean isUsesrelativerootpath()
    {
        return usesrelativerootpath;
    }

    public void setUsesrelativerootpath(boolean usesrelativerootpath)
    {
        this.usesrelativerootpath = usesrelativerootpath;
    }

    public boolean isCreateUniqueName()
    {
        return createUniqueName;
    }

    public void setCreateUniqueName(boolean createUniqueName)
    {
        this.createUniqueName = createUniqueName;
    }

    public boolean isErrorhandlerOn()
    {
        return errorhandlerOn;
    }

    public void setErrorhandlerOn(boolean errorhandlerOn)
    {
        this.errorhandlerOn = errorhandlerOn;
    }

    public long getNumberoferrors()
    {
        return numberoferrors;
    }

    public void setNumberoferrors(long numberoferrors)
    {
        this.numberoferrors = numberoferrors;
    }

    public long getMaxInactivitytime()
    {
        return maxInactivitytime;
    }

    public void setMaxInactivitytime(long maxInactivitytime)
    {
        this.maxInactivitytime = maxInactivitytime;
    }

    public boolean isErrornotificationOn()
    {
        return isErrornotificationOn;
    }

    public void setErrornotificationOn(boolean errornotificationOn)
    {
        this.isErrornotificationOn = errornotificationOn;
    }

    public String getEmailonfailure()
    {
        return emailonfailure;
    }

    public String getEmailservice()
    {
        return emailservice;
    }

    public void setEmailonfailure(String emailonfailure)
    {
        this.emailonfailure = emailonfailure;
    }

    public void setEmailservice(String emailservice)
    {
        this.emailservice = emailservice;
    }

    public String getSmsonfailure()
    {
        return smsonfailure;
    }

    public String getSmsservice()
    {
        return smsservice;
    }

    public void setSmsservice(String smsservice)
    {
        this.smsservice = smsservice;
    }

    public void setSmsonfailure(String smsonfailure)
    {
        this.smsonfailure = smsonfailure;
    }


    public String getErrorInfolderName()
    {
        return errorInfolderName;
    }

    public void setErrorInfolderName(String errorInfolderName)
    {
        this.errorInfolderName = errorInfolderName;
    }



    public long getSendintervall()
    {
        return sendintervall;
    }

    public void setSendintervall(long sendintervall)
    {
        this.sendintervall = sendintervall;
    }


    public long getNotifyeveryintervallInMilliSeconds()
    {
        return notifyeveryintervallInMilliSeconds;
    }

    public void setNotifyeveryintervallInMilliSeconds(long notifyeveryintervallInMilliSeconds)
    {
        this.notifyeveryintervallInMilliSeconds = notifyeveryintervallInMilliSeconds;
    }

    public String getApplicationappenderrollingschedule()
    {
        return applicationappenderrollingschedule;
    }

    public void setApplicationappenderrollingschedule(String applicationappenderrollingschedule)
    {
        this.applicationappenderrollingschedule = applicationappenderrollingschedule;
    }

    public String getMessageappendertype()
    {
        return messageappendertype;
    }

    public void setMessageappendertype(String messageappendertype)
    {
        this.messageappendertype = messageappendertype;
    }

    public String getMessageappendermaxfilesize()
    {
        return messageappendermaxfilesize;
    }

    public void setMessageappendermaxfilesize(String messageappendermaxfilesize)
    {
        this.messageappendermaxfilesize = messageappendermaxfilesize;
    }

    public String getMessageappenderrollingschedule()
    {
        return messageappenderrollingschedule;
    }

    public void setMessageappenderrollingschedule(String messageappenderrollingschedule)
    {
        this.messageappenderrollingschedule = messageappenderrollingschedule;
    }

    public String getMessagelogpattern()
    {
        return messagelogpattern;
    }

    public void setMessagelogpattern(String messagelogpattern)
    {
        this.messagelogpattern = messagelogpattern;
    }

    public int getMessagemaxBackupIndex()
    {
        return messagemaxBackupIndex;
    }

    public void setMessagemaxBackupIndex(int messagemaxBackupIndex)
    {
        this.messagemaxBackupIndex = messagemaxBackupIndex;
    }

    public String getMessagelogfolder()
    {
        return messagelogfolder;
    }

    public void setMessagelogfolder(String messagelogfolder)
    {
        this.messagelogfolder = messagelogfolder;
    }
    public String getCronintervall()
    {
        return cronintervall;
    }

    public void setCronintervall(String cronintervall)
    {
        this.cronintervall = cronintervall;
    }
    public String getApplicationLogLevel()
    {
        return applicationLogLevel;
    }

    public void setApplicationLogLevel(String applicationLogLevel)
    {
        this.applicationLogLevel = applicationLogLevel;
    }

    public String getMessageLogLevel()
    {
        return messageLogLevel;
    }

    public void setMessageLogLevel(String messageLogLevel)
    {
        this.messageLogLevel = messageLogLevel;
    }


    public String getEmailonincativity() {
        return emailonincativity;
    }

    public void setEmailonincativity(String emailonincativity) {
        this.emailonincativity = emailonincativity;
    }

    public boolean isInacativityHandlerOn() {
        return isInacativityHandlerOn;
    }

    public void setInacativityHandlerOn(boolean inacativityHandlerOn) {
        isInacativityHandlerOn = inacativityHandlerOn;
    }


}