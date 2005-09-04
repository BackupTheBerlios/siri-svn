package org.iris.client.swing.panels.location;

import java.util.*;
import java.util.List;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import org.iris.client.settings.*;
import org.iris.client.settings.xml.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.dialogs.*;
import org.iris.client.swing.util.statuspanel.*;
import org.iris.client.swing.util.tablesort.*;

public class LocationDialog
    extends JDialog
{
    private static int COLUMN1_SIZE = 20;
    private static int COLUMN2_SIZE = 300;
    private static int COLUMN3_SIZE = 300;
    JTable myListTable = new JTable();
    LocationTableModel myLocationTableModel;
    private SortButtonRenderer myButtonRenderer;
    private JTableHeader myTableHeader = null;
    TableColumnModel myTableColumnModel;
    private static int[] COLUMNS =
        {
        COLUMN1_SIZE, COLUMN2_SIZE, COLUMN3_SIZE};

    JPanel panel1 = new JPanel();
    JPanel jPanelContents = new JPanel();
    TitledBorder titledBorder1;
    JScrollPane jScrollPaneTable = new JScrollPane();
    JButton jButtonEdit = new JButton();
    JButton jButtonAdd = new JButton();
    ArrayList myLocationItems;
    JButton jButtonRemove = new JButton();
    JPanel jPanelEdit = new JPanel();
    private String selectedUrl;
    IrisserverType selectedService;
    JPanel jPanelOKCancel = new JPanel();
    JButton jButtonApply = new JButton();
    Irisclient mySettings;
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    GridBagLayout gridBagLayout4 = new GridBagLayout();
    JButton jButtonCancel = new JButton();
    GridBagLayout gridBagLayout5 = new GridBagLayout();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();

    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    GridBagLayout gridBagLayout3 = new GridBagLayout();
    BorderLayout borderLayout4 = new BorderLayout();

    public LocationDialog(Frame frame, String title, boolean modal, Irisclient settings)
    {
        super(frame, title, modal);
        mySettings = settings;
        this.myLocationItems = getLocationItems();

        try
        {
            jbInit();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        initialize();
    }

    private ArrayList getLocationItems()
    {
        List listServer = mySettings.getIrisserver();
        Iterator iter = listServer.iterator();
        LocationItem item;
        ArrayList ls = new ArrayList();

        while (iter.hasNext())
        {
            IrisserverType server = (IrisserverType) iter.next();
            item = new LocationItem();
            item.setServerinfo(server);
            item.setDescription(server.getDescription());
            item.setRemote(false);
            item.setUrl(server.getRmiservice());
            item.setIcon(ResourcesFactory.getImageIcon("Server24.gif"));
            ls.add(item);
        }

        return ls;
    }

    private void jbInit() throws Exception
    {
        myListTable.setRowHeight(25);

        myLocationTableModel = new LocationTableModel(myLocationItems);
        titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.white, new Color(142, 142, 142)),
                        "Profile select");
        panel1.setLayout(borderLayout1);
        jPanelContents.setBorder(titledBorder1);
        jPanelContents.setDebugGraphicsOptions(0);
        jPanelContents.setLayout(borderLayout4);
        jButtonEdit.setHorizontalAlignment(SwingConstants.LEFT);
        jButtonEdit.setText("Edit");
        jButtonEdit.addActionListener(new LocationDialog_jButtonEdit_actionAdapter(this));
        jButtonAdd.setHorizontalAlignment(SwingConstants.LEFT);
        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new LocationDialog_jButtonAdd_actionAdapter(this));
        jButtonRemove.setHorizontalAlignment(SwingConstants.LEFT);
        jButtonRemove.setText("Remove");
        jButtonRemove.addActionListener(new LocationDialog_jButtonRemove_actionAdapter(this));
        jPanelEdit.setLayout(gridBagLayout3);
        jPanelOKCancel.setLayout(flowLayout4);
        jButtonApply.setText("OK");
        jButtonApply.addActionListener(new LocationDialog_jButtonApply_actionAdapter(this));
        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new LocationDialog_jButtonCancel_actionAdapter(this));

        this.getContentPane().setLayout(borderLayout2);

        jPanel1.setLayout(borderLayout3);
        getContentPane().add(panel1, BorderLayout.CENTER);
        panel1.add(jPanelContents, BorderLayout.CENTER);

        jButtonRemove.setIcon(ResourcesFactory.getImageIcon("Delete24.gif"));
        jButtonAdd.setIcon(ResourcesFactory.getImageIcon("Add24.gif"));
        jButtonEdit.setIcon(ResourcesFactory.getImageIcon("Edit24.gif"));
        //jButtonApply.setIcon( ResourcesFactory.getImageIcon("Play24.gif") );

        jPanelEdit.add(jButtonAdd, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
            GridBagConstraints.NONE, new Insets(17, 42, 0, 34), 34, -1));
        jPanelEdit.add(jButtonEdit, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
            GridBagConstraints.NONE, new Insets(0, 42, 0, 34), 35, -2));
        jPanelEdit.add(jButtonRemove, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
            GridBagConstraints.NONE, new Insets(0, 42, 26, 34), 11, -2));
        jPanelContents.add(jScrollPaneTable, BorderLayout.CENTER);
        jScrollPaneTable.getViewport().add(myListTable, null);
        jPanelContents.add(jPanel1, BorderLayout.EAST);
        jPanel1.add(jPanelOKCancel, BorderLayout.SOUTH);
        jPanelOKCancel.add(jButtonApply, null);
        jPanel1.add(jPanelEdit, BorderLayout.NORTH);

        jPanelOKCancel.add(jButtonCancel, null);

        //
        myListTable.setModel(myLocationTableModel);
        myListTable.addMouseListener(new LocationDialog_myListTable_mouseAdapter(this));
        //

        //
        //  Stuff for sorting the table myTableHeader
        myTableHeader = myListTable.getTableHeader();
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
        myTableColumnModel = myListTable.getColumnModel();

        for (int i = 0; i < COLUMNS.length; i++)
        {
            myTableColumnModel.getColumn(i).setHeaderRenderer(myButtonRenderer);
            myTableColumnModel.getColumn(i).setPreferredWidth(COLUMNS[i]);
        }
        //

        LocationItemCellRenderer renderer = new LocationItemCellRenderer();
        renderer.setPreferredSize(new Dimension(200, 30));

    }

    private void setCentered()
    {
        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        this.setLocation( (screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    private void initialize()
    {

        pack();
        setCentered();
    }

    public static void main(String[] args)
    {
        LocationItem item1 = new LocationItem();
        item1.setDescription("Local property file for Iris");
        item1.setRemote(false);
        item1.setUrl("file:///c:/tradevision/iris/conf/iris.xml");
        item1.setIcon(ResourcesFactory.getImageIcon("greycirclewithletterL.gif"));

        LocationItem item2 = new LocationItem();
        item2.setDescription("Local property file for Iris");
        item2.setRemote(false);
        item2.setUrl("file:///d:/tradevision/iris/conf/iris.xml");
        item2.setIcon(ResourcesFactory.getImageIcon("greycirclewithletterL.gif"));

        ArrayList ls = new ArrayList();
        ls.add(item1);
        ls.add(item2);

        LocationDialog dlg = new LocationDialog(new JFrame(), "", true, null);
        dlg.setVisible(true);
    }

    public void headerMouseReleased(MouseEvent e)
    {
        int col = myTableHeader.columnAtPoint(e.getPoint());
        myButtonRenderer.setPressedColumn( -1);
        // clear
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
        this.myLocationTableModel.sort(isAscent, sortCol);
        this.myLocationTableModel.fireTableDataChanged();
    }

    void myListTable_mouseClicked(MouseEvent e)
    {
    }

    public IrisserverType getSelectedServier()
    {
        return selectedService;
    }

    void jButtonAdd_actionPerformed(ActionEvent e)
    {
        LocationItemDialog aLocationItemDialog = new LocationItemDialog(new JFrame(), "New server", true, this);

        aLocationItemDialog.setVisible(true);

    }

    public void addNewServer(LocationItem item)
    {
        this.myLocationTableModel.addNewServer(item);
    }

    void jButtonEdit_actionPerformed(ActionEvent e)
    {
        int row = myListTable.getSelectedRow();
        LocationItem selectedLocation = myLocationTableModel.getSelectedItem(row);
        LocationItemDialog aLocationItemDialog = new LocationItemDialog(new JFrame(), "New server", true, this);
        aLocationItemDialog.init(selectedLocation);
        aLocationItemDialog.setVisible(true);

    }

    void jButtonRemove_actionPerformed(ActionEvent e)
    {
        LocationItem removeItem = myLocationTableModel.getSelectedItem(myListTable.getSelectedRow());
        if (removeItem != null)
        {
            myLocationTableModel.deleteRow(removeItem);
        }
    }

    void jButtonCancel_actionPerformed(ActionEvent e)
    {
        System.exit(0);
    }

    void jButtonApply_actionPerformed(ActionEvent e)
    {
        int row = myListTable.getSelectedRow();
        if (row == -1)
        {
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("No server selected");
            dlg.setInformationText("You must select a server to proceed");
            dlg.setVisible(true);
            return;
        }
        else
        {
            if (myLocationTableModel.getisDirty())
            {

                Irisclient clientSetttings = SettingsFascade.getInstance().getIrisclientSettings();
                clientSetttings.getIrisserver().clear();
                LocationItem[] ls = myLocationTableModel.getAllItems();
                ArrayList als = new ArrayList();
                for (int i = 0; i < ls.length; i++)
                {
                    IrisserverType serversettings = ls[i].getServerinfo();
                    als.add(serversettings);
                }
                clientSetttings.getIrisserver().addAll(als);
                try
                {
                    SettingsFascade.getInstance().storeIrisclientSettings(clientSetttings);
                    StatusJPanel.getInstance().setStatusText("Saved settings for client");
                }
                catch (Exception ex)
                {
                    InformationDialog dlg = new InformationDialog();
                    dlg.setTitle("Could not store settings");
                    dlg.setInformationText("Unable to store settings");
                    dlg.setStackTrace(ex);
                    dlg.setVisible(true);
                }

            }
            row = myListTable.getSelectedRow();
            LocationItem selectedLocation = myLocationTableModel.getSelectedItem(row);
            this.selectedService = selectedLocation.getServerinfo();
            this.selectedUrl = selectedLocation.getUrl();
            this.setVisible(false);
        }

    }

    void jButtonddd_actionPerformed(ActionEvent e)
    {
        System.exit(0);
    }

}

class LocationDialog_myListTable_mouseAdapter
    extends java.awt.event.MouseAdapter
{
    LocationDialog adaptee;

    LocationDialog_myListTable_mouseAdapter(LocationDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e)
    {
        adaptee.myListTable_mouseClicked(e);
    }
}

class LocationDialog_jButtonAdd_actionAdapter
    implements java.awt.event.ActionListener
{
    LocationDialog adaptee;

    LocationDialog_jButtonAdd_actionAdapter(LocationDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButtonAdd_actionPerformed(e);
    }
}

class LocationDialog_jButtonEdit_actionAdapter
    implements java.awt.event.ActionListener
{
    LocationDialog adaptee;

    LocationDialog_jButtonEdit_actionAdapter(LocationDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButtonEdit_actionPerformed(e);
    }
}

class LocationDialog_jButtonRemove_actionAdapter
    implements java.awt.event.ActionListener
{
    LocationDialog adaptee;

    LocationDialog_jButtonRemove_actionAdapter(LocationDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButtonRemove_actionPerformed(e);
    }
}

class LocationDialog_jButtonApply_actionAdapter
    implements java.awt.event.ActionListener
{
    LocationDialog adaptee;

    LocationDialog_jButtonApply_actionAdapter(LocationDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButtonApply_actionPerformed(e);
    }

}

class LocationDialog_jButtonCancel_actionAdapter
    implements java.awt.event.ActionListener
{
    LocationDialog adaptee;

    LocationDialog_jButtonCancel_actionAdapter(LocationDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButtonCancel_actionPerformed(e);
    }
}
