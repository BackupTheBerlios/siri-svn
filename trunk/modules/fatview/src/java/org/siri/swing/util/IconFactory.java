package org.siri.swing.util;

//import java.util.logging.*;
import javax.swing.*;

public class IconFactory
{
    public final static String ROOTPATH = "pics/";
    private static ClassLoader cl = IconFactory.class.getClassLoader();

    public static ImageIcon getImageIcon(String imageName)
    {
        ImageIcon im = null;
        try
        {
            im = new ImageIcon(cl.getResource(imageName));
        }
        catch (Exception ex)
        {
            //myLogger.severe("Did not get gif for " + imageName);
            ex.printStackTrace();
        }

        return im;
    }

}
