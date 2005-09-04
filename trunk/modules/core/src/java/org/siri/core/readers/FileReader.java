/**
 * FileReader.java
 */
package org.siri.core.readers;

import org.apache.log4j.Logger;
import org.iris.server.util.config.ServiceFileFile;
import org.iris.server.util.config.Service;
import org.siri.command.Command;
import org.siri.command.FileToFileCommand;
import org.siri.core.util.queue.QueueTalker;
import org.siri.core.writer.FileWriter;

import java.util.Arrays;
import java.io.File;


/**
 * Creates commands an puts them on queue for further handling by
 * invokers and receivers.
 * 
 * @author Georges Polyzois
 */
public class FileReader extends AbstractReader
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(FileReader.class);
    QueueTalker talker = new QueueTalker();
    FileWriter fileWriter = new FileWriter();
    ServiceFileFile service;

    public FileReader(Service service)
    {
        service = (ServiceFileFile) service;
    }


    public void run()
    {
        logger.debug( "Reading a file" );
        //FileUtils.getMessageContent()


        Command com = new FileToFileCommand(fileWriter);
        talker.sendToQueue(com);

    }

    private Command[] read()
    {
//        File readFromFile = new File(readFromDir);
        File[] curFiles = readFromFile.listFiles();
        int fileCollectionSize = getFileCollectionSize(curFiles);
        Command[] commands = new Command[fileCollectionSize];

//        logStatistics( fileCollectionSize );

        if ( !isMessagesReceived(curFiles) )
        {
            return null;
        }

        getSortedFiles(curFiles);

        String fileNameOriginal = null;
        for (int i = 0; i < fileCollectionSize; i++)
        {
            fileNameOriginal = curFiles[i].getName();
            //empty file
            if (curFiles[i].length() == 0)
            {
                curFiles[i].delete();
                return null;
            }
            try
            {
                fileWriter.setMessage("New message read from file");
                Command com = new FileToFileCommand(fileWriter);
                commands[i] = com;
  /*              if (createuniquename)
                {
                    fileNameUnique = createUniqueFileName(curFiles[i].getName());
                    fileNameOut = fileNameUnique;
                }
                if (transformon)
                {
                    sendAndTransform(curFiles[i], fileNameOut, fileNameOriginal);
                }
                else
                {
                    send( curFiles[i] , new File( copyToDir + fileNameUnique )  );
                }
                */
            }
            catch (Exception ex)
            {
                //myApplicationLogger.info(ex.getMessage());
            }
            curFiles[i].delete();
        }
        // this enforces a status file written to error info folder if any errors
        //handleStatusFileForErrors();
        return commands;
    }

    /**
     * If sendinfolderchunkson is on then we should not try sending the whole colletion at once. We will
     * then instead try sedning files in batches using  infolderchunkthreshold as batch size.
     * If file colletion is empty return null. If not return the collection size unless sendinfolderchunkson
     * is on, then
     *
     * return  infolderchunkthreshold if file colletion is greater than infolderchunkthreshold.
     *
     * @param curFiles  the colletion of files currently residing in infolder
     * @return          size of colletion we should send
     */
    public int getFileCollectionSize(File[] curFiles)
    {
        if (curFiles == null)
        {
            return 0;
        }
        int size = curFiles.length;
//            if (sendinfolderchunkson && (curFiles.length > infolderchunkthreshold))
        //          {
        //             size = infolderchunkthreshold;
        //           }
        return size;
    }


    /**
         * Sort files in the collection
         *
         * @param curFiles    the file collection to be sorted
         */
        protected void getSortedFiles(File[] curFiles)
        {
//            if (sortalpha)
                        if (true)
            {
                Arrays.sort(curFiles);
            }
        }



    /**
     * Logs a statement to inform nothing has happend in a certain amount of time - incativity time - and send an email
     * if it has been setup to do so.
     *
     * @param curFiles
     * @return
     */
    protected boolean isMessagesReceived( File[] curFiles )
    {
        if (curFiles == null || curFiles.length == 0  )
        {
/*            if ( sendNotificationIfIncativytTimeExceeded() )
            {
                myApplicationLogger.info("Service idle for to long according to conf parameters. Preparing to send email.");
                sendInactivityNotification();
            }
  */          return false;
        }
        else
        {
//            lastDateOfNotification =  null;
            return true;
        }
    }


	@Override
	void readFromSource() 
	{
		// TODO Auto-generated method stub
		
	}

}
