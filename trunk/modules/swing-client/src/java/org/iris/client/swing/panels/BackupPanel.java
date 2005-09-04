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

public class BackupPanel
    extends IrisPanel implements IrisPanelOperations
{
    static Logger myLogger = Logger.getLogger(BackupPanel.class.getName());
    final static MessageFormat FORMATTER = new MessageFormat("\n" + "\t\t<backup backuphandleron={0}>\n" +
                                           "\t\t\t<backupfolderpath>{1}</backupfolderpath>\n" + "\t\t</backup>");
    JLabel myJLabelErrorFolderPath = new JLabel();
    String folderScanIntervall;
    JLabel imagejLabel = new JLabel();
    TitledBorder titledBorder1;
    Border border1;
    TitledBorder titledBorder2;
    Border border2;
    TitledBorder titledBorder3;
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JCheckBox myJCheckboxenableBackupCheckBox = new JCheckBox();
    JTextField myJTextFieldBackupFolderPath = new JTextField();
    JLabel jLabelArchivePathjLabel1 = new JLabel();
    TitledBorder titledBorder4;
    Border border3;
    TitledBorder titledBorder5;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel3 = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanel4 = new JPanel();
    BorderLayout borderLayout4 = new BorderLayout();

    private static final String PANEL_IMAGE = "panelbackup.gif";
    private static final String TAB_IMAGE = "panelbackup_tab.gif";
    private static final String TAB_TITLE = "Backup";

    MultiLineLabel jLabel1 = new MultiLineLabel("If backup handling is set all files sucessfully handled by this service will be copied into the backup folder. Backup folders are usually archived once every day - depending on the settings under the archive settings.");

    public BackupPanel()
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
            myJTextFieldBackupFolderPath}, setFieldsEditable);
        //this.myJTextFieldBackupFolderPath.setEditable( setFieldsEditable );
    }

    public void initPanel(Service service, boolean setFieldsEditable)
    {
        try
        {
            setFieldsToEditable(setFieldsEditable);
            if (service == null)
            {
                myLogger.warn("Service object was null. Could not initialize properly");
                return;
            }

            this.getMyJTextFieldBackupFolderPath().setText(service.getBackupfolder());
            if (service.isBackuphandlerOn())
            {
                this.getMyJCheckboxenableBackupCheckBox().setSelected(true);
            }
            else
            {
                this.getMyJCheckboxenableBackupCheckBox().setSelected(false);
            }
            myLogger.debug("Backup panel initialized");
        }
        catch (Exception ex)
        {
            myLogger.error("Failed init panel for backup");
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

        this.setLayout(borderLayout3);
        imagejLabel.setText("");
        imagejLabel.setIcon(ResourcesFactory.getImageIcon(PANEL_IMAGE));

        jPanel2.setBorder(border2);
        jPanel2.setDebugGraphicsOptions(0);
        jPanel2.setLayout(borderLayout2);
        myJCheckboxenableBackupCheckBox.setText("Enable backup");
        myJCheckboxenableBackupCheckBox.setToolTipText("");
        myJTextFieldBackupFolderPath.setText("");
        myJTextFieldBackupFolderPath.setDisabledTextColor(Color.black);
        jLabelArchivePathjLabel1.setText("Backup folder path");
        jLabelArchivePathjLabel1.setToolTipText(
            "This is where the messages for this service are backuped after succesfull " + "delivery");
        jPanel3.setLayout(gridBagLayout1);
        jPanel4.setLayout(borderLayout4);
        //inPanel.add(jTextFielldArchiveFolderPath, null);

        jPanel3.add(myJTextFieldBackupFolderPath, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 29, 0, 16), 454, 1));
        jPanel3.add(jLabelArchivePathjLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 6, 0), 0, 0));
        jPanel3.add(myJCheckboxenableBackupCheckBox, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(12, 28, 4, 338), 42, -2));
        jPanel3.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 10, 0), 0, 0));
        this.add(jPanel1, BorderLayout.WEST);
        jPanel1.add(imagejLabel, null);
        this.add(jPanel4, BorderLayout.CENTER);
        jPanel4.add(jPanel2, BorderLayout.NORTH);
        jPanel2.add(jPanel3, BorderLayout.NORTH);

    }

    void enableArchivingjCheckBox_actionPerformed(ActionEvent e)
    {

    }

    void myJCheckboxenableBackupCheckBox_actionPerformed(ActionEvent e)
    {

    }

    public JCheckBox getMyJCheckboxenableBackupCheckBox()
    {
        return myJCheckboxenableBackupCheckBox;
    }

    public void setMyJCheckboxenableBackupCheckBox(JCheckBox myJCheckboxenableBackupCheckBox)
    {
        this.myJCheckboxenableBackupCheckBox = myJCheckboxenableBackupCheckBox;
    }

    public JTextField getMyJTextFieldBackupFolderPath()
    {
        return myJTextFieldBackupFolderPath;
    }

    public void setMyJTextFieldBackupFolderPath(JTextField myJTextFieldBackupFolderPath)
    {
        this.myJTextFieldBackupFolderPath = myJTextFieldBackupFolderPath;
    }

    public void changeServiceId(String newServiceId, String oldServiceId)
    {
        changeServiceId(myJTextFieldBackupFolderPath, newServiceId, oldServiceId);
    }

    public String toXml()
    {
        Object[] values =
                          {
                          new String("true"), new String(myJTextFieldBackupFolderPath.getText())};
        return FORMATTER.format(values);
    }

    void jButton1_actionPerformed(ActionEvent e)
    {
        System.out.println("  " + this.toXml());
    }

}
