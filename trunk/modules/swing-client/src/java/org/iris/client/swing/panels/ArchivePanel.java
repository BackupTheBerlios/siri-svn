package org.iris.client.swing.panels;

import java.text.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.textfield.*;
import org.iris.server.util.config.*;

public class ArchivePanel
    extends IrisPanel
{
    final static MessageFormat FORMATTER = new MessageFormat("\n" + "\t\t<backup backuphandleron={0}>\n" +
                                           "\t\t\t<backupfolderpath>{1}</backupfolderpath>\n" + "\t\t</backup>");

    static Logger myLogger = Logger.getLogger(ArchivePanel.class.getName());
    String myFolderScanIntervall;
    JPanel inPanel = new JPanel();
    JLabel imagejLabel = new JLabel();
    TitledBorder titledBorder1;
    Border border1;
    TitledBorder titledBorder2;
    Border border2;
    TitledBorder titledBorder3;
    JCheckBox myJCheckboxenableArchivingjCheckBox = new JCheckBox();
    JLabel jLabelArchivePathjLabel = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    JTextField myJTextFieldArchiveFolderPAth = new JTextField();
    TitledBorder titledBorder4;
    Border border3;
    TitledBorder titledBorder5;
    JPanel jPanelGif = new JPanel();
    JPanel jPanel2 = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanelCenter = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();

    private static final String PANEL_IMAGE = "panelarchiving.gif";
    private static final String TAB_IMAGE = "panelarchiving_tab.gif";
    private static final String TAB_TITLE = "Archiving";

    MultiLineLabel jLabel1 = new MultiLineLabel("The archive folder path is where all archived files will be stored. The scheduler for archiving is global and not set here.");

    public ArchivePanel()
    {
        tabtitle = TAB_TITLE;
        tabIcon = ResourcesFactory.getImageIcon(TAB_IMAGE);

        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void setFieldsToEditable(boolean setFieldsEditable)
    {
        setComponentsEnabled(new JComponent[]
            {
            myJTextFieldArchiveFolderPAth}, setFieldsEditable);
        //this.myJTextFieldArchiveFolderPAth.setEditable( setFieldsEditable );
    }

    public void initPanel(Service service, boolean setFieldsEditable)
    {
        myLogger.debug("Initinit archive panel" + service.getArchivefoldername());

        setFieldsToEditable(setFieldsEditable);
        if (service == null)
        {
            myLogger.warn("Service object was null. Could not initialize properly");
            return;
        }
        this.myJTextFieldArchiveFolderPAth.setText(service.getArchivefoldername());
        if (service.isArchiverOn())
        {
            this.getMyJCheckboxenableArchivingjCheckBox().setSelected(true);
        }
        else
        {
            this.getMyJCheckboxenableArchivingjCheckBox().setSelected(false);
        }
    }

    void jbInit() throws Exception
    {
        titledBorder1 = new TitledBorder("");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                        "Archiving");
        border2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "Backup");
        titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                        "Error handling");
        //jTextFielldArchiveFolderPath.setEditable(false);
        //jTextFielldArchiveFolderPath.setText("");
        //jTextFielldArchiveFolderPath.setBounds(new Rectangle(161, 23, 419, 20));

//        jTextFielldArchiveFolderPath.setEditable(false);
//      jTextFielldArchiveFolderPath.setText("");
        //    jTextFielldArchiveFolderPath.setBounds(new Rectangle(160, 25, 419, 20));
        titledBorder4 = new TitledBorder("");
        border3 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder5 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                        "Error & Inactivity Notification");
        jLabelArchivePathjLabel.setToolTipText("This is where the messages for this service are archived");
        jLabelArchivePathjLabel.setText("Archive folder path");
        inPanel.setLayout(borderLayout4);

        this.setLayout(borderLayout2);
        inPanel.setBorder(titledBorder2);
        imagejLabel.setText("");
        imagejLabel.setIcon(ResourcesFactory.getImageIcon(PANEL_IMAGE));
        myJCheckboxenableArchivingjCheckBox.setToolTipText("");
        myJCheckboxenableArchivingjCheckBox.setText("Enable archiving");

        myJTextFieldArchiveFolderPAth.setCaretColor(Color.black);
        myJTextFieldArchiveFolderPAth.setDisabledTextColor(Color.black);
        myJTextFieldArchiveFolderPAth.setText("");
        jPanel2.setLayout(gridBagLayout1);
        jPanelCenter.setLayout(borderLayout3);
        jPanelCenter.setMinimumSize(new Dimension(400, 75));
        jPanelCenter.setPreferredSize(new Dimension(400, 75));

        jPanel2.add(jLabelArchivePathjLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        jPanel2.add(myJCheckboxenableArchivingjCheckBox, new GridBagConstraints(1, 2, 1, 2, 0.0, 0.0,
            GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 10, 0), 0, 0));
        jPanel2.add(myJTextFieldArchiveFolderPAth, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(4, 3, 1, 55), 524, 0));
        this.add(jPanelGif, BorderLayout.WEST);
        jPanelGif.add(imagejLabel, null);
        this.add(jPanelCenter, BorderLayout.CENTER);
        //inPanel.add(jTextFielldArchiveFolderPath, null);

        jPanelCenter.add(inPanel, BorderLayout.NORTH);
        inPanel.add(jPanel2, BorderLayout.NORTH);

    }

    public JTextField getMyJTextFieldArchiveFolderPAth()
    {
        return myJTextFieldArchiveFolderPAth;
    }

    public JCheckBox getMyJCheckboxenableArchivingjCheckBox()
    {
        return myJCheckboxenableArchivingjCheckBox;
    }

    public void setMyJCheckboxenableArchivingjCheckBox(JCheckBox myJCheckboxenableArchivingjCheckBox)
    {
        this.myJCheckboxenableArchivingjCheckBox = myJCheckboxenableArchivingjCheckBox;
    }

    public void setMyJTextFieldArchiveFolderPAth(JTextField myJTextFieldArchiveFolderPAth)
    {
        this.myJTextFieldArchiveFolderPAth = myJTextFieldArchiveFolderPAth;
    }

    void myJCheckboxenableBackupCheckBox_actionPerformed(ActionEvent e)
    {

    }

    public void changeServiceId(String newServiceId, String oldServiceId)
    {
        changeServiceId(myJTextFieldArchiveFolderPAth, newServiceId, oldServiceId);
    }

    public String toXml()
    {
        Object[] values =
                          {
                          new String("true"), new String(myJTextFieldArchiveFolderPAth.getText())};
        return FORMATTER.format(values);
    }

    void jButton1_actionPerformed(ActionEvent e)
    {
        System.out.println("" + toXml());
    }

}
