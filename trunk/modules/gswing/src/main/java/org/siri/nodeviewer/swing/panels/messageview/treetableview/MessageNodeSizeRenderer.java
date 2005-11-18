package org.siri.nodeviewer.swing.panels.messageview.treetableview;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.NumberFormat;

public class MessageNodeSizeRenderer extends DefaultTableCellRenderer
{
    /**
     * static number format instance. Used to format filesizes.
     */
    private static NumberFormat numberFormat = NumberFormat.getInstance();

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (value instanceof Long)
        {
            String sizeStr = formatFileSize(((Long) value).longValue());
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, sizeStr, isSelected, hasFocus, row, column);
            label.setHorizontalAlignment(JLabel.RIGHT);
            return label;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    /**
     * Formats filesize in bytes as appropriate to Bytes, KB, MB or GB
     *
     * @param filesize in bytes
     * @return formatted filesize
     */
    public static String formatFileSize(long filesize)
    {
        String result;

        result = formatFileSizeKB(filesize);

        return result;
    }


    /**
     * Formats filesize in bytes to KB
     *
     * @param filesize in bytes
     * @return formatted filesize
     */
    private static String formatFileSizeKB(long filesize)
    {
        return new StringBuffer(numberFormat.format(Math.round((double) filesize / 1024.0))).append(" KB ").toString();
    }
}
