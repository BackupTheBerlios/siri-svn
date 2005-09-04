package org.iris.server.util.config;

import org.apache.log4j.Logger;
import org.iris.server.settings.xml.SettingsException;
import org.iris.server.settings.xml.SettingsFascade;
import org.iris.xml.server.settings.ServiceType;

import java.util.Iterator;
import java.util.List;

public class ServerSystemConfigHandler
    extends SystemConfigHandler
{
    static Logger myLogger = Logger.getLogger(ServerSystemConfigHandler.class.getName());
    private static ServerSystemConfigHandler mySystemConfigHandler;

    public static String extractCronintervall( String cronWithExplanation )
    {
        return org.apache.commons.lang.StringUtils.substringAfter( cronWithExplanation, "@");
    }

    public static ServerSystemConfigHandler getInstance()
    {
        if (mySystemConfigHandler == null)
        {
            mySystemConfigHandler = new ServerSystemConfigHandler();
        }
        return mySystemConfigHandler;
    }

    public ServerSystemConfigHandler()
    {
        try
        {
            init();
        }
        catch (SettingsException ex)
        {
            myLogger.fatal("Could not get system settings  -exiting.", ex);
            ex.printStackTrace();
        }
    }

    
    private void init() throws SettingsException
    {
        myLogger.debug("Init configuration");
        List lsService = SettingsFascade.getInstance().getSystemconfigSettings().getService();
        services = new Service[lsService.size()];
        Iterator iter = lsService.iterator();
        int i = 0;
        while (iter.hasNext())
        {
            ServiceType aServiceType = (ServiceType) iter.next();
            services[i] = ServiceFactory.getService(aServiceType);
            servicesList.add(services[i]);
            servicesMap.put(  services[i].getId(), services[i] );
            i++;
        }
    }


}