package org.siri.swing.util.tablesort;

import java.awt.*;
import javax.swing.*;

public class BlankIcon
    implements Icon
{
    private Color fillColor;
    private int size;

    /**
     *  Constructor for the BlankIcon object
     */
    public BlankIcon()
    {
        this(null, 11);
    }

    /**
     *  Constructor for the BlankIcon object
     *
     *@param  color  Description of Parameter
     *@param  size   Description of Parameter
     */
    public BlankIcon(Color color, int size)
    {
        //UIManager.getColor("control")
        //UIManager.getColor("controlShadow")
        fillColor = color;

        this.size = size;
    }

    /**
     *  Gets the IconWidth attribute of the BlankIcon object
     *
     *@return    The IconWidth value
     */
    public int getIconWidth()
    {
        return size;
    }

    /**
     *  Gets the IconHeight attribute of the BlankIcon object
     *
     *@return    The IconHeight value
     */
    public int getIconHeight()
    {
        return size;
    }

    /**
     *  Description of the Method
     *
     *@param  c  Description of Parameter
     *@param  g  Description of Parameter
     *@param  x  Description of Parameter
     *@param  y  Description of Parameter
     */
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        if (fillColor != null)
        {
            g.setColor(fillColor);
            g.drawRect(x, y, size - 1, size - 1);
        }
    }
}
