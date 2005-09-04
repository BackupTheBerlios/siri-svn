package org.iris.client.swing.panels.tree;

import java.util.*;

import org.iris.client.swing.util.events.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Tradevision AB</p>
 * @author not attributable
 * @version 1.0
 */

public class VerifyRemoteServices
    extends TimerTask
{
    public VerifyRemoteServices(long period)
    {
        Timer timer = new Timer();
        timer.schedule(this, 5000, period);
    }

    public void run()
    {
        ApplicationEventHandler.getInstance().fireDataChanged(new ApplicationStateEvent(this, "Verify remote services",
            ApplicationStateEvent.VERIFYREMOTESERVICES));
    }
}