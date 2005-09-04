package org.iris.client.swing.panels.logging;

import java.io.*;
import java.net.*;

import org.apache.log4j.*;
import org.apache.log4j.spi.*;
import org.iris.client.swing.util.statuspanel.*;

/**
 * Connect to socket in server process and receives logging events.
 */
public class SocketHubConnector
    implements Runnable
{
    Logger myLogger = Logger.getLogger(SocketHubConnector.class.getName());
    static final int DEFAULT_PORT = 4560;
    static final int DEFAULT_RECONNECTION_DELAY = 10000;
    private String remoteHost;
    private InetAddress address;
    private int port = DEFAULT_PORT;
    ObjectInputStream ois;
    private int reconnectionDelay = DEFAULT_RECONNECTION_DELAY;
    private Connector connector;
    private LoggingTableModel myTableModel = null;
    boolean isReceivingLogEvents = true;

    public SocketHubConnector()
    {
    }

    public static void main(String[] args)
    {
        try
        {
            SocketHubConnector sa = new SocketHubConnector("zeus2", 4560, null);
            Thread t = new Thread(sa);
            t.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public SocketHubConnector(InetAddress address, int port, LoggingTableModel aTableModel) throws Exception
    {
        this.myTableModel = aTableModel;
        this.address = address;
        this.remoteHost = address.getHostName();
        this.port = port;
        connect(address, port);
    }

    public SocketHubConnector(String host, int port, LoggingTableModel aTableModel) throws Exception
    {
        this.myTableModel = aTableModel;
        this.port = port;
        this.address = getAddressByName(host);
        this.remoteHost = host;
        myLogger.debug(remoteHost + " " + port);
        connect(address, port);
    }

    public void activateOptions() throws Exception
    {
        connect(address, port);
    }

    /**
     * Drop the connection to the remote host and release the underlying
     * connector thread if it has been created
     * */
    public void cleanUp()
    {
        if (ois != null)
        {
            try
            {
                ois.close();
            }
            catch (IOException e)
            {
                myLogger.error("Could not close oos.", e);
            }
            ois = null;
        }
        if (connector != null)
        {
            //myLogger.debug("Interrupting the connector.");
            connector.interrupted = true;
            connector = null; // allow gc
        }
    }

    void connect(InetAddress address, int port) throws Exception
    {
        myLogger.debug("Connecting to server");
        if (this.address == null)
        {
            return;
        }
        try
        {
            cleanUp();

            ois = new ObjectInputStream(new Socket(address, port).getInputStream());
            //oos = new ObjectOutputStream(new Socket(address, port).getOutputStream());
            myLogger.debug("Connected");
        }
        catch (Exception e)
        {
            String msg = "Could not connect to remote log4j server at [" + address.getHostName() + "," + port + "].";
            if (reconnectionDelay > 0)
            {
                msg += " We will try again later.";
                fireConnector(); // fire the connector thread
            }

            throw new Exception(msg, e);

        }
    }

    /*    public void append(LoggingEvent event)
        {
            if (event == null)
            {
                return;
            }
            if (address == null)
            {
                return;
            }
            if (oos != null)
            {
                try
                {
                    if (locationInfo)
                    {
                        event.getLocationInformation();
                    }
                    oos.writeObject(event);
                    //myLogger.debug("=========Flushing.");
                    oos.flush();
                    if (++counter >= RESET_FREQUENCY)
                    {
                        counter = 0;
                        // Failing to reset the object output stream every now and
                        // then creates a serious memory leak.
                        //System.err.println("Doing oos.reset()");
                        oos.reset();
                    }
                }
                catch (IOException e)
                {
                    oos = null;
                    myLogger.warn("Detected problem with connection: " + e);
                    if (reconnectionDelay > 0)
                    {
                        fireConnector();
                    }
                }
            }
        }
     */
    void fireConnector()
    {
        if (connector == null)
        {
            myLogger.debug("Starting a new connector thread.");
            connector = new Connector();
            connector.setDaemon(true);
            connector.setPriority(Thread.MIN_PRIORITY);
            connector.start();
        }

    }

    static InetAddress getAddressByName(String host)
    {
        try
        {
            return InetAddress.getByName(host);
        }
        catch (Exception e)
        {
            e.printStackTrace();
//            myLogger.error("Could not find address of [" + host + "].", e);
            return null;
        }
    }

    public void setRemoteHost(String host)
    {
        address = getAddressByName(host);
        remoteHost = host;
    }

    public String getRemoteHost()
    {
        return remoteHost;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public int getPort()
    {
        return port;
    }

    public void setReconnectionDelay(int delay)
    {
        this.reconnectionDelay = delay;
    }

    public int getReconnectionDelay()
    {
        return reconnectionDelay;
    }

    public void run()
    {
        while (isReceivingLogEvents)
        {
            try
            {

                final LoggingEvent event = (LoggingEvent) ois.readObject();
                myTableModel.addEvent(new LoggingTableModelItem(event));

            }
            catch (Exception ex1)
            {
                fireConnector();
                try
                {
                    Thread.sleep(reconnectionDelay + 1000);
                }
                catch (InterruptedException ex)
                {
                }

            }
        }

    }

    class Connector
        extends Thread
    {
        boolean interrupted = false;
        public void run()
        {
            Socket socket;
            while (!interrupted)
            {
                try
                {
                    sleep(reconnectionDelay);
                    StatusJPanel.getInstance().setStatusText("Connecting to iris server for logging");
                    StatusJPanel.getInstance().startProgressBar();

                    myLogger.debug("Attempting connection to " + address.getHostName());
                    socket = new Socket(address, port);
                    synchronized (this)
                    {
                        ois = new ObjectInputStream(socket.getInputStream());
                        connector = null;
                        myLogger.debug("Connection established. Exiting connector thread.");
                        isReceivingLogEvents = true;
                        break;
                    }

                }
                catch (InterruptedException e)
                {
                    myLogger.debug("Connector interrupted. Leaving loop.", e);
                    return;
                }
                catch (java.net.ConnectException e)
                {
                    myLogger.warn("Connect failed to server for logging. Remote host " + address.getHostName() +
                        " refused connection on port " + port + ". Is the remote server started?");
                }
                catch (IOException e)
                {
                    myLogger.warn("Connect failed to server for logging. Remote host " + address.getHostName() +
                        " refused connection on port " + port + ". Is the remote server started?");
                }
                StatusJPanel.getInstance().stopProgressBar();

            }
        }

    }

}
