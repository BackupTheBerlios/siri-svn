package org.iris.client.swing.util.panels.optionspanel.lookandfeel;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import org.iris.client.swing.util.*;

/**
 *  Class is responsible for
 *
 * @author     Georges Polyzois
 * @created    den 27 mars 2002
 */
public class LookAndFeelPanel
    extends JPanel
{
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanelRoot = new JPanel();
    TitledBorder titledBorder1;
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanelNorth = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JList jListLookAndFeel = new JList();
    JPanel jPanelSouth = new JPanel();
    JButton jButtonApply = new JButton();
    JLabel jLabelNorth = new JLabel();
    FlowLayout flowLayout1 = new FlowLayout();

    /**
     *  Constructor for the LookAndFeelPanel object
     */
    public LookAndFeelPanel()
    {
        try
        {
            jbInit();
            initLookAndFeelList();
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
        titledBorder1 = new TitledBorder(BorderFactory.createLineBorder(new Color(153, 153, 153), 2), "Look and Feel");
        this.setLayout(borderLayout1);
        jPanelRoot.setBorder(titledBorder1);
        jPanelRoot.setLayout(borderLayout2);
        jButtonApply.setText("Apply");
        jButtonApply.addActionListener(new java.awt.event.ActionListener()
        {
            /**
             *  Method is responsible for
             *
             * @param  e  Description of Parameter
             */
            public void actionPerformed(ActionEvent e)
            {
                jButtonApply_actionPerformed(e);
            }
        });
        jLabelNorth.setToolTipText("");
        jLabelNorth.setHorizontalAlignment(SwingConstants.LEFT);
        jLabelNorth.setHorizontalTextPosition(SwingConstants.LEFT);
        jLabelNorth.setText("Select a look and feel from the list below:");
        jPanelNorth.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        this.add(jPanelRoot, BorderLayout.CENTER);
        jPanelRoot.add(jPanelNorth, BorderLayout.NORTH);
        jPanelNorth.add(jLabelNorth, null);
        jPanelRoot.add(jScrollPane1, BorderLayout.CENTER);
        jPanelRoot.add(jPanelSouth, BorderLayout.SOUTH);
        jPanelSouth.add(jButtonApply, null);
        jScrollPane1.getViewport().add(jListLookAndFeel, null);
    }

    /**
     *  Method is responsible for
     *
     * @param  e  Description of Parameter
     */
    void jButtonApply_actionPerformed(ActionEvent e)
    {
        try
        {

            UIManager.setLookAndFeel( ( (LookAndFeelListItem)this.jListLookAndFeel.getSelectedValue()).getMetaInfo());
//			SwingUtilities.updateComponentTreeUI(OptionsDialog.getStaticInstance());
//			SwingUtilities.updateComponentTreeUI(org.irisclient.swing.MainFrame.getInstance());
            //. .getInstance() );

        }
        catch (Exception ex)
        {
            /* what else can we do? */
            ex.printStackTrace();
        }
    }

    /**
     *  Method is responsible for
     */
    private void initLookAndFeelList()
    {
        UIManager.LookAndFeelInfo lafs[] = UIManager.getInstalledLookAndFeels();
        Vector al = new Vector();
        LookAndFeelListItem jli;
        for (int i = 0; i < lafs.length; ++i)
        {
            jli = new LookAndFeelListItem();
            jli.setListName(lafs[i].getName());
            jli.setMetaInfo(lafs[i].getClassName());

            try
            {
                jli.setIcon(ResourcesFactory.getImageIcon("referenceitem_round.gif"));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            al.add(jli);
            this.jListLookAndFeel.setListData(al);
        }

        LookAndFeelListItemCellRenderer renderer = new LookAndFeelListItemCellRenderer();
        renderer.setPreferredSize(new Dimension(200, 20));
        this.jListLookAndFeel.setCellRenderer(renderer);
        this.jListLookAndFeel.setVisibleRowCount(5);

    }
}
