package org.iris.server.util;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class TestDateUtil
    extends TestCase
{


    public TestDateUtil(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();

    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    public void testisDateMoreThanXNumberOfMinutes()
    {
        // now should not be older than a date 10 minutes back in time
        assertFalse( DateUtil.isDateOlderThanXnumberOfMinutes( 10, new Date( System.currentTimeMillis() ) ));

        // a date 100 minutes back in time should be older than 99 miutes
        Date now = new Date(System.currentTimeMillis());
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime( now );
        nowCal.add( Calendar.MINUTE, -(int)100 );
        assertTrue( DateUtil.isDateOlderThanXnumberOfMinutes( 99, nowCal.getTime()) );

        // a date 24 hours in the future should be not be older than 99 minutes
        now = new Date(System.currentTimeMillis());
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.setTime( now );
        tomorrow.add( Calendar.HOUR, 24 );
        assertFalse( DateUtil.isDateOlderThanXnumberOfMinutes( 99, tomorrow.getTime()) );

    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(TestDateUtil.class);
    }

}
