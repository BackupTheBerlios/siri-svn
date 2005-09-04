package org.iris.client.swing.panels.location;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Tradevision AB</p>
 * @author not attributable
 * @version 1.0
 */

import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import org.apache.log4j.*;

public class LocationTableModel
    extends AbstractTableModel
{
    private static boolean sortAscending = true;
    private static boolean TESTMODE = true;
    String[] columnNames =
                           {
                           "", "LOCATION", "DESCRIPTION"};
    private boolean isDirty;
    int numberOfRows = 10;
    private ArrayList myLocationItems = new ArrayList(30);
    Logger myLogger = Logger.getLogger(LocationTableModel.class.getName());

    public LocationTableModel(ArrayList arrLocationItems)
    {
        myLocationItems = arrLocationItems;

    }

    public LocationItem[] getAllItems()
    {
        LocationItem[] allItems = new LocationItem[myLocationItems.size()];
        return (LocationItem[]) myLocationItems.toArray(allItems);

    }

    public ArrayList getAll()
    {
        return myLocationItems;
    }

    public void addNewServer(LocationItem newServer)
    {
        myLocationItems.add(newServer);
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
        LocationItem item = (LocationItem) myLocationItems.get(row);
        switch (col)
        {
            case 0:
                return item.getIcon();
            case 1:
                return item.getUrl();
            case 2:
                return item.getDescription();

        }
        return null;
    }

    public LocationItem getSelectedItem(int row)
    {
        LocationItem item = (LocationItem) myLocationItems.get(row);
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

    public boolean deleteRow(LocationItem removeItem)
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
