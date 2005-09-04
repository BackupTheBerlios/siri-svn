package org.siri.swing.util.autocompletetextfield;

import java.util.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 *  Class is responsible for
 *
 * @author     Georges Polyzois
 */
public class JComboBoxExt
    extends JComboBox implements JComboBox.KeySelectionManager
{

    private String searchFor;
    private long lap;

    /**
     *  Constructor for the JComboBoxExt object
     *
     * @param  aModel  Description of Parameter
     */
    public JComboBoxExt(ComboBoxModel aModel)
    {
        super(aModel);
        lap = new java.util.Date().getTime();
        setKeySelectionManager(this);
        JTextField tf;
        if (getEditor() != null)
        {
            tf = (JTextField) getEditor().getEditorComponent();
            if (tf != null)
            {
                tf.setDocument(new CBDocument());
                addActionListener(new ActionListener()
                {
                    /**
                     *  Method is responsible for
                     *
                     * @param  evt  Description of Parameter
                     */
                    public void actionPerformed(ActionEvent evt)
                    {
                        JTextField tf = (JTextField) getEditor().getEditorComponent();
                        String text = tf.getText();
                        ComboBoxModel aModel = getModel();
                        String current;
                        for (int i = 0; i < aModel.getSize(); i++)
                        {
                            current = aModel.getElementAt(i).toString().toLowerCase();
                            if (current.toLowerCase().startsWith(text.toLowerCase()))
                            {
                                tf.setText(current);
                            }
                            tf.setSelectionStart(text.length());
                            tf.setSelectionEnd(current.length());
                        }
                    }
                });
            }
        }
    }

    /**
     *  The main program for the JComboBoxExt class
     *
     * @param  args  The command line arguments
     */
    public static void main(String[] args)
    {
        String[] tt =
            {
            "gepo", "gepo1", "gepo2", "gepo3", "nisse", "hubert", "arnold", "astrid", "malin", "josef", "per", "rubert"};

        Vector vec = new Vector();
        for (int i = 0; i < tt.length; i++)
        {
            vec.add(tt[i]);
        }

        JComboBox cb = new JComboBox(vec);

        JComboBoxExt ext = new JComboBoxExt(cb.getModel());
        JFrame fr = new JFrame();
        fr.getContentPane().add(ext);
        fr.pack();
        fr.setVisible(true);
    }

    public int selectionForKey(char aKey, ComboBoxModel aModel)
    {
        long now = new java.util.Date().getTime();
        if (searchFor != null && aKey == KeyEvent.VK_BACK_SPACE && searchFor.length() == 0)
        {
            searchFor = searchFor.substring(0, searchFor.length() - 1);
        }
        else
        {
            if (lap + 1000 < now)
            {
                searchFor = "" + aKey;
            }
            else
            {
                searchFor = searchFor + aKey;
            }
        }
        lap = now;
        String current;
        for (int i = 0; i < aModel.getSize(); i++)
        {
            current = aModel.getElementAt(i).toString().toLowerCase();
            if (current.toLowerCase().startsWith(searchFor.toLowerCase()))
            {
                return i;
            }
        }
        return -1;
    }

    /**
     *  Method is responsible for
     */
    public void fireActionEvent()
    {
        super.fireActionEvent();
    }

    /**
     *  Class is responsible for
     *
     * @author     gepo
     */
    public class CBDocument
        extends PlainDocument
    {

        /**
         *  Method is responsible for
         *
         * @param  offset                    Description of Parameter
         * @param  str                       Description of Parameter
         * @param  a                         Description of Parameter
         * @exception  BadLocationException  Description of Exception
         */
        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
        {
            if (str == null)
            {
                return;
            }
            super.insertString(offset, str, a);
            if (!isPopupVisible() && str.length() != 0)
            {
                fireActionEvent();
            }
        }
    }
}
