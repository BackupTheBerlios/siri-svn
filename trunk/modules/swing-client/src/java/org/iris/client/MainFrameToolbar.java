package org.iris.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.iris.client.swing.util.*;
import org.iris.client.swing.util.events.*;

public class MainFrameToolbar
    implements ApplicationStateEventListener
{
    JPanel jPanelToolBarRootPanel = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JButton myHelp = new JButton();
    GridLayout gridLayout1 = new GridLayout();
    JPanel jPanel3 = new JPanel();
    JLabel myLogoLabel = new JLabel();
    BorderLayout borderLayout2 = new BorderLayout();
    Border border1;
    private static MainFrameToolbar myInstance;
    JToolBar myJToolBar = new JToolBar();
    JButton jButtonReconnect = new JButton();

    public static MainFrameToolbar getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new MainFrameToolbar();
        }
        return myInstance;
    }

    /**
     *  Constructor for the MainFrameToolbar object
     */
    private MainFrameToolbar()
    {
        super();
        ApplicationEventHandler.getInstance().addApplicationStateEventListener(this);
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        myInstance = this;
    }

    {
        try
        {
            UIManager.setLookAndFeel(new com.incors.plaf.kunststoff.KunststoffLookAndFeel());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void dataChanged(ApplicationStateEvent e)
    {
        if (e.getEventName() == ApplicationStateEvent.TREECLICKED)
        {
            myLogoLabel.setForeground(Color.red);
            myLogoLabel.setText(e.getText());
        }
        else
        {
            myLogoLabel.setForeground(Color.black);
            myLogoLabel.setText(e.getText());
        }

    }

    /**
     *  Method is responsible for
     *
     * @exception  Exception  Description of Exception
     */
    private void jbInit() throws Exception
    {

        border1 = BorderFactory.createLineBorder(Color.white, 1);
        myJToolBar.setFloatable(false);
        jPanelToolBarRootPanel.setBackground(Color.lightGray);
        jPanelToolBarRootPanel.setForeground(Color.white);

        jPanelToolBarRootPanel.setLayout(borderLayout1);
        myHelp.setOpaque(true);
        myHelp.setPreferredSize(new Dimension(24, 24));
        myHelp.setToolTipText("Help...");
        myHelp.setBorderPainted(false);
        myHelp.setContentAreaFilled(false);
        myHelp.setFocusPainted(false);
        myHelp.setHorizontalTextPosition(SwingConstants.CENTER);
        myHelp.setIcon(ResourcesFactory.getImageIcon("Help24.gif"));
        myHelp.setPressedIcon(ResourcesFactory.getImageIcon("Help24.gif"));
        myHelp.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                myHelp_actionPerformed(e);
            }
        });
        myHelp.setBounds(new Rectangle(2, 2, 25, 25));

//        myHelp.setText("B");

        jPanel2.setLayout(gridLayout1);
        myLogoLabel.setBackground(UIManager.getColor("Desktop.background"));
        //myLogoLabel.setHorizontalAlignment (  SwingConstants.LEFT);
        myLogoLabel.setHorizontalTextPosition(SwingConstants.LEFT);

        myLogoLabel.setForeground(Color.white);
        myLogoLabel.setFont(new Font("Arial", Font.BOLD, 14));

        jPanel3.setLayout(borderLayout2);
        myJToolBar.setBackground(Color.lightGray);
        myJToolBar.setBorder(border1);
        jPanel2.setBackground(Color.lightGray);
        jPanel3.setBackground(Color.lightGray);
        myJToolBar.setFloatable(false);
        myJToolBar.add(jPanelToolBarRootPanel, null);
        jPanelToolBarRootPanel.add(jPanel2, BorderLayout.WEST);
        jPanel2.add(myHelp, null);
        jPanel2.add(jButtonReconnect, null);
        jPanelToolBarRootPanel.add(jPanel3, BorderLayout.EAST);
        jPanel3.add(myLogoLabel, BorderLayout.CENTER);
    }

    void myHelp_actionPerformed(ActionEvent e)
    {
        JOptionPane.showMessageDialog(MainFrame.getInstance(), "Contact operatinos for more info", "Info",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public JToolBar getMyJToolBar()
    {
        return myJToolBar;
    }

}
