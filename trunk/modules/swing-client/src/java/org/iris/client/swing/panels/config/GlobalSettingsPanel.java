package org.iris.client.swing.panels.config;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import org.iris.client.settings.*;
import org.iris.client.swing.util.*;
import org.iris.server.util.config.*;

public class GlobalSettingsPanel
    extends JPanel
{
    JLabel jLabelImage = new JLabel();
    JPanel jPanelLogging = new JPanel();
    TitledBorder titledBorder1;
    JLabel jLabel1 = new JLabel();
    JTextField jTextFieldServerLocation = new JTextField();
    JLabel jLabel2 = new JLabel();
    JTextField jTextFieldServerPort = new JTextField();

    private static GlobalSettingsPanel myInstance;
    Border border1;
    TitledBorder titledBorder2;
    TitledBorder titledBorder3;
    TitledBorder titledBorder4;
    JPanel jPanelArchiving = new JPanel();
    TitledBorder titledBorder5;
    JLabel jLabel7 = new JLabel();
    JTextField jTextFieldArchiveIntervall = new JTextField();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel4 = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JPanel jPanelPing = new JPanel();
    JPanel jPanel5 = new JPanel();
    TitledBorder titledBorder6;
    TitledBorder titledBorder7;
    TitledBorder titledBorder8;
    Border border2;
    TitledBorder titledBorder9;
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel6 = new JPanel();
    JLabel jLabel4 = new JLabel();
    JTextField jTextFieldServicePingIntervall = new JTextField();

    public static GlobalSettingsPanel getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new GlobalSettingsPanel();
        }
        return myInstance;
    }

    private GlobalSettingsPanel()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception
    {
        titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                        "Error Message Resender");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                        "Logging Server");
        titledBorder3 = new TitledBorder("");
        titledBorder4 = new TitledBorder("");
        titledBorder5 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                        "Archive Handler");
        titledBorder6 = new TitledBorder("");
        titledBorder7 = new TitledBorder("");
        titledBorder8 = new TitledBorder(BorderFactory.createLineBorder(new Color(153, 153, 153), 2),
                        "Service Ping Intervall");
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder9 = new TitledBorder(border2, "Service Ping Intervall");
        jLabelImage.setText("");
        jLabelImage.setBounds(new Rectangle(1, 1, 72, 162));
        jLabelImage.setIcon(ResourcesFactory.getImageIcon("panellogging.gif"));

        this.setLayout(null);
        jPanelLogging.setBorder(titledBorder2);
        jPanelLogging.setLayout(null);
        jLabel1.setText("Server location");
        jTextFieldServerLocation.setText("");
        jLabel2.setText("Server port");
        jTextFieldServerPort.setText("");
        jPanelArchiving.setBorder(titledBorder5);
        jPanelArchiving.setDebugGraphicsOptions(0);
        jPanelArchiving.setLayout(null);
        jLabel7.setText("Archive intervall [cron]");
        jLabel7.setBounds(new Rectangle(12, 23, 226, 23));
        jTextFieldArchiveIntervall.setBounds(new Rectangle(268, 23, 195, 22));
        jTabbedPane1.setBounds(new Rectangle(78, 5, 557, 143));
        jPanel4.setLayout(gridBagLayout1);
        jPanel4.setBounds(new Rectangle(8, 21, 525, 71));
        jPanelPing.setLayout(borderLayout1);
        jPanel5.setBorder(titledBorder9);
        jPanel5.setLayout(null);
        jPanel6.setBounds(new Rectangle(7, 20, 335, 38));
        jPanel6.setLayout(null);
        jLabel4.setText("Service Ping Intervall [ms]");
        jLabel4.setBounds(new Rectangle(6, 2, 159, 15));
        jTextFieldServicePingIntervall.setText("");
        jTextFieldServicePingIntervall.setBounds(new Rectangle(166, 0, 115, 20));
        this.add(jLabelImage, null);
        this.add(jTabbedPane1, null);
        jPanel4.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 4, 0, 0), 105, 7));
        jPanel4.add(jTextFieldServerLocation, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 105, 0, 48), 223, 3));
        jPanel4.add(jTextFieldServerPort, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 105, 0, 48), 223, 0));
        jPanel4.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 4, 0, 20), 103, 0));
        jTabbedPane1.add(jPanelPing, "Ping Intervall");
        jPanelPing.add(jPanel5, BorderLayout.CENTER);
        jTabbedPane1.add(jPanelArchiving, "Archiving");
        jPanelArchiving.add(jLabel7, null);
        jPanelArchiving.add(jTextFieldArchiveIntervall, null);
        jTabbedPane1.add(jPanelLogging, "Logging");
        jPanelLogging.add(jPanel4, null);
        jPanel6.add(jTextFieldServicePingIntervall, null);
        jPanel6.add(jLabel4, null);
        jPanel5.add(jPanel6, null);
    }

    public void init()
    {
        jTextFieldServicePingIntervall.setText(String.valueOf(SettingsFascade.getInstance().getIrisclientSettings().
            getPingservices()));
        jTextFieldServerLocation.setText(String.valueOf(SettingsFascade.getInstance().getConnectedToServer().getAddress()));
        jTextFieldServerPort.setText(String.valueOf(SettingsFascade.getInstance().getConnectedToServer().getLoggingport()));
        //todo      jTextFieldArchiveIntervall.setText( ClientSystemConfigHandler.getInstance().getGlobalSettings().getArchivehandler().getCronintervall());

    }

    public GlobalSettings getPanelSetting()
    {
        GlobalSettings settings = new GlobalSettings();
        settings.setLogging_sockethub(jTextFieldServerLocation.getText());
        settings.setLogging_port(jTextFieldServerPort.getText());
        return settings;
    }

    public JTextField getJTextFieldServerLocation()
    {
        return jTextFieldServerLocation;
    }

    public JTextField getJTextFieldServerPort()
    {
        return jTextFieldServerPort;
    }

}
