package org.iris.client.swing.panels.logging;

import java.text.*;
import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class LogDetailPanel
    extends JPanel implements ListSelectionListener
{
    BorderLayout borderLayout1 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    JEditorPane myEditorPane1 = new JEditorPane();

    private static final MessageFormat FORMATTER = new MessageFormat(
        "<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"3\" ><b>Time:</b> <code>{0,time,medium}</code>" +
        "&nbsp;&nbsp;<b>Priority:</b> <code>{1}</code>" + "&nbsp;&nbsp;<b>Thread:</b> <code>{2}</code>" +
        "&nbsp;&nbsp;<b>NDC:</b> <code>{3}</code>" + "<br><b>Category:</b> <code>{4}</code>" +
        "<br><b>Location:</b> <code>{5}</code>" + "<br><b>Message:</b>" + "{6}" + "<br><b>Throwable:</b>" +
        "<font face=\"Verdana, Arial, Helvetica, sans-serif\" size=\"3\" ><pre>{7}<pre></font>");

    private final LoggingTableModel myLoggingTableModel;

    public LogDetailPanel(JTable aTable, final LoggingTableModel aLoggingTableModel)
    {
        final ListSelectionModel rowSM = aTable.getSelectionModel();
        rowSM.addListSelectionListener(this);

        myLoggingTableModel = aLoggingTableModel;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception
    {
        this.setLayout(borderLayout1);
        this.add(jScrollPane1, BorderLayout.CENTER);
        myEditorPane1.setEditable(false);
        myEditorPane1.setContentType("text/html");

        jScrollPane1.getViewport().add(myEditorPane1, null);
    }

    public void valueChanged(ListSelectionEvent listSelectionEvent)
    {
        //Ignore extra messages.
        if (listSelectionEvent.getValueIsAdjusting())
        {
            return;
        }

        final ListSelectionModel lsm = (ListSelectionModel) listSelectionEvent.getSource();
        if (lsm.isSelectionEmpty())
        {
            myEditorPane1.setText("Nothing selected");
        }
        else
        {
            final int selectedRow = lsm.getMinSelectionIndex();
            final LoggingTableModelItem e = myLoggingTableModel.getEventDetails(selectedRow);
            final Object[] args =
                {
                new Date(e.getTimeStamp()), e.getPriority(), escape(e.getThreadName()), escape(e.getNDC()),
                escape(e.getCategoryName()), escape(e.getLocationDetails()), escape(e.getMessage()),
                escape(getThrowableStrRep(e))
            };
            myEditorPane1.setText(FORMATTER.format(args));
            myEditorPane1.setCaretPosition(0);
        }

    }

    private static String getThrowableStrRep(LoggingTableModelItem aEvent)
    {
        final String[] strs = aEvent.getThrowableStrRep();
        if (strs == null)
        {
            return null;
        }

        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.length; i++)
        {
            sb.append(strs[i]).append("\n");
        }

        return sb.toString();
    }

    /**
     * Escape &lt;, &gt; &amp; and &quot; as their entities. It is very
     * dumb about &amp; handling.
     * @param aStr the String to escape.
     * @return the escaped String
     */
    private String escape(String aStr)
    {
        if (aStr == null)
        {
            return null;
        }

        final StringBuffer buf = new StringBuffer();
        for (int i = 0; i < aStr.length(); i++)
        {
            char c = aStr.charAt(i);
            switch (c)
            {
                case '<':
                    buf.append("&lt;");
                    break;
                case '>':
                    buf.append("&gt;");
                    break;
                case '\"':
                    buf.append("&quot;");
                    break;
                case '&':
                    buf.append("&amp;");
                    break;
                default:
                    buf.append(c);
                    break;
            }
        }
        return buf.toString();
    }

}