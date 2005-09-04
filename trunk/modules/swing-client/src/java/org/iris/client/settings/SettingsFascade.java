package org.iris.client.settings;

import java.io.*;
import javax.xml.bind.*;
import javax.xml.parsers.*;

import org.apache.commons.lang.*;
import org.apache.log4j.*;
import org.iris.client.settings.xml.*;
import org.iris.server.util.filehelpers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class SettingsFascade
{
    private static Unmarshaller unmarshaller;
    private static Marshaller marshaller;
    private static SettingsFascade myInstance;
    Logger myLogger = Logger.getLogger(SettingsFascade.class.getName());
    private static JAXBContext jc;
    private File myDefaultSettingsFile;
    private Irisclient myIrisclientSettings;
    private static String propertyFile = "irisclient";
    private IrisserverType myConnectedToServer;

    /*
     {
         ResourceBundle rb = null;
         try
         {
             rb = ResourceBundle.getBundle(propertyFile);
             Enumeration proplist = rb.getKeys();
             while (proplist.hasMoreElements())
             {
                 String tKey = (String) proplist.nextElement();
                 System.setProperty(tKey, rb.getString(tKey));
             }
         }
         catch (MissingResourceException e)
         {
             System.out.println("Missing resource : Iris");
             e.printStackTrace();
         }
     }
     */

    private SettingsFascade()
    {

        // myDefaultSettingsFile = new java.io.File(System.getProperty("iris.client.settings"));

        setDefaultSettingsFile();

        try
        {
            jc = JAXBContext.newInstance("org.iris.client.settings.xml");
            unmarshaller = jc.createUnmarshaller();
            marshaller = jc.createMarshaller();

            loadIrisclientSettings(null);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        myInstance = this;
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
        return unmarshaller;
    }

    public Marshaller getMarshallHandler()
    {
        return marshaller;
    }

    private void loadIrisclientSettings(File overrideDefaultFile) throws Exception
    {
        if (overrideDefaultFile != null)
        {
            myDefaultSettingsFile = overrideDefaultFile;
        }
        Document document = null;
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            FileInputStream filestream = new FileInputStream(myDefaultSettingsFile);
            InputSource source = new InputSource(filestream);
            document = builder.parse(source);
            Unmarshaller unm = getUnmarshallHandler();
            myIrisclientSettings = (Irisclient) unm.unmarshal(document);
        }
        catch (Exception ex)
        {
            myLogger.error(ex.getMessage(), ex);
            throw new Exception("Could not locate settings file : " + myDefaultSettingsFile.getAbsolutePath());
        }
    }

    public void reloadIrisclientSettings() throws Exception
    {
        loadIrisclientSettings(null);
    }

    /*    public Irisclient getIrisclientSettings( File file ) throws Exception
        {
            Irisclient result = null;
            Document document = null;
            try
            {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                DocumentBuilder builder = factory.newDocumentBuilder();
                FileInputStream filestream = new FileInputStream( file );
                InputSource source = new InputSource(filestream);
                document = builder.parse(source);
                Unmarshaller unm = SettingsFascade.getInstance().getUnmarshallHandler();
                result = (Irisclient) unm.unmarshal(document);
            }
            catch (Exception ex)
            {
                myLogger.error(ex.getMessage(), ex);
                throw new Exception("Could not locate settings file : " + file.getAbsolutePath());
            }
            return result;
        }
     */

    public void storeIrisclientSettings(Irisclient settings, File storeInfile) throws Exception
    {
        try
        {
            Marshaller m = this.getMarshallHandler();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            if (storeInfile.exists())
            {
                storeInfile.getName().endsWith("1.xml");
                FileCopyHelper.copy(storeInfile.getAbsolutePath(), storeInfile.getAbsolutePath());
            }
            FileOutputStream fileoute = new FileOutputStream(storeInfile);
            m.marshal(settings, fileoute);

            reloadIrisclientSettings();

            myLogger.debug("Settings stored and reloaded " + storeInfile.getAbsolutePath());
        }
        catch (Exception ex)
        {
            throw new Exception("Could not store settings in file : " + storeInfile.getAbsolutePath(), ex);
        }
    }

    public void storeIrisclientSettings(Irisclient settings) throws Exception
    {
        storeIrisclientSettings(settings, myDefaultSettingsFile);
    }

    public void storeCurrentIrisclientSettings() throws Exception
    {
        storeIrisclientSettings(myIrisclientSettings, myDefaultSettingsFile);
    }

    public Irisclient getIrisclientSettings()
    {
        try
        {
            if (myIrisclientSettings == null)
            {
                loadIrisclientSettings(null);
            }
        }
        catch (Exception ex)
        {
            myLogger.error(ex);
            return null;
        }
        return myIrisclientSettings;
    }

    public Irisclient getIrisclientSettings(File overrideDefaultFile)
    {
        try
        {
            loadIrisclientSettings(overrideDefaultFile);
        }
        catch (Exception ex)
        {
            myLogger.error(ex);
            return null;
        }
        return myIrisclientSettings;
    }

    public String getIrisclientSettingsAsString()
    {
        try
        {
            if (myIrisclientSettings == null)
            {
                loadIrisclientSettings(null);
            }

            Marshaller m = this.getMarshallHandler();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter writer = new StringWriter();
            m.marshal(myIrisclientSettings, writer);
            return writer.toString();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public File getDefaultSettingsFile()
    {
        return myDefaultSettingsFile;
    }

    public void setConnectedToServer(IrisserverType conToIrisserverType)
    {
        myConnectedToServer = conToIrisserverType;
    }

    public IrisserverType getConnectedToServer()
    {
        return myConnectedToServer;
    }

    public static void main(String[] args)
    {
        SettingsFascade.getInstance();

    }

    /**
     * Checks user dir and tries to find out if we are in developent mode or we have deployed the application.
     * Based on that it setts configuration file for client.
     *
     * @return
     */
    private String setDefaultSettingsFile()
    {
        String fileName = "irisclient.xml";
        String userDirPath = System.getProperty("user.dir");
        System.out.println(userDirPath);
        String filePath = "";
        if (userDirPath.endsWith("scripts"))
        {
            filePath = StringUtils.chomp(userDirPath, "scripts") + "/properties/" + fileName;
            myLogger.info("Using settings file from deployed module");
        }
        else if (userDirPath.endsWith("swing-client"))
        {
            filePath = userDirPath + "/src/conf/" + fileName;
            myLogger.info("Using settings file from development module");
        }
        else
        {
            myLogger.error("No settings file found");

        }
        File settingsFileDeploy = new File(filePath);
        if (settingsFileDeploy.exists())
        {
            myDefaultSettingsFile = settingsFileDeploy;
        }
        else
        {
            myLogger.error("Could not locate settings file - path used was " + filePath);
            myDefaultSettingsFile = null;
        }

        return filePath;
    }
}
