package org.iris.client.swing.panels.servicediagram;

import java.util.*;
import java.util.Timer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.*;
import org.iris.server.statistics.*;
import org.iris.server.util.*;

/**
 * Panel shows diagram of service status intensity versus time.
 */
public class ServiceStatusSummaryPanel
    extends JPanel
{
    Logger myLogger = Logger.getLogger(ServiceStatusSummaryPanel.class.getName());
    ServiceStatusSummaryTableModel myServiceSummaryTableModel;
    private static ServiceStatusSummaryPanel myInstance;
    private ArrayList myServiceSummaryItems = new ArrayList();
    JLabel jLabelImage = new JLabel();
    ServiceStatusSummaryServerPoller pollerThread;
    Timer myPollerTimer = new Timer();
    ServiceStatusChartPanel myServiceChartPanel = new ServiceStatusChartPanel("Service intensity");
    JLabel jLabel2 = new JLabel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    StatisticsVO[] arrStatisticsVO;
    JPanel jPanel1 = new JPanel();
    TitledBorder titledBorder1;
    Border border1;
    TitledBorder titledBorder2;
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();

    public static ServiceStatusSummaryPanel getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new ServiceStatusSummaryPanel();
        }
        return myInstance;
    }

    public void reInit(ServiceDetailsVO[] arrVos)
    {
        myLogger.debug("Setting new model " + arrVos.length);
        if (arrVos == null)
        {
            return;
        }
        ArrayList items = new ArrayList();
        for (int i = 0; i < arrVos.length; i++)
        {
            ServiceStatusSummaryItem serviceItem = new ServiceStatusSummaryItem();
            serviceItem.setNumberOfArchivedFiles(arrVos[i].getNumberOfArchivedFiles());
            serviceItem.setNumberOfErrorFiles(arrVos[i].getNumberOfErrorFiles());
            serviceItem.setNumberOfInfolderFiles(arrVos[i].getNumberOfInFIles());
            serviceItem.setName(arrVos[i].getServiceName());
            items.add(serviceItem);
        }
        myServiceSummaryTableModel.reinit(items);
    }

    public void reInitDiagram(StatisticsVO[] arrVos)
    {
        if (arrVos == null)
        {
            return;
        }
        myLogger.debug("Setting new model for diagram with number of items " + arrVos.length);
        ArrayList items = new ArrayList();
        for (int i = 0; i < arrVos.length; i++)
        {
            //       myLogger.debug( "arrVos[i] : " + arrVos[i] );
            ServiceStatusChartItem item = new ServiceStatusChartItem();
            item.setTime(arrVos[i].getTimestamp());
            item.setValue(arrVos[i].getValue());
            items.add(item);
        }
        myLogger.debug("Setting model to items size " + items.size());

//        myServiceChartPanel.setBackground( new Color(218,200,219) );
        myServiceChartPanel.setData(items);
    }

    public ServiceStatusSummaryPanel()
    {
        ServiceStatusSummaryItem item = new ServiceStatusSummaryItem();
        item.setDescription("");
        item.setUrl("");
        myServiceSummaryItems.add(item);
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception
    {
        titledBorder1 = new TitledBorder("");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(border1, "Service Status Diagram");
        this.setLayout(borderLayout1);
        jLabelImage.setIcon(ResourcesFactory.getImageIcon("servicestatistics.gif"));
        jLabel2.setText("Right click for options like save diagram, print diagram etc. Left " + "click to zoom in.");
        jLabel2.setBounds(new Rectangle(6, 21, 718, 15));
        jPanel2.setLayout(borderLayout2);
        jPanel1.setBorder(titledBorder2);
        jPanel1.setLayout(null);
        myServiceChartPanel.setBounds(new Rectangle(8, 48, 715, 354));
        this.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jLabel2, null);
        jPanel1.add(myServiceChartPanel, null);
        this.add(jPanel3, BorderLayout.WEST);
        jPanel3.add(jLabelImage, null);

//        myServiceSummaryTableModel = new ServiceStatusSummaryTableModel(myServiceSummaryItems);

        ServiceStatusSummaryItemCellRenderer renderer = new ServiceStatusSummaryItemCellRenderer();
        renderer.setPreferredSize(new Dimension(200, 30));

    }

    public ServiceStatusChartPanel getMyServiceChartPanel()
    {
        return myServiceChartPanel;
    }

    public void setMyServiceChartPanel(ServiceStatusChartPanel myServiceChartPanel)
    {
        this.myServiceChartPanel = myServiceChartPanel;
    }

    void jButton1_actionPerformed(ActionEvent e)
    {
        StatisticsVO aa = new StatisticsVO();
        aa.setTimestamp(System.currentTimeMillis());
        aa.setValue(2.0);
        StatisticsVO[] arr = new StatisticsVO[]
                             {
                             aa};
        reInitDiagram(arr);
    }

}