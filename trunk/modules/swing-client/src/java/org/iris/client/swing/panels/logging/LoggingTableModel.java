package org.iris.client.swing.panels.logging;

import java.text.*;
import java.util.*;

import javax.swing.table.*;

import org.apache.log4j.*;

public class LoggingTableModel
    extends AbstractTableModel
{
    private static final String[] COL_NAMES =
                                              {
                                              "Time", "Level", "Service(NDC)", "Message"};
    private static final LoggingTableModelItem[] EMPTY_LIST = new LoggingTableModelItem[]
        {};
    private static final DateFormat DATE_FORMATTER = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
    private final Object myLock = new Object();
    private final SortedSet mAllEvents = new TreeSet(MY_COMP);
    private LoggingTableModelItem[] myFilteredEvents = EMPTY_LIST;
    private final List myPendingEvents = new ArrayList();
    private boolean myPaused = false;
    private String myThreadFilter = "";
    private String myMessageFilter = "";
    private String myNDCFilter = "";
    private String myCategoryFilter = "";
    private Priority myPriorityFilter = Priority.DEBUG;

    private static final Comparator MY_COMP = new Comparator()
    {
        public int compare(Object aObj1, Object aObj2)
        {
            if ( (aObj1 == null) && (aObj2 == null))
            {
                return 0; // treat as equal
            }
            else if (aObj1 == null)
            {
                return -1; // null less than everything
            }
            else if (aObj2 == null)
            {
                return 1; // think about it. :->
            }

            // will assume only have LoggingEvent
            final LoggingTableModelItem le1 = (LoggingTableModelItem) aObj1;
            final LoggingTableModelItem le2 = (LoggingTableModelItem) aObj2;

            if (le1.getTimeStamp() < le2.getTimeStamp())
            {
                return 1;
            }
            // assume not two events are logged at exactly the same time
            return -1;
        }
    };

    private class Processor
        implements Runnable
    {
        public void run()
        {
            while (true)
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    // ignore
                }

                synchronized (myLock)
                {
                    if (myPaused)
                    {
                        continue;
                    }

                    boolean toHead = true; // were events added to head
                    boolean needUpdate = false;
                    final Iterator it = myPendingEvents.iterator();
                    while (it.hasNext())
                    {
                        final LoggingTableModelItem event = (LoggingTableModelItem) it.next();
                        mAllEvents.add(event);
                        toHead = toHead && (event == mAllEvents.first());
                        needUpdate = needUpdate || matchFilter(event);
                    }
                    myPendingEvents.clear();

                    if (needUpdate)
                    {
                        updateFilteredEvents(toHead);
                    }
                }
            }

        }
    }

    LoggingTableModel()
    {
        final Thread t = new Thread(new Processor());
        t.setDaemon(true);
        t.start();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Table Methods
    ////////////////////////////////////////////////////////////////////////////

    public int getRowCount()
    {
        synchronized (myLock)
        {
            return myFilteredEvents.length;
        }
    }

    public int getColumnCount()
    {
        // does not need to be synchronized
        return COL_NAMES.length;
    }

    public String getColumnName(int aCol)
    {
        // does not need to be synchronized
        return COL_NAMES[aCol];
    }

    /** @see javax.swing.table.TableModel **/
    public Class getColumnClass(int aCol)
    {
        // does not need to be synchronized
        //return (aCol == 2) ? Boolean.class : Object.class;
        /*        switch (aCol)
                {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                }
         */

        return String.class;
    }

    public Object getValueAt(int aRow, int aCol)
    {
        synchronized (myLock)
        {
            final LoggingTableModelItem event = myFilteredEvents[aRow];

            if (aCol == 0)
            {
                return DATE_FORMATTER.format(new Date(event.getTimeStamp()));
            }
            else if (aCol == 1)
            {
                return event.getPriority();
            }
            /* else if (aCol == 2)
              {
                  return event.getCategoryName();
              }*/
            else if (aCol == 2)
            {
                return event.getNDC();
            }
            else if (aCol == 3)
            {
                event.getMessage();
            }

            return event.getMessage();
        }
    }

    public void setPriorityFilter(Priority aPriority)
    {
        synchronized (myLock)
        {
            myPriorityFilter = aPriority;
            updateFilteredEvents(false);
        }
    }

    public void setThreadFilter(String aStr)
    {
        synchronized (myLock)
        {
            myThreadFilter = aStr.trim();
            updateFilteredEvents(false);
        }
    }

    public void setMessageFilter(String aStr)
    {
        synchronized (myLock)
        {
            myMessageFilter = aStr.trim();
            updateFilteredEvents(false);
        }
    }

    public void setNDCFilter(String aStr)
    {
        synchronized (myLock)
        {
            myNDCFilter = aStr.trim();
            updateFilteredEvents(false);
        }
    }

    public void setCategoryFilter(String aStr)
    {
        synchronized (myLock)
        {
            myCategoryFilter = aStr.trim();
            updateFilteredEvents(false);
        }
    }

    public void addEvent(LoggingTableModelItem aEvent)
    {
        synchronized (myLock)
        {
            myPendingEvents.add(aEvent);
        }
    }

    public void clear()
    {
        synchronized (myLock)
        {
            mAllEvents.clear();
            myFilteredEvents = new LoggingTableModelItem[0];
            myPendingEvents.clear();
            fireTableDataChanged();
        }
    }

    public void toggle()
    {
        synchronized (myLock)
        {
            myPaused = !myPaused;
        }
    }

    public boolean isPaused()
    {
        synchronized (myLock)
        {
            return myPaused;
        }
    }

    public LoggingTableModelItem getEventDetails(int aRow)
    {
        synchronized (myLock)
        {
            return myFilteredEvents[aRow];
        }
    }

    private void updateFilteredEvents(boolean aInsertedToFront)
    {
        final List filtered = new ArrayList();
        final Iterator it = mAllEvents.iterator();

        while (it.hasNext())
        {
            final LoggingTableModelItem event = (LoggingTableModelItem) it.next();
            if (matchFilter(event))
            {
                filtered.add(event);
            }
        }

        final LoggingTableModelItem lastFirst = (myFilteredEvents.length == 0) ? null : myFilteredEvents[0];
        myFilteredEvents = (LoggingTableModelItem[]) filtered.toArray(EMPTY_LIST);

        if (aInsertedToFront && (lastFirst != null))
        {
            final int index = filtered.indexOf(lastFirst);
            if (index < 1)
            {
                fireTableDataChanged();
            }
            else
            {
                fireTableRowsInserted(0, index - 1);
            }
        }
        else
        {
            fireTableDataChanged();
        }

    }

    private boolean matchFilter(LoggingTableModelItem aEvent)
    {
        if (aEvent.getPriority().isGreaterOrEqual(myPriorityFilter) &&
            (aEvent.getThreadName().indexOf(myThreadFilter) >= 0) &&
            (aEvent.getCategoryName().indexOf(myCategoryFilter) >= 0) && ( (myNDCFilter.length() == 0) ||
            ( (aEvent.getNDC() != null) && (aEvent.getNDC().indexOf(myNDCFilter) >= 0))))
        {
            final String rm = aEvent.getMessage();
            if (rm == null)
            {
                // only match if we have not filtering in place
                return (myMessageFilter.length() == 0);
            }
            else
            {
                return (rm.indexOf(myMessageFilter) >= 0);
            }
        }

        return false; // by default not match
    }
}
