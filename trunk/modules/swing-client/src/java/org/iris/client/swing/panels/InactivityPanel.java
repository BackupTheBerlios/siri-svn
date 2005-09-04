package org.iris.client.swing.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.textfield.*;
import org.iris.server.util.config.*;

/**
 * Panel holds info about inactivity settings.
 *
 * @author not attributable
 * @version 1.0
 */
public class InactivityPanel
    extends IrisPanel implements IrisPanelOperations
{
    static Logger myLogger = Logger.getLogger(InactivityPanel.class.getName());

    JPanel jPanel2 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JTextField myjTextFieldInactivityTimeMS = new JTextField();
    JLabel jLabel2 = new JLabel();
    JTextField myjTextFieldmailPath = new JTextField();
    JLabel imagejLabel = new JLabel();
    Border border1;
    TitledBorder titledBorder1;
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();

    private static final String PANEL_IMAGE = "panelinactivity.gif";
    private static final String TAB_IMAGE = "panelinactivity_tab.gif";
    private static final String TAB_TITLE = "Inactivity";

    MultiLineLabel jLabel3 = new MultiLineLabel("If on, alarms will go off if this service is idle more than maximum idle time. An email will be sent accroding to the email (xml) file specified for this service.");

    public InactivityPanel()
    {
        tabtitle = TAB_TITLE;
        tabIcon = ResourcesFactory.getImageIcon(TAB_IMAGE);

        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void initPanel(Service service, boolean setFieldsEditable)
    {
        setFieldsToEditable(setFieldsEditable);
        if (service == null)
        {
            myLogger.warn("Service object was null. Could not initialize properly");
            return;
        }

        this.myjTextFieldInactivityTimeMS.setText(String.valueOf(service.getMaxInactivitytime()));
        this.myjTextFieldmailPath.setText(service.getEmailonincativity());
    }

    public void setFieldsToEditable(boolean setFieldsEditable)
    {
        setComponentsEnabled(new JComponent[]
            {
            myjTextFieldmailPath, myjTextFieldInactivityTimeMS}, setFieldsEditable);
        // this.myjTextFieldmailPath.setEditable( setFieldsEditable );
        // this.myjTextFieldInactivityTimeMS.setEditable( setFieldsEditable );
    }

    void jbInit() throws Exception
    {
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder1 = new TitledBorder(border1, "Incativity Settings");
        imagejLabel.setText("");
        imagejLabel.setIcon(ResourcesFactory.getImageIcon(PANEL_IMAGE));

        this.setLayout(borderLayout2);
        jPanel2.setBorder(titledBorder1);
        jPanel2.setDebugGraphicsOptions(0);
        jPanel2.setLayout(gridBagLayout1);
        jLabel1.setText("Maximum inactivity allowed before email alarm sent  [ms]");
        myjTextFieldInactivityTimeMS.setMinimumSize(new Dimension(40, 20));
        myjTextFieldInactivityTimeMS.setPreferredSize(new Dimension(40, 20));
        myjTextFieldInactivityTimeMS.setDisabledTextColor(Color.black);
        myjTextFieldInactivityTimeMS.setText("");
        jLabel2.setText("Email path");
        myjTextFieldmailPath.setDisabledTextColor(Color.black);
        myjTextFieldmailPath.setText("");
        jPanel3.setLayout(borderLayout1);

        jPanel2.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(2, 10, 0, 0), 2, 12));
        jPanel2.add(myjTextFieldInactivityTimeMS, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(2, 21, 0, 100), 72, 2));
        jPanel2.add(myjTextFieldmailPath, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 21, 17, 0), 276, 0));
        jPanel2.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 17, 0), 228, 7));
        jPanel2.add(jLabel3, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 10, 0), 0, 0));
        this.add(jPanel1, BorderLayout.WEST);
        jPanel1.add(imagejLabel, null);
        this.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jPanel2, BorderLayout.NORTH);
    }

    public void changeServiceId(String newServiceId, String oldServiceId)
    {
        changeServiceId(myjTextFieldmailPath, newServiceId, oldServiceId);
    }

}
