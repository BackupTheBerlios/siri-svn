package org.iris.server.util.console;

/**
 * Used to print progress to console when server starts up.
 *
 * @author gepo
 */
public class SystemOutThread
    implements Runnable
{
    boolean isRunning = true;
    private int outputIntervall = 1000;

    public SystemOutThread()
    {
    }

    public void run()
    {
        while (isRunning)
        {
            System.out.print(".");
            try
            {
                Thread.sleep( outputIntervall );
            }
            catch (InterruptedException ex)
            {
            }
        }
        return;
    }

    public void stopThread()
    {
        isRunning = false;
    }
}