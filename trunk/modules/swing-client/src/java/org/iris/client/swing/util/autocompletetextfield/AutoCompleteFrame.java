package org.iris.client.swing.util.autocompletetextfield;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Title: Ocean Java Client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Tradevsion</p>
 * @author unascribed
 * @version 1.0
 */

public class AutoCompleteFrame
    extends JFrame
{
    JTextField jTextField1 = new JTextField();

    public AutoCompleteFrame()
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

    public static void main(String[] args)
    {
        AutoCompleteFrame autoCompleteFrame = new AutoCompleteFrame();
        autoCompleteFrame.pack();
        autoCompleteFrame.setVisible(true);
    }

    boolean isTextSelected = false;
    private void jbInit() throws Exception
    {
        jTextField1.setText("jTextField1");

        jTextField1.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                isTextSelected = jTextField1.getSelectionStart() != jTextField1.getSelectionEnd();
            }

            public void keyReleased(KeyEvent e)
            {
                char charPressed = e.getKeyChar();
                int charCodePressed = e.getKeyCode();

                if (charCodePressed == KeyEvent.VK_DELETE || charPressed == KeyEvent.CHAR_UNDEFINED)
                {
                    return;
                }

                String input = jTextField1.getText();
                if (charCodePressed == KeyEvent.VK_BACK_SPACE && isTextSelected && input.length() > 0)
                {
                    jTextField1.setText(input.substring(0, input.length() - 1));
                }

                //do your database lookup here and store thr new value
                jTextField1.setText("newValueFromDatabase");

                jTextField1.setSelectionStart(input.length());
                jTextField1.setSelectionEnd(jTextField1.getText().length());
            }
        });

        this.getContentPane().add(jTextField1, BorderLayout.NORTH);
    }
}