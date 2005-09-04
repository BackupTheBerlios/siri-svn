package org.iris.server.remoteservices.rmi;

import java.rmi.*;
import java.util.HashMap;
import org.iris.server.util.ServiceDetailsVO;
import org.iris.server.statistics.StatisticsVO;

/**
 * Clients calling operations remotely on the Iris server uses this interface.
 */
public interface IrisOperations
    extends Remote
{
    /**
     * Stop service with this name
     * @param serviceName
     * @throws RemoteException
     */
    public void stopService(String serviceName) throws RemoteException;

    /**
     * Start service with this name
     * @param serviceName
     * @throws RemoteException
     */
    public void startService(String serviceName) throws RemoteException;

    /**
     * Retrieve iris configurations as a string
     * @return
     * @throws RemoteException
     */
    public String getConfig() throws RemoteException;

    /**
     * Ping service with this name
     * @param serviceName
     * @return
     * @throws RemoteException
     */
    public boolean isAlive(String serviceName) throws RemoteException;

    /**
     * Pings all services
     * @return
     * @throws RemoteException
     */
    public HashMap pingServices() throws RemoteException;

    public ServiceDetailsVO[] getDetailsOfServices() throws RemoteException;

    public StatisticsVO[] getStatisticsForService(String service) throws RemoteException;

    public StatisticsVO getLastStatisticsForService(String service) throws RemoteException;

    public HashMap getMemoryStatistics() throws RemoteException;
}
