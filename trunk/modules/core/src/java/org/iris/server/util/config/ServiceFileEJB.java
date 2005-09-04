package org.iris.server.util.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Holds specific settings for this service type.
 *
 * @author Georges Polyzois
 */ 
public class ServiceFileEJB
    extends Service
{
    private String nameService;
    private String serviceName;
    private String serviceMethod;
    private String serviceParamtype;
    private String useexplicitclass;
    private long reconnectToObjectInMilliSeconds;
    private String reconnectToObjectPolicy;
    public final static String RECONNECT_ON_NEW_MESSAGE = "reconnectOnNewMessage";
    public final static String RECONNECT_FOREVER = "reconnectForever";
    public final static String DO_NOT_RECONNECT = "doNotReconnect";

    public ServiceFileEJB()
    {}

    public String getNameService()
    {
        return nameService;
    }

    public void setNameService(String nameService)
    {
        this.nameService = nameService;
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getServiceMethod()
    {
        return serviceMethod;
    }

    public void setServiceMethod(String serviceMethod)
    {
        this.serviceMethod = serviceMethod;
    }

    public String getServiceParamtype()
    {
        return serviceParamtype;
    }

    public void setServiceParamtype(String serviceParamtype)
    {
        this.serviceParamtype = serviceParamtype;
    }

    public void setUseexplicitclass(String useexplicitclass)
    {
        this.useexplicitclass = useexplicitclass;
    }

    public String getUseexplicitclass()
    {
        return useexplicitclass;
    }

    public long getReconnectToObjectInMilliSeconds()
    {
        return reconnectToObjectInMilliSeconds;
    }

    public void setReconnectToObjectInMilliSeconds(long reconnectToObjectInMilliSeconds)
    {
        this.reconnectToObjectInMilliSeconds = reconnectToObjectInMilliSeconds;
    }


    public String getReconnectToObjectPolicy()
    {
        return reconnectToObjectPolicy;
    }

    public void setReconnectToObjectPolicy(String reconnectToObjectPolicy)
    {
        this.reconnectToObjectPolicy = reconnectToObjectPolicy;
    }


}
