package org.iris.client;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.iris.client.swing.util.dialogs.*;
import org.iris.client.swing.util.events.*;

public class MainFrameMenuBar
    extends JMenuBar
{
    private static MainFrameMenuBar myInstance;
    JMenu myFileMenu = new JMenu();
    JMenuItem myExitMenuItem = new JMenuItem();
    JMenu myHelpMenu = new JMenu();
    JMenu myEditMenu = new JMenu();
    JMenuItem jMenuItemOptions = new JMenuItem();
    JMenuItem myAboutMenuItem = new JMenuItem();
    private transient Vector actionListeners;
    JMenuItem jMenuItemSaveSettings = new JMenuItem();

    private MainFrameMenuBar()
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

    public static MainFrameMenuBar getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new MainFrameMenuBar();
        }
        return myInstance;
    }

    /**
     *  Method is responsible for
     *
     * @param  l  Description of Parameter
     */
    public synchronized void removeActionListener(ActionListener l)
    {
        if (actionListeners != null && actionListeners.contains(l))
        {
            Vector v = (Vector) actionListeners.clone();
            v.removeElement(l);
            actionListeners = v;
        }
    }

    /**
     *  Adds a feature to the ActionListener attribute of the MainFrameMenuBar object
     *
     * @param  l  The feature to be added to the ActionListener attribute
     */
    public synchronized void addActionListener(ActionListener l)
    {
        Vector v = actionListeners == null ? new Vector(2) : (Vector) actionListeners.clone();
        if (!v.contains(l))
        {
            v.addElement(l);
            actionListeners = v;
        }
    }

    protected void fireActionPerformed(ActionEvent e)
    {
        if (actionListeners != null)
        {
            Vector listeners = actionListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++)
            {
                 ( (ActionListener) listeners.elementAt(i)).actionPerformed(e);
            }
        }
    }

    void myExitMenuItem_actionPerformed(ActionEvent e)
    {
        System.out.println("exit called");
        ApplicationEventHandler.getInstance().fireDataChanged(new ApplicationStateEvent(this, "Exit application",
            ApplicationStateEvent.EXIT_APPLICATION));
    }

    void jMenuItemSaveSettings_actionPerformed(ActionEvent e)
    {
        ApplicationEventHandler.getInstance().fireDataChanged(new ApplicationStateEvent(this, "Save settings",
            ApplicationStateEvent.SAVE_SETTINGS));

    }

    void jMenuItemOptions_actionPerformed(ActionEvent e)
    {
        OptionsDialog.getStaticInstance().setVisible(true);
    }

    void myAboutMenuItem_actionPerformed(ActionEvent e)
    {
        AboutJDialog.getStaticInstance().setVisible(true);
    }

    /**
     *  Method is responsible for
     *
     * @param  e  Description of Parameter
     */


    /**
     *  Method is responsible for
     *
     * @exception  Exception  Description of Exception
     */
    private void jbInit() throws Exception
    {
        myEditMenu.setBackground(Color.lightGray);
        myEditMenu.setText("Edit");
        jMenuItemOptions.setBackground(Color.lightGray);
        jMenuItemOptions.setText("Options");
        jMenuItemOptions.addActionListener(new java.awt.event.ActionListener()
        {
            /**
             *  Method is responsible for
             *
             * @param  e  Description of Parameter
             */
            public void actionPerformed(ActionEvent e)
            {
                jMenuItemOptions_actionPerformed(e);
            }
        });
        myAboutMenuItem.setBackground(Color.lightGray);
        myAboutMenuItem.setText("About");
        myAboutMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            /**
             *  Method is responsible for
             *
             * @param  e  Description of Parameter
             */
            public void actionPerformed(ActionEvent e)
            {
                myAboutMenuItem_actionPerformed(e);
            }
        });
        myFileMenu.setBackground(Color.lightGray);
        myFileMenu.setText("File");
        myExitMenuItem.setBackground(Color.lightGray);
        myExitMenuItem.setText("Exit");
        myExitMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            /**
             *  Method is responsible for
             *
             * @param  e  Description of Parameter
             */
            public void actionPerformed(ActionEvent e)
            {
                myExitMenuItem_actionPerformed(e);
            }
        });
        myHelpMenu.setBackground(Color.lightGray);
        myHelpMenu.setText("Help");
        this.setBackground(Color.lightGray);
        jMenuItemSaveSettings.setText("Save settings");
        jMenuItemSaveSettings.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jMenuItemSaveSettings_actionPerformed(e);
            }
        });
        this.add(myFileMenu);
        this.add(myEditMenu);
        this.add(myHelpMenu);
        myFileMenu.add(jMenuItemSaveSettings);
        myFileMenu.add(myExitMenuItem);
        myEditMenu.add(jMenuItemOptions);
        myHelpMenu.add(myAboutMenuItem);
    }

}
