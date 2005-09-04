package org.iris.client.swing.panels;

import javax.swing.*;

import org.iris.client.swing.panels.logging.*;
import org.iris.client.swing.panels.transform.*;

public class ServicePanel
    extends JPanel
{
    protected ArchivePanel myArchivingPanel = new ArchivePanel();
    protected ErrorPanel myErrorPanel = new ErrorPanel();
    protected ServiceLoggingPanel myLoggingPanel = new ServiceLoggingPanel();
    protected TransformPanel myTransformPanel = new TransformPanel();
    protected InactivityPanel myInactivityPanel = new InactivityPanel();
    protected BackupPanel myBackupPanel = new BackupPanel();

    public ServicePanel()
    {
    }

    public ServiceLoggingPanel getMyLoggingPanel()
    {
        return myLoggingPanel;
    }

    public void setMyLoggingPanel(ServiceLoggingPanel myLoggingPanel)
    {
        this.myLoggingPanel = myLoggingPanel;
    }

    public TransformPanel getMyTransformPanel()
    {
        return myTransformPanel;
    }

    public void setMyTransformPanel(TransformPanel myTransformPanel)
    {
        this.myTransformPanel = myTransformPanel;
    }

    public InactivityPanel getMyInactivityPanel()
    {
        return myInactivityPanel;
    }

    public void setMyInactivityPanel(InactivityPanel myInactivityPanel)
    {
        this.myInactivityPanel = myInactivityPanel;
    }

    public ArchivePanel getMyArchivingPanel()
    {
        return myArchivingPanel;
    }

    public ErrorPanel getMyErrorPanel()
    {
        return myErrorPanel;
    }

    public void setMyErrorPanel(ErrorPanel myErrorPanel)
    {
        this.myErrorPanel = myErrorPanel;
    }

    public void setMyArchivingPanel(ArchivePanel myArchivingPanel)
    {
        this.myArchivingPanel = myArchivingPanel;
    }

    public BackupPanel getMyBackupPanel()
    {
        return myBackupPanel;
    }

    public void setMyBackupPanel(BackupPanel myBackupPanel)
    {
        this.myBackupPanel = myBackupPanel;
    }

}
