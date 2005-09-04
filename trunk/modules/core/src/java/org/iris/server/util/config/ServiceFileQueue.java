package org.iris.server.util.config;

import org.apache.commons.lang.builder.*;


/**
 * Holds specific settings for this service type.
 *
 * @author Georges Polyzois
 */
public class ServiceFileQueue
    extends Service
{
    private String infolderIntervall;
    private String infolderPath;
    private String nameService;
    private String backupfolder;
    private String serviceName;
    private String name;
    private String queueName;
    private String factoryname;

    public String getInfolderPath()
    {
        return infolderPath;
    }

    public String getInfolderIntervall()
    {
        return infolderIntervall;
    }

    public String getNameService()
    {
        return nameService;
    }

    public void setNameService(String nameService)
    {
        this.nameService = nameService;
    }

    public void setInfolderIntervall(String infolderIntervall)
    {
        this.infolderIntervall = infolderIntervall;
    }

    public void setInfolderPath(String infolderPath)
    {
        this.infolderPath = infolderPath;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getBackupfolder()
    {
        return backupfolder;
    }

    public void setBackupfolder(String backupfolder)
    {
        this.backupfolder = backupfolder;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getQueueName()
    {
        return queueName;
    }

    public void setQueueName(String queueName)
    {
        this.queueName = queueName;
    }

    public String getFactoryname()
    {
        return factoryname;
    }

    public void setFactoryname(String factoryname)
    {
        this.factoryname = factoryname;
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
