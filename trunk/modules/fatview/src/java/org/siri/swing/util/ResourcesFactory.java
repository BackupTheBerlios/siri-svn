package org.siri.swing.util;

import java.net.*;
import java.util.*;

//import java.util.logging.Logger;
import javax.swing.*;

import org.apache.log4j.*;

public class ResourcesFactory
{
    public final static String ROOTPATH_IMAGES = "pics/";
    static Logger myLogger = Logger.getLogger(ResourcesFactory.class.getName());

    private static ClassLoader cl;

    private static ClassLoader cl2;

    static
    {
        //      cl = myClass.getClass().getClassLoader();
        cl = org.siri.swing.util.ResourcesFactory.class.getClassLoader();
    }

    public ResourcesFactory()
    {
    }

    public static URL getFile(String s)
    {
        URL url = null;
        try
        {

            url = cl.getResource("net/tradevision/ocean/oceanguiclient/properties/" + s);
        }
        catch (Exception exception)
        {
            //myLogger.severe("Did not get gif for " + s);
            exception.printStackTrace();
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }

        return url;
    }

    public static Properties getProperties(String s)
    {
        Properties properties = new Properties();
        try
        {
            java.io.InputStream inputstream = cl.getResourceAsStream("net/tradevision/ocean/oceanguiclient/properties/" +
                                              s);
            properties.load(inputstream);
        }
        catch (Exception exception)
        {
            //myLogger.severe("Did not get gif for " + s);
            exception.printStackTrace();
        }
        return properties;
    }

    /**
     *
     */
    public static void main(String[] args)
    {

        ResourcesFactory.getImageIcon("referenceitem_round.gif");
    }

    public static ImageIcon getImageIcon(String imageName)
    {
        ImageIcon im = null;
        try
        {
            im = new ImageIcon(cl.getResource(ROOTPATH_IMAGES + imageName));

//            gui.MyClass.class.getResource("/icon.gif")
        }
        catch (Exception ex)
        {
            myLogger.error("Did not get gif for " + imageName);

        }

        return im;
    }

    /*
      static Class class$(String s)
      {
          try
          {
              return Class.forName(s);
          }
          catch(ClassNotFoundException classnotfoundexception)
          {
              throw new NoClassDefFoundError(classnotfoundexception.getMessage());
          }
      }
     */
}
