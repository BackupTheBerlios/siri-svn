package org.iris.server.statistics;

import junit.framework.*;

import org.iris.server.*;

public class TestStatisticsHandler extends TestCase
{
    StatisticsVO vo1;
    StatisticsVO vo2;
    StatisticsVO vo3;

    protected void setUp() throws Exception
    {
        super.setUp();

        String key = "mykey";
        vo1 = new StatisticsVO();
        vo1.setServiceName( key );
        vo1.setTimestamp( 1000000000001L );
        vo1.setValue( 2.0 );

        String key2 = "mykey2";
        vo2 = new StatisticsVO();
        vo2.setServiceName( key2 );
        vo2.setTimestamp( 1000000000002L );
        vo2.setValue( 2.0 );

        String key3 = "mykey2";
        vo3 = new StatisticsVO();
        vo3.setServiceName( key3 );
        vo3.setTimestamp( 1000000000002L );
        vo3.setValue( 3.0 );


    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    public void testPop()
    {
        StatisticsHandler handler = StatisticsHandler.getInstance();

        StatisticsVO expectedReturn = vo1;
        handler.push( vo1 );
        StatisticsVO actualReturn = handler.pop(vo1.getServiceName());
        assertEquals("return value", expectedReturn, actualReturn);

        handler.clearAll();
        expectedReturn = vo2;
        handler.push( vo1 );
        handler.push( vo2 );

        handler.pop(vo1.getServiceName());
        actualReturn = handler.pop(vo2.getServiceName());
        assertEquals("return value", expectedReturn, actualReturn);

        handler.clearAll();

    }


}
