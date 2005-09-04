package org.iris.server.settings.xml;



import java.io.*;
import javax.xml.bind.*;
import javax.xml.parsers.*;

import org.apache.log4j.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.iris.xml.server.settings.*;
import org.iris.server.util.filehelpers.FileCopyHelper;



/**
 * Fascade for handling loading of settings
 *
 */

public class SettingsFascade
{
    private static Unmarshaller myUnmarshaller;
    private static Marshaller myMarshaller;
    private static SettingsFascade myInstance;
    Logger myLogger = Logger.getLogger(SettingsFascade.class.getName());
    private static JAXBContext jc;
    private File myDefaultSettingsFile;
    private static String propertyFile = "iris";
    private SystemConfig mySystemConfig;
    private SettingsErrorHandler mySettingsErrorHandler = new SettingsErrorHandler();
    private Validator myValidator;

    private SettingsFascade()
    {
        //if we are calling the constructor from server we should have this
        // property set pointing to the settings file
        if ( System.getProperty("iris.initservices") != null )
        {
            myLogger.info( "Using local settings file - call made from server" );
            myDefaultSettingsFile = new java.io.File(System.getProperty("iris.initservices"));
        }
        else
        {
            myLogger.info( "No local settings file used - probably call from client" );
        }


        try
        {
            jc = JAXBContext.newInstance(   "org.iris.xml.server.settings");
            myUnmarshaller = jc.createUnmarshaller();
            myMarshaller = jc.createMarshaller();
        }
        catch (Exception ex)
        {
            myLogger.error( ex );
        }
    }

