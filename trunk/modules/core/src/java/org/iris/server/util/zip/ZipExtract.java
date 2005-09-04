package org.iris.server.util.zip;

import java.io.*;
import java.util.zip.*;

import org.apache.log4j.*;

public class ZipExtract
{
    static Logger myLogger = Logger.getLogger(ZipExtract.class.getName());

    public static void main(String[] args) throws IOException
    {
        String zipName = args[0];
        String entryName = args[1];
        ZipFile zip = new ZipFile(zipName);
        System.out.println(zipName + " opened.");

        try
        {
            ZipEntry entry = zip.getEntry(entryName);
            if (entry != null)
            {
                InputStream entryStream = zip.getInputStream(entry);
                try
                {
                    FileOutputStream file = new FileOutputStream(entry.getName());
                    try
                    {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ( (bytesRead = entryStream.read(buffer)) != -1)
                        {
                            file.write(buffer, 0, bytesRead);
                        }
                    }
                    finally
                    {
                        file.close();
                    }
                }
                finally
                {
                    entryStream.close();
                }
            }
            else
            {
                myLogger.info(entryName + " not found.");
            }
        }
        finally
        {
            zip.close();
            System.out.println(zipName + " closed.");
        }
    }
}
