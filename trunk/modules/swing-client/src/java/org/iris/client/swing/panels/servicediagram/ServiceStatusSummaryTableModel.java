package org.iris.client.swing.panels.servicediagram;

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import org.apache.log4j.*;

public class ServiceStatusSummaryTableModel
    extends AbstractTableModel
{
    String[] columnNames =
                           {
                           "", "SERVICE", "DESCRIPTION", "# IN MSGS", "# ERROR MSGS", "# ARCHIVED FILES"};
    private boolean isDirty;
    int numberOfRows = 10;
    private ArrayList myLocationItems = new ArrayList(30);
    Logger myLogger = Logger.getLogger(ServiceStatusSummaryTableModel.class.getName());

    public ServiceStatusSummaryTableModel(ArrayList arrLocationItems)
    {
        myLocationItems = arrLocationItems;

    }

    public ServiceStatusSummaryItem[] getAllItems()
    {
        ServiceStatusSummaryItem[] allItems = new ServiceStatusSummaryItem[myLocationItems.size()];
        return (ServiceStatusSummaryItem[]) myLocationItems.toArray(allItems);

    }

    public ArrayList getAll()
    {
        return myLocationItems;
    }

    public void addNewItem(ServiceStatusSummaryItem newItem)
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
        ServiceStatusSummaryItem item = (ServiceStatusSummaryItem) myLocationItems.get(row);
        switch (col)
        {
            case 0:
                return item.getIcon();
            case 1:
                return item.getName();
            case 2:
                return item.getDescription();
            case 3:
                return new Long(item.getNumberOfInfolderFiles());
            case 4:
                return new Long(item.getNumberOfErrorFiles());
            case 5:
                return new Long(item.getNumberOfArchivedFiles());

        }
        return null;
    }

    public ServiceStatusSummaryItem getSelectedItem(int row)
    {
        ServiceStatusSummaryItem item = (ServiceStatusSummaryItem) myLocationItems.get(row);
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
                return String.class;
            case 3:
                return Long.class;
            case 4:
                return Long.class;
            case 5:
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

    public boolean deleteRow(ServiceStatusSummaryItem removeItem)
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
