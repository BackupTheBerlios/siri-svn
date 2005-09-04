package org.iris.client.swing.panels.location;

import org.iris.client.settings.xml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Tradevision AB</p>
 * @author not attributable
 * @version 1.0
 */

public class LocationItem
{
    private String url;
    private boolean remote;
    private String description;
    private String name;
    private javax.swing.ImageIcon icon;
    protected IrisserverType serverinfo;

    public LocationItem()
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

}
