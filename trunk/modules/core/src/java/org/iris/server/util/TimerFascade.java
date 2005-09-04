package org.iris.server.util;

import org.apache.log4j.Logger;
import org.iris.server.filehandlers.FileMessageHandler;
import org.iris.server.filehandlers.FileMessageHandlerFactory;
import org.iris.server.util.console.SystemOutThread;
import org.iris.server.settings.xml.SettingsException;
import org.iris.server.util.config.ServerSystemConfigHandler;
import org.iris.server.util.config.Service;
import org.iris.server.util.filehelpers.FileUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;

public class TimerFascade
{
    Logger myLogger = Logger.getLogger(TimerFascade.class.getName());
    private HashMap myServiceMap;
    private int numberOfServicesStarted = 0;
    private static TimerFascade myInstance;

    private TimerFascade()
    {}

    public static TimerFascade getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new TimerFascade();
        }
        return myInstance;
    }

    /**
     * Map with services
     *
     * @param serviceMap
     */
    public void initServices(HashMap serviceMap)
    {
        myServiceMap = serviceMap;
        Collection col = serviceMap.values();
        Iterator iter = col.iterator();

        System.out.println("->Starting scheduled services");

        while (iter.hasNext())
        {
            // Use this thread to printout dots to indicate progress
            SystemOutThread outThr = new SystemOutThread();
            Thread printer = new Thread(outThr);
            printer.start();


            FileMessageHandler msghandler = (FileMessageHandler) iter.next();
            System.out.print("\t" + numberOfServicesStarted + ": " + msghandler.getServiceId() + " is starting.");
            if (msghandler == null)
            {
                myLogger.error("Could not start service, got null messagehandler. Is configuration file ok?");
                System.out.print(" -> NOT started");
                return;
            }

            try
            {
                Timer myTimer = new Timer();
                myLogger.info(msghandler.getServiceId() + " is starting..." + msghandler.getStart() + "  " +
                    msghandler.getIntervall());
                myTimer.schedule(msghandler, msghandler.getStart(), msghandler.getIntervall());
                msghandler.setTimer(myTimer);
                msghandler.setServiceStatus(FileMessageHandler.RUNNING);
                System.out.println(" started ok");
                numberOfServicesStarted++;
                myLogger.info(msghandler.getServiceId() + "started.");
            }
            catch (Exception ex)
            {
                myLogger.fatal("Failed starting messagehandler : " + msghandler);
                System.out.println("Failed starting messagehandler : " + msghandler);
                myLogger.fatal(ex.getMessage(), ex);
                System.exit(0);
            }

            outThr.stopThread();
        }
        System.out.println( "\t" + numberOfServicesStarted + " of (" + col.size() +
            ") number of scheduled services actually started");
        myLogger.info(numberOfServicesStarted + " of (" + col.size() + ") number of services started");

    }

    public void start(String serviceName)
    {
        if (myServiceMap.isEmpty())
        {
            myLogger.info("No services in collection");
            return;
        }

        myLogger.debug( "Got call to start service up " +  serviceName );
        Object obj = myServiceMap.get(serviceName);
        if (obj != null)
        {
            Service service = (Service) ServerSystemConfigHandler.getInstance().getServicesMap().get(serviceName);
            if (service != null)
            {
                myLogger.debug( "Got service from map" );
                FileMessageHandler messageHandler = null;
                try
                {
                    messageHandler = FileMessageHandlerFactory.createNotStartedInstance(service);
                    myServiceMap.put(serviceName, messageHandler);

                    if (messageHandler.getServiceStatus() == FileMessageHandler.STOPPED ||
                        messageHandler.getServiceStatus() == FileMessageHandler.NOTSTARTED)
                    {
                        try
                        {
                            Timer myTimer = new Timer();
                            myLogger.info(messageHandler.getServiceId() + " is starting..." + messageHandler.getIntervall());
                            messageHandler.setTimer(myTimer);
                            myTimer.schedule(messageHandler, 0, messageHandler.getIntervall());
                            messageHandler.setServiceStatus(FileMessageHandler.RUNNING);
                            myServiceMap.put(serviceName, messageHandler);
                        }
                        catch (Exception ex)
                        {
                            myLogger.error("Starting service failed : " + serviceName, ex);
                            messageHandler.stopThread();
                            messageHandler.setServiceStatus(FileMessageHandler.STOPPED);
                        }
                    }
                    else if (messageHandler.getServiceStatus() == FileMessageHandler.RUNNING)
                    {
                        myLogger.info("Service " + serviceName + " already running");
                    }
                }
                catch (Exception ex1)
                {
                    myLogger.error("Could not create messagehandler - failed starting service", ex1);
                }
            }
            else
            {
                myLogger.info("No service with name " + serviceName + " has been configured");
            }
        }
        else
        {
            myLogger.info("No service found : " + serviceName);
        }

    }

    public void stop(String serviceName)
    {
        myLogger.info("Stopping " + serviceName);
        if (myServiceMap.isEmpty())
        {
            myLogger.info("No services in collection.");
            return;
        }
        Object service = myServiceMap.get(serviceName);
        if (service != null && service instanceof FileMessageHandler)
        {
            FileMessageHandler messageHandler = (FileMessageHandler) service;
            if (messageHandler.getServiceStatus() == FileMessageHandler.RUNNING)
            {
                myLogger.debug("Service is running, stopping it...");
                messageHandler.stopThread();
                messageHandler.setServiceStatus(FileMessageHandler.STOPPED);
                myLogger.info("Stopped service " + serviceName);

            }
            else if (messageHandler.getServiceStatus() == FileMessageHandler.STOPPED)
            {
                myLogger.info("Service " + serviceName + " already stopped.");
            }
            else
            {
                myLogger.info("Unknown status " + messageHandler.getServiceStatus());
            }
        }
        else
        {
            myLogger.info("No service with name : " + serviceName + " exists or it has already been stopped.");
        }
    }

    /**
     * Method does a simple lookup in the service map and checks if service status.
     *
     * @param serviceId
     * @return true if service is found and status is RUNNING
     */
    public boolean isAlive(String serviceId)
    {
        myLogger.debug("Is service alive? servicid : " + serviceId );
        Service service = (Service) ServerSystemConfigHandler.getInstance().getServicesMap().get(serviceId);
        if (service != null)
        {
            Object obj = myServiceMap.get(serviceId);
            if (obj != null)
            {
                FileMessageHandler messageHandler = (FileMessageHandler) obj;
                try
                {
                    if (messageHandler == null)
                    {
                        myLogger.info("Service " + serviceId + " is NOT running ");
                        return true;
                    }
                    else if (messageHandler.getServiceStatus() == FileMessageHandler.RUNNING)
                    {
                        myLogger.info("Service " + serviceId + " is running");
                        return true;
                    }
                }
                catch (Exception ex1)
                {
                    myLogger.error("Could not create messagehandler - failed pinging service", ex1);
                }
            }
            else
            {
                myLogger.info("Service has not been started.");
                return false;
            }
        }
        else
        {
            myLogger.info("No service with name " + serviceId + " has been configured");
            return false;
        }
        return false;
    }

    public HashMap pingServices() throws SettingsException
    {
        myLogger.debug("Checking if services are alive");
        HashMap serviceStatusMapResult = new HashMap();
        ServerSystemConfigHandler serverSystemConfigHandler = ServerSystemConfigHandler.getInstance();
        Service[] arrService = serverSystemConfigHandler.getArrOfServices();
        myLogger.debug("Number of services : " + arrService.length);
        for (int i = 0; i < arrService.length; i++)
        {
            Service service = (Service) serverSystemConfigHandler.getServicesMap().get(arrService[i].getId());
            if (service != null)
            {
                Object obj = myServiceMap.get(arrService[i].getId());
                if (obj != null)
                {
                    FileMessageHandler messageHandler = (FileMessageHandler) obj;
                    try
                    {
                        if (messageHandler.getServiceStatus() == FileMessageHandler.RUNNING)
                        {
                            myLogger.debug("Service " + arrService[i].getId() + "  running");
                            serviceStatusMapResult.put(arrService[i].getId(), new Integer(FileMessageHandler.RUNNING));
                        }
                        else if (messageHandler.getServiceStatus() == FileMessageHandler.NOTSTARTED)
                        {
                            myLogger.debug("Service " + arrService[i].getId() + "  not started");
                            serviceStatusMapResult.put(arrService[i].getId(), new Integer(FileMessageHandler.NOTSTARTED));
                        }
                        else if (messageHandler.getServiceStatus() == FileMessageHandler.DOESNOTEXIST)
                        {
                            myLogger.debug("Service " + arrService[i].getId() + "  does not exist");
                            serviceStatusMapResult.put(arrService[i].getId(), new Integer(FileMessageHandler.DOESNOTEXIST));
                        }
                        else if (messageHandler.getServiceStatus() == FileMessageHandler.STOPPED)
                        {
                            myLogger.debug("Service " + arrService[i].getId() + "  stopped");
                            serviceStatusMapResult.put(arrService[i].getId(), new Integer(FileMessageHandler.STOPPED));
                        }
                    }
                    catch (Exception ex1)
                    {
                        myLogger.error("Could not create messagehandler - failed pinging service", ex1);
                    }
                }
                else
                {
                    myLogger.debug("Service " + arrService[i].getId() + "  does not exist");
                    serviceStatusMapResult.put(arrService[i].getId(), new Integer(FileMessageHandler.DOESNOTEXIST));
                }
            }
            else
            {
                myLogger.debug("Service " + arrService[i].getId() + "  does not exist");
                serviceStatusMapResult.put(arrService[i].getId(), new Integer(FileMessageHandler.DOESNOTEXIST));
            }
        }
        return serviceStatusMapResult;
    }

    public ServiceDetailsVO[] getDetailsOfServices()
    {
        myLogger.debug("Checking if services are alive");
        ServerSystemConfigHandler serverSystemConfigHandler = ServerSystemConfigHandler.getInstance();
        Service[] arrService = serverSystemConfigHandler.getArrOfServices();
        ServiceDetailsVO[] serviceStatus = new ServiceDetailsVO[arrService.length];

        myLogger.debug("Checking number of service " + arrService.length);
        for (int i = 0; i < arrService.length; i++)
        {
            Service service = (Service) serverSystemConfigHandler.getServicesMap().get(arrService[i].getId());
            if (service != null)
            {
                Object obj = myServiceMap.get(arrService[i].getId());
                ServiceDetailsVO aServiceDetailsVO = new ServiceDetailsVO();
                if (obj != null)
                {
                    FileMessageHandler messageHandler = (FileMessageHandler) obj;
                    try
                    {
                        if (messageHandler.getServiceStatus() == FileMessageHandler.RUNNING)
                        {
                            myLogger.debug("Service " + arrService[i].getId() + "  running");
                            aServiceDetailsVO.setStatus(FileMessageHandler.RUNNING);
                        }
                        else if (messageHandler.getServiceStatus() == FileMessageHandler.NOTSTARTED)
                        {
                            myLogger.debug("Service " + arrService[i].getId() + "  not started");
                            aServiceDetailsVO.setStatus(FileMessageHandler.NOTSTARTED);
                        }
                        else if (messageHandler.getServiceStatus() == FileMessageHandler.DOESNOTEXIST)
                        {
                            myLogger.debug("Service " + arrService[i].getId() + "  does not exist");
                            aServiceDetailsVO.setStatus(FileMessageHandler.DOESNOTEXIST);
                        }
                        else if (messageHandler.getServiceStatus() == FileMessageHandler.STOPPED)
                        {
                            myLogger.debug("Service " + arrService[i].getId() + "  stopped");
                            aServiceDetailsVO.setStatus(FileMessageHandler.STOPPED);
                        }

                        aServiceDetailsVO.setServiceName(service.getId());
                        aServiceDetailsVO.setNumberOfArchivedFiles(FileUtils.count(messageHandler.getArchivefoldername()));
                        aServiceDetailsVO.setNumberOfErrorFiles(FileUtils.count(messageHandler.getErrorfoldername()));
                        aServiceDetailsVO.setNumberOfInFIles(FileUtils.count(messageHandler.getReadFromDir()));
                        aServiceDetailsVO.setLastCreatedArchiveFile(FileUtils.lastModifiedDateOfFile(messageHandler.
                            getArchivefoldername()));
                        aServiceDetailsVO.setLastCreatedErrorFile(FileUtils.lastModifiedDateOfFile(messageHandler.
                            getErrorfoldername()));
                        aServiceDetailsVO.setLastReceivedInFile(FileUtils.lastModifiedDateOfFile(messageHandler.
                            getReadFromDir()));

                        //myLogger.debug(aServiceDetailsVO);
                        serviceStatus[i] = aServiceDetailsVO;
                    }
                    catch (Exception ex1)
                    {
                        myLogger.error("Could not create messagehandler - failed pinging service", ex1);
                    }
                }
                else
                {
                    myLogger.debug("Service " + arrService[i].getId() + "  does not exist");
//                    serviceStatus.put(arrService[i].getId(), new Integer(FileMessageHandler.DOESNOTEXIST) );
                }
            }
        }
        return serviceStatus;

    }

}