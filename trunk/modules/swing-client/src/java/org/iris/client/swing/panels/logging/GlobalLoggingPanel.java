package org.iris.client.swing.panels.logging;

import java.io.*;
import java.util.Timer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.dialogs.*;
import org.iris.client.swing.util.statuspanel.*;
import org.iris.client.swing.util.tablesort.*;
import org.iris.server.util.config.*;

public class GlobalLoggingPanel
    extends JPanel implements TableModelListener
{
    Timer timer;

    private static int COLUMN1_SIZE = 140;
    private static int COLUMN2_SIZE = 55;
    private static int COLUMN3_SIZE = 120;
    private static int COLUMN4_SIZE = 700;
//    private static int COLUMN5_SIZE = 400;

    private static File myLogFile;
    private JPanel filterOptionsPanel = new JPanel();
    private TitledBorder titledBorder3;
    private TitledBorder titledBorder4;
    private Border border2;
    private TitledBorder titledBorder5;
    private String numberOfBackupsTextFieldStr;
    private static int[] COLUMNS =
        {
        COLUMN1_SIZE, COLUMN2_SIZE, COLUMN3_SIZE, COLUMN4_SIZE};

    private TitledBorder titledBorder1;
    private JScrollPane myScrollPaneLoggingTable = new JScrollPane();
    private JTextArea jTextArea1 = new JTextArea();
    private static GlobalLoggingPanel myInstance;
    private JLabel imagejLabel = new JLabel();
    private String loggingFolderPathStr;
    private Border border1;
    private TitledBorder titledBorder2;
    private String backupFileSizeStr;
    private String appenderTypeStr;

    private LoggingTableModel myLoggingModel;
    private SortButtonRenderer myButtonRenderer;
    private JTableHeader myTableHeader = null;
    private JTable myLogginTable = new JTable();
    private TableColumnModel myTableColumnModel;
    private LoggingTableModelListener myLoggingTableModelListener;
    private static int myLoggingServerPort = 4560;

    static int t = 0;
    JComboBox jComboBoxFilterLevel;
    JTextField jTextFieldNDC = new JTextField();
    JButton jButtonClear = new JButton();
    JButton jButtonPause = new JButton();
    JTextField jTextFieldThreadFilter = new JTextField();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JComboBox jComboBoxNDCServiceFilter;

    String myLog4jServerAddress; // = System.getProperty("loggingserverdefault");
    JScrollPane jScrollPane1 = new JScrollPane();
    JSplitPane jSplitPane1 = new JSplitPane();
    LogDetailPanel myLogDetailPanel;
    private static Logger myLogger = Logger.getLogger(GlobalLoggingPanel.class.getName());
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    JLabel jLabel4 = new JLabel();

    private GlobalLoggingPanel(String logServer, short logport)
    {
        try
        {

            myLoggingServerPort = logport;
            myLog4jServerAddress = logServer;
            jbInit();
            try
            {
                SocketHubConnector sa = new SocketHubConnector(myLog4jServerAddress, myLoggingServerPort,
                                        myLoggingModel);
                Thread t = new Thread(sa);
                t.start();
            }
            catch (Exception ex)
            {
                InformationDialog dlg = new InformationDialog();
                dlg.setTitle("No property file found");
                dlg.setInformationText("Unable to connect to socket hub server for receiving logging events. Host : " +
                    myLog4jServerAddress + ":" + myLoggingServerPort);
                dlg.setStackTrace(ex);
                dlg.setVisible(true);
                StatusJPanel.getInstance().setStatusErrorText(
                    "Unable to connect to socket hub server for receiving logging events. Host : " +
                    myLog4jServerAddress + ":" + myLoggingServerPort, 6000);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static GlobalLoggingPanel getInstance(String logServer, short logport)
    {
        if (myInstance == null)
        {
            myInstance = new GlobalLoggingPanel(logServer, logport);
        }
        return myInstance;
    }

    public static GlobalLoggingPanel getInstance()
    {
        return myInstance;
    }

    private void jbInit() throws Exception
    {
        myLoggingModel = new LoggingTableModel();
        myLogDetailPanel = new LogDetailPanel(myLogginTable, myLoggingModel);

        myLoggingTableModelListener = new LoggingTableModelListener(myLogginTable, myScrollPaneLoggingTable);
        myLoggingModel.addTableModelListener(myLoggingTableModelListener);

        myLogginTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //horizbars

        myLogginTable.setDefaultRenderer(String.class, new ColoredTableCellRenderer());

        titledBorder3 = new TitledBorder("");
        titledBorder4 = new TitledBorder(BorderFactory.createLineBorder(new Color(153, 153, 153), 2), "Reload Options");
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder5 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                        "Filter Options");
        this.myLogginTable.setModel(myLoggingModel);

        titledBorder1 = new TitledBorder("");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(border1, "Logging options");
        this.setLayout(borderLayout2);
        imagejLabel.setText("");
        imagejLabel.setIcon(ResourcesFactory.getImageIcon("panellogging.gif"));
        filterOptionsPanel.setBorder(titledBorder5);
        filterOptionsPanel.setLayout(gridBagLayout1);

        String[] services = ClientSystemConfigHandler.getInstance().getServiceNames();
        String[] servicesWithAll = new String[services.length + 1]; //.getServiceNames().length + 1];
        servicesWithAll[0] = "ALL";
        for (int i = 1; i < servicesWithAll.length; i++)
        {
            servicesWithAll[i] = services[i - 1];
        }
        jComboBoxNDCServiceFilter = new JComboBox(servicesWithAll);
        jComboBoxNDCServiceFilter.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jComboBoxNDCServiceFilter_actionPerformed(e);
            }
        });
        jComboBoxNDCServiceFilter.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jComboBoxNDCServiceFilter_actionPerformed(e);
            }
        });
        jComboBoxNDCServiceFilter.addActionListener(new GlobalLoggingPanel_jComboBoxNDCServiceFilter_actionAdapter(this));

        final Priority[] allPriorities = Priority.getAllPossiblePriorities();
        jComboBoxFilterLevel = new JComboBox(allPriorities);
        jComboBoxFilterLevel.addActionListener(new GlobalLoggingPanel_jComboBoxFilterLevel_actionAdapter(this));
        final Priority lowest = allPriorities[allPriorities.length - 1];
        jComboBoxFilterLevel.setSelectedItem(lowest);
        myLoggingModel.setPriorityFilter(lowest);

        jTextFieldNDC.setToolTipText("Filters on service (or NDC)");
        jTextFieldNDC.setText("");
        jTextFieldNDC.getDocument().addDocumentListener(new DocumentListener()
        {
            public void insertUpdate(DocumentEvent aEvent)
            {
                myLoggingModel.setNDCFilter(jTextFieldNDC.getText());
            }

            public void removeUpdate(DocumentEvent aEvent)
            {
                myLoggingModel.setNDCFilter(jTextFieldNDC.getText());
            }

            public void changedUpdate(DocumentEvent aEvent)
            {
                myLoggingModel.setNDCFilter(jTextFieldNDC.getText());
            }
        });

        jButtonClear.setToolTipText("Clear log table");
        jButtonClear.setText("CLEAR");
        jButtonClear.setIcon(ResourcesFactory.getImageIcon("Remove24.gif"));
        jButtonClear.addActionListener(new GlobalLoggingPanel_jButtonClear_actionAdapter(this));
        jButtonPause.setToolTipText("Pause logging");
        jButtonPause.setText("PAUSE");
        jButtonPause.setIcon(ResourcesFactory.getImageIcon("Pause24.gif"));
        jButtonPause.setRolloverEnabled(true);

        jButtonPause.addActionListener(new GlobalLoggingPanel_jButtonPause_actionAdapter(this));
        jTextFieldThreadFilter.setText("");
        jTextFieldThreadFilter.addActionListener(new GlobalLoggingPanel_jTextFieldThreadFilter_actionAdapter(this));
        jLabel1.setToolTipText("Actually filters on NDC");
        jLabel1.setText("Filter on service:");
        jLabel2.setText("Filter on level");
        jLabel3.setText("Filter on thread:");
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        myLogDetailPanel.setPreferredSize(new Dimension(200, 200));
        myScrollPaneLoggingTable.setPreferredSize(new Dimension(200, 300));
        jPanel3.setLayout(borderLayout1);
        jPanel3.setMinimumSize(new Dimension(500, 133));
        jPanel3.setPreferredSize(new Dimension(500, 445));
        this.setPreferredSize(new Dimension(580, 445));
        jLabel4.setText("");
        filterOptionsPanel.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 30, 4));
        filterOptionsPanel.add(jComboBoxFilterLevel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 60, 0));
        filterOptionsPanel.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 5, 0, 10), 30, -1));
        filterOptionsPanel.add(jTextFieldNDC, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 50, 0));
        filterOptionsPanel.add(jComboBoxNDCServiceFilter, new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 60, 0));
        filterOptionsPanel.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 5, 4, 10), 30, 6));
        filterOptionsPanel.add(jButtonClear, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 10, -13));
        filterOptionsPanel.add(jButtonPause, new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 5, 4, 100), 10, -13));
        filterOptionsPanel.add(jTextFieldThreadFilter, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 4, 10), 50, 0));
        jPanel3.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jSplitPane1, null);
        this.add(jPanel2, BorderLayout.WEST);
        jPanel2.add(imagejLabel, null);
        this.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(filterOptionsPanel, BorderLayout.NORTH);
        jSplitPane1.add(myLogDetailPanel, JSplitPane.BOTTOM);
        jSplitPane1.add(myScrollPaneLoggingTable, JSplitPane.TOP);
        myScrollPaneLoggingTable.getViewport().add(myLogginTable, null);
        filterOptionsPanel.add(jLabel4, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
            GridBagConstraints.NONE, new Insets(0, 0, 0, 20), 100, 0));
