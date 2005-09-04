package org.iris.client.swing.util;

import java.io.*;

public class MiscUtils
{
    public MiscUtils()
    {
    }

    /**
     * Saves a backup (optionally numbered) of a file.
     * @param file A local file
     * @param backups The number of backups. Must be >= 1. If > 1, backup
     * files will be numbered.
     * @param backupPrefix The backup file name prefix
     * @param backupSuffix The backup file name suffix
     * @param backupDirectory The directory where to save backups; if null,
     * they will be saved in the same directory as the file itself.
     */
    public static void saveBackup(File file, int backups, String backupPrefix, String backupSuffix,
        String backupDirectory)
    {
        saveBackup(file, backups, backupPrefix, backupSuffix, backupDirectory, 0);
    } //}}}

//{{{ saveBackup() method
    /**
     * Saves a backup (optionally numbered) of a file.
     * @param file A local file
     * @param backups The number of backups. Must be >= 1. If > 1, backup
     * files will be numbered.
     * @param backupPrefix The backup file name prefix
     * @param backupSuffix The backup file name suffix
     * @param backupDirectory The directory where to save backups; if null,
     * they will be saved in the same directory as the file itself.
     * @param backupTimeDistance The minimum time in minutes when a backup
     * version 1 shall be moved into version 2; if 0, backups are always
     * moved.
     * @since jEdit 4.2pre5
     */
    public static void saveBackup(File file, int backups, String backupPrefix, String backupSuffix,
        String backupDirectory, int backupTimeDistance)
    {
        if (backupPrefix == null)
        {
            backupPrefix = "";
        }
        if (backupSuffix == null)
        {
            backupSuffix = "";
        }

        String name = file.getName();

        // If backups is 1, create ~ file
        if (backups == 1)
        {
            File backupFile = new File(backupDirectory, backupPrefix + name + backupSuffix);
            long modTime = backupFile.lastModified();
            /* if backup file was created less than
             * 'backupTimeDistance' ago, we do not
             * create the backup */
            if (System.currentTimeMillis() - modTime >= backupTimeDistance)
            {
                backupFile.delete();
                file.renameTo(backupFile);
            }
        }
        // If backups > 1, move old ~n~ files, create ~1~ file
        else
        {
            /* delete a backup created using above method */
            new File(backupDirectory, backupPrefix + name + backupSuffix + backups + backupSuffix).delete();

            File firstBackup = new File(backupDirectory, backupPrefix + name + backupSuffix + "1" + backupSuffix);
            long modTime = firstBackup.lastModified();
            /* if backup file was created less than
             * 'backupTimeDistance' ago, we do not
             * create the backup */
            if (System.currentTimeMillis() - modTime >= backupTimeDistance)
            {
                for (int i = backups - 1; i > 0; i--)
                {
                    File backup = new File(backupDirectory, backupPrefix + name + backupSuffix + i + backupSuffix);

                    backup.renameTo(new File(backupDirectory,
                        backupPrefix + name + backupSuffix + (i + 1) + backupSuffix));
                }

                file.renameTo(new File(backupDirectory, backupPrefix + name + backupSuffix + "1" + backupSuffix));
            }
        }
    } //}}}

}