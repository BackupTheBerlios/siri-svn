package org.iris.server.util.config;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.iris.server.util.filehelpers.FileCreateHelper;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Parse and build init props using Digester (Apache/Jakarta)
 *
 * Georges
 *
 * @deprecated using jaxb instead
 */
public class SystemConfigurator
{
    protected static ArrayList services = new ArrayList();
    protected static HashMap servicesMap = new HashMap();
    static Logger myLogger = Logger.getLogger(SystemConfigurator.class.getName());
    private static GlobalSettings myGlobalServiceArchiver;

    public void addServiceFileQueue(ServiceFileQueue service)
    {
        services.add(service);
        servicesMap.put(service.getName(), service);
        myLogger.debug(service);
    }

    public void addGlobalSettings(GlobalSettings service)
    {
        myGlobalServiceArchiver = service;
        servicesMap.put(service.getName(), service);
        myLogger.debug(service);
    }

    public void addServiceFileFile(ServiceFileFile service)
    {
        services.add(service);
        servicesMap.put(service.getId(), service);
        myLogger.debug(service);
        //myLogger.debug( service.toXMLString() );
    }

    public void addServiceFileEJB(ServiceFileEJB service)
    {
        services.add(service);
        servicesMap.put(service.getId(), service);
        myLogger.debug(service);
    }

    public void addServicePollEJB(ServicePollEJB service)
    {
        services.add(service);
        servicesMap.put(service.getId(), service);
        myLogger.debug(service);

    }

    public void addServiceFileCORBA(ServiceFileCORBA service)
    {
        services.add(service);
        servicesMap.put(service.getId(), service);
        // myLogger.debug( service );
    }

    public void addServiceFileEmail(ServiceFileEmail service)
    {
        services.add(service);
        servicesMap.put(service.getId(), service);
        myLogger.debug(service);
        //myLogger.debug( service.toXMLString() );
    }

