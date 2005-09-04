package org.iris.client.swing.panels.tree;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Tradevision AB</p>
 * @author not attributable
 * @version 1.0
 */

public class TreeUserObject
{
    private String displayName;
    private String serviceName;
    public TreeUserObject(String displayName, String serviceName)
    {
        this.displayName = displayName;
        this.serviceName = serviceName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String toString()
    {
        return displayName;
    }

}