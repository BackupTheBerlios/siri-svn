package org.iris.client.swing.panels;

import java.awt.*;
import javax.swing.*;

public class ServiceFileToFilePanel
    extends ServicePanel
{
    private InOutPanel myInOutPanel = new InOutPanel();
    private JTabbedPane serviceTabbedPane1 = new JTabbedPane();
    private BorderLayout borderLayout1 = new BorderLayout();

    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();

    public ServiceFileToFilePanel()
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

    void jbInit() throws Exception
    {
        this.setLayout(borderLayout1);
        this.add(serviceTabbedPane1, BorderLayout.CENTER);

        serviceTabbedPane1.addTab(myInOutPanel.getTabTitle(), myInOutPanel.getTabIcon(), myInOutPanel);
        serviceTabbedPane1.addTab(myLoggingPanel.getTabTitle(), myLoggingPanel.getTabIcon(), myLoggingPanel);

        serviceTabbedPane1.addTab(myArchivingPanel.getTabTitle(), myArchivingPanel.getTabIcon(), myArchivingPanel);
        serviceTabbedPane1.addTab(myBackupPanel.getTabTitle(), myBackupPanel.getTabIcon(), myBackupPanel);

        serviceTabbedPane1.addTab(myErrorPanel.getTabTitle(), myErrorPanel.getTabIcon(), myErrorPanel);
        serviceTabbedPane1.addTab(myInactivityPanel.getTabTitle(), myInactivityPanel.getTabIcon(), myInactivityPanel);
        serviceTabbedPane1.addTab(myTransformPanel.getTabTitle(), myTransformPanel.getTabIcon(), myTransformPanel);

    }

    public static void main(String[] args)
    {
        JFrame fr = new JFrame();
        fr.getContentPane().add(new ServiceFileToFilePanel());
        fr.pack();
        fr.setVisible(true);
    }

    public void setInPathStr(String inPathStr)
    {
        myInOutPanel.setInPathStr(inPathStr);
    }

    public void setOutPathStr(String outPathStr)
    {
        myInOutPanel.setOutPathStr(outPathStr);
    }

    public void setFolderScanIntervall(String folderScanIntervall)
    {
        myInOutPanel.setFolderScanIntervall(folderScanIntervall);
    }

}
