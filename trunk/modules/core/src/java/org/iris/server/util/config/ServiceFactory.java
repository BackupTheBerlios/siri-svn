package org.iris.server.util.config;

import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.iris.xml.server.settings.ServiceType;
import org.iris.xml.server.settings.TransformfileType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract factory to create service settings objects.
 *
 * @author Georges Polyzois
 */
public class ServiceFactory
{
    public final static String FILETOFILE = "file-file";
    public final static String FILETOEJB = "file-ejb";
    public final static String POLLEJB = "poll-ejb";
    public final static String FILETOEMAIL = "file-email";

    static Logger myLogger = Logger.getLogger(ServiceFactory.class.getName());

    public ServiceFactory()
    {
    }

    public static Service getService(ServiceType aServiceType)
    {
        if (aServiceType.getType().equals(FILETOEJB))
        {
            ServiceFileEJB aService = new ServiceFileEJB();
            setService(aServiceType, aService);
            setError(aServiceType, aService);
            setLogging(aServiceType, aService);
            setTransform(aServiceType, aService);
            setInfolder(aServiceType, aService);
            setBackupAndArchive(aServiceType, aService);
            setInactivity(aServiceType, aService);

            aService.setNameService(aServiceType.getNameservice().getIorpath());
            aService.setServiceName(aServiceType.getNameservice().getObjectname());
            aService.setServiceMethod(aServiceType.getNameservice().getObjectmethod());
            aService.setServiceParamtype(aServiceType.getNameservice().getParamtype());
            aService.setReconnectToObjectInMilliSeconds( aServiceType.getNameservice().getReconnectToObjectInMilliSeconds() );
            aService.setReconnectToObjectPolicy( aServiceType.getNameservice().getReconnectToObjectPolicy() );
            myLogger.debug(org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(aService));

            return aService;
        }
        else if (aServiceType.getType().equals(POLLEJB))
        {
            ServicePollEJB aService = new ServicePollEJB();
            setService(aServiceType, aService);
            setError(aServiceType, aService);
            setLogging(aServiceType, aService);
            setTransform(aServiceType, aService);
            setInfolder(aServiceType, aService);
            setBackupAndArchive(aServiceType, aService);
            setInactivity(aServiceType, aService);

            aService.setNameService(aServiceType.getNameservice().getIorpath());
            aService.setServiceName(aServiceType.getNameservice().getParamtype());
            aService.setServiceMethod(aServiceType.getNameservice().getObjectmethod());
            aService.setServiceParamtype(aServiceType.getNameservice().getParamtype());
            myLogger.debug(org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(aService));
            return aService;
        }
        else if (aServiceType.getType().equals(FILETOFILE))
        {
            ServiceFileFile aService = new ServiceFileFile();
            setService(aServiceType, aService);
            setError(aServiceType, aService);
            setLogging(aServiceType, aService);
            setTransform(aServiceType, aService);
            setInfolder(aServiceType, aService);
            setBackupAndArchive(aServiceType, aService);
            setInactivity(aServiceType, aService);

            aService.setOutfolderPath(aServiceType.getOutfolder().getOutfolderpath());

            return aService;
        }
        else if (aServiceType.getType().equals(FILETOEMAIL))
        {
            ServiceFileEmail aService = new ServiceFileEmail();
            setService(aServiceType, aService);
            setError(aServiceType, aService);
            setLogging(aServiceType, aService);
            setTransform(aServiceType, aService);
            setInfolder(aServiceType, aService);
            setBackupAndArchive(aServiceType, aService);
            setInactivity(aServiceType, aService);

            aService.setEmailbatchsize( aServiceType.getEmailsettings().getEmailbatchsize() );
            aService.setEmailsendintervall( aServiceType.getEmailsettings().getEmailsendintervallInMilliSeconds() );
            aService.setEmailbatchsize( aServiceType.getEmailsettings().getEmailbatchsize());
            return aService;
        }
        return null;
    }

    private static void setError(ServiceType aServiceType, Service aService)
    {
        aService.setErrorInfolderName( aServiceType.getError().getErrorinfofolderpath());
        aService.setErrorfoldername(aServiceType.getError().getErrorfolderpath());
        aService.setSortOnResend(aServiceType.getError().isSortonresend());
        aService.setErrorhandlerOn(aServiceType.getError().isErrorhandleron());
        aService.setEmailonfailure(aServiceType.getError().getErrornotification().getEmailnotification().getEmailonfailure());
        aService.setSmsonfailure(aServiceType.getError().getErrornotification().getSms().getSmsonfailure());
        aService.setSmsservice(aServiceType.getError().getErrornotification().getSms().getSmsservice());
        aService.setErrornotificationOn( aServiceType.getError().getErrornotification().isErrornotificationon()  );
        aService.setNotifyeveryintervallInMilliSeconds( aServiceType.getError().getErrornotification().getNotifyeveryintervallInMilliSeconds() );
        aService.setNumberoferrors( aServiceType.getError().getErrornotification().getNumberoferrors()   );
        aService.setCronintervall( aServiceType.getError().getCronintervall()   );

    }

