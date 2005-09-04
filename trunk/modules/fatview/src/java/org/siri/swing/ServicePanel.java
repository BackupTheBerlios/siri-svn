package org.siri.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServicePanel
    extends JPanel
{
    JPanel jPanel1 = new JPanel();
    JTextField jTextField1 = new JTextField();
    JTextField jTextField2 = new JTextField();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();

    public ServicePanel()
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
        this.setLayout(null);
        jPanel1.setBounds(new Rectangle(39, 48, 330, 159));
        jPanel1.setLayout(null);
        jTextField1.setText("");
        jTextField1.setBounds(new Rectangle(133, 5, 63, 20));
        jTextField1.addActionListener(new ServicePanel_jTextField1_actionAdapter(this));
        jTextField2.setText("");
        jTextField2.setBounds(new Rectangle(133, 37, 63, 20));
        jLabel1.setText("Nameservice path");
        jLabel1.setBounds(new Rectangle(34, 38, 93, 22));
        jLabel2.setBounds(new Rectangle(35, 5, 93, 22));
        jLabel2.setText("Nameservice path");
        this.add(jPanel1, null);
        jPanel1.add(jTextField1, null);
        jPanel1.add(jTextField2, null);
        jPanel1.add(jLabel1, null);
        jPanel1.add(jLabel2, null);
    }

    void jTextField1_actionPerformed(ActionEvent e)
    {

    }
}

class ServicePanel_jTextField1_actionAdapter
    implements java.awt.event.ActionListener
{
    ServicePanel adaptee;

    ServicePanel_jTextField1_actionAdapter(ServicePanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jTextField1_actionPerformed(e);
    }
}
