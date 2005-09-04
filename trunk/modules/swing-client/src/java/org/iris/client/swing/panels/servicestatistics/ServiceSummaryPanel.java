package org.iris.client.swing.panels.servicestatistics;

import java.util.*;
import java.util.Timer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.tablesort.*;
import org.iris.server.util.*;

/**
 *
 *
 */

public class ServiceSummaryPanel
    extends JPanel
{
    Logger myLogger = Logger.getLogger(ServiceSummaryPanel.class.getName());
    private final static long POLLERINTERVALL = 10000;

    private static int COLUMN1_SIZE = 20;
    private static int COLUMN2_SIZE = 150;
    private static int COLUMN3_SIZE = 100;
    private static int COLUMN4_SIZE = 100;
    private static int COLUMN5_SIZE = 100;

    // private static int COLUMN6_SIZE = 120;

    private static int[] COLUMNS =
        {
        COLUMN1_SIZE, COLUMN2_SIZE, COLUMN3_SIZE, COLUMN4_SIZE, COLUMN5_SIZE};

    JTable myTable = new JTable();
    ServiceSummaryTableModel myServiceSummaryTableModel;
    private SortButtonRenderer myButtonRenderer;
    private JTableHeader myTableHeader = null;
    TableColumnModel myTableColumnModel;
    private static ServiceSummaryPanel myInstance;
    private ArrayList myServiceSummaryItems = new ArrayList();

    JPanel myRootPanel1 = new JPanel();
    JScrollPane myScrollPaneTable = new JScrollPane();
    JLabel jLabel1 = new JLabel();
    JLabel jLabelImage = new JLabel();
    JCheckBox jCheckBoxPollServer = new JCheckBox();

    ServiceSummaryServerPoller pollerThread;
    Timer myPollerTimer = new Timer();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    BorderLayout borderLayout2 = new BorderLayout();

    public static ServiceSummaryPanel getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new ServiceSummaryPanel();
        }
        return myInstance;
    }

    public void reInit(ServiceDetailsVO[] arrVos)
    {
        if (arrVos == null)
        {
            return;
        }
        myLogger.debug("Setting new model " + arrVos.length);
        ArrayList items = new ArrayList();
        for (int i = 0; i < arrVos.length; i++)
        {
            ServiceSummaryItem serviceItem = new ServiceSummaryItem();
            serviceItem.setNumberOfArchivedFiles(arrVos[i].getNumberOfArchivedFiles());
            serviceItem.setNumberOfErrorFiles(arrVos[i].getNumberOfErrorFiles());
            serviceItem.setNumberOfInfolderFiles(arrVos[i].getNumberOfInFIles());
            serviceItem.setName(arrVos[i].getServiceName());

            items.add(serviceItem);
        }

        myServiceSummaryTableModel.reinit(items);
    }

    public ServiceSummaryPanel()
    {
        ServiceSummaryItem item = new ServiceSummaryItem();
        item.setDescription("loading data...");
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

        pollerThread = new ServiceSummaryServerPoller(POLLERINTERVALL);

    }

    void jbInit() throws Exception
    {
        this.setLayout(borderLayout1);
        jCheckBoxPollServer.setSelected(true);
        myRootPanel1.setLayout(gridBagLayout1);
        jLabel1.setText("Status of services");
        jLabelImage.setIcon(ResourcesFactory.getImageIcon("servicestatistics.gif"));
        jCheckBoxPollServer.setText("Poll server");
        jCheckBoxPollServer.addItemListener(new ServiceSummaryPanel_jCheckBoxPollServer_itemAdapter(this));
        jPanel2.setLayout(borderLayout2);
        myRootPanel1.setDebugGraphicsOptions(0);
        myRootPanel1.add(myScrollPaneTable, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
            GridBagConstraints.BOTH, new Insets(0, 10, 10, 10), 1, 1));
        myRootPanel1.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(9, 10, 10, 0), 1, 14));
        myRootPanel1.add(jCheckBoxPollServer, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(29, 10, 0, 92), 38, -4));
        myScrollPaneTable.getViewport().add(myTable, null);
        this.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jTabbedPane1, BorderLayout.CENTER);
        jTabbedPane1.add(myRootPanel1, "Ping services");
        this.add(jPanel3, BorderLayout.WEST);
        jPanel3.add(jLabelImage, null);

        //
        myServiceSummaryTableModel = new ServiceSummaryTableModel(myServiceSummaryItems);
        myTable.setModel(myServiceSummaryTableModel);
        //

        //
        //  Stuff for sorting the table myTableHeader
        myTableHeader = myTable.getTableHeader();
        myTableHeader.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                headerMouseReleased(e);
            }

            public void mousePressed(MouseEvent e)
            {
                headerMousePressed(e);
            }
        });
        myButtonRenderer = new SortButtonRenderer();
        myTableColumnModel = myTable.getColumnModel();
        for (int i = 0; i < COLUMNS.length; i++)
        {
            myTableColumnModel.getColumn(i).setHeaderRenderer(myButtonRenderer);
            myTableColumnModel.getColumn(i).setPreferredWidth(COLUMNS[i]);
        }
        //

        ServiceSummaryItemCellRenderer renderer = new ServiceSummaryItemCellRenderer();
        renderer.setPreferredSize(new Dimension(200, 30));

    }

    public void headerMouseReleased(MouseEvent e)
    {
        int col = myTableHeader.columnAtPoint(e.getPoint());
        myButtonRenderer.setPressedColumn( -1);
        myTableHeader.repaint();
    }

    private void headerMousePressed(MouseEvent e)
    {

        int col = myTableHeader.columnAtPoint(e.getPoint());
        int sortCol = myTableHeader.getTable().convertColumnIndexToModel(col);
        myButtonRenderer.setPressedColumn(col);
        myButtonRenderer.setSelectedColumn(col);
        myTableHeader.repaint();

        if (myTableHeader.getTable().isEditing())
        {
            myTableHeader.getTable().getCellEditor().stopCellEditing();
        }

        boolean isAscent;
        if (SortButtonRenderer.DOWN == myButtonRenderer.getState(col))
        {
            isAscent = true;
        }
        else
        {
            isAscent = false;
        }
        this.myServiceSummaryTableModel.sort(isAscent, sortCol);
        this.myServiceSummaryTableModel.fireTableDataChanged();
    }

    void jCheckBoxPollServer_itemStateChanged(ItemEvent e)
    {
        if (jCheckBoxPollServer.isSelected())
        {
            myLogger.debug("Starting pollerthread");
            pollerThread = new ServiceSummaryServerPoller(POLLERINTERVALL);
        }
        else
        {
            myLogger.debug("Canceling pollerthread");
            pollerThread.cancel();
        }
    }

}

class ServiceSummaryPanel_jCheckBoxPollServer_itemAdapter
    implements java.awt.event.ItemListener
{
    ServiceSummaryPanel adaptee;

    ServiceSummaryPanel_jCheckBoxPollServer_itemAdapter(ServiceSummaryPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e)
    {
        adaptee.jCheckBoxPollServer_itemStateChanged(e);
    }
}
