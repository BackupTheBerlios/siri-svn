package org.siri.swing.util.tablesort;

import java.awt.*;
import javax.swing.*;

public class BevelArrowIcon
    implements Icon
{

    private Color edge1;
    private Color edge2;
    private Color fill;
    private int size;
    private int direction;

    public final static int UP = 0;

    public final static int DOWN = 1;

    private final static int DEFAULT_SIZE = 11;

    public BevelArrowIcon(int direction, boolean isRaisedView, boolean isPressedView)
    {
        if (isRaisedView)
        {
            if (isPressedView)
            {
                init(UIManager.getColor("controlLtHighlight"), UIManager.getColor("controlDkShadow"),
                    UIManager.getColor("controlShadow"), DEFAULT_SIZE, direction);
            }
            else
            {
                init(UIManager.getColor("controlHighlight"), UIManager.getColor("controlShadow"),
                    UIManager.getColor("control"), DEFAULT_SIZE, direction);
            }
        }
        else
        {
            if (isPressedView)
            {
                init(UIManager.getColor("controlDkShadow"), UIManager.getColor("controlLtHighlight"),
                    UIManager.getColor("controlShadow"), DEFAULT_SIZE, direction);
            }
            else
            {
                init(UIManager.getColor("controlShadow"), UIManager.getColor("controlHighlight"),
                    UIManager.getColor("control"), DEFAULT_SIZE, direction);
            }
        }
    }

    /**
     *  Constructor for the BevelArrowIcon object
     *
     *@param  edge1      Description of Parameter
     *@param  edge2      Description of Parameter
     *@param  fill       Description of Parameter
     *@param  size       Description of Parameter
     *@param  direction  Description of Parameter
     */
    public BevelArrowIcon(Color edge1, Color edge2, Color fill, int size, int direction)
    {
        init(edge1, edge2, fill, size, direction);
    }

    /**
     *  Gets the IconWidth attribute of the BevelArrowIcon object
     *
     *@return    The IconWidth value
     */
    public int getIconWidth()
    {
        return size;
    }

    /**
     *  Gets the IconHeight attribute of the BevelArrowIcon object
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
        switch (direction)
        {
            case DOWN:
                drawDownArrow(g, x, y);
                break;
            case UP:
                drawUpArrow(g, x, y);
                break;
        }
    }

    /**
     *  Description of the Method
     *
     *@param  edge1      Description of Parameter
     *@param  edge2      Description of Parameter
     *@param  fill       Description of Parameter
     *@param  size       Description of Parameter
     *@param  direction  Description of Parameter
     */
    private void init(Color edge1, Color edge2, Color fill, int size, int direction)
    {
        this.edge1 = edge1;
        this.edge2 = edge2;
        this.fill = fill;
        this.size = size;
        this.direction = direction;
    }

    /**
     *  Description of the Method
     *
     *@param  g   Description of Parameter
     *@param  xo  Description of Parameter
     *@param  yo  Description of Parameter
     */
    private void drawDownArrow(Graphics g, int xo, int yo)
    {
        g.setColor(edge1);
        g.drawLine(xo, yo, xo + size - 1, yo);
        g.drawLine(xo, yo + 1, xo + size - 3, yo + 1);
        g.setColor(edge2);
        g.drawLine(xo + size - 2, yo + 1, xo + size - 1, yo + 1);
        int x = xo + 1;
        int y = yo + 2;
        int dx = size - 6;
        while (y + 1 < yo + size)
        {
            g.setColor(edge1);
            g.drawLine(x, y, x + 1, y);
            g.drawLine(x, y + 1, x + 1, y + 1);
            if (0 < dx)
            {
                g.setColor(fill);
                g.drawLine(x + 2, y, x + 1 + dx, y);
                g.drawLine(x + 2, y + 1, x + 1 + dx, y + 1);
            }
            g.setColor(edge2);
            g.drawLine(x + dx + 2, y, x + dx + 3, y);
            g.drawLine(x + dx + 2, y + 1, x + dx + 3, y + 1);
            x += 1;
            y += 2;
            dx -= 2;
        }
        g.setColor(edge1);
        g.drawLine(xo + (size / 2), yo + size - 1, xo + (size / 2), yo + size - 1);
    }

    /**
     *  Description of the Method
     *
     *@param  g   Description of Parameter
     *@param  xo  Description of Parameter
     *@param  yo  Description of Parameter
     */
    private void drawUpArrow(Graphics g, int xo, int yo)
    {
        g.setColor(edge1);
        int x = xo + (size / 2);
        g.drawLine(x, yo, x, yo);
        x--;
        int y = yo + 1;
        int dx = 0;
        while (y + 3 < yo + size)
        {
            g.setColor(edge1);
            g.drawLine(x, y, x + 1, y);
            g.drawLine(x, y + 1, x + 1, y + 1);
            if (0 < dx)
            {
                g.setColor(fill);
                g.drawLine(x + 2, y, x + 1 + dx, y);
                g.drawLine(x + 2, y + 1, x + 1 + dx, y + 1);
            }
            g.setColor(edge2);
            g.drawLine(x + dx + 2, y, x + dx + 3, y);
            g.drawLine(x + dx + 2, y + 1, x + dx + 3, y + 1);
            x -= 1;
            y += 2;
            dx += 2;
        }
        g.setColor(edge1);
        g.drawLine(xo, yo + size - 3, xo + 1, yo + size - 3);
        g.setColor(edge2);
        g.drawLine(xo + 2, yo + size - 2, xo + size - 1, yo + size - 2);
        g.drawLine(xo, yo + size - 1, xo + size, yo + size - 1);
    }

}
