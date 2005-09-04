package org.iris.client.swing.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import org.iris.client.swing.util.*;

public class PollEJBPanel
    extends JPanel
{
    JTextField sessionEJBTextField = new JTextField();
    JLabel InpathjLabel = new JLabel();
    private static PollEJBPanel myInOutPanel;
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
    JPanel injPanel = new JPanel();
    private String sessionEJB;
    Border border2;
    TitledBorder titledBorder3;
    JLabel InpathjLabel1 = new JLabel();
    JTextField sessionEJBMethodTextField = new JTextField();
    private String sessionEJBMethod;

    public PollEJBPanel()
    {
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

    public static PollEJBPanel getInstance()
    {
        if (myInOutPanel == null)
        {
            new PollEJBPanel();
        }
        return myInOutPanel;

    }

    void jbInit() throws Exception
    {
        titledBorder1 = new TitledBorder("");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                        "Poll");
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder3 = new TitledBorder(border2, "Out");

        sessionEJBTextField.setEditable(false);
        sessionEJBTextField.setText("");
        sessionEJBTextField.setBounds(new Rectangle(163, 27, 250, 20));
        InpathjLabel.setToolTipText("This ise where messages handled by this service are delivered");
        InpathjLabel.setText("Session EJB");
        InpathjLabel.setBounds(new Rectangle(17, 28, 93, 22));
        inFolderScanIntervalljLabel.setText("Poll intervall [ms]");
        inFolderScanIntervalljLabel.setBounds(new Rectangle(18, 25, 127, 14));
        inFolderScanIntervall.setEditable(false);
        inFolderScanIntervall.setText("");
        inFolderScanIntervall.setBounds(new Rectangle(163, 21, 86, 22));
        inPanel.setLayout(null);

        this.setLayout(null);
        inPanel.setBorder(titledBorder2);
        inPanel.setBounds(new Rectangle(76, 3, 588, 57));
        imagejLabel.setText("");
        imagejLabel.setBounds(new Rectangle(0, 1, 74, 164));
        imagejLabel.setIcon(ResourcesFactory.getImageIcon("panelejbservice.gif"));
        injPanel.setBorder(titledBorder3);
        injPanel.setDebugGraphicsOptions(0);
        injPanel.setBounds(new Rectangle(77, 63, 588, 90));
        injPanel.setLayout(null);
        InpathjLabel1.setBounds(new Rectangle(17, 55, 134, 22));
        InpathjLabel1.setText("Session EJB method");
        InpathjLabel1.setToolTipText("This ise where messages handled by this service are delivered");
        sessionEJBMethodTextField.setBounds(new Rectangle(163, 54, 250, 20));
        sessionEJBMethodTextField.setText("");
        sessionEJBMethodTextField.setEditable(false);
        inPanel.add(inFolderScanIntervalljLabel, null);
        inPanel.add(inFolderScanIntervall, null);
        this.add(injPanel, null);
        injPanel.add(InpathjLabel1, null);
        injPanel.add(sessionEJBMethodTextField, null);
        injPanel.add(sessionEJBTextField, null);
        injPanel.add(InpathjLabel, null);
        this.add(imagejLabel, null);
        this.add(inPanel, null);

    }

    public String getInPathStr()
    {
        return inPathStr;
    }

    public String getOutPathStr()
    {
        return outPathStr;
    }

    public void setOutPathStr(String outPathStr)
    {
        this.outPathStr = outPathStr;
        this.sessionEJBTextField.setText(outPathStr);
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

    public String getSessionEJB()
    {
        return sessionEJB;
    }

    public void setSessionEJB(String sessionEJB)
    {
        this.sessionEJB = sessionEJB;
        sessionEJBTextField.setText(sessionEJB);
    }

    public String getSessionEJBMethod()
    {
        return sessionEJBMethod;
    }

    public void setSessionEJBMethod(String sessionEJBMethod)
    {
        this.sessionEJBMethod = sessionEJBMethod;
        sessionEJBMethodTextField.setText(sessionEJBMethod);
    }

}
