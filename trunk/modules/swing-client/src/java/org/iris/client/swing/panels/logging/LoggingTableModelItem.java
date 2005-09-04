package org.iris.client.swing.panels.logging;

import java.io.*;

import org.apache.log4j.*;
import org.apache.log4j.spi.*;

public class LoggingTableModelItem
    implements Serializable //, Comparable
{
    private final long mTimeStamp;
    private final Priority mPriority;
    private final String mCategoryName;
    private final String mNDC;
    private final String mThreadName;
    private final String mMessage;
    private final String[] mThrowableStrRep;
    private final String mLocationDetails;

    LoggingTableModelItem(long aTimeStamp, Priority aPriority, String aCategoryName, String aNDC, String aThreadName,
        String aMessage, String[] aThrowableStrRep, String aLocationDetails)
    {
        mTimeStamp = aTimeStamp;
        mPriority = aPriority;
        mCategoryName = aCategoryName;
        mNDC = aNDC;
        mThreadName = aThreadName;
        mMessage = aMessage;
        mThrowableStrRep = aThrowableStrRep;
        mLocationDetails = aLocationDetails;
    }

    /**
     * Creates a new <code>EventDetails</code> instance.
     *
     * @param aEvent a <code>LoggingEvent</code> value
     */ LoggingTableModelItem(LoggingEvent aEvent)
    {

        this(aEvent.timeStamp, aEvent.getLevel(), aEvent.getLoggerName(), aEvent.getNDC(), aEvent.getThreadName(),
            aEvent.getRenderedMessage(), aEvent.getThrowableStrRep(),
            (aEvent.getLocationInformation() == null) ? null : aEvent.getLocationInformation().fullInfo);
    }

    /** @see #mTimeStamp **/ long getTimeStamp()
    {
        return mTimeStamp;
    }

    /** @see #mPriority **/
    Priority getPriority()
    {
        return mPriority;
    }

    /** @see #mCategoryName **/ String getCategoryName()
    {
        return mCategoryName;
    }

    /** @see #mNDC **/ String getNDC()
    {
        return mNDC;
    }

    /** @see #mThreadName **/ String getThreadName()
    {
        return mThreadName;
    }

    /** @see #mMessage **/ String getMessage()
    {
        return mMessage;
    }

    /** @see #mLocationDetails **/ String getLocationDetails()
    {
        return mLocationDetails;
    }

    /** @see #mThrowableStrRep **/ String[] getThrowableStrRep()
    {
        return mThrowableStrRep;
    }
}
/*
    private String date;
    private String level;
    private String message;
    public transient static boolean sortAscending = true;
    public transient static int SORTONDATE = 0;
    public transient static int SORTONLEVEL = 1;
    public transient static int SORTONMESSAGE = 2;
    public transient static int columnToSort;
    //sortOnProperty;
      //        ProjectBean.setSortAscending( sortAs
    public LoggingTableModelItem()
    {
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public void setLevel(String level)
    {
        this.level = level;
    }
    public String getDate()
    {
        return date;
    }
    public String getLevel()
    {
        return level;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public String toString()
    {
        return ToStringBuilder.reflectionToString(  this );
    }
    public int compareTo(Object o1)
    {
        int signum = 1;
        if (sortAscending)
        {
            signum = -1;
        }
        System.out.println("sort on " + columnToSort);
        if (SORTONMESSAGE == columnToSort)
        {
            return signum * (compare(getMessage(), ( (LoggingTableModelItem) o1).getMessage()));
        }
        else if (SORTONLEVEL == columnToSort)
        {
            return signum * (compare(getLevel(), ( (LoggingTableModelItem) o1).getLevel()));
        }
        else if (SORTONDATE == columnToSort)
        {
            return signum * (compare(getDate(), ( (LoggingTableModelItem) o1).getDate()));
        }
        return -1;
    }
    public int compare(String str1, String str2)
    {
        return str1.compareTo(str2);
    }
  public int compare(long str1, long str2)
    {
        if (str1 < str2)
        {
            return -1;
        }
        else if (str1 > str2)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    public int compare(double str1, double str2)
    {
        if (str1 < str2)
        {
            return -1;
        }
        else if (str1 > str2)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
 }*/