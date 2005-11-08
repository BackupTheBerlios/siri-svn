package org.siri.common.logging;

import org.apache.log4j.xml.DOMConfigurator;

import javax.xml.parsers.FactoryConfigurationError;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Initializer for log4j.
 *
 * @author Georges Polyzois
 */
public class Log4JInit
{
    static
    {
        try
        {
            String log4jconf = System.getProperty("log4j.configuration");
            if(log4jconf != null)
            {
                System.out.println("log4j.configuration was set to " + log4jconf);
                DOMConfigurator.configure(log4jconf);
            }
            else
            {
                DOMConfigurator.configure("log4j.xml");
            }

            //org.apache.log4j.MDC.put("host", getLocalHostName());
        }
        catch (FactoryConfigurationError ex)
        {
            System.out.println("Log4j can not start properly!!! Have you added -Dlog4j.configuration=<path to your log4j.xml file>?");
            System.out.println("Log4j can not start properly!!! " + ex.getMessage());

            //ex.printStackTrace();
        }
        catch (Throwable thr)
        {
            System.out.println("Log4j can not start properly!!! Have you added -Dlog4j.configuration=<path to your log4j.xml file>?");
            System.out.println("Log4j can not start properly!!! " + thr.getMessage());

            //ex.printStackTrace();
        }
    }

    private static String getLocalHostName()
    {
        String hostName = null;
        try
        {
            hostName = InetAddress.getLocalHost().getHostName();
            hostName = (hostName.indexOf(".") == -1) ? hostName : hostName.substring(0, hostName.indexOf("."));
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return hostName;
    }

}
