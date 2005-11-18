package org.siri.command;

import org.siri.core.config.ServiceNodeConfig;
import org.siri.core.config.FileReaderConfig;
import org.siri.core.config.FileWriterConfig;
import org.apache.log4j.Logger;


/**
 * Class description.
 */
public class CommandFactory
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(CommandFactory.class);

    public static Command getCommand(ServiceNodeConfig theServiceNodeConfig)
    {
        if (theServiceNodeConfig==null)
        {
            throw new IllegalArgumentException("Config was null");
        }
        switch(theServiceNodeConfig.getCommandType())
        {
            case TOFILE :
            {
                FileWriterConfig fileWriterConfig = (FileWriterConfig)theServiceNodeConfig;
                /*FileWriter writer = null;
                try
                {
                    writer = new FileWriter( fileReaderConfig);
                } catch (IOException e)
                {
                    logger.error("Failed creating filewriter for fileReaderConfig",e);
                }
                */
                return new FileWriterCommand(fileWriterConfig);
            }
            default :
                return null;
        }
    }
}
