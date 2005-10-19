package org.msgrouter.feeder;

import org.apache.log4j.Logger;
import org.msgrouter.feeder.FeederPlugin;


/**
 * @author Georges Polyzois
 */
public class FeederPluginImpl implements FeederPlugin
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(FeederPluginImpl.class);

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    String name;

    public void execute()
    {
        logger.debug("");
    }
}
