package org.iris.server.util.log;

import org.apache.log4j.spi.*;

public class ServiceFilter
    extends org.apache.log4j.spi.Filter
{
    private String myServiceName;

    public ServiceFilter(String serviceName)
    {
        myServiceName = serviceName;
    }

    public int decide(LoggingEvent loggingEvent)
    {
        if (loggingEvent.getNDC() != null && loggingEvent.getNDC().equalsIgnoreCase(myServiceName))
        {
            return Filter.ACCEPT;
        }
        else
        {
            return Filter.DENY;
        }
    }
}