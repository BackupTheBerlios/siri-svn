package org.iris.client.swing.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.textfield.*;
import org.iris.server.util.config.*;

public class InOutPanel //extends JPanel
    extends IrisPanel
{
    static Logger myLogger = Logger.getLogger(InOutPanel.class.getName());

    JTextField myInPathTextField = new JTextField();
    JTextField myOutPathTextField = new JTextField();
    JLabel inpathjLabel = new JLabel();
    JLabel outPathjLabel = new JLabel();

    private String inPathStr;
    JLabel inFolderScanIntervalljLabel = new JLabel();
    JTextField inFolderScanIntervall = new JTextField();
    private String outPathStr;
    private String folderScanIntervall;
    JPanel inPanel = new JPanel();
    JLabel imagejLabel = new JLabel();
    TitledBorder titledBorder1;
    Border border1;
    TitledBorder titledBorder2;
    JPanel jPanel1 = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel4 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    GridBagLayout gridBagLayout3 = new GridBagLayout();

    private static final String PANEL_IMAGE = "panelejbservice.gif";
    private static final String TAB_IMAGE = "panelejbservice_tab.gif";
    private static final String TAB_TITLE = "In / Out";
    MultiLineLabel inHelptextLabel = new MultiLineLabel("The infolder is scanned according" +
                                     "to the intervall specified in ms.");

    MultiLineLabel jLabel1 = new MultiLineLabel("All files that are processed are delivered to the out folder, unless there is a problem incase they are copied to the error folder (if error handling is set ).");

    public InOutPanel()
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
            myInPathTextField, myOutPathTextField, inFolderScanIntervall}, setFieldsEditable);
        //    myInPathTextField.setEditable( setFieldsEditable );
        // myOutPathTextField.setEditable( setFieldsEditable );

        //myInFolderScanIntervall.setEditable( setFieldsEditable );
    }

    public void initPanel(Service service, boolean setFieldsEditable)
    {
        setFieldsToEditable(setFieldsEditable);
        if (service == null)
        {
            myLogger.warn("Service object was null. Could not initialize properly");
            return;
        }

        inFolderScanIntervall.setText(String.valueOf(service.getInFolderIntervall()));
        myInPathTextField.setText(String.valueOf(service.getInfolderPath()));
        myOutPathTextField.setText( ( (ServiceFileFile) service).getOutfolderPath());
    }

    void jbInit() throws Exception
    {
        titledBorder1 = new TitledBorder("");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(border1, "In");
        myInPathTextField.setDisabledTextColor(Color.black);
        myInPathTextField.setText("");

        myOutPathTextField.setDisabledTextColor(Color.black);
        myOutPathTextField.setText("");
        inpathjLabel.setToolTipText("This ise where messages handled by this service are delivered");
        inpathjLabel.setText("Outfolder path");
        outPathjLabel.setToolTipText("This is where the messages for this service arrives");
        outPathjLabel.setText("Infolder path");
        inFolderScanIntervalljLabel.setText("Infolder intervall [ms]");
        inFolderScanIntervall.setDisabledTextColor(Color.black);
        inFolderScanIntervall.setText("");
        inPanel.setLayout(gridBagLayout2);

        this.setLayout(borderLayout2);
        inPanel.setBorder(titledBorder2);
        imagejLabel.setText("");
        imagejLabel.setIcon(ResourcesFactory.getImageIcon(PANEL_IMAGE));
        jPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
            "Out"));
        jPanel1.setLayout(gridBagLayout1);
        jPanel2.setLayout(gridBagLayout3);
        jPanel3.setLayout(borderLayout1);

        inPanel.add(outPathjLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 13, 0, 34), 33, 7));
        inPanel.add(myInPathTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 12), 415, 0));
        inPanel.add(inFolderScanIntervall, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(6, 0, 19, 345), 82, 2));
        inPanel.add(inFolderScanIntervalljLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(9, 13, 19, 0), 28, -1));
        inPanel.add(inHelptextLabel, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 10, 10, 0), 0, 0));

        jPanel2.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
            GridBagConstraints.BOTH, new Insets(0, 5, 72, 9), 0, 0));
        jPanel2.add(inPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
            GridBagConstraints.BOTH, new Insets(3, 5, 0, 9), 5, 0));
        jPanel1.add(myOutPathTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(3, 39, 14, 16), 415, 0));
        jPanel1.add(inpathjLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(3, 9, 14, 0), 24, 7));
        jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 10, 0), 0, 0));
        this.add(jPanel4, BorderLayout.WEST);
        jPanel4.add(imagejLabel, null);
        this.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jPanel2, BorderLayout.NORTH);

    }

    public String getInPathStr()
    {
        return inPathStr;
    }

    public void setInPathStr(String inPathStr)
    {
        this.inPathStr = inPathStr;
        this.myInPathTextField.setText(inPathStr);
    }

    public String getOutPathStr()
    {
        return outPathStr;
    }

    public void setOutPathStr(String outPathStr)
    {
        this.outPathStr = outPathStr;
        this.myOutPathTextField.setText(outPathStr);
    }

    public String getFolderScanIntervall()
    {
        return folderScanIntervall;

    }

    public void setFolderScanIntervall(String folderScanIntervall)
    {
        this.folderScanIntervall = folderScanIntervall;
        this.inFolderScanIntervall.setText(folderScanIntervall);
    }

    public void changeServiceId(String newServiceId, String oldServiceId)
    {
        changeServiceId(myInPathTextField, newServiceId, oldServiceId);
        changeServiceId(myOutPathTextField, newServiceId, oldServiceId);

    }

}
