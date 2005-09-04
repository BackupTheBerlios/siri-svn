package org.iris.client.swing.panels.config;

import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.iris.client.settings.*;
import org.iris.client.swing.util.dialogs.*;
import com.jxmleditor.*;

public class SettingFileChooser
    extends JFrame
{
    BorderLayout borderLayout1 = new BorderLayout();
    JFileChooser jFileChooser1;
    BorderLayout borderLayout2 = new BorderLayout();
    JXMLEditorFascade myXMLPanel = new JXMLEditorFascade(new Dimension(300, 200), true, true);
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanelCenter = new JPanel();
    JPanel jPanelSouth = new JPanel();
    BorderLayout borderLayout4 = new BorderLayout();
    String xmlSettings;

    public SettingFileChooser(String xmlSettings, File saveToFile)
    {
        this.xmlSettings = xmlSettings;

        jFileChooser1 = new JFileChooser(saveToFile);
        jFileChooser1.setCurrentDirectory(saveToFile);
        jFileChooser1.setApproveButtonText("Save");
        jFileChooser1.setApproveButtonToolTipText("This will overwrite existing settings and store a backup file");

        SettingsFileFilter filter = new SettingsFileFilter("xml", "Iris settings file extension");

        jFileChooser1.addChoosableFileFilter(filter);

        try
        {
            jbInit();

            setCentered();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        /*
                int returnVal = jFileChooser1.s
                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    System.out.println("You chose to open this file: ");
                }
         */
    }

    void jbInit() throws Exception
    {
        this.getContentPane().setLayout(borderLayout3);
        jPanelCenter.setLayout(borderLayout4);
        jFileChooser1.addActionListener(new SettingFileChooser_jFileChooser1_actionAdapter(this));
        this.getContentPane().add(jPanelCenter, BorderLayout.CENTER);
        this.getContentPane().add(jPanelSouth, BorderLayout.EAST);

        System.out.println(myXMLPanel);
        myXMLPanel.setXMLText(xmlSettings);
        jPanelCenter.add(myXMLPanel, BorderLayout.NORTH);
        jPanelSouth.add(jFileChooser1);

        this.setSize(new Dimension(700, 400));

    }

    public static void main(String[] args)
    {
        String xml = "	    <global-settings>" + "<logging>" + "  <socket-hub>" + "     <address>localhost</address>" +
                     "      <port>4560</port>" + "       <reconnect-delay>30000</reconnect-delay>" +
                     "    </socket-hub>" + " </logging>" + "  <service-archive name=\"archivehandler\" >" +
                     "       <archiverintervall>600000</archiverintervall>" + "    </service-archive>" +
                     " </global-settings>";
        SettingFileChooser chooser = new SettingFileChooser(xml, new File("c:\test.xml"));
        chooser.setVisible(true);

    }

    public void setText(String text)
    {
        try
        {
            //         myXMLPanel.setXMLText(text);
        }
        catch (Exception ex)
        {
        }
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

    void jFileChooser1_actionPerformed(ActionEvent e)
    {
        System.out.println("" + jFileChooser1.getSelectedFile());

        if (e.getActionCommand().equalsIgnoreCase(jFileChooser1.APPROVE_SELECTION) && (jFileChooser1.getSelectedFile() != null))
        {
            System.out.println("" + jFileChooser1.getSelectedFile());
            try
            {
                SettingsFascade.getInstance().storeCurrentIrisclientSettings();
            }
            catch (Exception ex)
            {
                InformationDialog dlg = new InformationDialog();
                dlg.setTitle("Failed save");
                dlg.setInformationText("Settings could not be stored");
                dlg.setStackTrace(ex);
                dlg.setVisible(true);

            }
            this.dispose();
            this.setVisible(false);

        }
        else if (e.getActionCommand().equalsIgnoreCase(jFileChooser1.CANCEL_SELECTION))
        {
            this.dispose();
            this.setVisible(false);
        }

    }

}

class SettingFileChooser_jFileChooser1_actionAdapter
    implements java.awt.event.ActionListener
{
    SettingFileChooser adaptee;

    SettingFileChooser_jFileChooser1_actionAdapter(SettingFileChooser adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jFileChooser1_actionPerformed(e);
    }
}