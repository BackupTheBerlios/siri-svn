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
    private String description;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }



    public void execute()
    {
        logger.debug("->" + description);
    }
}
