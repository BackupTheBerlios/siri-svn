package org.siri.swing.util.textfield;

import javax.swing.text.*;

/**
 * <p>Title: Ocean Java Client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Tradevsion</p>
 * @author unascribed
 * @version 1.0
 */

public class RestrictedNumberOfCharsDocument
    extends PlainDocument
{
    private int myMaxNumberOfCharsAllowed;
    private int myMinNumberOfCharsAllowed = 0;

    public RestrictedNumberOfCharsDocument(int myMaxNumberOfCharsAllowed)
    {
        super();
        setLimit(myMaxNumberOfCharsAllowed); // store the myMaxNumberOfCharsAllowed
    }

    public final int getLimit()
    {
        return myMaxNumberOfCharsAllowed;
    }

    //Override
    public void insertString(int offset, String str, AttributeSet aSet) throws BadLocationException
    {
        System.out.println("offset " + offset + "  str" + str);
        if (myMinNumberOfCharsAllowed <= offset && offset < myMaxNumberOfCharsAllowed) // if we haven't reached the myMaxNumberOfCharsAllowed, insert the string
        {
            super.insertString(offset, str, aSet);
        } // otherwise, just lose the string
    }

    public final void setLimit(int newValue)
    {
        this.myMaxNumberOfCharsAllowed = newValue;
    }

}
