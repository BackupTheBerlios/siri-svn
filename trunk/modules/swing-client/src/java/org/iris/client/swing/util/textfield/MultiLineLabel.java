package org.iris.client.swing.util.textfield;

import java.util.*;

import java.awt.*;
import javax.swing.*;

/**
 * This class uses a <tt>JTextArea</tt> to simulate a <tt>JLabel</tt> that
 * allows multiple-line labels.  It does this by using JLabel's values for
 * border, font, etc.
 */
public class MultiLineLabel
    extends JTextArea
{

    /**
     * The default pixel width for labels when the width is not
     * specified in the constructor.
     */
    private final static int DEFAULT_LABEL_WIDTH = 600;

    /**
     * Creates a label that can have multiple lines and that has the
     * default width.
     *
     * @param s the <tt>String</tt> to display in the label
     * @throws <tt>NullPointerException</tt> if the string argument is
     *  <tt>null</tt>
     */
    public MultiLineLabel(String s)
    {
        if (s == null)
        {
            throw new NullPointerException("null string in multilinelabel");
        }
        this.setOpaque(false);
        setText(s);
    }

    /**
     * Creates a label with new lines inserted after the specified number
     * of pixels have been filled on each line.
     *
     * @param s the <tt>String</tt> to display in the label
     * @param pixels the pixel limit for each line
     * @throws <tt>NullPointerException</tt> if the string argument is
     *  <tt>null</tt>
     */
    public MultiLineLabel(String s, int pixels)
    {
        if (s == null)
        {
            throw new NullPointerException("null string in multilinelabel");
        }
        this.setOpaque(false);
        setText(s, pixels);
    }

    /**
     * New constructor that takes an array of strings.  This creates a
     * new <tt>MultiLineLabel</tt> with the string at each index in
     * the array placed on its own line.  The array cannot contain
     * any null strings.
     *
     * @param strs the array of strings that should each be placed on
     *  its own line in the label
     */
    public MultiLineLabel(String[] strs)
    {
        this.setOpaque(false);
        super.setText(createSizedString(strs));
    }

    /**
     * Creates a label that can have multiple lines and that sets the
     * number of rows and columns for the JTextArea.
     *
     * @param s the <tt>String</tt> to display in the label
     * @param pixels the pixel limit for each line.
     * @param rows the number of rows to include in the label
     * @param cols the number of columns to include in the label
     * @throws <tt>NullPointerException</tt> if the string argument is
     *  <tt>null</tt>
     */
    public MultiLineLabel(String s, int pixels, int rows, int cols)
    {
        super(rows, cols);
        if (s == null)
        {
            throw new NullPointerException("null string in multilinelabel");
        }
        this.setOpaque(false);
        setText(s, pixels);
    }

    /**
     * Change the text before passing it up to the super setText.
     *
     * @param s the <tt>String</tt> to display in the label
     * @param pixels the pixel limit for each line
     */
    public void setText(String s, int pixels)
    {
        super.setText(createSizedString(s, pixels));
    }

    /**
     * Change the text before passing it up to the super setText.
     *
     * @param s the <tt>String</tt> to display in the label
     */
    public void setText(String s)
    {
        super.setText(createSizedString(s, DEFAULT_LABEL_WIDTH));
    }

    /**
     * Tells the look and feel to reset some of the  values for this
     * component so that it doesn't use JTextArea's default values.
     *
     *  DO NOT CALL THIS METHOD YOURSELF!
     */
    public void updateUI()
    {
        super.updateUI();
        //setLineWrap(true);
        setWrapStyleWord(true);
        setHighlighter(null);
        setEditable(false);
        LookAndFeel.installBorder(this, "Label.border");
        LookAndFeel.installColorsAndFont(this, "Label.background", "Label.foreground", "Label.font");
    }

    /**
     * Convert the input string to a string with newlines at the
     * closest word to the number of pixels specified in the 'pixels'
     * parameter.
     *
     * @param message the <tt>String</tt> to display in the label
     * @param pixels the pixel width on each line before
     *  inserting a new line character
     */
    private String createSizedString(final String message, final int pixels)
    {
        FontMetrics fm = getFontMetrics(getFont());
        StringTokenizer st = new StringTokenizer(message);
        String word;
        StringBuffer sb = new StringBuffer();
        StringBuffer cursb = new StringBuffer();
        while (st.hasMoreTokens())
        {
            word = st.nextToken();
            if (fm.stringWidth(cursb.toString() + word) > pixels)
            {
                sb.append(cursb.toString());
                sb.append("\n");
                cursb = new StringBuffer();
            }
            cursb.append(word);
            cursb.append(" ");
        }
        sb.append(cursb.toString());

        return sb.toString();
    }

    /**
     * Creates a multiline label with one line taken up by each string
     * in the string array argument.
     *
     * @param strs the array of strings to put in the multiline label
     * @return a new string with newlines inserted at the appropriate
     *  places
     */
    private String createSizedString(final String[] strs)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.length; i++)
        {
            sb.append(strs[i]);
            sb.append("\n");
        }
        return sb.toString();
    }
}
