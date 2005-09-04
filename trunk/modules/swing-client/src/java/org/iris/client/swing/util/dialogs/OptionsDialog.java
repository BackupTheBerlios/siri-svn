package org.iris.client.swing.util.dialogs;

// Java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.iris.client.swing.util.panels.optionspanel.lookandfeel.*;
import org.iris.client.swing.util.panels.optionspanel.systemprops.*;

//import java.util.logging.*;

public class OptionsDialog
    extends JDialog
{
    //
    //  An instance of this dialog
    private static OptionsDialog theOptionsDialog;
    JPanel panelRoot = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JTabbedPane jTabbedPaneAllOptions = new JTabbedPane();
    JPanel jPanelSouth = new JPanel();
    JButton jButtonOk = new JButton();
    BorderLayout borderLayout2 = new BorderLayout();
    JButton jButtonCancel = new JButton();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();
    TitledBorder titledBorder1;
    //

    //
    //  Tab panels in this dialog

    LookAndFeelPanel lafPanel = new LookAndFeelPanel();
    AllSystemsPanel asPanel = new AllSystemsPanel();
    TitledBorder titledBorder2;

    /**
     *  Constructor for the OptionsDialog object
     */
    public OptionsDialog()
    {
        this(null, "", false);
    }

    //

    /**
     *  Constructor for the OptionsDialog object
     *
     * @param  frame  Description of Parameter
     * @param  title  Description of Parameter
     * @param  modal  Description of Parameter
     */
    private OptionsDialog(Frame frame, String title, boolean modal)
    {
        super(frame, title, modal);
        try
        {
            jbInit();
            initialize();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     *  Gets the {3} attribute of the OptionsDialog class
     *
     * @return    The {3} value
     */
    public static OptionsDialog getStaticInstance()
    {
        if (theOptionsDialog == null)
        {
            theOptionsDialog = new OptionsDialog();
        }
        return theOptionsDialog;
    }

    /**
     *  The main program for the OptionsDialog class
     *
     * @param  args  The command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        OptionsDialog dlg = new OptionsDialog();
        dlg.pack();
        dlg.setVisible(true);
    }

    /**
     *  Method is responsible for
     *
     * @exception  Exception  Description of Exception
     */
    void jbInit() throws Exception
    {
        titledBorder2 = new TitledBorder("");
        this.getContentPane().setBackground(Color.lightGray);
        this.setTitle("Options and configurations");
        panelRoot.setLayout(borderLayout1);

        panelRoot.setMinimumSize(new Dimension(200, 200));
        panelRoot.setPreferredSize(new Dimension(400, 300));
        jTabbedPaneAllOptions.setBackground(Color.lightGray);
        jButtonOk.setText("Ok");
        jButtonOk.addActionListener(new java.awt.event.ActionListener()
        {
            /**
             *  Method is responsible for
             *
             * @param  e  Description of Parameter
             */
            public void actionPerformed(ActionEvent e)
            {
                jButtonOk_actionPerformed(e);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener()
        {
            /**
             *  Method is responsible for
             *
             * @param  e  Description of Parameter
             */
            public void actionPerformed(ActionEvent e)
            {
                jButtonCancel_actionPerformed(e);
            }
        });

        getContentPane().add(panelRoot);
        panelRoot.add(jTabbedPaneAllOptions, BorderLayout.CENTER);
        // jTabbedPaneAllOptions.add(lafPanel, "Look And Feel");

        jTabbedPaneAllOptions.add(asPanel, "System info");

        panelRoot.add(jPanelSouth, BorderLayout.SOUTH);
        jPanelSouth.add(jButtonCancel, null);
        jPanelSouth.add(jButtonOk, null);
    }

    /**
     *  Method is responsible for
     *
     * @param  e  Description of Parameter
     */
    void jButtonCancel_actionPerformed(ActionEvent e)
    {
        this.dispose();
    }

    /**
     *  Method is responsible for
     *
     * @param  e  Description of Parameter
     */
    void jButtonOk_actionPerformed(ActionEvent e)
    {
        this.dispose();
    }

    /**
     *  Sets the {3} attribute of the OptionsDialog object
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
     */
    private void initialize()
    {

        pack();
        setCentered();
    }

}
