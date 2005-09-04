package org.iris.client.swing.panels.logging;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import org.apache.log4j.*;
import org.iris.client.swing.panels.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.textfield.*;
import org.iris.client.swing.util.tooltip.*;
import org.iris.server.util.config.*;

public class ServiceLoggingPanel
    extends IrisPanel implements TableModelListener
{
    Logger myLogger = Logger.getLogger(ServiceLoggingPanel.class.getName());
    TitledBorder titledBorder3;
    TitledBorder titledBorder4;
    Border border2;
    TitledBorder titledBorder5;
    JLabel loggingFolderPathLabel = new JLabel();
    JTextField jTextFieldApplicationLoggingFolderPathTextField = new JTextField();
    JPanel loggingPathPanel = new JPanel();
    TitledBorder titledBorder1;
    private static ServiceLoggingPanel myInstance;
    JLabel imagejLabel = new JLabel();
    JLabel appenderTpeLabel = new JLabel();
    JLabel backupFileSizeLabel = new JLabel();
    JLabel numberOfBackupsLabel = new JLabel();

    private JTextField jTextFieldApplicationAppenderTypeTextField = new JTextField();
    private JTextField jTextFieldApplicationBackupFileSizeTextField = new JTextField();
    private JTextField jTextFieldApplicationNumberOfBackupsTextField;

    private JTextField jTextFieldApplicationLayout = new JTextField();

    private JTextField jTextFieldMessageBackupFileSizeTextField = new JTextField();
    private JTextField jTextFieldMessageAppenderTypeTextField1 = new JTextField();
    private JTextField jTextFieldMessageNumberOfBackupsTextField = new JTextField();
    private JTextField jTextFieldMessageloggingFolderPathTextField = new JTextField();

    Border border1;
    TitledBorder titledBorder2;

    LoggingTableModel myLoggingModel;
    TableColumnModel myTableColumnModel;
    LoggingTableModelListener myLoggingTableModelListener;
    JLabel layoutLabel = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabelLayout1 = new JLabel();
    JTextField jTextFieldMessageLayout = new JTextField();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JPanel loggingPathPanel1 = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    BorderLayout borderLayout3 = new BorderLayout();

    private static final String PANEL_IMAGE = "panellogging.gif";
    private static final String TAB_IMAGE = "panellogging_tab.gif";
    private static final String TAB_TITLE = "Logging";
    MultiLineLabel jLabel9 = new MultiLineLabel("An application logger is logging all activities relating to this service. The appender can be a Rollingfileappender or a DailyRollingfileappender.");

    MultiLineLabel jLabel1 = new MultiLineLabel("A message logger is logging all succesfully handled messages in raw content as received. It can be used to search for message content, without having to extract all archived messages for the sevice. The appender can be a Rollingfileappender or a DailyRollingfileappender.");

    public ServiceLoggingPanel()
    {
        jTextFieldApplicationNumberOfBackupsTextField = new JTextField()
        {
            public JToolTip createToolTip()
            {
                // does not work!?!?
                JMultilineToolTip ret = JMultilineToolTip.instance();
                ret.setToolTipArray(new String[]
                    {
                    "tips a sdfsdfas dflasdf ", "111111111111111111"});

                return ret;
            }
        };

        tabtitle = TAB_TITLE;
        tabIcon = ResourcesFactory.getImageIcon(TAB_IMAGE);
        try
        {
            jbInit();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static ServiceLoggingPanel getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new ServiceLoggingPanel();
        }
        return myInstance;
    }

    public static void main(String[] args)
    {
        JFrame fr = new JFrame();
        fr.getContentPane().add(new ServiceLoggingPanel());
        fr.setVisible(true);
    }

    public void initPanel(Service service)
    {
        try
        {
            this.getJTextFieldApplicationAppenderTypeTextField().setText(service.getApplicationappendertype());
            this.getJTextFieldApplicationBackupFileSizeTextField().setText(service.getApplicationappendermaxfilesize());
            this.getJTextFieldApplicationNumberOfBackupsTextField().setText("WWWWWWWw" +
                String.valueOf(service.getApplicationmaxBackupIndex()));
            this.getJTextFieldApplicationLoggingFolderPathTextField().setText(service.getApplicationlogfolder());
            this.getJTextFieldApplicationLayout().setText(service.getApplicationlogpattern());
            this.getJTextFieldMessageAppenderTypeTextField().setText(service.getMessageappendertype());
            this.getJTextFieldMessageBackupFileSizeTextField().setText(String.valueOf(service.
                getMessageappendermaxfilesize()));
            this.getJTextFieldMessageNumberOfBackupsTextField().setText(String.valueOf(service.getMessagemaxBackupIndex()));
            this.getJTextFieldMessageLoggingFolderPathTextField().setText(service.getMessagelogfolder());
            this.getJTextFieldMessageLayout().setText(service.getMessagelogpattern());
            myLogger.debug("Logging panel initialized");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private void jbInit() throws Exception
    {
        //jTextFieldApplicationNumberOfBackupsTextField.setText( "Multiline tooltip\n sdfasd fa sdf as df asdf\n asdfasdf asd" );

        myLoggingModel = new LoggingTableModel();

//        myLoggingTableModelListener = new LoggingTableModelListener(myLogginTable, myScrollPaneLoggingTable);
        //      myLoggingModel.addTableModelListener(myLoggingTableModelListener);
//horizbars

//        myXMLContainer.setSplit( false );
        titledBorder3 = new TitledBorder("");
        titledBorder4 = new TitledBorder(BorderFactory.createLineBorder(new Color(153, 153, 153), 2), "Reload Options");
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder5 = new TitledBorder(border2, "Reload Options");

        titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                        "Message Logging Options");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                        "Application Logging Options");
        loggingFolderPathLabel.setText("Logging folder path :");
        this.setLayout(borderLayout3);
        jTextFieldApplicationLoggingFolderPathTextField.setEnabled(true);
        //   jTextFieldApplicationLoggingFolderPathTextField.setT



        //setToolTipText( new MultilineToolTipUI(  "This is  where the logging files are stored"));
        jTextFieldApplicationLoggingFolderPathTextField.setText("");
        loggingPathPanel.setBorder(titledBorder2);
        loggingPathPanel.setDebugGraphicsOptions(0);
        loggingPathPanel.setLayout(gridBagLayout2);
        imagejLabel.setText("");
        imagejLabel.setIcon(ResourcesFactory.getImageIcon(PANEL_IMAGE));
        appenderTpeLabel.setText("Appender type");
        jTextFieldApplicationAppenderTypeTextField.setText("");
        backupFileSizeLabel.setText("Backup file size");
        jTextFieldApplicationBackupFileSizeTextField.setText("");
        numberOfBackupsLabel.setText("Number of backups");

        layoutLabel.setRequestFocusEnabled(true);
        layoutLabel.setText("Layout");
        jTextFieldApplicationLayout.setEnabled(true);
        jTextFieldApplicationLayout.setToolTipText("dddd");
        jTextFieldApplicationLayout.setText("");

        jLabel4.setText("Appender type");
        jTextFieldMessageBackupFileSizeTextField.setText("");
        jLabelLayout1.setText("Layout");
        jLabelLayout1.setRequestFocusEnabled(true);
        jTextFieldMessageLayout.setText("");
        jTextFieldMessageLayout.setEnabled(true);
        jLabel6.setText("Logging folder path :");
        jLabel7.setText("Backup file size");
        loggingPathPanel1.setLayout(gridBagLayout1);
        loggingPathPanel1.setDebugGraphicsOptions(0);
        loggingPathPanel1.setBorder(titledBorder1);
        jTextFieldMessageAppenderTypeTextField1.setText("");
        jLabel8.setText("Number of backups");
        jTextFieldMessageNumberOfBackupsTextField.setText("");
        jTextFieldMessageloggingFolderPathTextField.setEnabled(true);
        jTextFieldMessageloggingFolderPathTextField.setToolTipText("This is  where the logging files are stored");
        jTextFieldMessageloggingFolderPathTextField.setText("");
        jPanel2.setLayout(borderLayout2);
        jPanel2.setDebugGraphicsOptions(0);
        jPanel1.setDebugGraphicsOptions(0);
        jPanel1.setLayout(flowLayout1);
        jPanel3.setLayout(borderLayout1);

        loggingPathPanel.add(jTextFieldApplicationLoggingFolderPathTextField, new GridBagConstraints(1, 1, 1, 1, 1.0,
            0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 5), 480, 1));
        loggingPathPanel.add(loggingFolderPathLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 17), 51, 1));
        loggingPathPanel.add(jTextFieldApplicationAppenderTypeTextField, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 204), 287, 1));
        loggingPathPanel.add(jTextFieldApplicationBackupFileSizeTextField, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 300), 100, 1));
        loggingPathPanel.add(jTextFieldApplicationNumberOfBackupsTextField, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 10), 100, 1));
        loggingPathPanel.add(appenderTpeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(6, 10, 0, 0), 95, 1));
        loggingPathPanel.add(backupFileSizeLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(6, 10, 0, 0), 89, 1));
        loggingPathPanel.add(numberOfBackupsLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(6, 10, 0, 0), 75, 1));
        loggingPathPanel.add(layoutLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 5, 0), 138, 6));
        loggingPathPanel.add(jTextFieldApplicationLayout, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 5, 100), 287, 1));
        loggingPathPanel.add(jLabel9, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 15, 0), 0, 0));
        jPanel2.add(loggingPathPanel1, BorderLayout.CENTER);
        jPanel2.add(loggingPathPanel, BorderLayout.NORTH);
        loggingPathPanel1.add(jTextFieldMessageloggingFolderPathTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 21, 0, 5), 480, 0));
        loggingPathPanel1.add(jLabel6, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 0, 17), 51, 6));
        loggingPathPanel1.add(jTextFieldMessageAppenderTypeTextField1, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 200), 287, 1));
        loggingPathPanel1.add(jTextFieldMessageBackupFileSizeTextField, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 300), 100, 1));
        loggingPathPanel1.add(jTextFieldMessageNumberOfBackupsTextField, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 350), 100, 1));
        loggingPathPanel1.add(jLabel4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 95, 1));
        loggingPathPanel1.add(jLabel7, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 89, 1));
        loggingPathPanel1.add(jLabel8, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 75, 1));
        loggingPathPanel1.add(jLabelLayout1, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 138, 1));
        loggingPathPanel1.add(jTextFieldMessageLayout, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 20, 0, 100), 287, 1));
        this.add(jPanel1, BorderLayout.WEST);
        jPanel1.add(imagejLabel, null);
        this.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jPanel2, BorderLayout.NORTH);
        loggingPathPanel1.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 15, 0), 0, 0));
