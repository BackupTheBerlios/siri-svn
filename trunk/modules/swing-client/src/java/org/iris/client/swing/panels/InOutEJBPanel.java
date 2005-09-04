package org.iris.client.swing.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.textfield.*;
import org.iris.server.util.config.*;

public class InOutEJBPanel
    extends IrisPanel
{
    public final static ImageIcon TABICON = ResourcesFactory.getImageIcon("panelejbservice_tab.gif"); //

    static Logger myLogger = Logger.getLogger(InOutPanel.class.getName());

    JTextField myInPathTextField = new JTextField();
    JTextField mySessionEJBTextField = new JTextField();
    JTextField myInFolderScanIntervall = new JTextField();
    JTextField mySessionEJBMethodTextField = new JTextField();

    JLabel InpathjLabel = new JLabel();
    JLabel OutPathjLabel = new JLabel();
    private static InOutEJBPanel myInOutPanel;
    private String inPathStr;
    JLabel inFolderScanIntervalljLabel = new JLabel();

    private String outPathStr;
    private String folderScanIntervall;
    JPanel inPanel = new JPanel();
    JLabel imagejLabel = new JLabel();
    TitledBorder titledBorder1;
    Border border1;
    TitledBorder titledBorder2;
    JPanel injPanel = new JPanel();
    private String sessionEJB;
    Border border2;
    TitledBorder titledBorder3;
    JLabel InpathjLabel1 = new JLabel();

    private String sessionEJBMethod;
    JPanel jPanelGif = new JPanel();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JPanel jPanel1 = new JPanel();
    GridBagLayout gridBagLayout3 = new GridBagLayout();
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    private static final String PANEL_IMAGE = "panelejbservice.gif";
    private static final String TAB_IMAGE = "panelejbservice_tab.gif";
    private static final String TAB_TITLE = "In / Out";

    MultiLineLabel jLabel1 = new MultiLineLabel("A session ejb is a component residing in a J2EE server. Messages are read and their contents are sent to the receiving component in the J2EE server.");

    MultiLineLabel jLabel2 = new MultiLineLabel("All files that are processed are delivered to the out folder, unless there is a problem incase they are copied to the error folder (if error handling is set ).");

    public InOutEJBPanel()
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

        myInOutPanel = this;
    }

    public static InOutEJBPanel getInstance()
    {
        if (myInOutPanel == null)
        {
            new InOutEJBPanel();
        }
        return myInOutPanel;

    }

    public void initPanel(Service service, boolean setFieldsEditable)
    {
        setFieldsToEditable(setFieldsEditable);
        if (service == null)
        {
            myLogger.warn("Service object was null. Could not initialize properly");
            return;
        }

        myInFolderScanIntervall.setText(String.valueOf(service.getInFolderIntervall()));
        myInPathTextField.setText(String.valueOf(service.getInfolderPath()));
        mySessionEJBTextField.setText( ( (ServiceFileEJB) service).getServiceName());
        mySessionEJBMethodTextField.setText( ( (ServiceFileEJB) service).getServiceMethod());

    }

    public void setFieldsToEditable(boolean setFieldsEditable)
    {
        setComponentsEnabled(new JComponent[]
            {
            myInPathTextField, mySessionEJBTextField, myInFolderScanIntervall}, setFieldsEditable);
    }

    void jbInit() throws Exception
    {
        titledBorder1 = new TitledBorder("");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "In");
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder3 = new TitledBorder(border2, "Out");
        myInPathTextField.setEditable(true);
        myInPathTextField.setText("");

        mySessionEJBTextField.setEditable(true);
        mySessionEJBTextField.setText("");
        InpathjLabel.setToolTipText("This ise where messages handled by this service are delivered");
        InpathjLabel.setText("Session EJB");
        OutPathjLabel.setToolTipText("This is where the messages for this service arrives");
        OutPathjLabel.setText("Infolder path");
        inFolderScanIntervalljLabel.setText("Infolder intervall [ms]");
        myInFolderScanIntervall.setEditable(true);
        myInFolderScanIntervall.setText("");
        inPanel.setLayout(gridBagLayout2);

        this.setLayout(borderLayout2);
        inPanel.setBorder(titledBorder2);
        imagejLabel.setText("");
        imagejLabel.setIcon(ResourcesFactory.getImageIcon(PANEL_IMAGE));
        injPanel.setBorder(titledBorder3);
        injPanel.setDebugGraphicsOptions(0);
        injPanel.setLayout(gridBagLayout1);
        InpathjLabel1.setText("Session EJB method");
        InpathjLabel1.setToolTipText("This ise where messages handled by this service are delivered");
        mySessionEJBMethodTextField.setEditable(true);
        mySessionEJBMethodTextField.setText("");
        jPanel1.setLayout(gridBagLayout3);
        jPanel2.setLayout(borderLayout1);

        inPanel.add(OutPathjLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(3, 14, 0, 34), 33, 7));
        inPanel.add(myInPathTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(3, 15, 0, 1), 415, 0));
        inPanel.add(myInFolderScanIntervall, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 15, 6, 334), 82, 2));
        inPanel.add(inFolderScanIntervalljLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 14, 6, 0), 28, -1));
        inPanel.add(jLabel2, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 10, 0), 0, 0));
        this.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel1, BorderLayout.NORTH);
        this.add(jPanelGif, BorderLayout.WEST);
        jPanelGif.add(imagejLabel, null);
        injPanel.add(InpathjLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(1, 13, 0, 38), 33, 7));
        injPanel.add(mySessionEJBTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(1, 11, 0, 171), 246, 0));
        injPanel.add(InpathjLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 13, 9, 0), 33, 7));
        injPanel.add(mySessionEJBMethodTextField, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 11, 9, 171), 247, 0));
        injPanel.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 10, 0), 0, 0));
        jPanel1.add(inPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
            GridBagConstraints.BOTH, new Insets(0, 1, 0, 19), 0, 0));
        jPanel1.add(injPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
            GridBagConstraints.BOTH, new Insets(0, 1, 30, 19), 0, -1));

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
        this.mySessionEJBTextField.setText(outPathStr);
    }

    public String getFolderScanIntervall()
    {
        return folderScanIntervall;

    }

    public void setFolderScanIntervall(String folderScanIntervall)
    {
        this.folderScanIntervall = folderScanIntervall;
        this.myInFolderScanIntervall.setText(folderScanIntervall);
    }

    public String getSessionEJB()
    {
        return sessionEJB;
    }

    public void setSessionEJB(String sessionEJB)
    {
        this.sessionEJB = sessionEJB;
        mySessionEJBTextField.setText(sessionEJB);
    }

    public String getSessionEJBMethod()
    {
        return sessionEJBMethod;
    }

    public void setSessionEJBMethod(String sessionEJBMethod)
    {
        this.sessionEJBMethod = sessionEJBMethod;
        mySessionEJBMethodTextField.setText(sessionEJBMethod);
    }

}
