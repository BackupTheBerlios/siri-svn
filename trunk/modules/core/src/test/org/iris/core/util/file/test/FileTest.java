package org.iris.core.util.file.test;

import junit.framework.TestCase;

import org.siri.core.util.file.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Date;
import java.util.Calendar;

public class FileTest extends TestCase
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(FileTest.class);
    private final static String DESTINATIONFOLDER = System.getProperty("user.home") + "/temp/";
    File testFolder =  new File(DESTINATIONFOLDER);


    public FileTest(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        testFolder.mkdir();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
        // remove temp file
        FileUtils.deleteDirContents(testFolder,false);
    }

    /**
     * Test create file.
     */
    public void testCreateFile()
    {
        String aMesage = "A message";
        String destination = System.getProperty("user.home");

        try
        {
            File createdFile = FileUtils.createFile(destination,aMesage);
            createdFile.delete();
        }
        catch (Exception ex)
        {
            logger.error(ex,ex);
            fail("Create file failed");
        }
    }

    /**
     * Test create file.
     */
    public void testGetMessageContent()
    {
        String aMesage = "A message";
        String destinationFile = DESTINATIONFOLDER + Math.random();
        String returnMeassage = null;

        try
        {
            //create the file
            File createdFile = FileUtils.createFile(aMesage,destinationFile);
            assertNotNull(createdFile);
            //get content from the file
            returnMeassage = FileUtils.getMessageContent(createdFile,false);
            assertEquals(aMesage,returnMeassage);
            // remove temp file
            createdFile.delete();
        }
        catch (Exception ex)
        {
            logger.error(ex,ex);
            fail("Message created in file is not equal to message sent to file : " +
                    returnMeassage  + " not equal to " + aMesage);
        }
    }


    /**
     * Test create file.
     */
    public void testGetMessageContentSkipFirstLine()
    {
        String secondLine = "A message on the second line";
        String message = "A message on the first line\n" + secondLine;

        String destinationFile = DESTINATIONFOLDER + Math.random();
        String returnMeassage = null;

        try
        {
            File testFolder =  new File(DESTINATIONFOLDER);
            testFolder.mkdir();

            //create the file
            File createdFile = FileUtils.createFile(message,destinationFile);
            assertNotNull(createdFile);
            //get content from the file
            returnMeassage = FileUtils.getMessageContent(createdFile,true);
            assertEquals(secondLine,returnMeassage);
            // remove temp file
            testFolder.delete();
        }
        catch (Exception ex)
        {
            logger.error(ex,ex);
            fail("Message created in file is not equal to message sent to file : " +
                    returnMeassage  + " not equal to " + message);
        }
    }

    /**
     * Test. Create file/s and count them.
     */
    public void testcountNumberOfFileInFolder()
    {
        String secondLine = "A message on the second line";
        String message = "A message on the first line\n" + secondLine;
        String destinationFile = DESTINATIONFOLDER + Math.random();
        String destinationFile2 = DESTINATIONFOLDER + Math.random();

        String returnMeassage = null;

        try
        {
            long numberOfFile = FileUtils.countNumberOfFileInFolder(DESTINATIONFOLDER);
            assertEquals(0,numberOfFile);

            File createdFile = FileUtils.createFile(message,destinationFile);
            assertNotNull(createdFile);
            numberOfFile = FileUtils.countNumberOfFileInFolder(DESTINATIONFOLDER);
            assertEquals(1,numberOfFile);

            File createdFile2 = FileUtils.createFile(message,destinationFile2);
            assertNotNull(createdFile2);
            numberOfFile = FileUtils.countNumberOfFileInFolder(DESTINATIONFOLDER);
            assertEquals(2,numberOfFile);
        }
        catch (Exception ex)
        {
            logger.error(ex,ex);
            fail("Message created in file is not equal to message sent to file : " +
                    returnMeassage  + " not equal to " + message);
        }
    }



    /**
        * Test. Createe file and copy file and verify contents ok.
        */
       public void testCopy()
       {
           String secondLine = "A message on the second line";
           String message = "A message on the first line\n" + secondLine;
           String readFromFile = DESTINATIONFOLDER + Math.random();
           String writeToFile = DESTINATIONFOLDER + Math.random();
           String returnMeassage = null;

           try
           {

               File createdReadFromFile = FileUtils.createFile(message,readFromFile);
               assertNotNull(createdReadFromFile);
               File writeTo = new File(writeToFile);

               //copy file
               FileUtils.copy(createdReadFromFile,writeTo);

               //should be two files
               assertEquals(2,FileUtils.countNumberOfFileInFolder(DESTINATIONFOLDER));
               assertEquals(message,FileUtils.getMessageContent(writeTo,false));
           }
           catch (Exception ex)
           {
               logger.error(ex,ex);
               fail("Message created in file is not equal to message sent to file : " +
                       returnMeassage  + " not equal to " + message);
           }
       }




    /**
        * Test. Createe file and copy file and verify contents ok.
        */
       public void testLastModified()
       {
           String secondLine = "A message on the second line";
           String message = "A message on the first line\n" + secondLine;
           String readFromFile = DESTINATIONFOLDER + Math.random();
           String returnMeassage = null;

           try
           {
               File createdReadFromFile = FileUtils.createFile(message,readFromFile);
               assertNotNull(createdReadFromFile);
               Date fileDate = FileUtils.lastModifiedDateOfFile(DESTINATIONFOLDER);

               Calendar c1 = Calendar.getInstance();
               java.util.Date today = new java.util.Date(System.currentTimeMillis());
               c1.setTime(today);
               c1.set(Calendar.MINUTE, -5);
               Date dateOneHourBack = c1.getTime();

               boolean fileNewerThanOneHour = false;
               if (!(fileDate.after(dateOneHourBack)))
               {
                   fail("Date of file just created is said to be more than one hour old! ");
               }

               logger.debug( dateOneHourBack );
               logger.debug( fileDate );
           }
           catch (Exception ex)
           {
               logger.error(ex,ex);
               fail("Message created in file is not equal to message sent to file : " +
                       returnMeassage  + " not equal to " + message);
           }
       }
}
