package org.iris.server.archivehandler;

import java.io.*;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Contains information about archiving information, where to read files from
 * and where to store files for archiving given a service name.
 *
 * @author Georges Polyzois
 *
 */
public class ArchiveVO implements Serializable
{
    private String serviceName;
    private String backupFromFolderPath;
    private String archiveToFolderPath;
    
    public ArchiveVO()
    {
    }


    public String toString()
    {
		return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Name of service to archive
     *
     * @return
     */
    public String getServiceName()
    {
        return serviceName;
    }


    /**
     * Name of service to archive
     *
     * @param serviceName
     */
    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    /**
     * Path where backup files are stored
     *
     * @return
     */
    public String getBackupFromFolderPath()
    {
        return backupFromFolderPath;
    }

    /**
     * Path where backup files are stored
     *
     * @param folderPath
     */
    public void setBackupFromFolderPath(String folderPath)
    {
        this.backupFromFolderPath = folderPath;
    }


    /**
     * Archiving of backup files goes to this folder
     *
     * @return
     */
    public String getArchiveToFolderPath()
    {
        return archiveToFolderPath;
    }

    /**
     * Archiving of backup files goes to this folder
     *
     * @param archiveToFolderPath
     */
    public void setArchiveToFolderPath(String archiveToFolderPath)
    {
        this.archiveToFolderPath = archiveToFolderPath;
    }
    
}