//        jScrollPane1.getViewport().add(jTextArea1, null);


        //
        //  Stuff for sorting the table myTableHeader
        /*myTableHeader = myLogginTable.getTableHeader();
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
         */
        //

    }

    public void tableChanged(TableModelEvent e)
    {
//         this.myLogginTable.changeSelection(e.getFirstRow(), 0, false, false);
        //       System.out.println( ((LoggingTableModel) myLogginTable.getModel()).getRowCount()  );

    }

    public void initLogWatcher(long intervall)
    {
        /*        LogWatcher watcher = new LogWatcher(jTloggingFolderPathStr + "log.txt", myLoggingModel);
                timer = new Timer();
                timer.schedule(watcher, 1000, intervall);
         */
    }

    void jButton2_actionPerformed(ActionEvent e)
    {
        //   this.myLoggingModel.reloadModel(null);
    }

    public JTextField getJTextFieldApplicationLayout()
    {
        return jTextFieldApplicationLayout;
    }

    public void setJTextFieldApplicationLayout(JTextField jTextFieldLayout)
    {
        this.jTextFieldApplicationLayout = jTextFieldLayout;
    }

    public JTextField getJTextFieldApplicationNumberOfBackupsTextField()
    {
        return jTextFieldApplicationNumberOfBackupsTextField;
    }

    public void setJTextFieldApplicationNumberOfBackupsTextField(JTextField jTextFieldnumberOfBackupsTextField)
    {
        this.jTextFieldApplicationNumberOfBackupsTextField = jTextFieldnumberOfBackupsTextField;
    }

    public void setJTextFieldApplicationAppenderTypeTextField(JTextField jTextFieldAppenderTypeTextField)
    {
        this.jTextFieldApplicationAppenderTypeTextField = jTextFieldAppenderTypeTextField;
    }

    public JTextField getJTextFieldApplicationAppenderTypeTextField()
    {
        return jTextFieldApplicationAppenderTypeTextField;
    }

    public JTextField getJTextFieldApplicationBackupFileSizeTextField()
    {
        return jTextFieldApplicationBackupFileSizeTextField;
    }

    public void setJTextFieldApplicationBackupFileSizeTextField(JTextField jTextFieldBackupFileSizeTextField)
    {
        this.jTextFieldApplicationBackupFileSizeTextField = jTextFieldBackupFileSizeTextField;
    }

    public JTextField getJTextFieldApplicationLoggingFolderPathTextField()
    {
        return jTextFieldApplicationLoggingFolderPathTextField;
    }

    public void setJTextFieldApplicationLoggingFolderPathTextField(JTextField jTextFieldloggingFolderPathTextField)
    {
        this.jTextFieldApplicationLoggingFolderPathTextField = jTextFieldloggingFolderPathTextField;
    }

