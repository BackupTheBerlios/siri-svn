package org.iris.server.errorhandler;

import java.io.*;

/**
 * @author Georges.Polyzois
 *
 */
public class ErrorMessages
{
    public final static String FILECOPYERROR = "Could not copy file to location";
    public final static String CONNECTTOEJBERROR = "Could not connect to ejb";
    public final static String TRANSFORMATIONERROR = "Could not transform file";

    public static String getStackTrace(Throwable thr)
    {
        StringWriter stackTraceWriter = new StringWriter();
        thr.printStackTrace(new PrintWriter(stackTraceWriter));
        return stackTraceWriter.toString();
    }

}