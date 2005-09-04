package org.iris.client.swing.panels.servicediagram;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import org.apache.log4j.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.*;
import org.jfree.data.*;
import org.jfree.data.time.*;

/**
 * Panel displays a graph using the data provided.
 */

public class ServiceStatusChartPanel
    extends JPanel
{
    Logger myLogger = Logger.getLogger(ServiceStatusChartPanel.class.getName());

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
            ServiceStatusChartItem item = new ServiceStatusChartItem();
            System.out.println(new java.util.Date(now + i * 10000));
            item.setTime(now + i * 1000000);
            item.setValue(Math.random() * 100);
            ls.add(item);
        }
        ServiceStatusChartPanel chart = new ServiceStatusChartPanel("");
        chart.setData(ls);

        JFrame fr = new JFrame();
        fr.getContentPane().add(chart);
        fr.pack();
        fr.setVisible(true);

    }

    public ServiceStatusChartPanel(String title)
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
                ServiceStatusChartItem item = (ServiceStatusChartItem) lsItems.get(i);
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

        // create a title with Unicode characters (currency symbols in this case)...

        JFreeChart jfchart = ChartFactory.createTimeSeriesChart(myTitle, "Time", "Intensity", dataset, false, true, false);
        jfchart.setBackgroundPaint(Color.LIGHT_GRAY);
        //jfchart.addSubtitle(   );
//        jfchart.setLegend( myServiceName );

        XYPlot plot = jfchart.getXYPlot();
        plot.setInsets(new Insets(0, 0, 0, 20));
        //plot.addRangeMarker(new Marker(700,  new BasicStroke(1.0f),Color.blue, Color.blue, 0.8f));
        plot.setBackgroundPaint(Color.white);

        //plot.setBackgroundImage(JFreeChart.INFO.getLogo());
        XYItemRenderer renderer = plot.getRenderer();
        if (renderer instanceof StandardXYItemRenderer)
        {
            StandardXYItemRenderer r = (StandardXYItemRenderer) renderer;
            r.setPlotShapes(true);
            r.setShapesFilled(true);
//            r.setSeriesPaint( 0, java.awt.Color.BLACK );
        }

        /*todo        chartPanel = new ChartPanel( jfchart);
                chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
                chartPanel.setBackground( Color.white );

                //chartPanel.setMouseZoomable(true, false);
                chartPanel.setMouseZoomable(true);
                this.add(chartPanel, BorderLayout.CENTER);
         */
    }

}
