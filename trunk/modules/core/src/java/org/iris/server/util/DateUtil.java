package org.iris.server.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: gepo
 * Date: 2004-mar-24
 * Time: 12:12:25
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil
{
    static SimpleDateFormat myFormat = new SimpleDateFormat( "yyyyMMdd HHmmss" );

    public static void main(String[] args)
    {
        java.util.Date now = new java.util.Date(System.currentTimeMillis());
        System.out.println( myFormat.format( now ));

        Calendar nowMinusTen = Calendar.getInstance();
        System.out.println( myFormat.format(  nowMinusTen.getTime() ) );


        System.out.println( isDateOlderThanXnumberOfMinutes( 5, nowMinusTen.getTime() ) );

    }

    /**
     * Utility method. Check if an inparamter date is older than x number of minutes
     *
     * @param numberOfMilliseconds
     * @param compareTo
     * @return
     */
    public static boolean isDateOlderThanXnumberOfMillisec(long numberOfMilliseconds, Date compareTo)
    {
        Date now = new Date(System.currentTimeMillis());
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime( now );
        nowCal.add( Calendar.MILLISECOND, -(int)numberOfMilliseconds );
        java.util.Date nowMinus = nowCal.getTime();
        return compareTo.before( nowMinus );
    }

    public static boolean isDateOlderThanXnumberOfMinutes(long numberOfMinutes, Date compareTo)
    {
        Date now = new Date(System.currentTimeMillis());
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime( now );
        nowCal.add( Calendar.MINUTE, -(int)numberOfMinutes );
        java.util.Date nowMinus = nowCal.getTime();
        return compareTo.before( nowMinus );
    }



}




