package org.iris.client.swing.panels.wizards.cloneservice;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.*;
import org.iris.client.swing.panels.*;
import org.iris.client.swing.panels.logging.*;
import org.iris.client.swing.panels.transform.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.events.*;
import org.iris.server.util.config.*;

public class CloneServicePanel
    extends JPanel
{
    static Logger myLogger = Logger.getLogger(CloneServicePanel.class.getName());

    JPanel jPanel1 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JTextField jTextFieldServiceName = new JTextField();
    Border border1;
    TitledBorder titledBorder1;
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel imagejLabel = new JLabel();
    InactivityPanel myInactivityPanel = new InactivityPanel();
    ArchivePanel myArchivePanel = new ArchivePanel();
    ErrorPanel myErrorPanel = new ErrorPanel();
    BackupPanel myBackupPanel = new BackupPanel();
    ServiceLoggingPanel myLoggingPanel = new ServiceLoggingPanel();
    TransformPanel myTransformPanel = new TransformPanel();

    JPanel jPanel2 = new JPanel();
    JTabbedPane jTabbedPane = new JTabbedPane();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanelButtons = new JPanel();
    JButton jButtonCancel = new JButton();
    JButton jButtonOk = new JButton();
    JButton jButtonInactivityNext = new JButton();
    JButton jButtonBack = new JButton();
    InOutPanel myInOutPanel = new InOutPanel();
    InOutEJBPanel myInOutEJBPanel = new InOutEJBPanel();
    String preclonedServiceId;
    JPanel jPanelServiceName = new JPanel();
    JPanel jPanelGif = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    GridBagLayout gridBagLayout1 = new GridBagLayout();

    public void initPanel(Service service)
    {
        myInactivityPanel.initPanel(service, true);
        myArchivePanel.init(service, true);
        myBackupPanel.initPanel(service, true);
        myErrorPanel.initPanel(service, true);
        myLoggingPanel.initPanel(service, true);

        if (service instanceof ServiceFileFile)
        {
            myInOutPanel.initPanel(service, true);
        }
        else if (service instanceof ServiceFileEJB)
        {
            myInOutEJBPanel.initPanel(service, true);
        }
    }

    public CloneServicePanel(Service service)
    {
        this();
        preclonedServiceId = service.getId();
        if (service instanceof ServiceFileFile)
        {
            jTabbedPane.insertTab(myInOutPanel.getTabTitle(), myInOutPanel.getTabIcon(), myInOutPanel, "", 1);
        }
        else if (service instanceof ServiceFileEJB)
        {
            jTabbedPane.insertTab(myInOutEJBPanel.getTabTitle(), myInOutEJBPanel.getTabIcon(), myInOutEJBPanel, "", 1);
        }

    }

    public CloneServicePanel()
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
        imagejLabel.setText("");
        imagejLabel.setBounds(new Rectangle(0, 0, 73, 139));
        imagejLabel.setIcon(ResourcesFactory.getImageIcon("panelarchivinganderrors.gif"));
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder1 = new TitledBorder(border1, "Service Name");
        this.setDebugGraphicsOptions(0);
        this.setLayout(borderLayout2);
        jPanel1.setBorder(titledBorder1);
        jPanel1.setBounds(new Rectangle(1, 44, 540, 72));
        jPanel1.setLayout(gridBagLayout1);
        jLabel1.setText("Select name of new cloned service");
        jTextFieldServiceName.setFont(new java.awt.Font("Dialog", 1, 12));
        jTextFieldServiceName.setText("");
        jTextFieldServiceName.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
                jTextFieldServiceName_focusLost(e);
            }
        });
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 11));
        jLabel2.setText("Service Name");
        jLabel2.setBounds(new Rectangle(1, 5, 224, 15));
        jLabel3.setRequestFocusEnabled(true);
        jLabel3.setText("Service name identifies a worker thread in the application that will " +
            "launch and serve according to the settings.");
        jLabel3.setBounds(new Rectangle(1, 29, 523, 15));
        jPanel2.setLayout(borderLayout1);
        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButtonCancel_actionPerformed(e);
            }
        });
        jButtonOk.setText("OK");
        jButtonOk.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButtonOk_actionPerformed(e);
            }
        });
        myInactivityPanel.setEnabled(false);
        jButtonInactivityNext.setText(">>");
        jButtonInactivityNext.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButtonInactivityNext_actionPerformed(e);
            }
        });

        jButtonBack.setText("<<");
        jButtonBack.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButtonBack_actionPerformed(e);
            }
        });
        //jPanel3.setLayout(null);
        jPanelServiceName.setLayout(null);
        jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets( -1, 2, 22, 0), 64, 9));
        jPanel1.add(jTextFieldServiceName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets( -1, 21, 22, 26), 240, -1));
        jPanelServiceName.add(jLabel2, null);
        jPanel2.add(jPanelGif, BorderLayout.WEST);
        jPanelGif.add(imagejLabel, null);
        jPanelServiceName.add(jLabel3, null);
        jPanelServiceName.add(jPanel1, null);
        jPanel2.add(jPanelServiceName, BorderLayout.CENTER);
        this.add(jPanelButtons, BorderLayout.SOUTH);
        jPanelButtons.add(jButtonBack, null);
        jPanelButtons.add(jButtonInactivityNext, null);
        jPanelButtons.add(jButtonCancel, null);
        jPanelButtons.add(jButtonOk, null);
        this.add(jTabbedPane, BorderLayout.CENTER);

        jTabbedPane.add(jPanel2, "Name");
        jTabbedPane.addTab(myLoggingPanel.getTabTitle(), myLoggingPanel.getTabIcon(), myLoggingPanel);
        jTabbedPane.addTab(myArchivePanel.getTabTitle(), myArchivePanel.getTabIcon(), myArchivePanel);
        jTabbedPane.addTab(myBackupPanel.getTabTitle(), myBackupPanel.getTabIcon(), myBackupPanel);
        jTabbedPane.addTab(myErrorPanel.getTabTitle(), myErrorPanel.getTabIcon(), myErrorPanel);
        jTabbedPane.addTab(myInactivityPanel.getTabTitle(), myInactivityPanel.getTabIcon(), myInactivityPanel);
        jTabbedPane.addTab(myTransformPanel.getTabTitle(), myTransformPanel.getTabIcon(), myTransformPanel);

    }

    void jButtonInactivityNext_actionPerformed(ActionEvent e)
    {
        if (jTabbedPane.getSelectedIndex() < (jTabbedPane.getTabCount() - 1))
        {
            jTabbedPane.setSelectedIndex(jTabbedPane.getSelectedIndex() + 1);
        }
    }

    void jButtonBack_actionPerformed(ActionEvent e)
    {
        if (jTabbedPane.getSelectedIndex() > 0)
        {
            jTabbedPane.setSelectedIndex(jTabbedPane.getSelectedIndex() - 1);
        }

    }

    /**
     * Hide JFrame holding this panel
     *
     * @param e
     */
    void jButtonCancel_actionPerformed(ActionEvent e)
    {
        getTopLevelAncestor().setVisible(false);
    }

    void jTextFieldServiceName_focusLost(FocusEvent e)
    {
        String newServiceId = jTextFieldServiceName.getText();
        if (newServiceId != null && !newServiceId.equals(""))
        {

            myInactivityPanel.changeServiceId(newServiceId, preclonedServiceId);
            myArchivePanel.changeServiceId(newServiceId, preclonedServiceId);
            myBackupPanel.changeServiceId(newServiceId, preclonedServiceId);
            myErrorPanel.changeServiceId(newServiceId, preclonedServiceId);
            myInOutPanel.changeServiceId(newServiceId, preclonedServiceId);
            // in case user changes id again
            preclonedServiceId = newServiceId;
        }
    }

    void jButtonOk_actionPerformed(ActionEvent e)
    {
        ApplicationEventHandler.getInstance().fireDataChanged(new ApplicationStateEvent(this, "Clone service id",
            ApplicationStateEvent.CLONINGSERVICEONREMOTESERVER));

    }

}
