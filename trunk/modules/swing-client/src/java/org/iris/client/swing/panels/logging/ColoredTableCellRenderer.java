package org.iris.client.swing.panels.logging;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import org.apache.log4j.*;

class ColoredTableCellRenderer
    extends DefaultTableCellRenderer
{
    static Color ligtgrey = new Color(235, 235, 235);
    static Color ligtred = new Color(255, 214, 214);

    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
        int row, int column)
    {
        setEnabled(table == null || table.isEnabled()); // see question above

        if ( (row % 2) == 0)
        {
            setBackground(ligtgrey);
        }
        else
        {
            setBackground(null);
        }

        if (value instanceof Priority)
        {
            if (value.equals(Priority.ERROR) || value.equals(Priority.WARN) || value.equals(Priority.FATAL))
            {
                setBackground(ligtred);
            }
        }

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        return this;
    }
}
