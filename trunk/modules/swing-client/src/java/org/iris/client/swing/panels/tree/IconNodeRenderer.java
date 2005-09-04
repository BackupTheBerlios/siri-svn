package org.iris.client.swing.panels.tree;

import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class IconNodeRenderer
    extends DefaultTreeCellRenderer
{

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
        int row, boolean hasFocus)
    {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        Icon icon = ( (IconNode) value).getIcon();

        if (icon == null)
        {
            Hashtable icons = (Hashtable) tree.getClientProperty("JTree.icons");
            String name = ( (IconNode) value).getTreeIconName();
            if ( (icons != null) && (name != null))
            {
                icon = (Icon) icons.get(name);
                if (icon != null)
                {
                    setIcon(icon);
                }
            }
        }
        else
        {
            setIcon(icon);
        }

        return this;
    }
}