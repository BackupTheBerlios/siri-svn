package org.iris.client.swing.panels;

import java.awt.*;
import javax.swing.*;

public class ServicePollEJBPanel
    extends ServicePanel
{
    private static ServicePollEJBPanel myInstance;
    private PollEJBPanel myInOutEJBPanel = new PollEJBPanel(); //.getInstance();
    private JTabbedPane serviceTabbedPane1 = new JTabbedPane();
    private BorderLayout borderLayout1 = new BorderLayout();
//    private LoggingPanel myLoggingPanel = new LoggingPanel();//.getInstance();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();

    public ServicePollEJBPanel()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static ServicePollEJBPanel getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new ServicePollEJBPanel();
        }
        return myInstance;

    }

    void jbInit() throws Exception
    {
        this.setLayout(borderLayout1);
        this.add(serviceTabbedPane1, BorderLayout.CENTER);
        myInOutEJBPanel.setLayout(borderLayout4);
        myLoggingPanel.setLayout(borderLayout2);
        myArchivingPanel.setLayout(borderLayout3);
        serviceTabbedPane1.add(myInOutEJBPanel, "InOut");
        serviceTabbedPane1.add(myLoggingPanel, "Logging");
    }

    public static void main(String[] args)
    {
        JFrame fr = new JFrame();
        fr.getContentPane().add(new ServicePollEJBPanel());
        fr.pack();
        fr.setVisible(true);
    }

    public void setFolderScanIntervall(String folderScanIntervall)
    {
        myInOutEJBPanel.setFolderScanIntervall(folderScanIntervall);
    }

    public void setSessionEJB(String sessionEJB)
    {
        myInOutEJBPanel.setSessionEJB(sessionEJB);
    }

    public void setSessionEJBMethod(String sessionEJBMethod)
    {
        myInOutEJBPanel.setSessionEJBMethod(sessionEJBMethod);
    }

}