//        jScrollPane1.getViewport().add(jTextArea1, null);


        //
        //  Stuff for sorting the table myTableHeader
        myTableHeader = myLogginTable.getTableHeader();
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
        myTableColumnModel = myLogginTable.getColumnModel();

        for (int i = 0; i < COLUMNS.length; i++)
        {
            myTableColumnModel.getColumn(i).setHeaderRenderer(myButtonRenderer);
            myTableColumnModel.getColumn(i).setPreferredWidth(COLUMNS[i]);
        }
        //

    }

    public String getLoggingFolderPathStr()
    {
        return loggingFolderPathStr;
    }

    public String getBackupFileSizeStr()
    {
        return backupFileSizeStr;

    }

    public String getAppenderTypeStr()
    {
        return appenderTypeStr;
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
//        this.myLoggingModel.sort(isAscent, sortCol);

        this.myLoggingModel.fireTableChanged(new TableModelEvent(null, -1, -1)); // fireTableDataChanged();
    }

    void reloadButton_actionPerformed(ActionEvent e)
    {
        String path = "C:/Tradevision/iris/services/xmlemail_in-email/log/";

//        setLoggingFolderPathStr(  path);
        this.myLoggingModel.fireTableDataChanged();
    }

    public void tableChanged(TableModelEvent e)
    {
//         this.myLogginTable.changeSelection(e.getFirstRow(), 0, false, false);
        //       System.out.println( ((LoggingTableModel) myLogginTable.getModel()).getRowCount()  );

    }

    public void stopLogWatcher()
    {
        if (timer != null)
        {
            timer.cancel();
        }
    }

    void jButtonPause_actionPerformed(ActionEvent e)
    {
        myLoggingModel.toggle();
    }

    void autoReloadCheckBox_actionPerformed(ActionEvent e)
    {

    }

    public String getNumberOfBackupsTextFieldStr()
    {
        return numberOfBackupsTextFieldStr;
    }

    void jComboBoxFilterLevel_actionPerformed(ActionEvent e)
    {
        myLoggingModel.setPriorityFilter( (Priority) jComboBoxFilterLevel.getSelectedItem());

    }

    void jButtonClear_actionPerformed(ActionEvent e)
    {
        myLoggingModel.clear();
    }

    void jTextFieldThreadFilter_actionPerformed(ActionEvent e)
    {
        myLoggingModel.setThreadFilter(jTextFieldThreadFilter.getText());

    }

    void jComboBoxNDCServiceFilter_actionPerformed(ActionEvent e)
    {
        if ( ( (String) jComboBoxNDCServiceFilter.getSelectedItem()).equalsIgnoreCase("ALL"))
        {
            myLoggingModel.setNDCFilter("");

        }
        else
        {
            myLoggingModel.setNDCFilter( (String) jComboBoxNDCServiceFilter.getSelectedItem());

        }
    }

}

