package org.iris.client.swing.panels.config;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.dialogs.*;
import org.jeditsyntax.*;

public class ServerConfigXMLPanel
    extends JPanel
{
    JEditTextArea xmlTextArea = new JEditTextArea();
    private String configxmlLocation;
    Logger myLogger = Logger.getLogger(ServerConfigXMLPanel.class.getName());
    JLabel jLabelImage = new JLabel();
    JPanel jPanel1 = new JPanel();
    TitledBorder titledBorder1;
    Border border1;
    TitledBorder titledBorder2;
    JLabel jLabel1 = new JLabel();
    JTextField jTextFieldConfigFilePath = new JTextField();
    GridBagLayout gridBagLayout1 = new GridBagLayout();

    //JXMLEditorFascade myXMLPanel = new JXMLEditorFascade( new Dimension(500,500), true, true );

    JPanel jPanelCenter = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    String xmlConfig;
    JPanel jPanelLeft = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel4 = new JPanel();

    public ServerConfigXMLPanel(String xmlData, String locationinfo)
    {
        xmlConfig = xmlData;
        configxmlLocation = locationinfo;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            myLogger.error(ex);
        }
    }

    void jbInit() throws Exception
    {
        // myXMLPanel.setXMLText( xmlConfig );
        xmlTextArea.setTokenMarker(new XMLTokenMarker());
        xmlTextArea.setText(xmlConfig);
        titledBorder1 = new TitledBorder("");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(border1, "Configuration file");
        this.setLayout(borderLayout2);

        jLabelImage.setText("");
        jLabelImage.setIcon(ResourcesFactory.getImageIcon("panellogging.gif"));

        jPanel1.setBorder(titledBorder2);
        jPanel1.setMinimumSize(new Dimension(200, 51));
        jPanel1.setPreferredSize(new Dimension(300, 51));
        jPanel1.setLayout(gridBagLayout1);
        jLabel1.setText("Server config file location");
        jTextFieldConfigFilePath.addActionListener(new ConfigXMLPanel_jTextFieldConfigFilePath_actionAdapter(this));
        jTextFieldConfigFilePath.setEnabled(true);
        jTextFieldConfigFilePath.setEditable(false);
        jTextFieldConfigFilePath.setText(configxmlLocation);

        jPanelCenter.setLayout(borderLayout1);
        jPanelLeft.setLayout(gridLayout1);
        this.add(jPanelCenter, BorderLayout.CENTER);
        jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 70, 7));
        jPanel1.add(jTextFieldConfigFilePath, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 249, 1));
        jPanelCenter.add(xmlTextArea, BorderLayout.CENTER);
        this.add(jPanelLeft, BorderLayout.WEST);
        jPanelLeft.add(jPanel4, null);
        jPanel4.add(jLabelImage, null);

        xmlTextArea.scrollTo(0, 0);
        //jScrollPane2.add(  );
        jPanelCenter.add(jPanel1, BorderLayout.NORTH);

//        jScrollPane1.getViewport().add(jEditorPane1, null);

    }

    public void reloadConfig(String config)
    {
        try
        {
//            myXMLPanel.setFile(new File(configxmlLocation));
            //configxmlLocation = System.getProperty("iris.initservices");
            myLogger.debug("Setting xml data");
            xmlTextArea.setText(config);
        }
        catch (Exception ex)
        {
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("Failed reading init file for Iris");
            dlg.setInformationText("No file found or error reading file at location : " + configxmlLocation);
            dlg.setStackTrace(ex);
            dlg.setVisible(true);
        }
    }

    void jTextFieldConfigFilePath_actionPerformed(ActionEvent e)
    {

    }

    public void actionPerformed(ActionEvent actionEvent)
    {

    }
}

class ConfigXMLPanel_jTextFieldConfigFilePath_actionAdapter
    implements java.awt.event.ActionListener
{
    ServerConfigXMLPanel adaptee;

    ConfigXMLPanel_jTextFieldConfigFilePath_actionAdapter(ServerConfigXMLPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jTextFieldConfigFilePath_actionPerformed(e);
    }
}