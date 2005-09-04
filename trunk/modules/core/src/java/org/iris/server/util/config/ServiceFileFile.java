package org.iris.server.util.config;

import org.apache.commons.lang.builder.*;

/**
 * Holds specific settings for this service type.
 *
 * @author Georges Polyzois
 */ 

public class ServiceFileFile
    extends Service
{
    protected String outfolderPath;

    public ServiceFileFile()
    {
    }

    public String getOutfolderPath()
    {
        return outfolderPath;
    }

    public void setOutfolderPath(String outfolderPath)
    {
        this.outfolderPath = outfolderPath;
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}