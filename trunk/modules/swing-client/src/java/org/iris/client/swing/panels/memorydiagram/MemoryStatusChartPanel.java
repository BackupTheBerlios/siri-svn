package org.iris.client.swing.panels.memorydiagram;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.StandardXYItemRenderer;
import org.jfree.chart.renderer.XYItemRenderer;
import org.jfree.data.SeriesException;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.iris.client.swing.panels.servicediagram.ServiceStatusChartPanel;
import org.iris.client.swing.panels.servicediagram.ServiceStatusChartItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel displays a graph using the data provided.
 */

public class MemoryStatusChartPanel
    extends JPanel
{
    Logger myLogger = Logger.getLogger(MemoryStatusChartPanel.class.getName());

    ChartPanel chartPanel;
    BorderLayout borderLayout1 = new BorderLayout();
    TimeSeries series;
    String myServiceName;
    String myTitle;

    public static void main(String[] args)
    {
        long now = System.currentTimeMillis();
        ArrayList ls = new ArrayList();
        for (int i = 0; i < 10; i++)
        {
            MemoryStatusChartItem item = new MemoryStatusChartItem();
            System.out.println(new java.util.Date(now + i * 10000));
            item.setTime(now + i * 1000000);
            item.setValue(Math.random() * 100);
            ls.add(item);
        }
        MemoryStatusChartPanel chart = new MemoryStatusChartPanel("");
        chart.setData(ls);

        JFrame fr = new JFrame();
        fr.getContentPane().add(chart);
        fr.pack();
        fr.setVisible(true);

    }

    public MemoryStatusChartPanel(String title)
    {
        series = new TimeSeries(title, Second.class);
        myTitle = title;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void setData(ServiceStatusChartItem[] arrItems)
    {
        for (int i = 0; i < arrItems.length; i++)
        {
            series.add(new Minute(new java.util.Date(arrItems[i].getTime())), arrItems[i].getValue());

        }
    }

    public void setData(ArrayList lsItems)
    {
        try
        {
            myLogger.debug("series.getItemCount() " + lsItems.size());
            if (series.getItemCount() > 0)
            {
                series.delete(0, series.getItemCount() - 1);
            }
            for (int i = 0; i < lsItems.size(); i++)
            {
                MemoryStatusChartItem item = (MemoryStatusChartItem) lsItems.get(i);
                series.add(new Second(new java.util.Date(item.getTime())), item.getValue());
            }
        }
        catch (SeriesException ex)
        {
            myLogger.warn(ex.getMessage());
        }
        myLogger.debug("series.getItemCount() " + series.getItemCount());

        //chartPanel.getChart().addSubtitle( Title.  ( "last updated : " + new java.util.Date(System.currentTimeMillis()) ) );
//        chartPanel.chartChanged( new ChartChangeEvent() );



    }

    public TimeSeries getData()
    {
        return series;
    }

    void jbInit() throws Exception
    {
        this.setLayout(borderLayout1);

        TimeSeriesCollection dataset = new TimeSeriesCollection(getData());

        JFreeChart chart = ChartFactory.createTimeSeriesChart(myTitle, "Time", "Memory", dataset, false, true, false);
        chart.setBackgroundPaint(Color.LIGHT_GRAY);
//        chart.setLegend( myServiceName );



        XYPlot plot = chart.getXYPlot();
        plot.setInsets(new Insets(0, 0, 0, 20));
        //plot.addRangeMarker(new Marker(700,  new BasicStroke(1.0f),Color.blue, Color.blue, 0.8f));
        plot.setBackgroundPaint(Color.BLACK);
        plot.setDomainGridlinePaint(Color.GREEN);
        plot.setRangeGridlinePaint(Color.YELLOW); //paints grid
        plot.setDomainCrosshairPaint(Color.ORANGE);
        plot.setOutlinePaint(Color.white); // paints outline

        //plot.setBackgroundImage(JFreeChart.INFO.getLogo());
        XYItemRenderer renderer = plot.getRenderer();
        if (renderer instanceof StandardXYItemRenderer)
        {
            StandardXYItemRenderer r = (StandardXYItemRenderer) renderer;
            r.setPlotShapes(true);
            r.setShapesFilled(true);

            r.setSeriesPaint(0, Color.GREEN);
            //r.set
            //r.setBasePaint( Color.GREEN );

//            r.setSeriesPaint( 0, java.awt.Color.BLACK );
        }
/*
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        chartPanel.setBackground(Color.white);
*/
        //chartPanel.setMouseZoomable(true, false);
        chartPanel.setMouseZoomable(true);
        this.add(chartPanel, BorderLayout.CENTER);

    }

}
