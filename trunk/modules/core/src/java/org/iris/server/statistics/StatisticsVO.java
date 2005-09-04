package org.iris.server.statistics;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Holds statistics for a service
 *
 */
public class StatisticsVO implements java.io.Serializable
{
    private long timestamp;
    private double value;
    private String serviceName;
    public StatisticsVO()
    {
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
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
        return ToStringBuilder.reflectionToString( this );
    }


}