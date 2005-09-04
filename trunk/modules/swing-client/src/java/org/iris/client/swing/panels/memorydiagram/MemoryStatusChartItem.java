package org.iris.client.swing.panels.memorydiagram;

public class MemoryStatusChartItem
{
    private long time;
    private double value;
    public MemoryStatusChartItem()
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