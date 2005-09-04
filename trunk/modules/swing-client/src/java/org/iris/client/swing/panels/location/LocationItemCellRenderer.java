package org.iris.client.swing.panels.location;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *  Class is responsible for
 *
 * @author     Georges Polyzois
 * @created    den 27 mars 2002
 */
public class LocationItemCellRenderer
    extends JLabel implements ListCellRenderer
{
    private Border lineBorder = BorderFactory.createLineBorder(Color.darkGray, 2),
        emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    /**
     *  Constructor for the LookAndFeelListItemCellRenderer object
     */
    public LocationItemCellRenderer()
    {
        setOpaque(true);
    }

    /**
     *  Gets the {3} attribute of the LookAndFeelListItemCellRenderer object
     *
     * @param  list          Description of Parameter
     * @param  value         Description of Parameter
     * @param  index         Description of Parameter
     * @param  isSelected    Description of Parameter
     * @param  cellHasFocus  Description of Parameter
     * @return               The {3} value
     */
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
        boolean cellHasFocus)
    {

        setText( ( (LocationItem) value).toString());
        setIcon( ( (LocationItem) value).getIcon());

        if (cellHasFocus)
        {
            setBorder(lineBorder);
        }
        else
        {
            setBorder(emptyBorder);
        }

        if (isSelected)
        {
            setForeground(list.getSelectionForeground());
            setBackground(list.getSelectionBackground());
        }
        else
        {
            setForeground(list.getForeground());
            setBackground(list.getBackground());
        }

        return this;
    }
}
