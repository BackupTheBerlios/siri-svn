package org.siri.swing.util.statuspanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StatusJPanelTest
    extends JFrame
{
    StatusJPanel jPanel1 = new StatusJPanel();
    JButton jButton1 = new JButton();

    public StatusJPanelTest()
    {
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        StatusJPanelTest statusJPanelTest1 = new StatusJPanelTest();
        statusJPanelTest1.setVisible(true);
    }

    private void jbInit() throws Exception
    {
        jButton1.setText("LONG OPERATION");
        jButton1.addActionListener(new ActionListener(this));
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButton1_actionPerformed(e);
            }
        });

        this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
        this.getContentPane().add(jButton1, BorderLayout.NORTH);
    }

    void jButton1_actionPerformed(ActionEvent e)
    {

        Runnable task = new Runnable()
        {
            public void run()
            {
                jPanel1.startProgressBar();
                jPanel1.setStatusText("Starting long operations...");

                try
                {
                    Thread.sleep(4000);
                }
                catch (Exception ex)
                {

                }
                jPanel1.stopProgressBar();
                jPanel1.setStatusText("");
            }
        };

        //SwingUtilities.invokeLater( task );
         (new Thread(task)).start();

    }
}

class ActionListener
    implements java.awt.event.ActionListener
{
    StatusJPanelTest adaptee;

    ActionListener(StatusJPanelTest adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButton1_actionPerformed(e);
    }
}
