package org.iris.client.swing.util.windows;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import org.iris.client.swing.util.*;

public class StartUpScreenWindow
    extends JWindow
{
    JPanel jPanelRoot = new JPanel();
    JLabel jLabelStatusGif = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanelSouth = new JPanel();
//    MyJProgressBar myProgressBar = new MyJProgressBar(  10, true);
    Border border1;

    /* todo
            {
            MainFrame.setLookandFeel();
        }
     */
    /**
     *  Constructor for the StartUpScreenWindow object
     */
    public StartUpScreenWindow()
    {
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        setCentered();

    }

    /**
     *  The main program for the StartUpScreenWindow class
     *
     * @param  args  The command line arguments
     */
    public static void main(String[] args)
    {
        StartUpScreenWindow startUpScreenJWindow1 = new StartUpScreenWindow();
        startUpScreenJWindow1.setVisible(true);
    }

    /**
     *  Sets the {3} attribute of the StartUpScreenWindow object
     *
     * @param  b  The new {3} value
     */
    public void setVisible(boolean b)
    {
        /**
         * @todo:    Override this java.awt.Component method
         */
        super.setVisible(b);

        // remember to stop the "snake"
//        if ( b == false ) myProgressBar.stop();
    }

    /**
     *  Method is responsible for
     *
     * @param  o1  Description of Parameter
     * @param  o2  Description of Parameter
     * @return     Description of the Returned Value
     */
    public int compare(Object o1, Object o2)
    {
        /**
         * @todo:    Implement this java.util.Comparator method
         */
        throw new java.lang.UnsupportedOperationException("Method compare() not yet implemented.");
    }

    /**
     *  Method is responsible for
     *
     * @param  obj  Description of Parameter
     * @return      Description of the Returned Value
     */
    public boolean equals(Object obj)
    {
        /**
         * @todo:    Implement this java.util.Comparator method
         */
        throw new java.lang.UnsupportedOperationException("Method equals() not yet implemented.");
    }

    /**
     *  Sets the {3} attribute of the StartUpScreenWindow object
     */
    private void setCentered()
    {
        //Center the window
        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        this.setLocation( (screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    /**
     *  Method is responsible for
     *
     * @exception  Exception  Description of Exception
     */
    private void jbInit() throws Exception
    {
        border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, Color.lightGray,
                  Color.darkGray);
        /*      myProgressBar.setBackground( Color.white );
           myProgressBar.setForeground(SystemColor.black);
           myProgressBar.setMaximum(200);
           myProgressBar.setMinimum(0);
           myProgressBar.start();
         */
        jPanelRoot.setBackground(Color.white);
        jPanelRoot.setBorder(border1);
        //jPanelRoot.setPreferredSize(new Dimension(300, 200));
        jPanelRoot.setLayout(borderLayout1);
        jLabelStatusGif.setForeground(SystemColor.activeCaptionText);
        jLabelStatusGif.setHorizontalAlignment(SwingConstants.CENTER);

        jLabelStatusGif.setIcon(ResourcesFactory.getImageIcon("splashscreenlogo.gif"));

        jPanelSouth.setBackground(Color.white);
        this.getContentPane().add(jPanelRoot, BorderLayout.CENTER);
//        jPanelRoot.add(jPanelSouth, BorderLayout.SOUTH);
//        jPanelSouth.add(myProgressBar, null);
        jPanelRoot.add(jLabelStatusGif, BorderLayout.CENTER);
        this.pack();
    }

    {
        //  MainFrame.setLookandFeel();
    }

}
