package org.iris.server.util.filehelpers;

import java.io.*;

public class FileCounter
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println(FileCounter.count("C:/Tradevision/iris/services/test/filetest-filetes/archived"));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public static long count(String dir) throws IOException
    {
        long numberOfFiles = 0;
        File sourceDir = new File(dir);
        try
        {
            FilenameFilter filter = new FilenameFilter()
            {
                public boolean accept(File sourceDir, String fileName)
                {
                    return (new File(sourceDir, fileName)).isFile();
                }
            };
            numberOfFiles = sourceDir.list(filter).length;
        }
        finally
        {
        }

        return numberOfFiles;
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