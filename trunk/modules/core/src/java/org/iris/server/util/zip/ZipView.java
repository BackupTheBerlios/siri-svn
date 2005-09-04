package org.iris.server.util.zip;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * This class is used to view the contents of a zip file.
 */
public class ZipView
{

    /**
     * This method is the main entry point for this class.
     */
    public static void main(String[] args) throws IOException
    {
        // Open the zip file, using the first command line argument as the file
        // name.

        ZipFile zip = new ZipFile(args[0]);

        // Process the zip file. Close it when the block is exited.

        try
        {
            // Loop through the zip entries and print the name of each one.

            for (Enumeration list = zip.entries(); list.hasMoreElements(); )
            {
                ZipEntry entry = (ZipEntry) list.nextElement();
                System.out.println(entry.getName());
            }
        }
        finally
        {
            zip.close();
        }
    }
}