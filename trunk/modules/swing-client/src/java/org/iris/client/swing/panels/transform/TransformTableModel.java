package org.iris.client.swing.panels.transform;

import java.util.*;

import javax.swing.table.*;

import org.apache.log4j.*;

public class TransformTableModel
    extends AbstractTableModel
{
    String[] columnNames =
        {
        "MESSAGE", "TRANSFORMFILE"};
    int numberOfRows = 10;
    private ArrayList arrListTransformEntrie = new ArrayList(30);
    Logger myLogger = Logger.getLogger(TransformTableModel.class.getName());

    public TransformTableModel()
    {
        TransformTableModelItem item = new TransformTableModelItem();
        item.setTransformfile("");
        item.setTransformmessage("");
        ArrayList logentries = new ArrayList();
        logentries.add(item);
        this.arrListTransformEntrie = logentries;
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
        return arrListTransformEntrie.size();
    }

    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    public void reloadModel(HashMap mp)
    {
        this.arrListTransformEntrie.clear();

        Collection col = mp.keySet();
        Iterator iter = col.iterator();
        while (iter.hasNext())
        {

            TransformTableModelItem item = new TransformTableModelItem();
            String key = (String) iter.next();
            item.setTransformfile( (String) mp.get(key));
            item.setTransformmessage(key);
            arrListTransformEntrie.add(item);
        }
        fireTableDataChanged();

    }

    public void reloadModel(ArrayList list)
    {
        if (list != null)
        {
            this.arrListTransformEntrie.clear();
            this.arrListTransformEntrie = list;
            fireTableDataChanged();
        }
        else
        {
            this.arrListTransformEntrie.clear();
            TransformTableModelItem item = new TransformTableModelItem();
            item.setTransformfile("no log files");
            item.setTransformmessage("");
            ArrayList logentries = new ArrayList();
            logentries.add(item);
            this.arrListTransformEntrie = logentries;
            fireTableDataChanged();

        }
    }

    public Object getValueAt(int row, int col)
    {
        TransformTableModelItem item = (TransformTableModelItem) arrListTransformEntrie.get(row);
        switch (col)
        {
            case 0:
                return item.getTransformmessage();
            case 1:
                return item.getTransformfile();
        }
        return null;
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
                return String.class;
            case 1:
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

}
