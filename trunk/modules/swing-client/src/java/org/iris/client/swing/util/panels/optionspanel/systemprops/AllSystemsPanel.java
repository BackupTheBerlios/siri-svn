package org.iris.client.swing.util.panels.optionspanel.systemprops;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import org.iris.client.swing.util.tablesort.*;

/**
 *  Class is responsible for
 *
 * @author     Georges Polyzois
 * @created    den 27 mars 2002
 */
public class AllSystemsPanel
    extends JPanel
{
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanelRoot = new JPanel();
    TitledBorder titledBorder1;
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanelNorth = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JPanel jPanelSouth = new JPanel();
    JLabel jLabelNumberOfProperties = new JLabel();
    BorderLayout borderLayout3 = new BorderLayout();

    AllSystemsTableModel myAllSystemstModel = new AllSystemsTableModel();
    private SortButtonRenderer renderer;
    private JTableHeader header = null;
    JTable jTableSystemInfo = new JTable();
    TitledBorder titledBorder2;
    Border border1;
    TitledBorder titledBorder3;
    Border border2;
    TitledBorder titledBorder4;

    /**
     *  Constructor for the AllSystemsPanel object
     */
    public AllSystemsPanel()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     *  Method is responsible for
     *
     * @exception  Exception  Description of Exception
     */
    void jbInit() throws Exception
    {
        titledBorder2 = new TitledBorder("");
        border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, new Color(103, 101, 98),
                  new Color(148, 145, 140));
        titledBorder3 = new TitledBorder(border1, "System properties");
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder4 = new TitledBorder(border2, "System Properties");
        this.jTableSystemInfo.setModel(myAllSystemstModel);
        titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(148, 145, 140)),
                        "System");
        this.setLayout(borderLayout1);
        jPanelRoot.setBorder(titledBorder4);
        jPanelRoot.setLayout(borderLayout2);

        jPanelNorth.setLayout(borderLayout3);
        jLabelNumberOfProperties.setText(myAllSystemstModel.getRowCount() + " # of properties");
        this.setBorder(null);
        this.add(jPanelRoot, BorderLayout.CENTER);
        jPanelRoot.add(jPanelNorth, BorderLayout.NORTH);
        jPanelNorth.add(jLabelNumberOfProperties, BorderLayout.EAST);
        jPanelRoot.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jTableSystemInfo, null);
        jPanelRoot.add(jPanelSouth, BorderLayout.SOUTH);

        //
        //  Stuff for sorting the table header
        header = jTableSystemInfo.getTableHeader();
        header.addMouseListener(new java.awt.event.MouseAdapter()
        {
            /**
             *  Method is responsible for
             *
             * @param  e  Description of Parameter
             */
            public void mouseReleased(MouseEvent e)
            {
                headerMouseReleased(e);
            }

            /**
             *  Method is responsible for
             *
             * @param  e  Description of Parameter
             */
            public void mousePressed(MouseEvent e)
            {
                headerMousePressed(e);
            }
        });
        renderer = new SortButtonRenderer();
        TableColumnModel model = jTableSystemInfo.getColumnModel();
        for (int i = 0; i < 2; i++)
        {
            model.getColumn(i).setHeaderRenderer(renderer);
            model.getColumn(i).setPreferredWidth(200);
        }
        //
    }

    public void headerMouseReleased(MouseEvent e)
    {
        int col = header.columnAtPoint(e.getPoint());
        renderer.setPressedColumn( -1);
        // clear
        header.repaint();
    }

    private void headerMousePressed(MouseEvent e)
    {

        int col = header.columnAtPoint(e.getPoint());
        int sortCol = header.getTable().convertColumnIndexToModel(col);
        renderer.setPressedColumn(col);
        renderer.setSelectedColumn(col);
        header.repaint();

        if (header.getTable().isEditing())
        {
            header.getTable().getCellEditor().stopCellEditing();
        }

        boolean isAscent;
        if (SortButtonRenderer.DOWN == renderer.getState(col))
        {
            isAscent = true;
        }
        else
        {
            isAscent = false;
        }
        this.myAllSystemstModel.sort(isAscent, sortCol);
        this.myAllSystemstModel.fireTableDataChanged();
    }

}