package org.iris.client.swing.util.dialogs;

import java.net.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.iris.client.swing.util.*;

public class AboutJDialog
    extends JDialog
{
    private static AboutJDialog dlg;
    JScrollPane jScrollPane1 = new JScrollPane();
    BorderLayout borderLayout5 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel jPanel1 = new JPanel();
    private JPanel panelRoot = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel jPanelAbout = new JPanel();
    private JPanel jPanelInfo = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JLabel jLabelPicture = new JLabel();
    private JPanel jPanelRootSouth = new JPanel();
    private JButton jButtonOK = new JButton();
    private JPanel jPanelCenter = new JPanel();
    private JEditorPane jEditorPaneHTML = new JEditorPane();
    private JLabel jLabel3 = new JLabel();
    private BorderLayout borderLayout4 = new BorderLayout();
    private String urlInAboutBox;

    /**
     *  Constructor for the AboutJDialog object
     *
     * @param  frame  Description of Parameter
     * @param  title  Description of Parameter
     * @param  modal  Description of Parameter
     */
    public AboutJDialog(Frame frame, String title, boolean modal)
    {
        super(frame, title, modal);
        try
        {
            jbInit();
            pack();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        this.setCentered();
    }

    /**
     *  Constructor for the AboutJDialog object
     */
    public AboutJDialog()
    {
        this(null, "", false);
    }

    /**
     *  Gets the {3} attribute of the AboutJDialog class
     *
     * @return    The {3} value
     */
    public static AboutJDialog getStaticInstance()
    {
        if (dlg == null)
        {
            dlg = new AboutJDialog();
        }
        return dlg;
    }

    /**
     *  Main method for testing only.
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

        AboutJDialog frame = new AboutJDialog();
        frame.pack();
        frame.setVisible(true);
    }

    /**
     *  Sets the {3} attribute of the AboutJDialog object
     */
    public void setCentered()
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
     *  Sets the {3} attribute of the AboutJDialog object
     *
     * @param  newUrlInAboutBox  The new {3} value
     */
    public void setUrlInAboutBox(String newUrlInAboutBox)
    {
        urlInAboutBox = newUrlInAboutBox;
    }

    /**
     *  Gets the {3} attribute of the AboutJDialog object
     *
     * @return    The {3} value
     */
    public String getUrlInAboutBox()
    {
        return urlInAboutBox;
    }

    /**
     *  Method is responsible for
     *
     * @exception  Exception  Description of Exception
     */
    void jbInit() throws Exception
    {

        panelRoot.setLayout(borderLayout1);
        jPanelInfo.setLayout(borderLayout2);
        jLabelPicture.setToolTipText("");
        try
        {
            jLabelPicture.setIcon(ResourcesFactory.getImageIcon("splashscreenlogo.gif"));
        }
        catch (Exception exc)
        {
            jLabelPicture.setText("Logo not found");
        }
        jPanelAbout.setLayout(borderLayout5);
        jPanelRootSouth.setLayout(flowLayout1);
        jButtonOK.setText("OK");
        jButtonOK.addActionListener(new java.awt.event.ActionListener()
        {
            /**
             *  Method is responsible for
             *
             * @param  e  Description of Parameter
             */
            public void actionPerformed(ActionEvent e)
            {
                jButtonOK_actionPerformed(e);
            }
        });
        jLabel3.setToolTipText("");
        jLabel3.setText("2004");
        jPanelCenter.setLayout(borderLayout4);
        jPanelAbout.setBackground(Color.black);
        jPanelAbout.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setModal(true);
        panelRoot.setBackground(Color.lightGray);
        panelRoot.setPreferredSize(new Dimension(400, 400));
        jTabbedPane1.setBackground(Color.lightGray);
        getContentPane().add(panelRoot);
        panelRoot.add(jTabbedPane1, BorderLayout.CENTER);
        jTabbedPane1.add(jPanelAbout, "About");
        jPanelAbout.add(jLabelPicture, BorderLayout.CENTER);
        jTabbedPane1.add(jPanelInfo, "Copyright");
        jPanelInfo.add(jPanelCenter, BorderLayout.CENTER);
        jPanelCenter.add(jScrollPane1, BorderLayout.CENTER);
        jPanelCenter.add(jPanel1, BorderLayout.NORTH);
        jPanel1.add(jLabel3, null);
        jScrollPane1.getViewport().add(jEditorPaneHTML, null);
        panelRoot.add(jPanelRootSouth, BorderLayout.SOUTH);
        jPanelRootSouth.add(jButtonOK, null);
        //this.setPage( "http://www.dn.se/" );
        URL url = ResourcesFactory.getFile("license.txt");
        if (url != null)
        {
            jEditorPaneHTML.setPage(url);
        }
        else
        {
            jEditorPaneHTML.setText("Iris  ");
        }
        this.setTitle("Help topics");
    }

    /**
     *  Method is responsible for
     *
     * @param  e  Description of Parameter
     */
    void jButtonOK_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

    /**
     *  Start a thread for retrieving the html page from the Internet. This process is time consuming
     *  and looks the application if no thread is used. If we are behind a proxy or in some way getting
     *  an exception we put in some default text in the panel.
     *
     * @param  url  The new {3} value
     * @author      Georges Polyzois
     */
    private void setPage(final String url)
    {
        Runnable run = new Runnable()
        {
            /**
             *  Main processing method for the AboutJDialog object
             */
            public void run()
            {
                try
                {
                    jEditorPaneHTML.setContentType("text/html");
                    jEditorPaneHTML.setEditable(false);
                    jEditorPaneHTML.setPage(url);
                }
                catch (Exception exc)
                {
                    jEditorPaneHTML.setText(
                        "<font size=\"3\">IRIS is free.<br><br>For information please email info@iris.org</font>");
                }
            }
        };
        Thread gethtml = new Thread(run);
        gethtml.start();
    }
}
