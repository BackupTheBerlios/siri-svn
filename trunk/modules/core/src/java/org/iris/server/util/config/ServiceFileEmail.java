package org.iris.server.util.config;

import org.apache.commons.lang.builder.*;

/**
 * Holds specific settings for this service type.
 *
 * @author Georges Polyzois
 */
public class ServiceFileEmail
    extends Service
{
    protected String emailServer;
    private int emailbatchsize;
    private long emailsendintervall;

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public int getEmailbatchsize()
    {
        return emailbatchsize;
    }

    public String getEmailServer()
    {
        return emailServer;
    }

    public void setEmailbatchsize(int emailbatchsize)
    {
        this.emailbatchsize = emailbatchsize;
    }

    public void setEmailServer(String emailServer)
    {
        this.emailServer = emailServer;
    }

    public long getEmailsendintervall()
    {
        return emailsendintervall;
    }

    public void setEmailsendintervall(long emailsendintervall)
    {
        this.emailsendintervall = emailsendintervall;
    }

}
