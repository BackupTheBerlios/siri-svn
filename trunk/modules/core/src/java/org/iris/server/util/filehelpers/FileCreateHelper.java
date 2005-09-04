package org.iris.server.util.filehelpers;

import java.io.*;

import com.sun.xml.bind.*;

public class FileCreateHelper
{

    public static String readFile(String readFrom) throws IOException
    {
        File inFile = new File(readFrom);
        FileReader reader = null;
        StringBuffer buf = new StringBuffer();
        if (inFile.exists())
        {
            if (inFile.isFile())
            {
                try
                {
                    reader = new FileReader(inFile);
                    BufferedReader bufreader = new BufferedReader(reader);
                    String line = bufreader.readLine();

                    while (line != null)
                    {
                        buf.append(line + "\n");
                        line = bufreader.readLine();
                    }
                }
                finally
                {
                    if (reader != null)
                    {
                        try
                        {
                            reader.close();
                        }
                        catch (IOException e)
                        {
                            ;
                        }
                    }
                }

            }
            else
            {
                throw new FileCopyException("FileRead: location is not a file: " + readFrom);
            }
        }
        return buf.toString();

    }

    public static void createFile(String message, String dest_name) throws IOException
    {
        File destination_file = new File(dest_name);
        StringInputStream source = new StringInputStream(message);
        FileOutputStream destination = null;
        byte[] buffer;
        int bytes_read;

        try
        {
            if (destination_file.exists())
            {
                if (destination_file.isFile())
                {
                    DataInputStream in = new DataInputStream(System.in);
                    String response;
                    if (!destination_file.canWrite())
                    {
                        throw new FileCopyException("FileCreate: destination " + "file is unwriteable: " + dest_name);
                    }

                }
                else
                {
                    throw new FileCopyException("FileCreate: destination " + "is not a file: " + dest_name);
                }
            }
            else
            {
                File parentdir = parent(destination_file);
                if (!parentdir.exists())
                {
                    throw new FileCopyException("FileCreate: destination " + "directory doesn't exist: " + dest_name);
                }
                if (!parentdir.canWrite())
                {
                    throw new FileCopyException("FileCreate: destination " + "directory is unwriteable: " + dest_name);
                }
            }

            destination = new FileOutputStream(destination_file);
            buffer = new byte[1024];
            while (true)
            {
                bytes_read = source.read(buffer);
                if (bytes_read == -1)
                {
                    break;
                }
                destination.write(buffer, 0, bytes_read);
            }
        }
        finally
        {
            if (source != null)
            {
                try
                {
                    source.close();
                }
                catch (IOException e)
                {
                    ;
                }
            }
            if (destination != null)
            {
                try
                {
                    destination.close();
                }
                catch (IOException e)
                {
                    ;
                }
            }
        }
    }

    private static File parent(File f)
    {
        String dirname = f.getParent();
        if (dirname == null)
        {
            if (f.isAbsolute())
            {
                return new File(File.separator);
            }
            else
            {
                return new File(System.getProperty("user.dir"));
            }
        }
        return new File(dirname);
    }

}