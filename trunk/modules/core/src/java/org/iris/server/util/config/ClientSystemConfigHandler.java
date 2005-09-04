package org.iris.server.util.config;

import org.apache.log4j.Logger;
import org.iris.server.settings.xml.SettingsException;
import org.iris.server.settings.xml.SettingsFascade;
import org.iris.xml.server.settings.ServiceType;

import java.util.Iterator;
import java.util.List;

/**
 * Use this class from a client perspective to load system config settings.
 *
 * @author Georges Polyzois
 */
public class ClientSystemConfigHandler
    extends SystemConfigHandler
{
    Logger myLogger = Logger.getLogger(ClientSystemConfigHandler.class.getName());
    private static ClientSystemConfigHandler myClientSystemConfigHandler;

    public static ClientSystemConfigHandler getInstance()
    {
        if (myClientSystemConfigHandler == null)
        {
            myClientSystemConfigHandler = new ClientSystemConfigHandler();
        }
        return myClientSystemConfigHandler;
    }

    /**
     * Use xml string to load systemconfig settings and get services from
     * the config. Puts services in an array and a hashmap using if of service as
     * a key to the map.
     *
     * @param xmlData
     * @throws SettingsException
     */
    public void initUsingXml(String xmlData) throws SettingsException
    {      
        try
        {
            myLogger.debug("Init configuration settings from xml data");
            List lsService = SettingsFascade.getInstance().getSystemconfigSettings(xmlData).getService();
            services = new Service[lsService.size()];
            Iterator iter = lsService.iterator();
            int i = 0;
            while (iter.hasNext())
            {
                ServiceType aServiceType = (ServiceType) iter.next();
                services[i] = ServiceFactory.getService(aServiceType);
                servicesList.add(services[i]);
                servicesMap.put( services[i].getId() , services[i]  );
            }
            myLogger.debug("Finished init configuration settings from xml data");
        }
        catch (SettingsException ex)
        {
            throw ex;
        }
    }

}