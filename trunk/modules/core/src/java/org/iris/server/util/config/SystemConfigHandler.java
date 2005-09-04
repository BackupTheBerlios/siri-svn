package org.iris.server.util.config;

import org.apache.log4j.Logger;
import org.iris.server.settings.xml.SettingsException;
import org.iris.server.settings.xml.SettingsFascade;
import org.iris.xml.server.settings.GlobalSettingsType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SystemConfigHandler
{
    Logger myLogger = Logger.getLogger(SystemConfigHandler.class.getName());
    protected static HashMap servicesMap = new HashMap();
    protected static Service[] services;
    protected static ArrayList servicesList = new ArrayList();

    public SystemConfigHandler()
    {
    }

    public GlobalSettingsType getGlobalSettings()
    {
        GlobalSettingsType global = null;
        try
        {
            global = SettingsFascade.getInstance().getSystemconfigSettings().getGlobalSettings();
        }
        catch (SettingsException ex)
        {
            myLogger.error(ex);
        }
        return global;
    }

    public String[] getArrOfServicesSetToResend() throws SettingsException
    {
        Service[] serviceobj = new Service[servicesList.size()];
        ArrayList servieErrorresendNames = new ArrayList();
        for (int i = 0; i < serviceobj.length; i++)
        {
            if (serviceobj[i].isErrorhandlerOn())
            {
                servieErrorresendNames.add(serviceobj[i].getId());
            }
        }

        String[] result = new String[servieErrorresendNames.size()];
        servieErrorresendNames.toArray(result);
        return result;
    }

    /**
     * Returns a map of services for easy lookup using service id.
     * @return
     */
    public HashMap getServicesMap()
    {
        return servicesMap;
    }

    public Service[] getArrOfServices() //throws SettingsException
    {
        /*if ( (services == null) || (servicesList == null))
        {
            throw new SettingsException("You must initialize before calling this method");
        } */
        //myLogger.debug( "Number of serveice sdfasdfasdfs df sdfsd" + services.length );
        return services;
    }

    public ArrayList getServices(boolean sortAscending) throws SettingsException
    {
        if ( (services == null) || (servicesList == null))
        {
            throw new SettingsException("You must initialize before calling this method");
        }
        Service.sortAscending = sortAscending;
        Collections.sort(servicesList);
        return servicesList;
    }

    public String[] getServiceNames()
    {
        Service.sortAscending = true;
        Collections.sort(servicesList);

        Service[] serviceobj = new Service[servicesList.size()];
        servicesList.toArray(serviceobj);
        String[] serviceNames = new String[serviceobj.length];
        for (int i = 0; i < serviceNames.length; i++)
        {
            serviceNames[i] = serviceobj[i].getId();
        }

        return serviceNames;
    }

    public ArrayList getServices()
    {
        return servicesList;
    }
}