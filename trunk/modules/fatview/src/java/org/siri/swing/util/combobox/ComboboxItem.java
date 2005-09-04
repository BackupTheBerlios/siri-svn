package org.siri.swing.util.combobox;

/**
 *  Class is responsible for
 *
 * @author     Georges Polyzois
 * @created
 */
public class ComboboxItem
    implements Comparable
{
    /**
     *  Description of the Field
     */
    public String value;

    /**
     *  Description of the Field
     */
    public String display;

    /**
     *  Constructor for the ComboboxItem object
     *
     * @param  aValue         Description of Parameter
     * @param  aDisplayvalue  Description of Parameter
     */
    public ComboboxItem(String aValue, String aDisplayvalue)
    {
        value = aValue;
        display = aDisplayvalue;
    }

    /**
     *  Method is responsible for
     *
     * @return    Description of the Returned Value
     */
    public String toString()
    {
        return display;
    }

    /**
     *  Method is responsible for
     *
     * @param  o1  Description of Parameter
     * @return     Description of the Returned Value
     */
    public int compareTo(Object o1)
    {
        if (java.beans.Beans.isInstanceOf(o1, ComboboxItem.class))
        {
            return (compare(this.display, ( (ComboboxItem) o1).display));
        }
        return 0;
    }

    /**
     *  Method is responsible for
     *
     * @param  str1  Description of Parameter
     * @param  str2  Description of Parameter
     * @return       Description of the Returned Value
     */
    private int compare(String str1, String str2)
    {
        return str1.compareTo(str2);
    }

}
