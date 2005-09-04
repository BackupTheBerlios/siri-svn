package org.iris.client.swing.util.events;

import java.util.*;

/**
 * All events for application
 *
 * @author not attributable
 * @version 1.0
 */


public class ApplicationStateEvent
    extends EventObject
{
    public final static String STR_EXIT_APPLICATION = "EXIT_APPLICATION";
    public final static String STR_MAIN_PANEL_CHANGED = "MAIN_PANEL_CHANGED";

    public final static int EXIT_APPLICATION = 0;
    public final static int SAVE_SETTINGS = 1;

    // used to change the toolbar information
    public final static int TOOLBAR_STATUS_CHANGED = 10;
    public final static int TREECLICKED = 11;
    public final static int SENDEMAIL_PNLBOOKING = 12;
    public final static int STOPREMOTESERVICE = 13;
    public final static int STARTREMOTESERVICE = 14;
    public final static int GETCONFIGXML = 15;
    public final static int PINGREMOTESERVICE = 17;
    public final static int VERIFYREMOTESERVICES = 18;
    public final static int VERIFYRMIANDIRISRUNNING = 19;
    public final static int POLLSERVERFORSERVICEDATA = 20;
    public final static int GETSTATTDATAFORSERVICE = 21;
    public final static int CLONINGSERVICEWIZARD = 22;
    public final static int CLONINGSERVICEONREMOTESERVER = 23;

    private int eventName;

    private String metaInfo;

    public ApplicationStateEvent(Object source, String metaInfo, int eventName)
    {
        super(source);
        this.metaInfo = metaInfo;
        this.eventName = eventName;
    }

    public void setText(String toolbarValue)
    {
        this.metaInfo = toolbarValue;
    }

    public String getText()
    {
        return metaInfo;
    }

    public int getEventName()
    {
        return eventName;
    }

    public void setEventName(int eventName)
    {
        this.eventName = eventName;
    }
}
