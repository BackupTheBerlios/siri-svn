package org.siri.swing.util.panels.optionspanel.lookandfeel;

import javax.swing.*;

/**
 *  Class is responsible for
 *
 * @author     Georges Polyzois
 * @created    den 27 mars 2002
 */
public class LookAndFeelListItem
{
    private String listName;
    private String metaInfo;
    private ImageIcon listImageIcon;

    /**
     *  Constructor for the LookAndFeelListItem object
     */
    public LookAndFeelListItem()
    {
    }

    /**
     *  Sets the {3} attribute of the LookAndFeelListItem object
     *
     * @param  newListName  The new {3} value
     */
    public void setListName(String newListName)
    {
        listName = newListName;
    }

    /**
     *  Sets the {3} attribute of the LookAndFeelListItem object
     *
     * @param  newMetaInfo  The new {3} value
     */
    public void setMetaInfo(String newMetaInfo)
    {
        metaInfo = newMetaInfo;
    }

    /**
     *  Sets the {3} attribute of the LookAndFeelListItem object
     *
     * @param  aIcon  The new {3} value
     */
    public void setIcon(ImageIcon aIcon)
    {
        listImageIcon = aIcon;
    }

    /**
     *  Gets the {3} attribute of the LookAndFeelListItem object
     *
     * @return    The {3} value
     */
    public String getListName()
    {
        return listName;
    }

    /**
     *  Gets the {3} attribute of the LookAndFeelListItem object
     *
     * @return    The {3} value
     */
    public String getMetaInfo()
    {
        return metaInfo;
    }

    /**
     *  Gets the {3} attribute of the LookAndFeelListItem object
     *
     * @return    The {3} value
     */
    public ImageIcon getIcon()
    {
        return listImageIcon;
    }

    /**
     *  Method is responsible for
     *
     * @return    Description of the Returned Value
     */
    public String toString()
    {
        return this.listName;
    }
}