    public static void main(String[] args) throws IOException, SAXException
    {
        try
        {
            String filelocation = "C:/gepo/mycvsprojects/iris/src/conf/iris_c2kintercdmp.xml";
            System.out.println("USING XML FILE" + filelocation);
            /*
                        init(filelocation);
                        getServiceNames();
             */

            String xml = FileCreateHelper.readFile(filelocation);
            initUsingXml(xml);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void init(String fileLocation) throws IOException, SAXException
    {
        myLogger.debug("Using " + fileLocation);
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addObjectCreate("system-config", SystemConfigurator.class);

        initDigesterFileToejb(digester);

        initDigesterFileToQueue(digester);

        initDigesterFileToFile(digester);

        initDigesterFileToCorba(digester);

        initDigesterFileToEmail(digester);

        initGlobalSetting(digester);

        initDigesterPollEjb(digester);

        SystemConfigurator abp = (SystemConfigurator) digester.parse(fileLocation);
    }

    public static void initUsingXml(String xmlData) throws IOException, SAXException
    {
        myLogger.debug("Using xmldata");
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addObjectCreate("system-config", SystemConfigurator.class);

        initDigesterFileToejb(digester);

        initDigesterFileToQueue(digester);

        initDigesterFileToFile(digester);

        initDigesterFileToCorba(digester);

        initDigesterFileToEmail(digester);

        initGlobalSetting(digester);

        initDigesterPollEjb(digester);

        ByteArrayInputStream bais = new ByteArrayInputStream(xmlData.getBytes());

        SystemConfigurator abp = (SystemConfigurator) digester.parse(bais);
        myLogger.debug("Finished init");
    }

    private static void initInfolder(String xmlpathforservice, Digester digester)
    {
        digester.addCallMethod(xmlpathforservice + "/infolder/path", "setInfolderPath", 0);
        digester.addCallMethod(xmlpathforservice + "/infolder/intervall", "setInfolderIntervall", 0);
        digester.addCallMethod(xmlpathforservice + "/infolder/skipfirstblankline", "setSkipfirstblankline", 0);
        digester.addCallMethod(xmlpathforservice + "/infolder/sortalpha", "setSortalpha", 0);
        digester.addSetProperties(xmlpathforservice + "/infolder/infoldersendchunks", "sendinfolderchunkson",
            "sendinfolderchunkson");
        digester.addCallMethod(xmlpathforservice + "/infolder/infoldersendchunks/infolderchunksize",
            "setInfolderchunksize", 0);
        digester.addCallMethod(xmlpathforservice + "/infolder/infoldersendchunks/infolderchunkthreshold",
            "setInfolderchunkthreshold", 0);
    }

    private static void initErrornotification(Digester digester, String xmlpathforservice)
    {
        digester.addSetProperties(xmlpathforservice + "errornotification", "errornotificationon", "errornotificationon");
        digester.addCallMethod(xmlpathforservice + "errornotification/sendonthreshold", "setSendonthreshold", 0);
    }

    private static void initLogging(Digester digester, String xmlpathforservice)
    {
        digester.addSetProperties(xmlpathforservice + "applicationappendertype", "type", "applicationappendertype");
        digester.addCallMethod(xmlpathforservice + "applicationappendertype/applicationappendermaxfilesize", "setApplicationappendermaxfilesize", 0);
        digester.addCallMethod(xmlpathforservice + "applicationappendertype/maxbackupindex", "setMaxbackupindex", 0);
        digester.addCallMethod(xmlpathforservice + "applicationlogpattern", "setApplicationlogpattern", 0);
        digester.addCallMethod(xmlpathforservice + "applicationlogfolder", "setApplicationlogfolder", 0);
        digester.addCallMethod(xmlpathforservice + "layout", "setApplicationlogpattern", 0);
    }

    private static void initTransform(Digester digester, String xmlpathforservice)
    {
        digester.addSetProperties(xmlpathforservice + "transform", "transformon", "transformon");
        digester.addCallMethod(xmlpathforservice + "transform/transformfile", "setTransformfile", 0);
        digester.addSetProperties(xmlpathforservice + "transform/transformfile", "transformmessage", "transformmessage");
    }

    private static void initBackupAndError(Digester digester, String xmlpathforservice)
    {

        digester.addSetProperties(xmlpathforservice + "backup", "backuphandleron", "backuphandleron");
        digester.addCallMethod(xmlpathforservice + "backup/backupfoldername", "setBackupfolder", 0);

        digester.addSetProperties(xmlpathforservice + "error", "errorhandleron", "errorhandleron");
        digester.addCallMethod(xmlpathforservice + "error/errorfoldername", "setErrorfoldername", 0);
        digester.addCallMethod(xmlpathforservice + "error/resendintervall", "setResendintervall", 0);
        digester.addCallMethod(xmlpathforservice + "error/sortonresend", "setSortonresend", 0);

        digester.addCallMethod(xmlpathforservice + "error/sendonstart", "setSendonstart", 0);
        digester.addSetProperties(xmlpathforservice + "error/sendchunks", "sendchunkson", "sendchunkson"); //setSendchunkson

        digester.addSetProperties(xmlpathforservice, "archiveron", "archiveron");

        digester.addCallMethod(xmlpathforservice + "backup/archivefoldername", "setArchivefoldername", 0);
    }

    private static void initDigesterFileToejb(Digester digester)
    {
        String root = "system-config/service-file-ejb";
        digester.addObjectCreate(root, ServiceFileEJB.class);
        digester.addSetProperties(root, "id", "id");
        digester.addSetProperties(root, "createuniquename", "createuniquename");

        initInfolder(root, digester);

        digester.addCallMethod(root + "/service/nameservice", "setNameService", 0);
        digester.addCallMethod(root + "/service/name", "setServiceName", 0);
        digester.addCallMethod(root + "/service/method", "setServiceMethod", 0);
        digester.addCallMethod(root + "/service/paramtype", "setServiceParamtype", 0);
        digester.addSetProperties(root + "/service", "ServiceParamtype", "setServiceParamtype");

//        digester.addSetProperties(root + "/service", "useexplicitclass", "setUseexplicitclass");

        digester.addCallMethod(root + "/infolder/sortalpha", "setSortalpha", 0);

        digester.addSetNext(root, "addServiceFileEJB");
        initLogging(digester, root + "/logging/");
        initTransform(digester, root + "/");

        initBackupAndError(digester, root + "/");

        initErrornotification(digester, root + "/logging/");

    }

    private static void initDigesterFileToCorba(Digester digester)
    {
        String root = "system-config/service-file-corba";
        digester.addObjectCreate(root, ServiceFileCORBA.class);
        digester.addSetProperties(root, "id", "id");
        digester.addSetProperties(root, "createuniquename", "createuniquename");

        initInfolder(root, digester);

        digester.addCallMethod(root + "/backupfolder", "setBackupfolder", 0);
        digester.addCallMethod(root + "/service/nameservice", "setNameService", 0);
        digester.addCallMethod(root + "/service/name", "setServiceName", 0);
        digester.addCallMethod(root + "/service/method", "setServiceMethod", 0);
        digester.addCallMethod(root + "/service/paramtype", "setServiceParamtype", 0);
        digester.addCallMethod(root + "/infolder/sortalpha", "setSortalpha", 0);

        digester.addSetNext(root, "addServiceFileCORBA");
        initLogging(digester, root + "/logging/");

        initBackupAndError(digester, root + "/");

    }

    private static void initDigesterFileToQueue(Digester digester)
    {
        String root = "system-config/service-file-queue";
        digester.addObjectCreate(root, ServiceFileQueue.class);
        digester.addSetProperties(root, "id", "id");
        digester.addSetProperties(root, "createuniquename", "createuniquename");

        digester.addSetProperties(root, "applicationlogfolder", "applicationlogfolder");

        initInfolder(root, digester);

        digester.addCallMethod(root + "/backupfolder", "setBackupfolder", 0);
        digester.addCallMethod(root + "/service/nameservice", "setNameService", 0);
        digester.addCallMethod(root + "/service/name", "setQueueName", 0);
        digester.addCallMethod(root + "/service/factoryname", "setFactoryname", 0);
        digester.addCallMethod(root + "/infolder/sortalpha", "setSortalpha", 0);

        digester.addSetNext(root, "addServiceFileQueue");

        initLogging(digester, root + "/logging/");
        initBackupAndError(digester, root + "/");

    }

    private static void initDigesterFileToFile(Digester digester)
    {
        String root = "system-config/service";
        digester.addObjectCreate(root, ServiceFileFile.class);
        digester.addSetProperties(root, "id", "id");
        digester.addSetProperties(root, "type", "type");

        digester.addSetProperties(root, "createuniquename", "createuniquename");

        digester.addSetProperties(root, "applicationlogfolder", "applicationlogfolder");

        initInfolder(root, digester);

        digester.addCallMethod(root + "/backupfolder", "setBackupfolder", 0);
        digester.addCallMethod(root + "/outfolder/path", "setOutfolderPath", 0);

        digester.addSetNext(root, "addServiceFileFile");

        initLogging(digester, root + "/logging/");
        initTransform(digester, root + "/");

        initBackupAndError(digester, root + "/");

    }

    private static void initDigesterFileToEmail(Digester digester)
    {
        String root = "system-config/service";
        digester.addObjectCreate(root, ServiceFileEmail.class);
        digester.addSetProperties(root, "id", "id");
        digester.addSetProperties(root, "createuniquename", "createuniquename");

        digester.addCallMethod(root + "/emailsettings/server", "setEmailServer", 0);
        digester.addCallMethod(root + "/emailsettings/emailbatchsize", "setEmailbatchsize", 0);
        digester.addCallMethod(root + "/emailsettings/emailsendintervall", "setEmailsendintervall", 0);

        initInfolder(root, digester);

        digester.addSetNext(root, "addServiceFileEmail");

        initLogging(digester, root + "/logging/");

        initTransform(digester, root + "/");

        initBackupAndError(digester, root + "/");
    }

    private static void initGlobalSetting(Digester digester)
    {
        digester.addObjectCreate("system-config/global-settings", GlobalSettings.class);
        digester.addSetProperties("system-config/global-settings/service-archive", "name", "name");
        digester.addCallMethod("system-config/global-settings/service-archive/archiverintervall",
            "setArchiverintervall", 0);

        digester.addCallMethod("system-config/global-settings/logging/socket-hub/address", "setLogging_sockethub", 0);
        digester.addCallMethod("system-config/global-settings/logging/socket-hub/port", "setLogging_port", 0);
        digester.addCallMethod("system-config/global-settings/logging/socket-hub/reconnect-delay",
            "setLogging_reconnectdelay", 0);

        digester.addSetProperties("system-config/global-settings/service-resend", "name", "serviceresend_name");
        digester.addCallMethod("system-config/global-settings/service-resend/errorresendintervall",
            "setServiceresend_errorresendintervall", 0);

        digester.addCallMethod("system-config/global-settings/primaryemailserver", "setPrimaryemailserver", 0);

        digester.addSetNext("system-config/global-settings", "addGlobalSettings");
    }

    public static ArrayList getServices(boolean sortAscending)
    {
        Service.sortAscending = sortAscending;
        Collections.sort(services);
        return services;
    }

    public static String[] getServiceNames()
    {
        Service.sortAscending = true;
        Collections.sort(services);

        Service[] serviceobj = new Service[services.size()];
        services.toArray(serviceobj);
        String[] serviceNames = new String[serviceobj.length];
        for (int i = 0; i < serviceNames.length; i++)
        {
            serviceNames[i] = serviceobj[i].getId();
        }

        return serviceNames;
    }

    public static ArrayList getServices()
    {
        return services;
    }

    public static Service[] getArrOfServices()
    {
        Service[] result = new Service[services.size()];
        return (Service[]) services.toArray(result);
    }

    public static String[] getArrOfServicesSetToResend()
    {
        Service[] serviceobj = new Service[services.size()];
        services.toArray(serviceobj);

        ArrayList servieErrorresendNames = new ArrayList();
        for (int i = 0; i < serviceobj.length; i++)
        {
            /*            if (serviceobj[i].getErrorhandleron().equalsIgnoreCase("true"))
                        {
                            servieErrorresendNames.add(serviceobj[i].getId());
                        }
             */}
        String[] result = new String[servieErrorresendNames.size()];
        servieErrorresendNames.toArray(result);
        return result;
    }

    public static HashMap getServicesMap()
    {
        return servicesMap;
    }

    private static void initDigesterPollEjb(Digester digester)
    {
        String root = "system-config/service-poll-ejb";
        digester.addObjectCreate(root, ServicePollEJB.class);
        digester.addSetProperties(root, "id", "id");

        digester.addCallMethod(root + "/poll/message", "setMessage", 0);
        digester.addCallMethod(root + "/poll/intervall", "setPollintervall", 0);
        digester.addCallMethod(root + "/poll/emailtosendonfailure", "setEmailtosendonfailure", 0);
        digester.addCallMethod(root + "/poll/emailservice", "setEmailservice", 0);

        digester.addCallMethod(root + "/service/nameservice", "setNameService", 0);
        digester.addCallMethod(root + "/service/name", "setServiceName", 0);
        digester.addCallMethod(root + "/service/method", "setServiceMethod", 0);
        digester.addCallMethod(root + "/service/paramtype", "setServiceParamtype", 0);
        digester.addSetProperties(root + "/service", "ServiceParamtype", "setServiceParamtype");

        digester.addSetNext(root, "addServicePollEJB");
        initLogging(digester, root + "/logging/");
    }

    public static GlobalSettings getGlobalSettings()
    {
        return myGlobalServiceArchiver;
    }

}