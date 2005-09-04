package org.iris.client.swing.panels.servicediagram;

import java.util.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.events.*;

public class ServiceStatusSummaryServerPoller
    extends TimerTask
{
    Logger myLogger = Logger.getLogger(ServiceStatusSummaryServerPoller.class.getName());
    Timer timer = new Timer();

    public ServiceStatusSummaryServerPoller(long period)
    {
        timer.schedule(this, 10000, period);
    }

    public void run()
    {
        myLogger.debug("polling");
        ApplicationEventHandler.getInstance().fireDataChanged(new ApplicationStateEvent(this, "Poll server for data",
            ApplicationStateEvent.GETSTATTDATAFORSERVICE));
    }

}
