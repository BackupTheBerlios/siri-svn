package org.iris.client.swing.panels.servicestatistics;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import org.apache.log4j.*;

public class ServiceSummaryTableModel
    extends AbstractTableModel
{
    private static boolean sortAscending = true;
    private static boolean TESTMODE = true;
    String[] columnNames =
                           {
                           "", "SERVICE", "# IN", "# ERROR", "# ARCHIVED"};
    private boolean isDirty;
    int numberOfRows = 10;
    private ArrayList myLocationItems = new ArrayList(30);
    Logger myLogger = Logger.getLogger(ServiceSummaryTableModel.class.getName());

    public ServiceSummaryTableModel(ArrayList arrLocationItems)
    {
        myLocationItems = arrLocationItems;

    }

    public ServiceSummaryItem[] getAllItems()
    {
        ServiceSummaryItem[] allItems = new ServiceSummaryItem[myLocationItems.size()];
        return (ServiceSummaryItem[]) myLocationItems.toArray(allItems);

    }

    public ArrayList getAll()
    {
        return myLocationItems;
    }

    public void addNewItem(ServiceSummaryItem newItem)
    {
        myLocationItems.add(newItem);
        isDirty = true;
        fireTableDataChanged();
    }

    public void reinit(ArrayList arrLocationItems)
    {
        myLocationItems = arrLocationItems;
        isDirty = true;
        fireTableDataChanged();
    }

    public void setRow(Vector rowValues, int row)
    {
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col)
    {
    }

    public int getRowCount()
    {
        return myLocationItems.size();
    }

    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col)
    {
        ServiceSummaryItem item = (ServiceSummaryItem) myLocationItems.get(row);
        switch (col)
        {
            case 0:
                return item.getIcon();
            case 1:
                return item.getName();
            case 2:
                return new Long(item.getNumberOfInfolderFiles());
            case 3:
                return new Long(item.getNumberOfErrorFiles());
            case 4:
                return new Long(item.getNumberOfArchivedFiles());

        }
        return null;
    }

    public ServiceSummaryItem getSelectedItem(int row)
    {
        ServiceSummaryItem item = (ServiceSummaryItem) myLocationItems.get(row);
        return item;
    }

    /*
     * JTable uses this method to determine the default renderer
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c)
    {
        switch (c)
        {
            case 0:
                return Icon.class;
            case 1:
                return String.class;
            case 2:
                return Long.class;
            case 3:
                return Long.class;
            case 4:
                return Long.class;

        }

        return null;
    }

    public boolean isCellEditable(int row, int col)
    {
        return false;
    }

    public int getColumnCount()
    {
        return columnNames.length;
    }

    public void sort(boolean sortAscending, int sortCol)
    {
    }

    public boolean deleteRow(ServiceSummaryItem removeItem)
    {
        boolean result = myLocationItems.remove(removeItem);
        isDirty = true;
        fireTableDataChanged();
        return result;
    }

    public boolean getisDirty()
    {
        return isDirty;
    }

}
