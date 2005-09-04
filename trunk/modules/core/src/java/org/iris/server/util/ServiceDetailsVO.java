package org.iris.server.util;

import java.io.*;

public class ServiceDetailsVO
    implements Serializable
{
    private long numberOfArchivedFiles;
    private long numberOfErrorFiles;
    private long numberOfInFIles;
    private java.util.Date lastCreatedArchiveFile;
    private java.util.Date lastReceivedInFile;
    private java.util.Date lastCreatedErrorFile;
    private int status;
    private String serviceName;
    //long serialVersionUID = -8769200447213761145

    public ServiceDetailsVO()
    {
    }

    public long getNumberOfArchivedFiles()
    {
        return numberOfArchivedFiles;
    }

    public void setNumberOfArchivedFiles(long numberOfArchivedFiles)
    {
        this.numberOfArchivedFiles = numberOfArchivedFiles;
    }

    public long getNumberOfErrorFiles()
    {
        return numberOfErrorFiles;
    }

    public void setNumberOfErrorFiles(long numberOfErrorFiles)
    {
        this.numberOfErrorFiles = numberOfErrorFiles;
    }

    public long getNumberOfInFIles()
    {
        return numberOfInFIles;
    }

    public void setNumberOfInFIles(long numberOfInFIles)
    {
        this.numberOfInFIles = numberOfInFIles;
    }

    public java.util.Date getLastCreatedArchiveFile()
    {
        return lastCreatedArchiveFile;
    }

    public void setLastCreatedArchiveFile(java.util.Date lastCreatedArchiveFile)
    {
        this.lastCreatedArchiveFile = lastCreatedArchiveFile;
    }

    public java.util.Date getLastReceivedInFile()
    {
        return lastReceivedInFile;
    }

    public void setLastReceivedInFile(java.util.Date lastReceivedInFile)
    {
        this.lastReceivedInFile = lastReceivedInFile;
    }

    public java.util.Date getLastCreatedErrorFile()
    {
        return lastCreatedErrorFile;
    }

    public void setLastCreatedErrorFile(java.util.Date lastCreatedErrorFile)
    {
        this.lastCreatedErrorFile = lastCreatedErrorFile;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String toString()
    {
        return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this);
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

}