    private static void setInfolder(ServiceType aServiceType, Service aService)
    {
        aService.setInFolderchunkSize(aServiceType.getInfolder().getInfoldersendchunks().getInfolderchunksize());
        aService.setInFolderchunkThreshold(aServiceType.getInfolder().getInfoldersendchunks().getInfolderchunkthreshold());
        aService.setInFolderIntervall(aServiceType.getInfolder().getIntervallInMilliSeconds());
        aService.setInfolderPath(aServiceType.getInfolder().getInfolderpath());
        aService.setSendinfolderchunksOn(aServiceType.getInfolder().getInfoldersendchunks().isSendinfolderchunkson());
        aService.setSkipfirstblankLine(aServiceType.getInfolder().isSkipfirstblankline());
        aService.setSortAlphaOn(  aServiceType.getInfolder().isSortalpha() );
    }

    private static void setTransform(ServiceType aServiceType, Service aService)
    {
        aService.setIsTransformOn(aServiceType.getTransform().isTransformon());
        if (aService.isIsTransformOn())
        {
            List transform = aServiceType.getTransform().getTransformfile();
            Iterator iter = transform.iterator();
            HashMap map = new HashMap();
            while (iter.hasNext())
            {
                TransformfileType aTransformType = (TransformfileType) iter.next();
                String key = aTransformType.getTransformmessage();
                String value = aTransformType.getValue();
                map.put(key, value);
            }
            aService.setTransformFiles(map);
        }
    }

    private static void setService(ServiceType aServiceType, Service aService)
    {
        aService.setId(aServiceType.getId());
        aService.setCreateUniqueName(aServiceType.isCreateuniquename());
        aService.setType(aServiceType.getType());
        aService.setUsesrelativerootpath(aServiceType.isUsesrelativerootpath());
    }

    private static void setBackupAndArchive(ServiceType aServiceType, Service aService)
    {
        aService.setArchivefoldername(aServiceType.getBackup().getArchivefolderpath());
        aService.setArchiverOn(aServiceType.getBackup().isArchiveron());
        aService.setBackupfolder(aServiceType.getBackup().getBackupfolderpath());
        aService.setBackuphandlerOn(aServiceType.getBackup().isBackuphandleron());
    }

    private static void setLogging(ServiceType aServiceType, Service aService)
    {
        aService.setApplicationlogfolder(aServiceType.getApplicationlogger().getLogfolderpath());
        aService.setApplicationlogpattern(aServiceType.getApplicationlogger().getLogpattern());
        aService.setApplicationmaxBackupIndex(aServiceType.getApplicationlogger().getAppender().getMaxbackupindex());
        aService.setApplicationappendertype(aServiceType.getApplicationlogger().getAppender().getType());
        aService.setApplicationappendermaxfilesize(aServiceType.getApplicationlogger().getAppender().getMaxfilesize());
        aService.setApplicationappenderrollingschedule(aServiceType.getApplicationlogger().getAppender().getRollingschedule());
        aService.setApplicationLogLevel(aServiceType.getApplicationlogger().getLoglevel() );

        aService.setMessagelogfolder(aServiceType.getMessagelogger().getLogfolderpath());
        aService.setMessagelogpattern(aServiceType.getMessagelogger().getLogpattern());
        aService.setMessagemaxBackupIndex(aServiceType.getMessagelogger().getAppender().getMaxbackupindex());
        aService.setMessageappendertype(aServiceType.getMessagelogger().getAppender().getType());
        aService.setMessageappendermaxfilesize(aServiceType.getMessagelogger().getAppender().getMaxfilesize());
        aService.setMessageappenderrollingschedule(aServiceType.getMessagelogger().getAppender().getRollingschedule());
        aService.setMessageLogLevel(aServiceType.getMessagelogger().getLoglevel() );
    }

    private static void setInactivity(ServiceType aServiceType, Service aService)
    {
        aService.setInacativityHandlerOn(  aServiceType.getInactivity().isInactivityOn() );
        aService.setMaxInactivitytime(  aServiceType.getInactivity().getMaxInactivityPeriodInMilliSeconds() );
        aService.setEmailonincativity(  aServiceType.getInactivity().getEmailOnInactivity() );

    }


}