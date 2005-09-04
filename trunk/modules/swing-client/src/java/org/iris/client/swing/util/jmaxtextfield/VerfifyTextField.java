package org.iris.client.swing.util.jmaxtextfield;

import java.awt.*;
import javax.swing.*;

public class VerfifyTextField
    extends JTextField
{
    protected int verifierType = 0;
    int maxLen;
    public final static int VERIFYEMAIL = 0;
    public final static int VERIFYNUMERIC = 1;

    public VerfifyTextField(int verifierType)
    {
        super();
        this.maxLen = maxLen;
    }

    public VerfifyTextField(int maxLen, int verifierType)
    {
        super();
        this.maxLen = maxLen;
        this.verifierType = verifierType;
        this.setInputVerifier(new FiledVerifier());
    }

    /*
      protected Document createDefaultModel()
      {
     return new MaxLenDocument();
      }

      class MaxLenDocument extends PlainDocument
      {
     public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
     {
         if (str == null) return;

         if ((getLength() + str.length()) <= maxLen)
             super.insertString(offs, str, a);
         else
       Toolkit.getDefaultToolkit().beep();
     }
      }*/

    class FiledVerifier
        extends InputVerifier
    {
        Font normal = new Font("Arial", Font.PLAIN, 12);
        Font bold = new Font("Arial", Font.BOLD, 12);

        //String numericVerigfierRegExp = "\\d{1,2}\\.\\d{1,2}";
        String emailVerifierRegExp = ".*[@].*[.].*{5," + maxLen + "}";
        String numericVerifierRegExp = "\\d{1," + maxLen + "}";

        public boolean verify(JComponent input)
        {
            return isValid(input);
        }

        private boolean isValid(JComponent input)
        {
            JTextField tf = (JTextField) input;
            switch (verifierType)
            {
                case VERIFYEMAIL:
                    if (tf.getText().equals("") || tf.getText().matches(emailVerifierRegExp))
                    {
                        tf.setFont(normal);
                        tf.setForeground(Color.black);
                        tf.setText(tf.getText());
                        return true;
                    }
                    else
                    {
                        tf.setFont(bold);
                        tf.setForeground(Color.red);
                        tf.setText(tf.getText());
                        return false;
                    }

                case VERIFYNUMERIC:
                    if (tf.getText().equals("") || tf.getText().matches(numericVerifierRegExp))
                    {
                        tf.setFont(normal);
                        tf.setForeground(Color.black);
                        tf.setText(tf.getText());
                        return true;
                    }
                    else
                    {
                        tf.setFont(bold);
                        tf.setForeground(Color.red);
                        tf.setText(tf.getText());
                        return false;
                    }
                default:
                {
                    tf.setFont(normal);
                    tf.setForeground(Color.black);
                    tf.setText(tf.getText());
                    return true;
                }

            }
        }

        public boolean shouldYieldFocus(JComponent input)
        {
            isValid(input);
            return true;
        }
    }

    public static void main(String[] args)
    {
        JFrame fr = new JFrame();
        fr.getContentPane().setLayout(new GridLayout(2, 1));
        fr.getContentPane().add(new VerfifyTextField(5, VerfifyTextField.VERIFYNUMERIC));
        fr.getContentPane().add(new JButton(""));
        fr.pack();
        fr.setVisible(true);
    }

}
