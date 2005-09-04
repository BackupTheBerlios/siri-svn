package org.iris.server.statistics;

import org.apache.commons.collections.*;
import org.apache.log4j.*;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

/**
 * A hashmap with all service ids as key to lookup service statistics. The hashmap contains a circular queue
 * for every entry with a maximum number of items set to :    maxQueueItems
 *
 * @author Georges Polyzois
 */
public class StatisticsHandler
{
    static Logger myLogger = Logger.getLogger(StatisticsHandler.class.getName());
    HashMap myMap = new HashMap();
    private static StatisticsHandler myStatisticsHandler;
    int maxQueueItems = 90;

    public static StatisticsHandler getInstance()
    {
        if ( myStatisticsHandler == null )
        {
            myStatisticsHandler = new StatisticsHandler();
        }
        return myStatisticsHandler;
    }


    private StatisticsHandler()
    {
    }

    /**
     * Clear all hashmap with all services and relating queues
     */
    public void clearAll()
    {
        myMap.clear();
    }

    /**
     * Gets the current size for the service using the serviceid as key to look it up.
     *
     * @param serviceId key to the hashmap with services
     * @return size of circular queue for the given serviceid
     */
    public int size( String serviceId)
    {
        return ((Buffer)myMap.get( serviceId )).size();
    }

    /**
     * Push an item on the circular queue for the serviceid if the service id exists in the hashmap with service,
     * else create a new hashmap entry using the service id and store the data.
     *
     * @param item contains the service id and data to store
     */
    public void push(StatisticsVO item)
    {
        if ( myMap.containsKey( item.getServiceName() ) )
        {
            Buffer aBuffer = (Buffer)myMap.get( item.getServiceName() );
            aBuffer.add( item );
        }
        else
        {
           Buffer aBuffer = BufferUtils.synchronizedBuffer(new  CircularFifoBuffer(maxQueueItems));
           aBuffer.add( item );
           myMap.put( item.getServiceName(),aBuffer );
       }
       myLogger.debug( item.getServiceName() + " size " + ((Buffer)myMap.get( item.getServiceName() )).size() );
    }


    /**
     * Get the last added item to the circular queue using service id as lookup key to the hashmap.
     * @param serviceId key to the hashmap with services
     * @return
     */
    public StatisticsVO pop( String serviceId)
    {
        Object obj = null;
        StatisticsVO result = null;
        try
        {
            Buffer aBuffer = (Buffer)myMap.get( serviceId );
            obj = aBuffer.get();
        }
        catch (Exception ex)
        {
            myLogger.debug("Queue empty");
        }
        if (obj != null)
        {
            result = (StatisticsVO) obj;
        }
        return result;
    }

    /**
     * Use to get all statistics for a given service id gathered until this point in time
     *
     * @param serviceId key to the hashmap with services
     * @return collection with service items
     * @throws Exception
     */
    public StatisticsVO[] getStatisticsForService( String serviceId ) throws Exception
    {
        StatisticsVO[] result = null;
        if ( myMap.containsKey( serviceId ) )
        {
            Buffer buf = (Buffer)myMap.get( serviceId );
            if ( buf.size() > 0 )
            {
                result =  new StatisticsVO[ buf.size() ];
                buf.toArray(result);
            }
        }
        else
        {
            throw new Exception( "No data is collected for serviceId " + serviceId );
        }
        myLogger.debug( "We have " + result.length + " number of items in serviceId " + serviceId );
        return result;
    }

    /**
     * Use to get last statistic item for a given service id gathered until this point in time
     *
     * @param serviceId key to the hashmap with services
     * @return
     * @throws Exception
     */
    public StatisticsVO getLastStatisticsForService( String serviceId ) throws Exception
    {
        StatisticsVO result = null;
        if ( myMap.containsKey( serviceId ) )
        {
            Buffer buf = (Buffer)myMap.get( serviceId );
            if ( buf.size() > 0 )
            {
                result =  (StatisticsVO) (buf.get());
            }
        }
        else
        {
            throw new Exception( "No data is collected for serviceId " + serviceId );
        }
        return result;
    }

    public String toString()
    {
        Collection col = myMap.keySet();
        Iterator iter = col.iterator();
        StringBuffer strbuf =  new StringBuffer();
        while ( iter.hasNext() )
        {
            String key = (String)iter.next();
            Buffer buf = (Buffer)myMap.get( key );
            strbuf.append( key + " has size : " + buf.size() + "\n");
        }
        return strbuf.toString();

    }

}

