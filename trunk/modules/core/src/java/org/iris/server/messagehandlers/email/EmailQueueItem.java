package org.iris.server.messagehandlers.email;

import java.io.Serializable;

public class EmailQueueItem implements Serializable
{
    protected String message;
    // where to store mailmessage if failure to send
    protected String errorFolder;
    protected String myMessageId;
    // the unique service id - used for logging purposes
    protected String serviceId;

    protected String errorInfoFolder; 

    public String getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }
    public String getErrorInfoFolder()
    {
        return errorInfoFolder;
    }

    public void setErrorInfoFolder(String errorInfoFolder)
    {
        this.errorInfoFolder = errorInfoFolder;
    }



    public EmailQueueItem(String aMessage, String aErrorFolder, String aMessageId, String errorInfoFolder)
    {
        this.message = aMessage;
        this.errorFolder = aErrorFolder;
        this.myMessageId = aMessageId;
        this.errorInfoFolder = errorInfoFolder;
    }


    public EmailQueueItem(String serviceId)
    {
        this.serviceId = serviceId;

    }

    public EmailQueueItem(String aMessage, String aMessageId)
    {
        this.message = aMessage;
        this.myMessageId = aMessageId;
    }

    public String getErrorFolder()
    {
        return errorFolder;
    }

    public String getMessage()
    {
        return message;
    }

    public void setErrorFolder(String errorFolder)
    {
        this.errorFolder = errorFolder;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessageId()
    {
        return myMessageId;
    }

    public void setMessageId(String myMessageId)
    {
        this.myMessageId = myMessageId;
    }

    public String toString()
    {
        return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString( this ) ;
    }


}