    public static SettingsFascade getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new SettingsFascade();
        }
        return myInstance;
    }

    public Unmarshaller getUnmarshallHandler()
    {
        return myUnmarshaller;
    }


    /**
     * Load systems settings from config file.
     *
     * @param overrideDefaultFile  if not null the we use this file as the settings file instead
     * @throws SettingsException
     */
    private void loadSystemconfigSettings( File overrideDefaultFile ) throws SettingsException
    {
        if ( overrideDefaultFile != null )
            myDefaultSettingsFile = overrideDefaultFile;

        Document document = null;
        FileInputStream filestream = null;
        InputSource source = null;
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       /*     String JAXPSCHEMALANGUAGE= "http://java.sun.com/xml/jaxp/properties/schemaSource";
            String W3CSCHEMALANGUAGE= "http://www.w3.org/2001/XMLSchema";
            String MYSCHEMA = "file://C:/Tradevision/iris/xml/style/iris.xsd";
             configure DocumentBuilderFactory to generate a namspace aware validating parser
             */
            factory.setNamespaceAware(true);
  //          factory.setValidating( true );
/*
            try
            {
                factory.setAttribute(JAXPSCHEMALANGUAGE, W3CSCHEMALANGUAGE);
                // if not in xml file use this instead
                factory.setAttribute(JAXPSCHEMALANGUAGE, MYSCHEMA);

            }
            catch (IllegalArgumentException ex3)
            {
                myLogger.error( "Pareser does not support JAXP1.2" );
            }
*/

            DocumentBuilder builder = factory.newDocumentBuilder();
           // builder.setErrorHandler( new SettingsErrorHandler() );
            filestream = new FileInputStream( myDefaultSettingsFile );
            source = new InputSource(filestream);
            document = builder.parse(source);
            Unmarshaller unm = getUnmarshallHandler();
            unm.setValidating(true);
            unm.setEventHandler( mySettingsErrorHandler );
            mySystemConfig = (SystemConfig) unm.unmarshal(document);
        }
        catch (JAXBException ex1)
        {
            throw new SettingsException("Could not parse settings file : " + myDefaultSettingsFile.getAbsolutePath() , ex1);
        }
        catch (SAXException ex1)
        {
            throw new SettingsException("Could not parse settings file : " + myDefaultSettingsFile.getAbsolutePath() , ex1);
        }
        catch (ParserConfigurationException ex1)
        {
           throw new SettingsException("Could not parse settings file : " + myDefaultSettingsFile.getAbsolutePath() , ex1);
        }
        catch (IOException ex)
        {
            throw new SettingsException("Could not locate settings file : " + myDefaultSettingsFile.getAbsolutePath() , ex);
        }
        finally
        {
            try
            {
                if (filestream != null)
                {
                    filestream.close();
                }
            }
            catch (IOException ex2)
            {
            }
        }
    }



    public void reloadSystemconfigSettings( File settingsFile) throws SettingsException
    {
        loadSystemconfigSettings( settingsFile );
    }

    /**
     * Get system settings from xml string. Used by iris client.
     *
     * @param xmlSettings
     * @return
     * @throws SettingsException
     */
    public SystemConfig getSystemconfigSettings( String xmlSettings) throws SettingsException
    {
        try
        {
            loadSystemconfigSettingsFromString( xmlSettings );
        }
        catch (SettingsException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw new SettingsException("",ex);
        }
        return mySystemConfig;
    }


    public void storeSystemconfigSettingsAndReload( SystemConfig settings, File storeInfile ) throws SettingsException
    {
        FileOutputStream fileoute = null;
        try
        {
            myMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            if ( storeInfile.exists() )
            {
                storeInfile.getName().endsWith( "1.xml" );
                FileCopyHelper.copy( storeInfile.getAbsolutePath(), storeInfile.getAbsolutePath()  );
            }
            fileoute = new FileOutputStream( storeInfile );
            myMarshaller.marshal( settings  , fileoute );
            reloadSystemconfigSettings( storeInfile);
        }
        catch ( Exception ex)
        {
            throw new SettingsException( "Could not store settings in file : " + storeInfile.getAbsolutePath(), ex);
        }
        finally
        {
            try
            {
                fileoute.close();
            }
            catch (IOException ex1)
            {
            }
        }
    }

    public void storeSystemconfigSettingsAndReload( SystemConfig settings ) throws SettingsException
    {
        storeSystemconfigSettingsAndReload( settings , myDefaultSettingsFile);
    }


    public void storeSystemconfigSettingsAndReload(  ) throws Exception
    {
        storeSystemconfigSettingsAndReload( mySystemConfig , myDefaultSettingsFile);
    }


    public SystemConfig getSystemconfigSettings() throws SettingsException
    {
        try
        {
            if (mySystemConfig == null)
            {
                loadSystemconfigSettings( null);
            }
        }
        catch (Exception ex)
        {
            throw new SettingsException("Could not load settings " , ex);
        }
        return mySystemConfig;
    }

    public SystemConfig getSystemconfigSettings( File overrideDefaultFile) throws SettingsException
    {
        try
        {
            loadSystemconfigSettings( overrideDefaultFile );
        }
        catch (SettingsException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw new SettingsException("",ex);
        }
        return mySystemConfig;
    }


    public String getSystemconfigSettingsAsString(  ) throws SettingsException
    {
        try
        {
            if ( mySystemConfig == null )
                loadSystemconfigSettings( null);
            myMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            myMarshaller.marshal( mySystemConfig , writer );
            return writer.toString();
        }
        catch ( SettingsException ex)
        {
            throw ex;
        }
        catch ( Exception ex )
        {
            throw new SettingsException( "Failed marshalling to string",ex );
        }
    }

    /**
     * Loads system settings using a xml string representation of the configuration.
     * Used by client side application - server side uses local congfig file instead.
     *
     * @param xmlSettings
     * @throws SettingsException
     */
    private void loadSystemconfigSettingsFromString(String xmlSettings) throws SettingsException
    {
        Document document = null;
        StringReader reader = null;
        InputSource source = null;
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            reader = new StringReader( xmlSettings );
            source = new InputSource( reader );
            document = builder.parse(source);
            Unmarshaller unm = getUnmarshallHandler();
            unm.setValidating(true);
            unm.setEventHandler( mySettingsErrorHandler );
            mySystemConfig = (SystemConfig) unm.unmarshal(document);
        }
        catch (JAXBException ex1)
        {
            myLogger.error( ex1 );
            throw new SettingsException("Could not parse settings xml string  " , ex1);
        }
        catch (SAXException ex1)
        {
            myLogger.error( ex1 );
            throw new SettingsException("Could not parse settings xml string  " , ex1);
        }
        catch (ParserConfigurationException ex1)
        {
            myLogger.error( ex1 );
            throw new SettingsException("Could not parse settings xml string  " , ex1);
        }
        catch (IOException ex1)
        {
            myLogger.error( ex1 );
            throw new SettingsException("Could not parse settings xml string  " , ex1);
        }
        catch (Exception ex1)
        {
            myLogger.error( ex1 );
            throw new SettingsException("Could not parse settings xml string  " , ex1);
        }
        finally
        {
        }
    }


    private File getMyDefaultSettingsFile()
    {
        return myDefaultSettingsFile;
    }

}




