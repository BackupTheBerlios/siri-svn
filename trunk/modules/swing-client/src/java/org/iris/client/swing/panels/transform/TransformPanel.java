package org.iris.client.swing.panels.transform;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import org.iris.client.swing.panels.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.tablesort.*;
import org.iris.client.swing.util.textfield.*;
import org.iris.server.util.config.*;

public class TransformPanel
    extends IrisPanel
{
    private static int COLUMN1_SIZE = 20;
    private static int COLUMN2_SIZE = 300;
    private static int[] COLUMNS =
        {
        COLUMN1_SIZE, COLUMN2_SIZE};
    JLabel imagejLabel = new JLabel();
    TitledBorder titledBorder3;
    TitledBorder titledBorder4;
    Border border2;
    TitledBorder titledBorder5;
    JPanel transformPathPanel = new JPanel();
    TitledBorder titledBorder1;
    JScrollPane jScrollPaneTransformTable = new JScrollPane();
//    private static TransformPanel myInstance;
    Border border1;
    TitledBorder titledBorder2;
    static int t = 0;
    JCheckBox jCheckBoxTransformOn = new JCheckBox();
    JLabel jLabel1 = new JLabel();
    TransformTableModel myTransformModel;
    private SortButtonRenderer myButtonRenderer;
    private JTableHeader myTableHeader = null;
    JTable myLogginTable = new JTable();
    TableColumnModel myTableColumnModel;
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JPanel jPanelGif = new JPanel();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    private static final String PANEL_IMAGE = "paneltransform.gif";
    private static final String TAB_IMAGE = "paneltransform_tab.gif";
    private static final String TAB_TITLE = "Transform";

    MultiLineLabel jLabel2 = new MultiLineLabel("If transformation is on all incoming files will be transformed according to their transformation file in the table below. Transformation is currently only handled for xml files using xsl transformations.");

    public TransformPanel()
    {
        tabtitle = TAB_TITLE;
        tabIcon = ResourcesFactory.getImageIcon(TAB_IMAGE);
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
        public static TransformPanel getInstance()
        {
            if (myInstance == null)
            {
                myInstance = new TransformPanel();
            }
            return myInstance;
        }
     */

    public static void main(String[] args)
    {
        JFrame fr = new JFrame();
        fr.getContentPane().add(new TransformPanel());
        fr.setVisible(true);
    }

    public void initPanel(Service service)
    {
        if (service.isIsTransformOn())
        {
            this.getJCheckBoxTransformOn().setSelected(true);
            this.getMyTransformModel().reloadModel(service.getTransformFiles());
        }
        else
        {
            this.getJCheckBoxTransformOn().setSelected(false);
        }

    }

    private void jbInit() throws Exception
    {
        myTransformModel = new TransformTableModel();
        // myTransformModel.addTableModelListener(new LoggingTableModelListener( myLogginTable));

        titledBorder3 = new TitledBorder("");
        titledBorder4 = new TitledBorder(BorderFactory.createLineBorder(new Color(153, 153, 153), 2), "Reload Options");
        border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder5 = new TitledBorder(border2, "Reload Options");
        this.myLogginTable.setModel(myTransformModel);

        imagejLabel.setText("");
        imagejLabel.setIcon(ResourcesFactory.getImageIcon(PANEL_IMAGE));

        titledBorder1 = new TitledBorder("");
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)),
                        "Transform options");
        this.setLayout(borderLayout2);
        transformPathPanel.setBorder(titledBorder2);
        transformPathPanel.setDebugGraphicsOptions(0);
        transformPathPanel.setLayout(gridBagLayout1);

        jCheckBoxTransformOn.setText("Transformation on");
        jLabel1.setText("Message transformation matrix");
        jPanel1.setLayout(borderLayout1);

        transformPathPanel.add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 0, 16), 375, 6));
        transformPathPanel.add(jScrollPaneTransformTable, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(7, 6, 39, 16), 73, -283));
        transformPathPanel.add(jCheckBoxTransformOn, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 10, 0, 151), 330, -1));
        transformPathPanel.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
            GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        jScrollPaneTransformTable.getViewport().add(myLogginTable, null);
        this.add(jPanelGif, BorderLayout.WEST);
        jPanelGif.add(imagejLabel, null);
        this.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(transformPathPanel, BorderLayout.NORTH);

        //
        //  Stuff for sorting the table myTableHeader
        myTableHeader = myLogginTable.getTableHeader();
        myTableHeader.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                headerMouseReleased(e);
            }

            public void mousePressed(MouseEvent e)
            {
                headerMousePressed(e);
            }
        });
        myButtonRenderer = new SortButtonRenderer();
        myTableColumnModel = myLogginTable.getColumnModel();

        for (int i = 0; i < COLUMNS.length; i++)
        {
            myTableColumnModel.getColumn(i).setHeaderRenderer(myButtonRenderer);
            myTableColumnModel.getColumn(i).setPreferredWidth(COLUMNS[i]);
        }
        //

    }

    public void headerMouseReleased(MouseEvent e)
    {
        int col = myTableHeader.columnAtPoint(e.getPoint());
        myButtonRenderer.setPressedColumn( -1);
        // clear
        myTableHeader.repaint();
    }

    private void headerMousePressed(MouseEvent e)
    {
        int col = myTableHeader.columnAtPoint(e.getPoint());
        int sortCol = myTableHeader.getTable().convertColumnIndexToModel(col);
        myButtonRenderer.setPressedColumn(col);
        myButtonRenderer.setSelectedColumn(col);
        myTableHeader.repaint();
        if (myTableHeader.getTable().isEditing())
        {
            myTableHeader.getTable().getCellEditor().stopCellEditing();
        }
        boolean isAscent;
        if (SortButtonRenderer.DOWN == myButtonRenderer.getState(col))
        {
            isAscent = true;
        }
        else
        {
            isAscent = false;
        }
        this.myTransformModel.sort(isAscent, sortCol);
        this.myTransformModel.fireTableDataChanged();
    }

    public TransformTableModel getMyTransformModel()
    {
        return myTransformModel;
    }

    public void setMyTransformModel(TransformTableModel myTransformModel)
    {
        this.myTransformModel = myTransformModel;
    }

    public JCheckBox getJCheckBoxTransformOn()
    {
        return jCheckBoxTransformOn;
    }

    public void setJCheckBoxTransformOn(JCheckBox jCheckBoxTransformOn)
    {
        this.jCheckBoxTransformOn = jCheckBoxTransformOn;
    }

}
