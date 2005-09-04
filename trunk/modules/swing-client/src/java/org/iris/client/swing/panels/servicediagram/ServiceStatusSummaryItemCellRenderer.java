package org.iris.client.swing.panels.servicediagram;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *  Class is responsible for
 *
 * @author     Georges Polyzois
 */
public class ServiceStatusSummaryItemCellRenderer
    extends JLabel implements ListCellRenderer
{
    private Border lineBorder = BorderFactory.createLineBorder(Color.darkGray, 2),
        emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    /**
     *  Constructor for the LookAndFeelListItemCellRenderer object
     */
    public ServiceStatusSummaryItemCellRenderer()
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

        setText( ( (ServiceStatusSummaryItem) value).toString());
        setIcon( ( (ServiceStatusSummaryItem) value).getIcon());

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
