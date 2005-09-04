package org.iris.server.util.config;

import org.apache.commons.lang.builder.*;

/**
 * Holds specific settings for this service type.
 *
 * @author Georges Polyzois
 */
public class ServiceFileCORBA
    extends Service
{
    private String nameService;
    private String serviceName;
    private String serviceMethod;
    private String serviceParamtype;

    public ServiceFileCORBA()
    {
    }

    public String getNameService()
    {
        return nameService;
    }

    public String getServiceMethod()
    {
        return serviceMethod;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public String getServiceParamtype()
    {
        return serviceParamtype;
    }

    public void setNameService(String nameService)
    {
        this.nameService = nameService;
    }

    public void setServiceMethod(String serviceMethod)
    {
        this.serviceMethod = serviceMethod;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public void setServiceParamtype(String serviceParamtype)
    {
        this.serviceParamtype = serviceParamtype;
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}