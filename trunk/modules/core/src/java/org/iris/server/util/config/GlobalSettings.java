package org.iris.server.util.config;

import org.apache.commons.lang.builder.ToStringBuilder;

public class GlobalSettings
{
    private String archiverintervall;
    private String name;
    private String logging_sockethub = "localhost";
    private String logging_port = "4560";
    private String logging_reconnectdelay = "30000";
    protected String serviceresend_name;
    protected String serviceresend_errorresendintervall = "300000";
    protected String primaryemailserver;

    public GlobalSettings()
    {
    }

    public String getArchiverintervall()
    {
        return archiverintervall;
    }

    public void setArchiverintervall(String archiverintervall)
    {
        this.archiverintervall = archiverintervall;
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLogging_port()
    {
        return logging_port;
    }

    public String getLogging_reconnectdelay()
    {
        return logging_reconnectdelay;
    }

    public String getLogging_sockethub()
    {
        return logging_sockethub;
    }

    public void setLogging_sockethub(String logging_sockethub)
    {
        this.logging_sockethub = logging_sockethub;
    }

    public void setLogging_reconnectdelay(String logging_reconnectdelay)
    {
        this.logging_reconnectdelay = logging_reconnectdelay;
    }

    public void setLogging_port(String logging_port)
    {
        this.logging_port = logging_port;
    }

    public String getServiceresend_errorresendintervall()
    {
        return serviceresend_errorresendintervall;
    }

    public String getServiceresend_name()
    {
        return serviceresend_name;
    }

    public void setServiceresend_errorresendintervall(String serviceresend_errorresendintervall)
    {
        this.serviceresend_errorresendintervall = serviceresend_errorresendintervall;
    }

    public void setServiceresend_name(String serviceresend_name)
    {
        this.serviceresend_name = serviceresend_name;
    }

    public String getPrimaryemailserver()
    {
        return primaryemailserver;
    }

    public void setPrimaryemailserver(String primaryemailserver)
    {
        this.primaryemailserver = primaryemailserver;
    }

}
