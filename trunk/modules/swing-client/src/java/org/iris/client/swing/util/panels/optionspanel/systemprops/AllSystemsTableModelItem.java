package org.iris.client.swing.util.panels.optionspanel.systemprops;

/**
 *  Class is responsible for
 *
 * @author     Georges Polyzois
 * @created    den 27 mars 2002
 */
public class AllSystemsTableModelItem
{
    //implements Comparable

    private String name;
    private String value;

    /**
     *  Constructor for the AllSystemsTableModelItem object
     */
    public AllSystemsTableModelItem()
    {
    }

    /**
     *  Sets the {3} attribute of the AllSystemsTableModelItem object
     *
     * @param  newName  The new {3} value
     */
    public void setName(String newName)
    {
        name = newName;
    }

    /**
     *  Sets the {3} attribute of the AllSystemsTableModelItem object
     *
     * @param  newValue  The new {3} value
     */
    public void setValue(String newValue)
    {
        value = newValue;
    }

    /**
     *  Gets the {3} attribute of the AllSystemsTableModelItem object
     *
     * @return    The {3} value
     */
    public String getName()
    {
        return name;
    }

    /**
     *  Gets the {3} attribute of the AllSystemsTableModelItem object
     *
     * @return    The {3} value
     */
    public String getValue()
    {
        return value;
    }
}
