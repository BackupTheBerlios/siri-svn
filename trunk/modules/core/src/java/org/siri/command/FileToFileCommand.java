package org.siri.command;

import org.siri.core.writer.FileWriter;

/**
 * A concrete command to be performed by a writer, held by the 
 * CommandInvoker.
 *
 */
public class FileToFileCommand implements Command
{
    FileWriter writer;

    /**
     * Constructor takes a writer.     
     * @param writer FileWriter
     */
    public FileToFileCommand(FileWriter writer)
    {
        this.writer = writer;
    }

    /**
     * Execute command on the writer.
     */
    public void execute()
    {
        writer.readSendFiles(null,null);
        writer.backupFiles(null);

        //writer.backup();
        //etc
    }
}
