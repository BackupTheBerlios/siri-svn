package org.iris.server.errorhandler;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Class is place holder for errors and messages related to sending errors.
 * It will hold any error information we want to
 */
public class ErrorMessageVO
{
    private String errorMessageFolder;
    private String errorMessage;
    private String errorLevel;
    protected String idErrorMessage;

    public Throwable getThrowable()
    {
        return throwable;
    }

    public void setThrowable(Throwable throwable)
    {
        this.throwable = throwable;
    }

    protected Throwable throwable;

    private String getStackTrace(Throwable thr)
    {
        if (thr == null)
        {
            return "";
        }
        StringWriter stackTraceWriter = new StringWriter();
        thr.printStackTrace(new PrintWriter(stackTraceWriter));
        return stackTraceWriter.toString();
    }

    public ErrorMessageVO(String errorFolderPath, String message, Throwable thr, String errorLevel, String id)
    {
        idErrorMessage = id;
        errorMessageFolder = errorFolderPath;
        StringBuffer buf = new StringBuffer();
        buf.append("Id :      " + id + "\n");
        buf.append("Level :   " + errorLevel + "\n");
        buf.append("Message : " + message + "\n");
        buf.append("Trace :   " + getStackTrace(thr) + "\n");
        errorMessage = buf.toString();
    }

    public String getErrorFolder()
    {
        return errorMessageFolder;
    }

    public void setErrorFolder(String errorFolder)
    {
        this.errorMessageFolder = errorFolder;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getErrorLevel()
    {
        return errorLevel;
    }

    public void setErrorLevel(String errorLevel)
    {
        this.errorLevel = errorLevel;
    }

    public String getIdErrorMessage()
    {
        return idErrorMessage;
    }

    public void setIdErrorMessage(String idErrorMessage)
    {
        this.idErrorMessage = idErrorMessage;
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString ( this, ToStringStyle.MULTI_LINE_STYLE );  
    }

}