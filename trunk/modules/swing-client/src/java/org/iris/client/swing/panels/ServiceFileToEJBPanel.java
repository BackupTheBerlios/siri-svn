package org.iris.client.swing.panels;

import java.awt.*;
import javax.swing.*;

public class ServiceFileToEJBPanel
    extends ServicePanel
{
    private static ServiceFileToEJBPanel myInstance;
    private InOutEJBPanel myInOutEJBPanel = new InOutEJBPanel(); //.getInstance();
    private JTabbedPane serviceTabbedPane1 = new JTabbedPane();
    private BorderLayout borderLayout1 = new BorderLayout();

    public ServiceFileToEJBPanel()
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

    public static ServiceFileToEJBPanel getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new ServiceFileToEJBPanel();
        }
        return myInstance;

    }

    void jbInit() throws Exception
    {
        this.setLayout(borderLayout1);
        this.setMinimumSize(new Dimension(400, 220));
        this.setPreferredSize(new Dimension(400, 220));
        this.add(serviceTabbedPane1, BorderLayout.CENTER);

        serviceTabbedPane1.addTab(myInOutEJBPanel.getTabTitle(), myInOutEJBPanel.getTabIcon(), myInOutEJBPanel);
        serviceTabbedPane1.addTab(myLoggingPanel.getTabTitle(), myLoggingPanel.getTabIcon(), myLoggingPanel);

        serviceTabbedPane1.addTab(myArchivingPanel.getTabTitle(), myArchivingPanel.getTabIcon(), myArchivingPanel);
        serviceTabbedPane1.addTab(myBackupPanel.getTabTitle(), myBackupPanel.getTabIcon(), myBackupPanel);

        serviceTabbedPane1.addTab(myErrorPanel.getTabTitle(), myErrorPanel.getTabIcon(), myErrorPanel);
        serviceTabbedPane1.addTab(myInactivityPanel.getTabTitle(), myInactivityPanel.getTabIcon(), myInactivityPanel);
        serviceTabbedPane1.addTab(myTransformPanel.getTabTitle(), myTransformPanel.getTabIcon(), myTransformPanel);

    }

    public void setInPathStr(String inPathStr)
    {
        myInOutEJBPanel.setInPathStr(inPathStr);
    }

    public void setOutPathStr(String outPathStr)
    {
        myInOutEJBPanel.setInPathStr(outPathStr);
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
