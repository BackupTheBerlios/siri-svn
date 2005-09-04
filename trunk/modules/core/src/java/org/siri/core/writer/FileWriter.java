package org.siri.core.writer;

import org.apache.log4j.Logger;

/**
 *
 * Acts as a receiver, which receives command from the invoker
 * and writes messages to file.
 */
public class FileWriter extends AbstractWriter
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(FileWriter.class);

    public FileWriter()
    {
        super();
    }

    public FileWriter(String message)
    {
        super();
        message = message;
    }


    public void readSendFiles( String readFromDir , String copyToDir)
    {
        logger.debug( "Reading and sending message : " + message );
    }

    public void backupFiles( String backupToDir )
    {
        logger.debug( "Backing up file" );
    }
}
