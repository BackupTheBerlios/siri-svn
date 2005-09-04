package org.iris.server;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.PropertyConfigurator;
import org.iris.server.archivehandler.ArchiveVO;
import org.iris.server.archivehandler.CrontriggerArchiveHandler;
import org.iris.server.filehandlers.FileMessageHandler;
import org.iris.server.filehandlers.FileMessageHandlerFactory;
import org.iris.server.util.console.SystemOutThread;
import org.iris.server.messagehandlers.InstantEmailMessageHandler;
import org.iris.server.remoteservices.rmi.IrisOperationsImpl;
import org.iris.server.util.TimerFascade;
import org.iris.server.util.config.ServerSystemConfigHandler;
import org.iris.server.util.config.Service;
import org.iris.server.util.config.ServiceFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.*;

/**
 * Main class for Iris.
 *
 * Reads in all config and starts up all services, archiving thread etc.
 *
 * Adds shutdown hook to vm so that we get to send an email when we close down gracefully
 * using ctrl+c or kill.
 *
 */
public class Iris implements Runnable
{
    static Category myLogger;
    private String emailonshutdown;
    HashMap myServiceThreads = new HashMap();
    IrisOperationsImpl irisOperations;

    public Iris()
    {
        try
        {
            myLogger = Logger.getLogger(Iris.class.getName());
            setUp();
            emailonshutdown = System.getProperty("iris.emailonshutdown");
        }
        catch (Exception ex)
        {
            myLogger.error("Failed setup - exiting", ex);
            System.exit(0);
        }

    }

    protected void setUp()
    {
        ArrayList arrListOfServices = null;
        try
        {
            arrListOfServices = loadSettingsFromXmlConfigFile(arrListOfServices);
        }
        catch (Exception ex)
        {
            myLogger.error("Failed reading system properties from xml file", ex);
        }

        Iterator iter = arrListOfServices.iterator();
        ArrayList backupFolders = new ArrayList();
        //12 hours
        boolean startErrorMessageResender = false;
        int scheduledServices = 0;
        int serviceNr = 1;
        System.out.println("->Scheduling services for start and doing lookups on remote services");
        while (iter.hasNext())
        {

            Object obj = iter.next();
            Service service = (Service) obj;
            FileMessageHandler fileMessageHandler = null;
            SystemOutThread outThr = null;
            try
            {
                // Use this thread to printout dots to indicate progress
                outThr = new SystemOutThread();
                Thread printer = new Thread(outThr);
                printer.start();

                System.out.print("\t" + serviceNr + ": " + service.getId() + " is beeing scheduled for start.");
                fileMessageHandler = FileMessageHandlerFactory.createNotStartedInstance(service);
                myServiceThreads.put(service.getId(), fileMessageHandler);
                if (service.isArchiverOn())
                {
                    ArchiveVO archiveVO = new ArchiveVO();
                    archiveVO.setServiceName(service.getId());
                    archiveVO.setBackupFromFolderPath(service.getBackupfolder());
                    archiveVO.setArchiveToFolderPath(service.getArchivefoldername());
                    backupFolders.add(archiveVO);
                }
                if (service.isErrorhandlerOn())
                {
                    myLogger.debug("Found an entry telling us to start error resender for this service...");
                    startErrorMessageResender = true;
                }
                System.out.println("scheduling ok");
                outThr.stopThread();

                scheduledServices++;

            }
            catch (Exception ex1)
            {
                myLogger.error("Failed creating message handler " + ex1.getMessage(), ex1);
                System.out.println(" scheduling NOT ok");
                if (outThr != null) outThr.stopThread();
            }
            serviceNr++;
        }

        myLogger.info("\t" + scheduledServices + " of (" + arrListOfServices.size() + ")  number of scheduled services configured for starting.");
        System.out.println("\t" + scheduledServices + " of (" + arrListOfServices.size() + ")  number of scheduled services configured for starting.");

        // Handle archiving of backup messages for every service
        if (backupFolders != null && backupFolders.size() > 0)
        {

            System.out.println("->Starting archive handler.");
            CrontriggerArchiveHandler.startSchedulerThread(backupFolders, "archiver", "archivergroup", "crontrigger",
                    getArchiverCronIntervall());
        }

        // If any service has requested error handling then we should start the global error handler using the
        // scheduling set up for this global service.
        if (startErrorMessageResender)
        {
            System.out.println("->Starting error handlers.");
            startErrorHandlers(ServerSystemConfigHandler.getInstance().getArrOfServices());
        }
        else
        {
            myLogger.debug("No error handling since no service has requested this in the xml init file");
        }

        // Time to start threads up for all services in map
        if (myServiceThreads != null && myServiceThreads.size() > 0)
        {
            TimerFascade.getInstance().initServices(myServiceThreads);
        }
        else
        {
            myLogger.info("No services found, exiting");
            System.out.println("No services found, exiting");
            System.exit(0);
        }

        //register server in rmi for remote operations
        startRemoteServicesServer();

        System.out.println(">> See log files or start iris gui");
    }

