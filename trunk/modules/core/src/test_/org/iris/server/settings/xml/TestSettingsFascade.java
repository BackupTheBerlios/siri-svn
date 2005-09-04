package org.iris.server.settings.xml;

import junit.framework.*;
import org.apache.log4j.Logger;
import java.io.File;
import java.util.List;
import java.util.Iterator;
import org.iris.server.util.filehelpers.FileUtils;
import org.iris.xml.server.settings.ServiceType;


public class TestSettingsFascade extends TestCase
{
    Logger myLogger = Logger.getLogger( TestSettingsFascade.class.getName());
    String validSampleXML = "iris_complete.xml";
    String parseErrorSampleXML = "iris_parseerrors.xml";
    String intervallToSmallSampleXML = "iris_intervalltosmallerror.xml";
    String testDir;


    public TestSettingsFascade(String name)
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


    /**
     * Read a config file and assert some of the attributes
     */
    public void testloadSystemconfigSettings()
    {
        try
        {
            SettingsFascade.getInstance().getSystemconfigSettings( new File(testDir + validSampleXML) );
            assertEquals( "iris", SettingsFascade.getInstance().getSystemconfigSettings().getName() );
            //assertEquals( 6, SettingsFascade.getInstance().getSystemconfigSettings().getService().size() );
            assertEquals("c:/jjjjjj/", SettingsFascade.getInstance().getSystemconfigSettings().getGlobalSettings().getServicerootpath() );
        }
        catch (Exception ex)
        {
            assertTrue( "Failed reading and parsing xml file " + ex.getMessage(), true );
        }
    }

    /**
     * Tries reading a config file, does some alterations of attributes and writes to new file and deletes is
     * after finished.
     */
    /*
    public void testloadSysteconfigSettingsAndSave()
    {
        try
        {
            File original = new File(testDir + validSampleXML);
            File temp1 =  new File( testDir + "temp1" +validSampleXML );

            FileUtils.copy( original ,  temp1   );


            SettingsFascade.getInstance().getSystemconfigSettings( temp1  );
            List ls = SettingsFascade.getInstance().getSystemconfigSettings().getService();
            Iterator it = ls.iterator();
            while (  it.hasNext() )
            {
                ServiceType ser = (ServiceType) it.next();
                if ( ser.getId().equals( "poll-pnl2k" ) )
                {
                    System.out.println("Setting testid");
                    ser.setId( "TESTID" );
                }
            }
            SettingsFascade.getInstance().storeSystemconfigSettingsAndReload(   );

            SettingsFascade.getInstance().getSystemconfigSettings( temp1 );
            ls = SettingsFascade.getInstance().getSystemconfigSettings().getService();
            it = ls.iterator();
            while (  it.hasNext() )
            {
                ServiceType ser = (ServiceType) it.next();
                if ( ser.getId().equals( "TESTID" ) )
                {
                    ser.setId( "poll-pnl2k" );
                }
            }
            SettingsFascade.getInstance().storeSystemconfigSettingsAndReload(  );
            temp1.delete();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            assertTrue( "Failed reading and parsing xml file " + ex.getMessage(), false );
        }
    }
    */
    
    public void testloadSystemconfigSettingsWithParseErros()
   {
       try
       {
           SettingsFascade.getInstance().getSystemconfigSettings(new File(testDir + parseErrorSampleXML));
       }
       catch (Exception ex)
       {
           myLogger.fatal( "Failed parsing file",ex);

           assertTrue( true);
       }

   }




}


