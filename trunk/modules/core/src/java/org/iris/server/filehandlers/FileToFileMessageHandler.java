package org.iris.server.filehandlers;

import org.iris.server.errorhandler.ErrorMessageVO;
import org.iris.server.util.config.ServiceFileFile;
import org.iris.server.util.filehelpers.FileUtils;
import org.iris.server.util.xml.XMLHelper;
import org.apache.log4j.NDC;

import java.io.File;
import java.io.IOException;
import java.util.Timer;

public class FileToFileMessageHandler
    extends FileMessageHandler
{
    protected String myOutdir;

    public FileToFileMessageHandler()
    {
    }

    public FileToFileMessageHandler(ServiceFileFile service)
    {
        super.init(service);
        myOutdir = service.getOutfolderPath();
        setUp( service );
    }

    protected void setUp( ServiceFileFile service)
    {
        startErrorHandler(service);
    }

    public void run()
    {
        NDC.push( serviceId );
        readSendFiles(readFromDir, myOutdir);
        NDC.remove();
    }

    synchronized public void readSendFiles( String readFromDir , String copyToDir)
    {
        File readFromFile = new File(readFromDir);
        File[] curFiles = readFromFile.listFiles();
        int fileCollectionSize = getFileCollectionSize(curFiles);

        logStatistics( fileCollectionSize );

        if ( !isMessagesReceived(curFiles) ) return;

        getSortedFiles(curFiles);

        String fileNameOriginal = null;
        String fileNameUnique = null;
        String fileNameOut = null;
        for (int i = 0; i < fileCollectionSize; i++)
        {
            fileNameOriginal = curFiles[i].getName();
            fileNameOut = fileNameOriginal;
            if (curFiles[i].length() == 0)
            {
                curFiles[i].delete();
                return;
            }
            try
            {
                if (createuniquename)
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
            }
            catch (Exception ex)
            {
                myApplicationLogger.info(ex.getMessage());
            }
            curFiles[i].delete();
        }
        // this enforces a status file written to error info folder if any errors
        handleStatusFileForErrors();
    }

    private void sendAndTransform(File curFile, String fileNameOut, String fileNameOriginal)
    {
        // get the key to the message from the filename
        String messageKey = curFile.getName().toString().substring(0, 6);
        try
        {
            File xmlTransformedFile;
            // transform the file using an xsl file
            xmlTransformedFile = XMLHelper.transformToFile(curFile, (String) transformMatrix.get(messageKey), myOutdir + fileNameOut);
            handleBackup(curFile, fileNameOut);
            myApplicationLogger.debug( "Message transformed and copied to out folder" );
        }
        catch (Exception ex)
        {
            // if we got an error and the size is not zero save it to error folder
            myApplicationLogger.error("Failed transformation of file " + curFile + "  using transformation file : " + (String) transformMatrix.get(messageKey), ex);
            if (curFile.length() == 0)
            {
                myApplicationLogger.error(curFile + " has size zero. Not storing in error folder.");
            }
            else
            {
                ErrorMessageVO error = new ErrorMessageVO(errorfoldername,
                    "Could not transform file " + curFile + " using " +
                    (String) transformMatrix.get(messageKey), ex, "ERROR", fileNameOriginal );
                handleError(curFile,error);
            }
        }
    }

    protected void send(File fileFrom, File fileTo)
    {
        try
        {
            FileUtils.copy( fileFrom, fileTo);
            handleBackup(fileFrom, fileFrom.getName());
            myApplicationLogger.info("Message copied " + fileTo.getName());
        }
        catch (IOException ex)
        {
            //myApplicationLogger.debug("Got exception trying to send file");
            ErrorMessageVO error = new ErrorMessageVO(errorfoldername,"Could send file " + fileFrom , ex, "ERROR", fileFrom.getName() );
            handleError(fileFrom, error);
        }
    }


    protected void send(File file, String newName)
    {
        try
        {
            FileUtils.copy(readFromDir + file.getName(), myOutdir + newName);
            handleBackup(file, file.getName());
            //myApplicationLogger.debug("Message copied");
        }
        catch (IOException ex)
        {
           // myApplicationLogger.debug("Got exception trying to send file");
            ErrorMessageVO error = new ErrorMessageVO(errorfoldername,"Could send file " + file , ex, "ERROR", file.getName() );
            handleError(file, error);
        }
    }




}
