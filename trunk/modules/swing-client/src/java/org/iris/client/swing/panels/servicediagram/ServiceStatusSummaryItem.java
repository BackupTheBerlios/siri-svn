package org.iris.client.swing.panels.servicediagram;

import org.iris.client.settings.xml.*;

public class ServiceStatusSummaryItem
{
    private String url;
    private boolean remote;
    private String description;
    private String name;
    private javax.swing.ImageIcon icon;
    protected IrisserverType serverinfo;
    private long numberOfArchivedFiles;
    private long numberOfInfolderFiles;
    private long numberOfErrorFiles;

    public ServiceStatusSummaryItem()
    {
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

    public javax.swing.ImageIcon getIcon()
    {
        return icon;
    }

    public boolean isRemote()
    {
        return remote;
    }

    public void setRemote(boolean remote)
    {
        this.remote = remote;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setIcon(javax.swing.ImageIcon icon)
    {
        this.icon = icon;
    }

    public IrisserverType getServerinfo()
    {
        return serverinfo;
    }

    public void setServerinfo(IrisserverType serverinfo)
    {
        this.serverinfo = serverinfo;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getNumberOfArchivedFiles()
    {
        return numberOfArchivedFiles;
    }

    public void setNumberOfArchivedFiles(long numberOfArchivedFiles)
    {
        this.numberOfArchivedFiles = numberOfArchivedFiles;
    }

    public long getNumberOfInfolderFiles()
    {
        return numberOfInfolderFiles;
    }

    public void setNumberOfInfolderFiles(long numberOfInfolderFiles)
    {
        this.numberOfInfolderFiles = numberOfInfolderFiles;
    }

    public long getNumberOfErrorFiles()
    {
        return numberOfErrorFiles;
    }

    public void setNumberOfErrorFiles(long numberOfErrorFiles)
    {
        this.numberOfErrorFiles = numberOfErrorFiles;
    }

}