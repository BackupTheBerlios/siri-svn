package org.iris.client.swing.panels.servicediagram;

public class ServiceStatusChartItem
{
    private long time;
    private double value;
    public ServiceStatusChartItem()
    {
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

}