////////

    public JTextField getJTextFieldMessageLayout()
    {
        return jTextFieldMessageLayout;
    }

    public void setJTextFieldMessageLayout(JTextField jTextFieldLayout)
    {
        this.jTextFieldMessageLayout = jTextFieldLayout;
    }

    public JTextField getJTextFieldMessageNumberOfBackupsTextField()
    {
        return jTextFieldMessageNumberOfBackupsTextField;
    }

    public void setJTextFieldMessageNumberOfBackupsTextField(JTextField jTextFieldnumberOfBackupsTextField)
    {
        this.jTextFieldMessageNumberOfBackupsTextField = jTextFieldnumberOfBackupsTextField;
    }

    public void setJTextFieldMessageAppenderTypeTextField(JTextField jTextFieldMessageAppenderTypeTextField)
    {
        this.jTextFieldMessageAppenderTypeTextField1 = jTextFieldMessageAppenderTypeTextField;
    }

    public JTextField getJTextFieldMessageAppenderTypeTextField()
    {
        return jTextFieldMessageAppenderTypeTextField1;
    }

    public JTextField getJTextFieldMessageBackupFileSizeTextField()
    {
        return jTextFieldMessageBackupFileSizeTextField;
    }

    public void setJTextFieldMessageBackupFileSizeTextField(JTextField jTextFieldMessageBackupFileSizeTextField)
    {
        this.jTextFieldMessageBackupFileSizeTextField = jTextFieldMessageBackupFileSizeTextField;
    }

    public JTextField getJTextFieldMessageLoggingFolderPathTextField()
    {
        return jTextFieldMessageloggingFolderPathTextField;
    }

    public void setJTextFieldMessageLoggingFolderPathTextField(JTextField jTextFieldMessageloggingFolderPathTextField)
    {
        this.jTextFieldMessageloggingFolderPathTextField = jTextFieldMessageloggingFolderPathTextField;
    }

}
