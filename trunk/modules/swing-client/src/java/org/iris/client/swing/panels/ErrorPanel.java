package org.iris.client.swing.panels;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.textfield.*;
import org.iris.server.util.config.*;

public class ErrorPanel
    extends IrisPanel implements IrisPanelOperations
{
    static Logger myLogger = Logger.getLogger(ErrorPanel.class.getName());

    JLabel myJLabelErrorFolderPath = new JLabel();
    JTextField myJTextFieldErrorFolderPath = new JTextField();
    String folderScanIntervall;
    JLabel imagejLabel = new JLabel();
    TitledBorder titledBorder1;
    Border border1;
    TitledBorder titledBorder2;
    JPanel jPanel1 = new JPanel();
    Border border2;
    TitledBorder titledBorder3;
    JCheckBox myJCheckboxEnableErrorHandlingjCheckBox1 = new JCheckBox();
    BorderLayout borderLayout1 = new BorderLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JTextField myJTextFieldErrorCronIntervall = new JTextField();
    TitledBorder titledBorder4;
    Border border3;
    TitledBorder titledBorder5;
    JPanel jPanelGif = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    private static final String PANEL_IMAGE = "panelerrors.gif";
    private static final String TAB_IMAGE = "panelerrors_tab.gif";
    private static final String TAB_TITLE = "Errors";

    MultiLineLabel jLabel3 = new MultiLineLabel("Files that could not be handled due to destination not reachable will be copied to the error folder. This folder (if error handling is set) will be scanned according to the cron intervall specified below");

    public ErrorPanel()
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
            myJTextFieldErrorCronIntervall, myJTextFieldErrorFolderPath}, setFieldsEditable);
        //this.myJTextFieldErrorCronIntervall.setEditable( setFieldsEditable );
        //this.myJTextFieldErrorFolderPath.setEditable( setFieldsEditable );
    }

    public void initPanel(Service service, boolean setFieldsEditable)
    {
        setFieldsToEditable(setFieldsEditable);
        if (service == null)
        {
            myLogger.warn("Service object was null. Could not initialize properly");
            return;
        }

        this.getMyJTextFieldErrorFolderPath().setText(service.getErrorfoldername());

        if (service.isErrorhandlerOn())
        {
            this.getMyJCheckboxEnableErrorHandlingjCheckBox1().setSelected(true);
        }
        else
        {
            this.getMyJCheckboxEnableErrorHandlingjCheckBox1().setSelected(false);
        }

        myJTextFieldErrorCronIntervall.setText(service.getCronintervall());

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
        myJTextFieldErrorFolderPath.setEnabled(true);
        myJTextFieldErrorFolderPath.setToolTipText("This ise where messages handled by this service are delivered");
        myJTextFieldErrorFolderPath.setDisabledTextColor(Color.black);
        myJTextFieldErrorFolderPath.setText("");

        this.setLayout(borderLayout3);
        imagejLabel.setText("");
        imagejLabel.setIcon(ResourcesFactory.getImageIcon(PANEL_IMAGE));
        jPanel1.setBorder(titledBorder3);
        jPanel1.setLayout(gridBagLayout1);

        myJCheckboxEnableErrorHandlingjCheckBox1.setText("Enable error handling");
        myJCheckboxEnableErrorHandlingjCheckBox1.setToolTipText("");
        jLabel1.setToolTipText("This is where messages are stored if not sent succesfully");
        jLabel1.setText("Error folder path");
        jLabel2.setText("Error cron intervall");
        myJTextFieldErrorCronIntervall.setText("");
        myJTextFieldErrorCronIntervall.setToolTipText("This ise where messages handled by this service are delivered");
        myJTextFieldErrorCronIntervall.setEnabled(true);
        jPanel2.setLayout(borderLayout2);
        //inPanel.add(jTextFielldArchiveFolderPath, null);

        jPanel1.add(jLabel1, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(2, 10, 0, 488), 38, 7));
        jPanel1.add(myJTextFieldErrorFolderPath, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(2, 30, 0, 1), 458, 2));
        jPanel1.add(myJCheckboxEnableErrorHandlingjCheckBox1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 26, 0, 241), 99, -2));
        jPanel1.add(jLabel2, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 8, 0), 23, 3));
        jPanel1.add(myJTextFieldErrorCronIntervall, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 28, 8, 1), 460, 2));
        jPanel1.add(jLabel3, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 10, 0), 0, 0));
        this.add(jPanelGif, BorderLayout.WEST);
        jPanelGif.add(imagejLabel, null);
        this.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel1, BorderLayout.NORTH);
    }

    void enableArchivingjCheckBox_actionPerformed(ActionEvent e)
    {
    }

    public JTextField getMyJTextFieldErrorFolderPath()
    {
        return myJTextFieldErrorFolderPath;
    }

    public void setMyJTextFieldErrorFolderPath(JTextField myJTextFieldErrorFolderPath)
    {
        this.myJTextFieldErrorFolderPath = myJTextFieldErrorFolderPath;
    }

    public JCheckBox getMyJCheckboxEnableErrorHandlingjCheckBox1()
    {
        return myJCheckboxEnableErrorHandlingjCheckBox1;
    }

    public void setMyJCheckboxEnableErrorHandlingjCheckBox1(JCheckBox myJCheckboxEnableErrorHandlingjCheckBox1)
    {
        this.myJCheckboxEnableErrorHandlingjCheckBox1 = myJCheckboxEnableErrorHandlingjCheckBox1;
    }

    void myJCheckboxenableBackupCheckBox_actionPerformed(ActionEvent e)
    {
    }

    public void changeServiceId(String newServiceId, String oldServiceId)
    {
        changeServiceId(myJTextFieldErrorFolderPath, newServiceId, oldServiceId);
    }
}
