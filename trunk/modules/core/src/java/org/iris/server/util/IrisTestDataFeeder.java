package org.iris.server.util;

import java.io.*;
import java.util.*;

import org.apache.log4j.*;

public class IrisTestDataFeeder
    extends TimerTask
{
    private String myLoadedFileContents;
    Logger myLogger = Logger.getLogger(IrisTestDataFeeder.class.getName());
    private static String pathToFile = "C:\\gepo\\mycvsprojects\\iris\\modules\\core-server\\services-test\\filetest-filetest\\c2kmup_RCF.xml.1";
    private static String pathToInfolder = "C:\\gepo\\mycvsprojects\\iris\\modules\\core-server\\services-test\\filetest-filetest\\error\\";
    private static long sendPeriod = 20000;

    public IrisTestDataFeeder()
    {
    }

    public static void main(String[] args)
    {
        IrisTestDataFeeder tt = new IrisTestDataFeeder();
        tt.loadFile( pathToFile );

        Timer timer = new Timer();
        timer.schedule(tt, 10, sendPeriod);
    }

    private String loadFile(String filepath)
    {
        try
        {
            BufferedReader bf = new BufferedReader(new FileReader(new File(filepath)));
            String line = bf.readLine();
            StringBuffer strbuf = new StringBuffer();
            while (line != null)
            {
                strbuf.append(line);

                line = bf.readLine();
            }
            myLoadedFileContents = strbuf.toString();
            myLogger.debug(myLoadedFileContents);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    public void run()
    {
        myLogger.debug("Sending data");
        saveToInFolder();

    }

    private void saveToInFolder()
    {
        try
        {
            BufferedWriter wrt = new BufferedWriter(new FileWriter(new File(
                                 pathToInfolder + Math.random() +
                                 ".txt")));
            wrt.write(myLoadedFileContents);
            wrt.flush();
            wrt.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}