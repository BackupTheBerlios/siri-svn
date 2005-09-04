package org.iris.client.swing.panels.servicestatistics;

import java.util.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.events.*;

public class ServiceSummaryServerPoller
    extends TimerTask
{
    Logger myLogger = Logger.getLogger(ServiceSummaryServerPoller.class.getName());
    Timer timer = new Timer();

    public ServiceSummaryServerPoller(long period)
    {
        timer.schedule(this, 5000, period);
    }

    public void run()
    {
        myLogger.debug("polling");
        ApplicationEventHandler.getInstance().fireDataChanged(new ApplicationStateEvent(this, "Poll server for data",
            ApplicationStateEvent.POLLSERVERFORSERVICEDATA));
    }

}
