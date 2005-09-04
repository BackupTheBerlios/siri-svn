package org.siri.swing.util.statuspanel;

import java.util.*;

public class StatusPanelInitializeTask
    extends TimerTask
{
    StatusJPanel myStatusPanel;

    public StatusPanelInitializeTask(StatusJPanel status)
    {
        myStatusPanel = status;
    }

    public void run()
    {
        myStatusPanel.setStatusText("");
    }
}
