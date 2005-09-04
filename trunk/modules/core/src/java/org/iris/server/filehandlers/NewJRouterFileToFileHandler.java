package org.iris.server.filehandlers;

import org.apache.log4j.Logger;
import org.iris.server.errorhandler.ErrorMessageVO;
import org.iris.server.errorhandler.ErrorMessages;
import org.iris.server.util.config.ServiceFileFile;
import org.iris.server.util.filehelpers.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;

public class NewJRouterFileToFileHandler
    extends FileMessageHandler
{
    private javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
    Logger myLogger = Logger.getLogger(NewJRouterFileToFileHandler.class.getName());
    private String myOutdir;
    java.util.Timer myTimer = new java.util.Timer();

    public NewJRouterFileToFileHandler(ServiceFileFile service)
    {
        super.init(service);
        myOutdir = service.getOutfolderPath();

        setUp();
    }

    /*
       public void startEmailThread()
      {
          NewJRouterFileToFileHandler aJRouterFileHandler = (NewJRouterFileToFileHandler) aJRouterHandler;
          myTimer.schedule(aJRouterFileHandler, aJRouterFileHandler.start, aJRouterFileHandler.intervall);
      }
     */
    public void startThread()
    {
//        FileToFileMessageHandler aJRouterFileHandler = (FileToFileMessageHandler) aJRouterHandler;
        myTimer = new Timer();
        myTimer.schedule(this, this.start, this.intervall);
    }

    protected void setUp()
    {
    }

    public void run()
    {
//        myApplicationLogger.debug( "Reading new messages" );
        readSendFiles();
    }

    synchronized private void readSendFiles()
    {
        long before = System.currentTimeMillis();
        readFrom = new File(readFromDir);
        File[] curFiles = readFrom.listFiles();
        BufferedReader bufReader = null;
        if (curFiles == null || curFiles.length == 0)
        {
            myLogger.debug("No files");
            return;
        }

        FileInputStream filin = null;
        String tmpName = "";
        for (int i = 0; i < curFiles.length; i++)
        {
            try
            {
                tmpName = createUniqueFileName(curFiles[i].getName());
                if (backuphandleron)
                {
                    FileUtils.copy(readFromDir + curFiles[i].getName(), backupDir + tmpName);

                }
                send(curFiles[i], tmpName);

            }
            catch (Exception ex)
            {
                myLogger.info("Exception caught" + ex.getMessage(), ex);
            }

            if (filin != null)
            {
                try
                {
                    filin.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            curFiles[i].delete();
        }
        long after = System.currentTimeMillis();

        System.out.println("TIME TO READ SEND FILES : " + (after - before));

    }

    protected void send(File file, String newName)
    {
        try
        {
            FileUtils.copy(readFromDir + file.getName(), myOutdir + newName);
        }
        catch (IOException ex)
        {
            if (errorhandleron)
            {
                saveFileToErrorFolder(file, new ErrorMessageVO(errorfoldername, ErrorMessages.FILECOPYERROR,
                    ex, "ERROR", file.getName()));
            }
        }
    }

    public void stopThread()
    {
        myTimer.cancel();
    }

}
