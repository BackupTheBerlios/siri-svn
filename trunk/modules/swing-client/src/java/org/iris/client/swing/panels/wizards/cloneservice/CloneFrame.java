package org.iris.client.swing.panels.wizards.cloneservice;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.iris.server.util.config.*;

public class CloneFrame
    extends JFrame
{
    CloneServicePanel myCloneServicePanel;
    static final int windowHeight = 500;
    static final int windowWidth = 800;

    public CloneFrame(Service service)
    {
        myCloneServicePanel = new CloneServicePanel(service);
        myCloneServicePanel.initPanel(service);
        setUpGUI();
    }

    public CloneFrame()
    {
        setUpGUI();
    }

    private void setUpGUI()
    {
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.setSize(new Dimension(windowWidth, windowHeight));
        setCentered();

    }

    private void jbInit() throws Exception
    {
        this.addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosed(WindowEvent e)
            {
                this_windowClosed(e);
            }
        });
        this.getContentPane().add(myCloneServicePanel, BorderLayout.CENTER);
    }

    private void setCentered()
    {
        //Center the window
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

    public static void main(String[] args)
    {
        CloneFrame fr = new CloneFrame();
        fr.setVisible(true);
    }

    void this_windowClosed(WindowEvent e)
    {

    }
}
