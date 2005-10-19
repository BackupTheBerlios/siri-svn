package org.siri.core.readers;

import org.siri.command.Command;
import org.siri.command.FileWriterCommand;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;
import java.util.Queue;

/**
 * Workerthreads are responsible for taking commands and executing them.
 */
public class WorkerThread  implements Callable<Boolean>
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(WorkerThread.class);
    private Queue queue;
    
    public WorkerThread(Queue queue)
    {
        this.queue = queue;
    }
    public Boolean call() throws Exception
    {
        Command cmd = (Command)queue.poll();
        if (cmd instanceof FileWriterCommand)
        {
            logger.debug("Got filewrite command");
            //(FileWriterCommand)cmd.
            cmd.execute();
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
