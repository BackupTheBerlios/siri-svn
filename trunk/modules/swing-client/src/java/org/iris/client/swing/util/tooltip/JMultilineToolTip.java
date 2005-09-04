package org.iris.client.swing.util.tooltip;

import javax.swing.*;

/**
 * Here's how we can use the new tooltips.


 button = new JButton( "QWERTY" )
 {
  public JToolTip createToolTip()
  {
   return new JMultiLineToolTip();
  }
 };
 button.setToolTipText("This is a multi-line tooltip\nThis is the second line");
 */

public class JMultilineToolTip
    extends JToolTip
{

    private String[] tipArray;
    private static JMultilineToolTip instance = new JMultilineToolTip();

    public static JMultilineToolTip instance()
    {
        instance.initialize(); //re-initialize
        return instance;
    }

    private void initialize()
    {
        //System.out.println("Sumeet: JMultilineToolTip:initialize");
        tipArray = new String[0];
        setUI(MultilineToolTipUI.instance());
    }

    //private constructor
    public JMultilineToolTip()
    {
        initialize();
    }

    public void setToolTipArray(String[] s)
    {
        this.tipArray = s;
    }

    public String[] getTipArray()
    {
        return tipArray;
    }
}