class GlobalLoggingPanel_jComboBoxFilterLevel_actionAdapter
    implements java.awt.event.ActionListener
{
    GlobalLoggingPanel adaptee;

    GlobalLoggingPanel_jComboBoxFilterLevel_actionAdapter(GlobalLoggingPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jComboBoxFilterLevel_actionPerformed(e);
    }
}

class GlobalLoggingPanel_jButtonClear_actionAdapter
    implements java.awt.event.ActionListener
{
    GlobalLoggingPanel adaptee;

    GlobalLoggingPanel_jButtonClear_actionAdapter(GlobalLoggingPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButtonClear_actionPerformed(e);
    }
}

class GlobalLoggingPanel_jButtonPause_actionAdapter
    implements java.awt.event.ActionListener
{
    GlobalLoggingPanel adaptee;

    GlobalLoggingPanel_jButtonPause_actionAdapter(GlobalLoggingPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButtonPause_actionPerformed(e);
    }
}

class GlobalLoggingPanel_jTextFieldThreadFilter_actionAdapter
    implements java.awt.event.ActionListener
{
    GlobalLoggingPanel adaptee;

    GlobalLoggingPanel_jTextFieldThreadFilter_actionAdapter(GlobalLoggingPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jTextFieldThreadFilter_actionPerformed(e);
    }
}

class GlobalLoggingPanel_jComboBoxNDCServiceFilter_actionAdapter
    implements java.awt.event.ActionListener
{
    GlobalLoggingPanel adaptee;

    GlobalLoggingPanel_jComboBoxNDCServiceFilter_actionAdapter(GlobalLoggingPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jComboBoxNDCServiceFilter_actionPerformed(e);
    }
}
