package org.iris.server.settings.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import org.apache.log4j.Logger;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEvent;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Georges Polyzois
 * @version 1.0
 */

public class SettingsErrorHandler extends DefaultHandler implements ValidationEventHandler
{
    Logger myLogger = Logger.getLogger( SettingsErrorHandler.class.getName());

    public SettingsErrorHandler()
    {
    }

    public void error(SAXParseException sAXParseException)
    {
        myLogger.error( "Failed parsing the config file : " + sAXParseException );
    }

    public void fatalError(SAXParseException sAXParseException)
    {
        myLogger.fatal( "Failed parsing the config file : " + sAXParseException );

    }

    public void warning(SAXParseException sAXParseException)
    {
        myLogger.warn( "Failed parsing the config file : " + sAXParseException );

    }

    public boolean handleEvent(ValidationEvent validationEvent)
    {
        if ( validationEvent.getSeverity() ==  ValidationEvent.ERROR)
        {
            myLogger.error( "¤¤¤¤¤¤¤" + validationEvent.getMessage(), validationEvent.getLinkedException() );

        }
        else if (  validationEvent.getSeverity() ==  ValidationEvent.WARNING)
        {
            myLogger.warn( "¤¤¤¤¤¤¤" +validationEvent.getMessage(), validationEvent.getLinkedException() );

        }
        else if ( validationEvent.getSeverity() ==  ValidationEvent.FATAL_ERROR)
        {
            myLogger.fatal( "¤¤¤¤¤¤¤"+ validationEvent.getMessage(), validationEvent.getLinkedException() );

        }
        return false;
    }
}