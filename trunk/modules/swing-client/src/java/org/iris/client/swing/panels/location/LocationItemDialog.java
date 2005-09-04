package org.iris.client.swing.panels.location;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.iris.client.settings.xml.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.dialogs.*;
import org.iris.client.swing.util.jmaxtextfield.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Tradevision AB</p>
 * @author not attributable
 * @version 1.0
 */

public class LocationItemDialog
    extends JDialog
{
    JPanel panel1 = new JPanel();
    JPanel jPanel1 = new JPanel();
    TitledBorder titledBorder1;
    TitledBorder titledBorder2;
    JTextField jTextFieldName = new JTextField();
    JTextField jTextFieldDescription = new JTextField();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JTextField jTextFieldRMIService = new JTextField();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
//    JTextField jTextFieldLoggingPort = new JTextField();
    VerfifyTextField jTextFieldLoggingPort = new VerfifyTextField(8, VerfifyTextField.VERIFYNUMERIC);

    JTextField jTextFieldAddress = new JTextField();
    JPanel jPanel2 = new JPanel();
    JButton jButtonOk = new JButton();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    Border border1;
    TitledBorder titledBorder3;
    LocationDialog myCaller;
    GridBagLayout gridBagLayout2 = new GridBagLayout();

    public LocationItemDialog(Frame frame, String title, boolean modal, LocationDialog caller)
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
        myCaller = caller;

        setCentered();
    }

    public LocationItemDialog()
    {
        this(null, "", false, null);
    }

    public void init(LocationItem item)
    {
        jTextFieldAddress.setText(item.getServerinfo().getAddress());
        jTextFieldDescription.setText(item.getServerinfo().getDescription());
        jTextFieldLoggingPort.setText(Short.toString(item.getServerinfo().getLoggingport()));
        jTextFieldName.setText(item.getServerinfo().getName());
        jTextFieldRMIService.setText(item.getServerinfo().getRmiservice());

    }

    private void setCentered()
    {
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

    private void jbInit() throws Exception
    {
        titledBorder1 = new TitledBorder("");
        titledBorder2 = new TitledBorder(BorderFactory.createLineBorder(new Color(153, 153, 153), 2),
                        "Server Information");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(142, 142, 142));
        titledBorder3 = new TitledBorder(border1, "New Server");
        panel1.setLayout(null);
        jPanel1.setBorder(titledBorder3);
        jPanel1.setBounds(new Rectangle(27, 31, 430, 278));
        jPanel1.setLayout(gridBagLayout1);
        jTextFieldName.setText("");
        jTextFieldDescription.setText("");
        jLabel1.setText("Name");
        jLabel2.setText("Description");
        jLabel3.setText("RMI service");
        jTextFieldRMIService.setText("");
        jLabel4.setText("Logging address");
        jLabel5.setText("Logging port");
        jTextFieldLoggingPort.setText("4560");
        jTextFieldAddress.setText("");
        jButtonOk.setSelected(true);
        jButtonOk.setText("OK");
        jButtonOk.addActionListener(new LocationItemDialog_jButtonOk_actionAdapter(this));
        jPanel2.setLayout(gridBagLayout2);
        jPanel2.setDebugGraphicsOptions(0);
        jPanel2.setBounds(new Rectangle(27, 316, 430, 109));
        getContentPane().add(panel1);
        panel1.add(jPanel1, null);
        jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(8, 10, 0, 251), 124, 0));
        jPanel1.add(jTextFieldName, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 119), 285, 4));
        jPanel1.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 0, 251), 92, 4));
        jPanel1.add(jTextFieldDescription, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 119), 285, 2));
        jPanel1.add(jLabel3, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 0, 251), 91, 0));
        jPanel1.add(jTextFieldRMIService, new GridBagConstraints(0, 5, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 119), 285, 0));
        jPanel1.add(jLabel4, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(8, 10, 0, 251), 62, 0));
        jPanel1.add(jTextFieldAddress, new GridBagConstraints(0, 7, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(8, 10, 0, 119), 285, 0));
        jPanel1.add(jLabel5, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(7, 10, 0, 251), 86, 0));
        jPanel1.add(jTextFieldLoggingPort, new GridBagConstraints(0, 9, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, new Insets(9, 10, 6, 342), 34, 0));
        panel1.add(jPanel2, null);
        jPanel2.add(jButtonOk, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
            GridBagConstraints.NONE, new Insets(31, 144, 0, 0), 0, 0));
        jButtonOk.setSelected(true);
        jButtonOk.requestFocus();
    }

    void jButtonOk_actionPerformed(ActionEvent e)
    {
        LocationItem item = new LocationItem();
        item.setIcon(ResourcesFactory.getImageIcon("greycirclewithletterL.gif"));
        item.setDescription(jTextFieldDescription.getText());
        item.setUrl(jTextFieldRMIService.getText());

        ObjectFactory objFac = new ObjectFactory();
        try
        {
            IrisserverType server = objFac.createIrisserverType();
            server.setAddress(jTextFieldAddress.getText());
            server.setDescription(jTextFieldDescription.getText());
            server.setLoggingport( (Short.valueOf(jTextFieldLoggingPort.getText())).shortValue());
            server.setName(jTextFieldName.getText());
            server.setRmiservice(jTextFieldRMIService.getText());
            item.setServerinfo(server);

        }
        catch (Exception ex)
        {
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("Values invalid");
            dlg.setInformationText("Unable to parse your values");
            dlg.setStackTrace(ex);
            dlg.setVisible(true);
            return;
        }

        this.setVisible(false);
        this.dispose();
        myCaller.addNewServer(item);
    }

    void jButtonCancel_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

}

class LocationItemDialog_jButtonOk_actionAdapter
    implements java.awt.event.ActionListener
{
    LocationItemDialog adaptee;

    LocationItemDialog_jButtonOk_actionAdapter(LocationItemDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jButtonOk_actionPerformed(e);
    }
}
