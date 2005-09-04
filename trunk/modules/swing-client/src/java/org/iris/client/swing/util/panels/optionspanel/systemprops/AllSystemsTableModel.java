package org.iris.client.swing.util.panels.optionspanel.systemprops;

import java.util.*;

import javax.swing.table.*;

/**
 *  Class is responsible for
 *
 * @author     Georges Polyzois
 * @created    den 27 mars 2002
 */
public class AllSystemsTableModel
    extends AbstractTableModel
{
    private static boolean sortAscending = true;
    private static boolean TESTMODE = true;
    String[] columnNames =
                           {
                           "Property", "Value",
    };
    int numberOfRows = 10;
    private ArrayList arrListSystemProperties = new ArrayList(30);
    //GlobalVariables.RUNNINGWITHOUTDB;


    /**
     *  Constructor for the AllSystemsTableModel object
     */
    public AllSystemsTableModel()
    {
        fillWholeTable();
    }

    /**
     *  Sets the Row attribute of the ProjectTableModel object
     *
     * @param  rowValues  The new Row value
     * @param  row        The new Row value
     */
    public void setRow(Vector rowValues, int row)
    {
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    /**
     *  Sets the {3} attribute of the AllSystemsTableModel object
     *
     * @param  value  The new {3} value
     * @param  row    The new {3} value
     * @param  col    The new {3} value
     */
    public void setValueAt(Object value, int row, int col)
    {
    }

    /**
     *  Gets the RowCount attribute of the ProjectTableModel object
     *
     * @return    The RowCount value
     */
    public int getRowCount()
    {
        return arrListSystemProperties.size();
    }

    /**
     *  Gets the ColumnName attribute of the ProjectTableModel object
     *
     * @param  col  Description of Parameter
     * @return      The ColumnName value
     */
    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    /**
     *  Gets the ValueAt attribute of the ProjectTableModel object
     *
     * @param  row  Description of Parameter
     * @param  col  Description of Parameter *@return The ValueAt value
     * @return      The {3} value
     */
    public Object getValueAt(int row, int col)
    {
        AllSystemsTableModelItem item = (AllSystemsTableModelItem) arrListSystemProperties.get(row);
        switch (col)
        {
            case 0:
                return item.getName();
            case 1:
                return item.getValue();
        }
        return null;
    }

    /*
     * JTable uses this method to determine the default renderer
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    /**
     *  Gets the {3} attribute of the AllSystemsTableModel object
     *
     * @param  c  Description of Parameter
     * @return    The {3} value
     */
    public Class getColumnClass(int c)
    {
        switch (c)
        {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
        }

        return null;
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    /**
     *  Gets the {3} attribute of the AllSystemsTableModel object
     *
     * @param  row  Description of Parameter
     * @param  col  Description of Parameter
     * @return      The {3} value
     */
    public boolean isCellEditable(int row, int col)
    {
        return false;
    }

    /**
     *  Gets the ColumnCount attribute of the ProjectTableModel object
     *
     * @return    The ColumnCount value
     */
    public int getColumnCount()
    {
        return columnNames.length;
    }

    /*
     * public void fireTableUpdated()
     * {
     * fireTableChanged();
     * }
     */
    /**
     *  Method is responsible for
     */
    public void fillWholeTable()
    {
        getAllSystemInfo();
        this.fireTableDataChanged();

    }

    /**
     *  Method is responsible for
     *
     * @param  sortAscending  Description of Parameter
     * @param  sortCol        Description of Parameter
     */
    public void sort(boolean sortAscending, int sortCol)
    {
        /*        ProjectBean.setPropertyToCompare( sortCol );
          ProjectBean.setSortAscending( sortAscending );
          Collections.sort( arrListProjets );
         */
    }

    /**
     *  Gets the {3} attribute of the AllSystemsTableModel object
     */
    private void getAllSystemInfo()
    {
        Properties props = System.getProperties();
        Enumeration enum = props.propertyNames();

        AllSystemsTableModelItem item;

        while (enum.hasMoreElements())
        {
            item = new AllSystemsTableModelItem();
            item.setName( (String)enum.nextElement());
            item.setValue(props.getProperty(item.getName()));
            arrListSystemProperties.add(item);
        }
    }

}