    /**
     * Loads all settings for all services configured and if ok returns a collection
     * of these.
     * If no services are found or something went wrong parsing the config file
     * Iris will exit from here.
     *
     * @param arrListOfServices
     * @return
     */
    private ArrayList loadSettingsFromXmlConfigFile(ArrayList arrListOfServices)
    {
        String servicepropes = System.getProperty("iris.initservices");
        if (servicepropes == null)
        {
            myLogger.info("Your Iris property file is incorrect - missing iris.initservices element!");
            System.out.println(">>Your iris property file is incorrect - missing iris.initservices element!");
            System.exit(0);
        }
        System.out.print("->Reading configuration and validating settings" + " (" + servicepropes + ")" + "...");
        arrListOfServices = ServerSystemConfigHandler.getInstance().getServices();
        if (arrListOfServices != null && arrListOfServices.size() == 0)
        {
            System.out.println("No services could be configured from settings - none found");
            System.out.println("Exiting and closing server");
            System.out.println("" + ServerSystemConfigHandler.getInstance().toString()  );
            myLogger.info("Is xml file empty or perhaps it does not conform to the xml schema? No services could be configured from settings.");
            System.exit(0);
        }
        if (arrListOfServices == null)
        {
            System.out.println("Is xml file empty or perhaps it does not conform to the xml schema? No services could be configured from settings.");
            System.out.println("Exiting and closing server");
            myLogger.info("Is xml file empty or perhaps it does not conform to the xml schema? No services found.");
            System.out.println("Iris not started - see log file");
            System.exit(0);
        }
        System.out.println("... ok");
        return arrListOfServices;
    }

    // todo fix
    private void startErrorHandlers(Service[] arrServices)
    {
        arrServices = null;
        if (arrServices != null)
        {
            // For every service ..
            for (int serviceNr = 0; serviceNr < arrServices.length; serviceNr++)
            {
                // If service is in list and has attribute error folder (Pollers do not have this attribute)
                Service arrService = arrServices[serviceNr];
                if (arrService.getErrorfoldername() != null)
                {
                    File readFromErrorFolder = new File(arrService.getErrorfoldername());
                    File[] curFiles = readFromErrorFolder.listFiles();
                    if (!arrService.isErrorhandlerOn())
                    {
                        myLogger.info("No resending enabled for service  : " + arrService.getId());
                        NDC.pop();
                        return;
                    }
                    else if (curFiles == null || curFiles.length == 0)
                    {
                        myLogger.info("Error resend is on for service but no files to resend for service at this moment : " + arrService.getId());
                        NDC.pop();
                        return;
                    }
                    else if (curFiles != null && curFiles.length == 1 && curFiles[0].isDirectory())
                    {
                        myLogger.info("Error resend is on for service but there are only folders in error folder for service (only subfolder exist) : " + arrService.getId());
                        NDC.pop();
                        return;
                    }
                    else
                    {
                        myLogger.info(curFiles.length + " number of files found for resending for service  : " + arrService.getId());

                        // todo handle all types of services
                        if (arrService.getType().equals(ServiceFactory.FILETOFILE))
                        {
                            //                     FileToFileErrorMessageHandler fileToFileErrorMessageHandler =  new FileToFileErrorMessageHandler( arrService, curFiles , arrService.gett );
                        }
                        else if (arrService.getType().equals(ServiceFactory.FILETOEJB))
                        {

                        }
                        else if (arrService.getType().equals(ServiceFactory.FILETOEMAIL))
                        {
                        }
                    }
                }
            }
        }

    }


