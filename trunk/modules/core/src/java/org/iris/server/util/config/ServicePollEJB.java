package org.iris.server.util.config;

import org.apache.commons.lang.builder.*;


/**
 * Holds specific settings for this service type.
 *
 * @author Georges Polyzois
 */ 
public class ServicePollEJB
    extends Service
{
    protected String nameService;
    protected String serviceName;
    protected String serviceMethod;
    protected String serviceParamtype;
    protected String useexplicitclass;
    private String pollintervall;
    private String emailtosendonfailure;
    private String emailservice;
    private String message;

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

    public String getPollintervall()
    {
        return pollintervall;
    }

    public void setPollintervall(String pollintervall)
    {
        this.pollintervall = pollintervall;
    }

    public String getEmailtosendonfailure()
    {
        return emailtosendonfailure;
    }

    public void setEmailtosendonfailure(String emailtosendonfailure)
    {
        this.emailtosendonfailure = emailtosendonfailure;
    }

    public String getEmailservice()
    {
        return emailservice;
    }

    public void setEmailservice(String emailservice)
    {
        this.emailservice = emailservice;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
