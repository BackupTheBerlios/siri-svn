package org.iris.server.remoteservices.rmi;

// -Djava.rmi.server.codebase=file:/C:/gepo/mycvsprojects/iris/classes/  -Djava.security.policy=java.policy     -Dvbroker.agent.port=13100
import org.apache.log4j.Logger;
import org.iris.server.settings.xml.SettingsException;
import org.iris.server.statistics.StatisticsHandler;
import org.iris.server.statistics.StatisticsVO;
import org.iris.server.util.ServiceDetailsVO;
import org.iris.server.util.TimerFascade;
import org.iris.server.util.filehelpers.FileUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 * Implementation of the remote services interface for iris and iris services.
 * 
 */
public class IrisOperationsImpl
    extends UnicastRemoteObject
    implements IrisOperations
{
    //    private HashMap myServices;
    Logger myLogger = Logger.getLogger(IrisOperationsImpl.class.getName());

    public IrisOperationsImpl(HashMap services) throws RemoteException
    {
        super();
        //      myServices = services;
    }



    public void stopService(String serviceName) throws RemoteException
    {
        myLogger.info("Got call to stopp service : " + serviceName);
        if (serviceName != null)
        {
            TimerFascade.getInstance().stop(serviceName);
        }
        else
        {
            myLogger.info("No service with name : " + serviceName + " exists.");
            throw new  RemoteException( "No service with name : " + serviceName + " exists." );
        }
    }

    public void startService(String serviceName) throws RemoteException
    {
        myLogger.info("Starting service " + serviceName);
        if (serviceName != null)
        {
            TimerFascade.getInstance().start(serviceName);
        }
        else
        {
            myLogger.info("No service with name : " + serviceName + " exists.");
            throw new  RemoteException( "No service with name : " + serviceName + " exists." );
        }
    }


    public String getConfig() throws RemoteException
    {
        String fileLocation = System.getProperty("iris.initservices");
        try
        {
            myLogger.debug( "Retrieving configuration settings for iris server using : " + fileLocation);
            return FileUtils.readFile(fileLocation);
        }
        catch (IOException ex)
        {
            throw new RemoteException("Could not read file at : " + fileLocation);
        }
    }

    public boolean isAlive(String serviceName) throws RemoteException
    {
        myLogger.info("Got call to ping service : " + serviceName);
        if (serviceName != null)
        {
            TimerFascade.getInstance().isAlive( serviceName );
            return true;
        }
        else
        {
            myLogger.info("No service with name : " + serviceName + " exists.");
            throw new  RemoteException( "No service with name : " + serviceName + " exists." );
        }

   }

    public HashMap pingServices()  throws RemoteException
    {
        try
        {
            return TimerFascade.getInstance().pingServices();
        }
        catch (SettingsException e)
        {
            String msg = "Caught exception: Client tried pinging services and failed. Not initialised properly?";
            myLogger.debug( msg , e);
            throw new RemoteException( msg , e);
        }
    }

    public ServiceDetailsVO[] getDetailsOfServices() throws RemoteException
    {
        return TimerFascade.getInstance().getDetailsOfServices();
    }


    public StatisticsVO[] getStatisticsForService(String service) throws RemoteException
    {
        StatisticsVO[] result = null;
        try
        {
            result = StatisticsHandler.getInstance().getStatisticsForService(service);
        }
        catch (Exception ex)
        {
            throw new RemoteException( ex.getMessage() );
        }
        return result;
    }

    public StatisticsVO getLastStatisticsForService(String service) throws RemoteException
     {
         StatisticsVO result = null;
         try
         {
             result = StatisticsHandler.getInstance().getLastStatisticsForService(service);
         }
         catch (Exception ex)
         {
             throw new RemoteException( ex.getMessage() );
         }
         return result;
     }

    public HashMap getMemoryStatistics() throws RemoteException
    {
        Runtime runtime = Runtime.getRuntime();
        long lfreeMemory = runtime.freeMemory();
        long ltotalMemory = runtime.totalMemory();


        Long freeMemory = new Long( lfreeMemory/ 1048576L );
        Long totalMemory = new Long( ltotalMemory/ 1048576L );

        long lmemoryUsed =  (ltotalMemory - lfreeMemory) / 1048576L ;


        Long memoryUsed =  new Long( lmemoryUsed );
        HashMap memoryStatts = new HashMap();

        memoryStatts.put( "freeMemory", freeMemory  );
        memoryStatts.put( "totalMemory", totalMemory  );
        memoryStatts.put( "memoryUsed", memoryUsed  );
        return memoryStatts;
    }

    public static void main(String[] args)
    {
        IrisOperationsImpl imp = null;
        try
        {
            imp = new IrisOperationsImpl(null);
            imp.getMemoryStatistics();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}