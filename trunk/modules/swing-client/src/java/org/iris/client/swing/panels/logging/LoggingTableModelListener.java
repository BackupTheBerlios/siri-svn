package org.iris.client.swing.panels.logging;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class LoggingTableModelListener
    implements TableModelListener
{
    private JTable myTable = null;
    private JScrollPane myscrollpane;
    private boolean focus = false;
    public LoggingTableModelListener(JTable t, JScrollPane scrollpane)
    {
        myTable = t;
        myscrollpane = scrollpane;
    }

    public void tableChanged(TableModelEvent e)
    {
        if (focus)
        {

            /*            if (e.getFirstRow() == -1)
                        {
                            myTable.changeSelection(0, 0, false, false);
                            return;
                        }
             */
            myTable.changeSelection(0, 0, false, false);
            JViewport vp = myscrollpane.getViewport();
            Point viewpoint = new Point(0, 1);
            vp.setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
            vp.setViewPosition(viewpoint);

            /*
                         int totrows = ( (LoggingTableModel) myTable.getModel()).getRowCount();
                         myTable.changeSelection(totrows - 1, 0, false, false);
                         JViewport vp = myscrollpane.getViewport();
                 Point viewpoint = new Point(0, (totrows - 1) * (myTable.getRowHeight() + myTable.getRowMargin()));
                         vp.setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
                         vp.setViewPosition(viewpoint);*/
        }
    }

    public boolean isFocus()
    {
        return focus;
    }

    public void setFocus(boolean focus)
    {
        this.focus = focus;
    }
}
