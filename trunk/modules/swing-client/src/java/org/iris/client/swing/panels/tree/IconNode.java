package org.iris.client.swing.panels.tree;

import javax.swing.*;
import javax.swing.tree.*;

public class IconNode
    extends DefaultMutableTreeNode
{
    protected Icon icon;
    protected String iconName;
    protected String treeNodeName;

    public IconNode()
    {
        this(null);
    }

    public IconNode(Object userObject)
    {
        this(userObject, true, null);
    }

    public IconNode(Object userObject, boolean allowsChildren, Icon icon)
    {
        super(userObject, allowsChildren);
        this.icon = icon;
    }

    public void setIcon(Icon icon)
    {
        this.icon = icon;
    }

    public Icon getIcon()
    {
        return icon;
    }

    public String getTreeIconName()
    {
        if (iconName != null)
        {
            return iconName;
        }
        else
        {
            String str = userObject.toString();
            int index = str.lastIndexOf(".");
            if (index != -1)
            {
                return str.substring(++index);
            }
            else
            {
                return null;
            }
        }
    }

    public void setTreeIconName(String name)
    {
        iconName = name;
    }

    public String getTreeNodeName()
    {
        return treeNodeName;
    }

    public void setTreeNodeName(String treeNodeName)
    {
        this.treeNodeName = treeNodeName;
    }

}