    /**
     * Binds to rmi registry for remote operations.
     *
     */
    private void startRemoteServicesServer()
    {
        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new RMISecurityManager());
        }

        try
        {
            myLogger.info("Starting core-server service for remote operations. Binding " + System.getProperty("iris.rmi.service.name"));
            irisOperations = new IrisOperationsImpl(myServiceThreads);
            Naming.rebind(System.getProperty("iris.rmi.service.name"), irisOperations);
            myLogger.info("Started core-server service for remote operations is now bound to RMI registry");
            System.out.println("->Binding to RMI registry succesfull");
        }
        catch (Exception e)
        {
            System.out.println("Could not bind to RMI registry for remote ops.");
            myLogger.error("Could not bind to RMI registry for remote ops.", e);

        }
    }

    /**
     * Starts Iris... and adds shutdown hook.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        Iris iris = new Iris();
        Thread thr = new Thread(iris);
        Runtime.getRuntime().addShutdownHook(thr);
    }

    /**
     * Reads config files log4j and iris
     */
    static
    {
        ResourceBundle resourceBundle = null;
        try
        {
            resourceBundle = ResourceBundle.getBundle("log4j");
            Enumeration proplist = resourceBundle.getKeys();
            proplist = resourceBundle.getKeys();
            Properties props = new Properties();
            while (proplist.hasMoreElements())
            {
                String tKey = (String) proplist.nextElement();
                props.setProperty(tKey, resourceBundle.getString(tKey));
            }
            PropertyConfigurator.configure(props);
            System.out.println("->Logging configured (log4j.properties)");
        }
        catch (MissingResourceException e)
        {
            System.out.println("Missing resource : log4j.properties");
            System.out.println("Iris not started - see log file");
            System.exit(0);
        }

        // reads properties for retrieving config file (iris.initservices)
        // and rmi registry settings (iris.rmi.service.name)
        try
        {
            resourceBundle = ResourceBundle.getBundle("iris");
            Enumeration proplist = resourceBundle.getKeys();
            while (proplist.hasMoreElements())
            {
                String tKey = (String) proplist.nextElement();
                System.setProperty(tKey, resourceBundle.getString(tKey));
            }
            System.out.println("->Iris properties loaded (iris.properties)");
        }
        catch (MissingResourceException e)
        {
            System.out.println("Missing resource : no property file iris.properties");
            e.printStackTrace();
            System.out.println("Iris not started - see log file");
            System.exit(0);
        }
        catch (Exception e)
        {
            System.out.println("Fatal exception");
            e.printStackTrace();
            System.out.println("Iris not started - see log file");
            System.exit(0);
        }
    }

    /**
     * Called by vm when when we get a controlled exit.
     */
    public void onExit()
    {
        myLogger.debug("Exiting Iris...");
        NDC.remove();
        sendEmail();
    }

    /**
     * Sends an email on shutting down iris
     */
    private void sendEmail()
    {
        myLogger.info("Trying to send an email alert using file : " + emailonshutdown);
        String email = null;
        try
        {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            Document document;
            factory.setNamespaceAware(true);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource source = new InputSource(new FileReader(emailonshutdown));
            document = builder.parse(source);
            Node firstchild = document.getFirstChild();
            NamedNodeMap attributes = firstchild.getAttributes();
            Node message = attributes.getNamedItem("messagetype");
            InstantEmailMessageHandler.getInstance().handleMessage(document, null, null);
        }
        catch (Exception ex)
        {
            myLogger.error("Could not send an email alert", ex);
        }
    }

    /**
     * Called by vm when when we get a controlled exit.
     */
    public void run()
    {
        onExit();
    }


    private String getArchiverCronIntervall()
    {
//        String complete = ServerSystemConfigHandler.getInstance().getGlobalSettings().getArchivehandler().getCronintervall();
  //      return ServerSystemConfigHandler.extractCronintervall(complete);
    	throw new UnsupportedOperationException();
    }

}
