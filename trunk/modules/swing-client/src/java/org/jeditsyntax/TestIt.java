package org.jeditsyntax;

import java.awt.*;
import javax.swing.*;

public class TestIt
    extends JFrame
{
    JTextArea ta = new JTextArea();
    public static void main(String[] args)
    {
        new TestIt();

    }

    public TestIt()
    {
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception
    {
        JEditTextArea ta = new JEditTextArea();
        ta.setTokenMarker(new XMLTokenMarker());
        ta.setText("<test>kalle\n<!-- kaksdfasdkfaksdf --></test>");

        ta.setBounds(new Rectangle(22, 21, 287, 216));
        this.getContentPane().setLayout(null);
        this.getContentPane().add(ta, null);

    }
}