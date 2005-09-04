package org.iris.client.swing.panels.logging;

import java.io.*;
import java.net.*;

import org.apache.log4j.*;
import org.apache.log4j.spi.*;

class LoggingReceiver
    extends Thread
{
    private static final Logger myLogger = Logger.getLogger(LoggingReceiver.class);
    private final LoggingTableModel mModel;
    private final ServerSocket mSvrSock;

    private class Slurper
        implements Runnable
    {
        private final Socket mClient;

        Slurper(Socket aClient)
        {
            mClient = aClient;
        }

        public void run()
        {
            myLogger.debug("Starting to get data");
            try
            {
                final ObjectInputStream ois = new ObjectInputStream(mClient.getInputStream());
                while (true)
                {
                    final LoggingEvent event = (LoggingEvent) ois.readObject();
                    System.out.println("GOT myLogger EVENT " + event.getMessage());
                    mModel.addEvent(new LoggingTableModelItem(event));
                }
            }
            catch (EOFException e)
            {
                myLogger.info("Reached EOF, closing connection");
            }
            catch (SocketException e)
            {
                myLogger.info("Caught SocketException, closing connection");
            }
            catch (IOException e)
            {
                myLogger.warn("Got IOException, closing connection", e);
            }
            catch (ClassNotFoundException e)
            {
                myLogger.warn("Got ClassNotFoundException, closing connection", e);
            }

            try
            {
                mClient.close();
            }
            catch (IOException e)
            {
                myLogger.warn("Error closing connection", e);
            }
        }
    }

    LoggingReceiver(LoggingTableModel aModel, int aPort) throws IOException
    {
        setDaemon(true);
        mModel = aModel;
        mSvrSock = new ServerSocket(aPort);
    }

    public void run()
    {
        myLogger.info("Thread started....");
        try
        {
            while (true)
            {
                myLogger.debug("Waiting for a connection...");
                final Socket client = mSvrSock.accept();
                myLogger.debug("Got a connection from " + client.getInetAddress().getHostName());
                final Thread t = new Thread(new Slurper(client));
                t.setDaemon(true);
                t.start();
            }
        }
        catch (IOException e)
        {
            myLogger.error("Error in accepting connections, stopping.", e);
        }
    }
}
