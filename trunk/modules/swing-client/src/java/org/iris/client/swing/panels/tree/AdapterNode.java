package org.iris.client.swing.panels.tree;

/**
 * Created by IntelliJ IDEA.
 * User: gepo
 * Date: 2004-jun-24
 * Time: 11:11:01
 * To change this template use File | Settings | File Templates.
 */
public class AdapterNode
{
    boolean compress = false;
    static final int ELEMENT_TYPE = 1;
    static final int ATTR_TYPE = 2;
    static final int TEXT_TYPE = 3;
    static final int CDATA_TYPE = 4;
    static final int ENTITYREF_TYPE = 5;
    static final int ENTITY_TYPE = 6;
    static final int PROCINSTR_TYPE = 7;
    static final int COMMENT_TYPE = 8;
    static final int DOCUMENT_TYPE = 9;
    static final int DOCTYPE_TYPE = 10;
    static final int DOCFRAG_TYPE = 11;
    static final int NOTATION_TYPE = 12;
    static final String[] typeName =
        {
        "none", "Element", "Attr", "Text", "CDATA", "EntityRef", "Entity", "ProcInstr", "Comment", "Document",
        "DocType", "DocFragment", "Notation",
    };

    // The list of elements to display in the tree
    // Could set this with a command-line argument, but
    // not much point -- the list of tree elements still
    // has to be defined internally.
    // Extra credit: Read the list from a file
    // Super-extra credit: Process a DTD and build the list.
    static String[] treeElementNames =
                                       {
                                       "slideshow", "slide", "title", "slide-title", "item", };

    org.w3c.dom.Node domNode;

    // Construct an Adapter node from a DOM node
    public AdapterNode(org.w3c.dom.Node node)
    {
        domNode = node;
    }

    // Return a string that identifies this node in the tree
    // *** Refer to table at top of org.w3c.dom.Node ***
    public String toString()
    {
        String s = typeName[domNode.getNodeType()];
        System.out.println("T" + s);
        String nodeName = domNode.getNodeName();
        System.out.println("nodeName : " + nodeName);
        if (!nodeName.startsWith("#"))
        {
            s += ": " + nodeName;
        }
        if (compress)
        {
            String t = content().trim();
            int x = t.indexOf("\n");
            if (x >= 0)
            {
                t = t.substring(0, x);
            }
            s += " " + t;
            return s;
        }
        if (domNode.getNodeValue() != null)
        {
            if (s.startsWith("ProcInstr"))
            {
                s += ", ";
            }
            else
            {
                s += ": ";
                // Trim the value to get rid of NL's at the front
            }
            String t = domNode.getNodeValue().trim();
            int x = t.indexOf("\n");
            if (x >= 0)
            {
                t = t.substring(0, x);
            }
            s += t;
        }
        return s;
    }

    boolean treeElement(String elementName)
    {
        for (int i = 0; i < treeElementNames.length; i++)
        {
            System.out.println("element" + elementName);
            System.out.println("treeElementNames[i]" + treeElementNames[i]);
            if (elementName.equals(treeElementNames[i]))
            {
                return true;
            }
        }
        return false;
    }

    public String content()
    {
        String s = "";
        org.w3c.dom.NodeList nodeList = domNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            org.w3c.dom.Node node = nodeList.item(i);
            int type = node.getNodeType();
            AdapterNode adpNode = new AdapterNode(node); //inefficient, but works
            if (type == ELEMENT_TYPE)
            {
                // Skip subelements that are displayed in the tree.
                if (treeElement(node.getNodeName()))
                {
                    continue;
                }

                // EXTRA-CREDIT HOMEWORK:
                //   Special case the SLIDE element to use the TITLE text
                //   and ignore TITLE element when constructing the tree.

                // EXTRA-CREDIT
                //   Convert ITEM elements to html lists using
                //   <ul>, <li>, </ul> tags

                s += "<" + node.getNodeName() + ">";
                s += adpNode.content();
                s += "</" + node.getNodeName() + ">";
            }
            else if (type == TEXT_TYPE)
            {
                s += node.getNodeValue();
            }
            else if (type == ENTITYREF_TYPE)
            {
                // The content is in the TEXT node under it
                s += adpNode.content();
            }
            else if (type == CDATA_TYPE)
            {
                // The "value" has the text, same as a text node.
                //   while EntityRef has it in a text node underneath.
                //   (because EntityRef can contain multiple subelements)
                // Convert angle brackets and ampersands for display
                StringBuffer sb = new StringBuffer(node.getNodeValue());
                for (int j = 0; j < sb.length(); j++)
                {
                    if (sb.charAt(j) == '<')
                    {
                        sb.setCharAt(j, '&');
                        sb.insert(j + 1, "lt;");
                        j += 3;
                    }
                    else if (sb.charAt(j) == '&')
                    {
                        sb.setCharAt(j, '&');
                        sb.insert(j + 1, "amp;");
                        j += 4;
                    }
                }
                s += "<pre>" + sb + "\n</pre>";
            }
            // Ignoring these:
            //   ATTR_TYPE      -- not in the DOM tree
            //   ENTITY_TYPE    -- does not appear in the DOM
            //   PROCINSTR_TYPE -- not "data"
            //   COMMENT_TYPE   -- not "data"
            //   DOCUMENT_TYPE  -- Root node only. No data to display.
            //   DOCTYPE_TYPE   -- Appears under the root only
            //   DOCFRAG_TYPE   -- equiv. to "document" for fragments
            //   NOTATION_TYPE  -- nothing but binary data in here
        }
        return s;
    }

    /*
     * Return children, index, and count values
     */
    public int index(AdapterNode child)
    {
        //System.err.println("Looking for index of " + child);
        int count = childCount();
        for (int i = 0; i < count; i++)
        {
            AdapterNode n = this.child(i);
            if (child.domNode == n.domNode)
            {
                return i;
            }
        }
        return -1; // Should never get here.
    }

    public AdapterNode child(int searchIndex)
    {
        //Note: JTree index is zero-based.
        org.w3c.dom.Node node = domNode.getChildNodes().item(searchIndex);
        if (compress)
        {
            // Return Nth displayable node
            int elementNodeIndex = 0;
            for (int i = 0; i < domNode.getChildNodes().getLength(); i++)
            {
                node = domNode.getChildNodes().item(i);
                if (node.getNodeType() == ELEMENT_TYPE && treeElement(node.getNodeName()) &&
                    elementNodeIndex++ == searchIndex)
                {
                    break;
                }
            }
        }
        return new AdapterNode(node);
    }

    public int childCount()
    {
        if (!compress)
        {
            // Indent this
            return domNode.getChildNodes().getLength();
        }
        int count = 0;
        for (int i = 0; i < domNode.getChildNodes().getLength(); i++)
        {
            org.w3c.dom.Node node = domNode.getChildNodes().item(i);
            if (node.getNodeType() == ELEMENT_TYPE && treeElement(node.getNodeName()))
            {
                // Note:
                //   Have to check for proper type.
                //   The DOCTYPE element also has the right name
                ++count;
            }
        }
        return count;
    }
}