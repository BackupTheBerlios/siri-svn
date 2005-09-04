package org.iris.client;

import java.rmi.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import org.apache.log4j.*;
import org.iris.client.settings.*;
import org.iris.client.settings.xml.*;
import org.iris.client.swing.panels.config.*;
import org.iris.client.swing.panels.location.*;
import org.iris.client.swing.panels.logging.*;
import org.iris.client.swing.panels.tree.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.dialogs.*;
import org.iris.client.swing.util.events.*;
import org.iris.client.swing.util.statuspanel.*;
import org.iris.client.swing.util.windows.*;
import org.iris.server.util.config.*;

public class MainFrame
    extends JFrame implements ActionListener
{
    private static Logger myLogger = Logger.getLogger(MainFrame.class.getName());
    private ApplicationEventDispatcher myApplicationEventDispatcher;

    private HashMap servicePanels = new HashMap();
    static final int windowHeight = 800;
    static final int leftWidth = 150;
    static final int rightWidth = 800;
    static final int windowWidth = leftWidth + rightWidth;

    private JScrollPane jScrollPane1 = new JScrollPane();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JScrollPane mainFrameLeftScrollPane = new JScrollPane();

    private TreePanel myTreePanel;
    private JScrollPane mainFrameRightScrollPane = new JScrollPane();
    private JPanel mainFrameRightPanel = new JPanel();
    private MainFrameMenuBar myMenuBar = MainFrameMenuBar.getInstance();
    private MainFrameToolbar myToolBar = MainFrameToolbar.getInstance();
    private static MainFrame myInstance;
    private BorderLayout borderLayout2 = new BorderLayout();
    private final static String PROPERTYFILE = "irisclient";
    private HashMap myServicesMap;
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout4 = new BorderLayout();
    JLabel jLabel1 = new JLabel();
    StatusJPanel myStatusJPanel = StatusJPanel.getInstance();
    static boolean propertiesInitialized;

    JLabel jLabelImage = new JLabel();

    IrisserverType myConnectedServer;
    ServerConfigXMLPanel myServerConfigXMLPanel;
    ClientConfigXMLPanel myClientConfigXMLPanel;

    public MainFrame()
    {
        try
        {
            // Read setting from xml file
            SettingsFascade.getInstance();
            myClientConfigXMLPanel = new ClientConfigXMLPanel(SettingsFascade.getInstance().
                                     getIrisclientSettingsAsString(),
                                     SettingsFascade.getInstance().getDefaultSettingsFile().toString()); //, System.getProperty("iris.client.settings") );
            myLogger.debug("Settings for client found and set");
            myLogger.debug(SettingsFascade.getInstance().getIrisclientSettingsAsString());
        }
        catch (Exception ex1)
        {
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("No settings file found");
            dlg.setInformationText("Could not locate " +
                SettingsFascade.getInstance().getDefaultSettingsFile().toString());
            dlg.setStackTrace(ex1);
            dlg.setVisible(true);
            System.exit(0);
        }

        // Pop up server selection panel and let user select server to connect to
        LocationDialog ldlg = new LocationDialog(new JFrame(), "Select location of Iris server", true,
                              SettingsFascade.getInstance().getIrisclientSettings());
        ldlg.setVisible(true);
        myConnectedServer = ldlg.getSelectedServier();
        SettingsFascade.getInstance().setConnectedToServer(myConnectedServer);

        // Look up remote server
        myApplicationEventDispatcher = new ApplicationEventDispatcher(this);

        try
        {

            myLogger.debug("Fetching server settings: ");
            String xmlForServerSettings = myApplicationEventDispatcher.getIrisServer().getConfig();
            myLogger.debug("Got server settings: ");
            ClientSystemConfigHandler.getInstance().initUsingXml(xmlForServerSettings);

            myLogger.debug("Initializing tree panel ");
            myTreePanel = TreePanel.getInstance(ClientSystemConfigHandler.getInstance().getServices(false));
            myLogger.debug("Initialized tree panel ");
            myServerConfigXMLPanel = new ServerConfigXMLPanel(xmlForServerSettings, myConnectedServer.getRmiservice());

        }
        catch (RemoteException ex)
        {
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("No settings could be retrieved from iris server or the settings could not be parsed correctly. \nOpen log file under the log dir and look.\nClient will exit!!!");
            dlg.setInformationText("No settings could be retrieved from iris server");
            dlg.setStackTrace(ex);
            dlg.setVisible(true);
            myLogger.error(ex);

            System.exit(0);
        }
        catch (Exception ex)
        {
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("No settings could be retrieved from iris server");
            dlg.setInformationText("No settings could be retrieved from iris server");
            dlg.setStackTrace(ex);
            dlg.setVisible(true);
            myLogger.error(ex);
            System.exit(0);
        }

        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        myMenuBar.addActionListener(this);

        setCentered();

//        ArrayList services = SystemConfigurator.getServices();
        ArrayList services = ClientSystemConfigHandler.getInstance().getServices();
        Iterator iter = services.iterator();
        myServicesMap = new HashMap();
        while (iter.hasNext())
        {
            Service ser = (Service) iter.next();
            myServicesMap.put(ser.getId(), ser);
        }

//        myApplicationEventDispatcher = new ApplicationEventDispatcher(this);


        // VerifyRemoteServices pinger = new VerifyRemoteServices( SettingsFascade.getInstance().getIrisclientSettings().getPingservices() );

    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals(ApplicationStateEvent.STR_EXIT_APPLICATION))
        {
            onExitApplication();
        }
    }

    public static void main(String[] args)
    {
        //MainFrame.setLookandFeel();
        StartUpScreenWindow win = new StartUpScreenWindow();
        win.setVisible(true);
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.setSize(new Dimension(windowWidth, windowHeight));
        win.setVisible(false);
        mainFrame.setVisible(true);

    }

    /**
     * Initialize gui settings
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception
    {
        myStatusJPanel.setStatusText("Init");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(new Dimension(windowWidth, windowHeight));
        this.getContentPane().setLayout(borderLayout1);
        jLabelImage.setBackground(Color.white);
        jLabelImage.setForeground(Color.white);
        jLabelImage.setToolTipText("");
        jLabelImage.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelImage.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabelImage.setText("");
        jLabelImage.setVerticalAlignment(SwingConstants.CENTER);
        jLabelImage.setVerticalTextPosition(SwingConstants.CENTER);
        mainFrameRightPanel.setBackground(Color.white);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainFrameRightScrollPane.setMaximumSize(new Dimension(500, 500));
        this.getContentPane().add(jSplitPane1, BorderLayout.CENTER);
        this.getContentPane().add(myStatusJPanel, BorderLayout.SOUTH);
        mainFrameRightPanel.setLayout(borderLayout2);
        jPanel1.setLayout(borderLayout4);
        mainFrameRightScrollPane.setBorder(BorderFactory.createRaisedBevelBorder());
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setPreferredSize(new Dimension(windowWidth, windowHeight));
        jSplitPane1.add(mainFrameLeftScrollPane, JSplitPane.LEFT);
        jSplitPane1.add(mainFrameRightScrollPane, JSplitPane.RIGHT);
        mainFrameRightPanel.add(GlobalLoggingPanel.getInstance(myConnectedServer.getAddress(),
            myConnectedServer.getLoggingport()), BorderLayout.CENTER);
        mainFrameRightScrollPane.getViewport().add(mainFrameRightPanel, null);
        mainFrameLeftScrollPane.getViewport().add(myTreePanel, null);

        setFrameIcon(this);

        this.setJMenuBar(myMenuBar);

        this.addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                this_windowClosing(e);
            }
        });
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

    void this_windowClosing(WindowEvent e)
    {
        onExitApplication();
    }

    private void setFrameIcon(JFrame frame)
    {
        try
        {
            ImageIcon icon = ResourcesFactory.getImageIcon("frameicon.gif");
            frame.setIconImage(icon.getImage());
            frame.setTitle(SettingsFascade.getInstance().getIrisclientSettings().getTitle());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static MainFrame getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new MainFrame();
        }
        return myInstance;
    }

    public void onExitApplication()
    {
        int selectVal = JOptionPane.showConfirmDialog(null, "Do you want to exit application?", "Exit",
                        JOptionPane.YES_NO_OPTION);
        if (JOptionPane.OK_OPTION == selectVal)
        {
            System.exit(0);
        }
    }

    public MainFrameToolbar getToolBar()
    {
        return myToolBar;
    }

    public void setToolBar(JToolBar newToolBar)
    {
        this.getContentPane().remove(myToolBar.getMyJToolBar());
        this.getContentPane().invalidate();
        this.getContentPane().add(newToolBar, BorderLayout.NORTH);
        this.getContentPane().validate();
        this.repaint();
    }

    public MainFrameMenuBar getMyMenuBar()
    {
        return myMenuBar;
    }

    public void setMyMenuBar(JMenuBar newMenuBar)
    {
        this.getContentPane().remove(myMenuBar);
        this.setJMenuBar(newMenuBar);
        this.repaint();

    }

    public JPanel getMainFrameRightPanel()
    {
        return mainFrameRightPanel;
    }

    public void setMainFrameRightPanel(JPanel mainFrameRightPanel)
    {
        this.mainFrameRightPanel = mainFrameRightPanel;

        //this.getContentPane().setC
    }

    public HashMap getServicePanels()
    {
        return servicePanels;
    }

    public void setServicePanels(HashMap servicePanels)
    {
        this.servicePanels = servicePanels;
    }

    public StatusJPanel getMyStatusJPanel()
    {
        return myStatusJPanel;
    }

    public void setMyStatusJPanel(StatusJPanel myStatusJPanel)
    {
        this.myStatusJPanel = myStatusJPanel;
    }

    public HashMap getMyServicesMap()
    {
        return myServicesMap;
    }

    {

        SettingsFascade.getInstance();

        loadLog4jSettings();

        loadIrisProperties();

        //   setLookandFeel(); //done from window splash screen
    }

    private static void loadLog4jSettings()
    {
        // Load log4j settings
        ResourceBundle rb = null;
        try
        {
            rb = ResourceBundle.getBundle("log4j");
            Enumeration proplist = rb.getKeys();
            proplist = rb.getKeys();
            Properties props = new Properties();
            while (proplist.hasMoreElements())
            {
                String tKey = (String) proplist.nextElement();
                props.setProperty(tKey, rb.getString(tKey));
            }
            PropertyConfigurator.configure(props);
        }
        catch (MissingResourceException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Loading properties from xml file instead...
     *
     * @deprecated
     */
    public static void loadIrisProperties()
    {
        ResourceBundle rb;
        try
        {
            rb = ResourceBundle.getBundle(PROPERTYFILE);
            Enumeration proplist = rb.getKeys();
            while (proplist.hasMoreElements())
            {
                String tKey = (String) proplist.nextElement();
                System.setProperty(tKey, rb.getString(tKey));
            }
            propertiesInitialized = true;
        }
        catch (MissingResourceException e)
        {
            System.out.println("Missing resource : Iris");
            e.printStackTrace();
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("No property file found");
            dlg.setInformationText("Could not locate " + PROPERTYFILE + ".properties");
            dlg.setStackTrace(e);
            dlg.setVisible(true);
            System.exit(0);
        }
    }

    /*
        public static void setLookandFeel()
        {

            // Try setting the Kunstoff look and feel
            try
            {
                List lookandfeel = SettingsFascade.getInstance().getIrisclientSettings().getLookandfeel();
                Iterator iter = lookandfeel.iterator();
                LookandfeelType lof = null;
                while (iter.hasNext())
                {
                    lof = (LookandfeelType) iter.next();
                }

                myLogger.info( "Settings: use lookandfeel " + lof.getType() );
                if ( lof.getType().equals( "aqua" )  )
                {
                    String thempack = "aquathemepack-1.0.0.jar"; //"
                    Skin    theSkinToUse = SkinLookAndFeel.loadThemePack( lof.getLookandfeelthemepath()  + thempack);
                    SkinLookAndFeel.setSkin(theSkinToUse);
                    UIManager.setLookAndFeel(new SkinLookAndFeel());
                    myLogger.info( "Aqua was set" );
                }
                else if ( lof.getType().equalsIgnoreCase( "kunststoff" )  )
                {
                    myLogger.info( "Kuhnststoff was set" );
                    UIManager.setLookAndFeel(new com.incors.plaf.kunststoff.KunststoffLookAndFeel());
                }
                else if ( lof.getType().equals( "themepack" )  )
                {
                    myLogger.info( "Theme was set" );
                    String thempack = "themepack-1.2.8.jar";
                    Skin    theSkinToUse = SkinLookAndFeel.loadThemePack( lof.getLookandfeelthemepath()  + thempack);
                    SkinLookAndFeel.setSkin(theSkinToUse);
                    UIManager.setLookAndFeel(new SkinLookAndFeel());
                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                myLogger.warn( "Failed setting lookandfeel according to settings - defaulting to kuhnststoff" );
                try
                {
                    // default to kunstoff if aqua fails
                    UIManager.setLookAndFeel(new com.incors.plaf.kunststoff.KunststoffLookAndFeel());
                }
                catch (UnsupportedLookAndFeelException e)
                {
                    e.printStackTrace();
                }

            }
        }
     */

    public IrisserverType getConnectedServer()
    {
        return myConnectedServer;
    }

    public ServerConfigXMLPanel getServerConfigXMLPanel()
    {
        return myServerConfigXMLPanel;
    }

    public ClientConfigXMLPanel getClientConfigXMLPanel()
    {
        return myClientConfigXMLPanel;
    }
}
