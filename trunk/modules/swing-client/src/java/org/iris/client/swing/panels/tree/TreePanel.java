package org.iris.client.swing.panels.tree;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.tree.*;

import org.apache.log4j.*;
import org.iris.client.swing.util.*;
import org.iris.client.swing.util.events.*;
import org.iris.server.util.config.*;

public class TreePanel
    extends JPanel
{
    Logger myLogger = Logger.getLogger(TreePanel.class.getName());
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    public static final String DISPLAYNAME_SETTINGS = "Settings";
    public static final String DISPLAYNAME_SERVICES = "Services";
    public static final String DISPLAYNAME_CLIENT = "Client";
    public static final String DISPLAYNAME_SERVER = "Server";
    public static final String DISPLAYNAME_LOGGING = "Logging";
    public static final String DISPLAYNAME_STATUS = "Status";
    public static final String DISPLAYNAME_CONFIG = "Config";
    public static final String DISPLAYNAME_GLOBALSETTINGS = "Global Settings";

    final static String SERVICEROOT_ICONNAME = "SERVICEROOT_ICONNAME";
    final static String SERVICES_GREEN_ICONNAME = "SERVICES_GREEN_ICONNAME";
    final static String SERVICES_RED_ICONNAME = "SERVICES_RED_ICONNAME";
    final static String GLOBALSETTINGS_ICONNAME = "GLOBALSETTINGS_ICONNAME";
    final static String NAMESERVICE_ICONNAME = "NAMESERVICE_ICONNAME";

    final static String FILETOFILE_GREEN_ICONNAME = "FILETOFILE_GREEN_ICONNAME";
    final static String FILETOFILE_RED_ICONNAME = "FILETOFILE_RED_ICONNAME";
    final static String FILETOFILE_GREY_ICONNAME = "FILETOFILE_GREY_ICONNAME";

    final static String FILETOEJB_GREEN_ICONNAME = "FILETOEJB_GREEN_ICONNAME";
    final static String FILETOEJB_RED_ICONNAME = "FILETOEJB_RED_ICONNAME";
    final static String FILETOEJB_GREY_ICONNAME = "FILETOEJB_GREY_ICONNAME";

    final static String FILETOQUEUE_GREEN_ICONNAME = "FILETOQUEUE_GREEN_ICONNAME";
    final static String FILETOQUEUE_RED_ICONNAME = "FILETOQUEUE_RED_ICONNAME";
    final static String FILETOQUEUE_GREY_ICONNAME = "FILETOQUEUE_GREY_ICONNAME";

    final static String FILETOCORBA_GREEN_ICONNAME = "FILETOCORBA_GREEN_ICONNAME";
    final static String FILETOCORBA_RED_ICONNAME = "FILETOCORBA_RED_ICONNAME";
    final static String FILETOCORBA_GREY_ICONNAME = "FILETOCORBA_GREY_ICONNAME";

    final static String FILETOEMAIL_GREEN_ICONNAME = "FILETOEMAIL_GREEN_ICONNAME";
    final static String FILETOEMAIL_RED_ICONNAME = "FILETOEMAIL_RED_ICONNAME";
    final static String FILETOEMAIL_GREY_ICONNAME = "FILETOEMAIL_GREY_ICONNAME";

    final static String POLLEJB_GREEN_ICONNAME = "POLLEJB_GREEN_ICONNAME";
    final static String POLLEJB_RED_ICONNAME = "POLLEJB_RED_ICONNAME";
    final static String POLLEJB_GREY_ICONNAME = "POLLEJB_GREY_ICONNAME";

    final static String LOG_ICONNAME = "LOG_ICONNAME";
    final static String CONFIGXML_ICONNAME = "CONFIGXML_ICONNAME";
    final static String SETTINGS_ICONNAME = "SETTINGS_ICONNAME";
    final static String GRAPH_ICONNAME = "GRAPH_ICONNAME";

    JPopupMenu myPopup = new JPopupMenu();
    private JTree myTree;
    private IconNode[] myTreeNodes;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jTreePanel = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JScrollPane jTreeScrollPane = new JScrollPane();
    private ArrayList arrServices;
    private IconNode selectedTreeIconNode;
    private static TreePanel myInstance;
    JMenuItem menuItemStart = new JMenuItem("Start");
    JMenuItem menuItemStop = new JMenuItem("Stop");
    JMenuItem menuItemPing = new JMenuItem("Alive?");
    JMenuItem menuIteRemotep = new JMenuItem(" ");
    JMenuItem menuIteCloneService = new JMenuItem("Clone service");

    Hashtable icons = new Hashtable();
    {
        icons.put(GRAPH_ICONNAME, ResourcesFactory.getImageIcon("graph.png"));
        icons.put(SETTINGS_ICONNAME, ResourcesFactory.getImageIcon("settings.png"));
        icons.put(GLOBALSETTINGS_ICONNAME, ResourcesFactory.getImageIcon("network.gif"));
        icons.put(SERVICEROOT_ICONNAME, ResourcesFactory.getImageIcon("root.gif"));
        icons.put(SERVICES_GREEN_ICONNAME, ResourcesFactory.getImageIcon("serviceswithgreencheckbox.gif"));
        icons.put(SERVICES_RED_ICONNAME, ResourcesFactory.getImageIcon("serviceswithredcross.gif"));
        icons.put(NAMESERVICE_ICONNAME, ResourcesFactory.getImageIcon("nameservices.gif"));

        icons.put(FILETOFILE_GREEN_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletofilegreen.gif"));
        icons.put(FILETOFILE_RED_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletofilered.gif"));
        icons.put(FILETOFILE_GREY_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletofilegrey.gif"));

        icons.put(FILETOQUEUE_GREEN_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletoqueuegreen.gif"));
        icons.put(FILETOQUEUE_RED_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletoqueuered.gif"));
        icons.put(FILETOQUEUE_GREY_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletoqueuegrey.gif"));

        icons.put(FILETOEJB_GREEN_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletoejbgreen.gif"));
        icons.put(FILETOEJB_RED_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletoejbred.gif"));
        icons.put(FILETOEJB_GREY_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletoejbgrey.gif"));

        icons.put(FILETOCORBA_GREEN_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletocorbagreen.gif"));
        icons.put(FILETOCORBA_RED_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletocorbared.gif"));
        icons.put(FILETOCORBA_GREY_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletocorbagrey.gif"));

        icons.put(FILETOEMAIL_GREEN_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletoejbgreen.gif"));
        icons.put(FILETOEMAIL_RED_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletoejbred.gif"));
        icons.put(FILETOEMAIL_GREY_ICONNAME, ResourcesFactory.getImageIcon("servicenodefiletoejbgrey.gif"));

        icons.put(POLLEJB_GREEN_ICONNAME, ResourcesFactory.getImageIcon("servicenodepollejbgreen.gif"));
        icons.put(POLLEJB_RED_ICONNAME, ResourcesFactory.getImageIcon("servicenodepollejbgreen.gif"));
        icons.put(POLLEJB_GREY_ICONNAME, ResourcesFactory.getImageIcon("servicenodepollejbgrey.gif"));

        icons.put(LOG_ICONNAME, ResourcesFactory.getImageIcon("logs.gif"));
        icons.put(CONFIGXML_ICONNAME, ResourcesFactory.getImageIcon("configxml.gif"));
    }

    private TreePanel(ArrayList services)
    {
        try
        {
            arrServices = services;
            myLogger.debug("Number of services " + arrServices.size());
            createTreeNodes();
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static TreePanel getInstance(ArrayList services)
    {
        if (myInstance == null)
        {
            myInstance = new TreePanel(services);
        }
        return myInstance;
    }

    public static TreePanel getInstance()
    {
        return myInstance;
    }

    void jbInit() throws Exception
    {
        initMenuPopUp();
        this.setLayout(borderLayout1);
        jTreePanel.setLayout(borderLayout2);
        jTreePanel.setBorder(BorderFactory.createEtchedBorder());
        this.add(jTreePanel, BorderLayout.CENTER);
        jTreePanel.add(jTreeScrollPane, BorderLayout.CENTER);

        myTree.putClientProperty("JTree.lineStyle", "Angled");
        myTree.setShowsRootHandles(true);
        jTreeScrollPane.getViewport().add(myTree, null);
    }

    /**
     * Build the tree nodes
     */
    private void createTreeNodes()
    {
        try
        {
            rootNode = new DefaultMutableTreeNode("Root Node");
            arrServices = ClientSystemConfigHandler.getInstance().getServices(true);
            Iterator iterServices = arrServices.iterator();
            int numberOfPreServiceNodes = 8;
            myTreeNodes = new IconNode[arrServices.size() * 3 + numberOfPreServiceNodes];

            myTreeNodes[0] = new IconNode(new TreeUserObject("Iris", null));
            myTreeNodes[1] = new IconNode(new TreeUserObject(DISPLAYNAME_GLOBALSETTINGS, null));
            myTreeNodes[2] = new IconNode(new TreeUserObject(DISPLAYNAME_SERVICES, null));

            myTreeNodes[0].add(myTreeNodes[1]);
            myTreeNodes[0].add(myTreeNodes[2]);

            myTreeNodes[0].setTreeIconName(SERVICEROOT_ICONNAME);
            myTreeNodes[1].setTreeIconName(GLOBALSETTINGS_ICONNAME);
            myTreeNodes[2].setTreeIconName(SERVICES_GREEN_ICONNAME);

            myTreeNodes[3] = new IconNode(new TreeUserObject(DISPLAYNAME_LOGGING, null));
            myTreeNodes[3].setTreeIconName(LOG_ICONNAME);
            myTreeNodes[1].add(myTreeNodes[3]);

            myTreeNodes[4] = new IconNode(new TreeUserObject(DISPLAYNAME_CONFIG, null));
            myTreeNodes[4].setTreeIconName(CONFIGXML_ICONNAME);
            myTreeNodes[1].add(myTreeNodes[4]);

            myTreeNodes[5] = new IconNode(new TreeUserObject(DISPLAYNAME_SERVER, null));
            myTreeNodes[5].setTreeIconName(CONFIGXML_ICONNAME);
            myTreeNodes[4].add(myTreeNodes[5]);

            myTreeNodes[6] = new IconNode(new TreeUserObject(DISPLAYNAME_CLIENT, null));
            myTreeNodes[6].setTreeIconName(CONFIGXML_ICONNAME);
            myTreeNodes[4].add(myTreeNodes[6]);

            myTreeNodes[7] = new IconNode(new TreeUserObject(DISPLAYNAME_SETTINGS, null));
            myTreeNodes[7].setTreeIconName(SETTINGS_ICONNAME);
            myTreeNodes[1].add(myTreeNodes[7]);

            // start adding service nodes and children to those eg config and settings
            int serviceNodeCounter = numberOfPreServiceNodes;
            while (iterServices.hasNext())
            {
                Object obj = iterServices.next();
                Service ser = null;
                if (obj instanceof Service)
                {
                    ser = (Service) obj;
                }
                int currentServiceNode = serviceNodeCounter;
                myTreeNodes[currentServiceNode] = new IconNode(new TreeUserObject(ser.getId(), ser.getId())); //new IconNode("Service " + ser.getId() );
                myTreeNodes[2].add(myTreeNodes[currentServiceNode]);
                serviceNodeCounter++;

                String currentServiceNodeIcon = "";
                String currentServiceName = "";
                if (obj instanceof ServiceFileFile)
                {
                    ServiceFileFile service = (ServiceFileFile) obj;
                    currentServiceNodeIcon = FILETOFILE_GREEN_ICONNAME;
                    currentServiceName = service.getId();
                    myTreeNodes[serviceNodeCounter] = new IconNode(new TreeUserObject(DISPLAYNAME_CONFIG,
                        currentServiceName)); //  - " + service.getId());
                }
                else if (obj instanceof ServiceFileEJB)
                {
                    ServiceFileEJB service = (ServiceFileEJB) obj;
                    currentServiceNodeIcon = FILETOEJB_GREEN_ICONNAME;
                    currentServiceName = service.getId();
                    myTreeNodes[serviceNodeCounter] = new IconNode(new TreeUserObject(DISPLAYNAME_CONFIG,
                        currentServiceName)); //service );//new IconNode("Config - " + service.getId());
                }
                else if (obj instanceof ServiceFileQueue)
                {
                    ServiceFileQueue service = (ServiceFileQueue) obj;
                    currentServiceNodeIcon = FILETOQUEUE_GREEN_ICONNAME;
                    currentServiceName = service.getId();
                    myTreeNodes[serviceNodeCounter] = new IconNode(new TreeUserObject(DISPLAYNAME_CONFIG,
                        currentServiceName));
                }
                else if (obj instanceof ServiceFileCORBA)
                {
                    ServiceFileCORBA service = (ServiceFileCORBA) obj;
                    currentServiceNodeIcon = FILETOCORBA_GREEN_ICONNAME;
                    currentServiceName = service.getId();
                    myTreeNodes[serviceNodeCounter] = new IconNode(new TreeUserObject(DISPLAYNAME_CONFIG,
                        currentServiceName));

                }
                else if (obj instanceof ServicePollEJB)
                {
                    ServicePollEJB service = (ServicePollEJB) obj;
                    currentServiceNodeIcon = POLLEJB_GREEN_ICONNAME;
                    currentServiceName = service.getId();
                    myTreeNodes[serviceNodeCounter] = new IconNode(new TreeUserObject(DISPLAYNAME_CONFIG,
                        currentServiceName));
                }
                else if (obj instanceof ServiceFileEmail)
                {
                    ServiceFileEmail service = (ServiceFileEmail) obj;
                    currentServiceNodeIcon = FILETOEMAIL_GREEN_ICONNAME;
                    currentServiceName = service.getId();
                    myTreeNodes[serviceNodeCounter] = new IconNode(new TreeUserObject(DISPLAYNAME_CONFIG,
                        currentServiceName));
                }
                myTreeNodes[serviceNodeCounter].setTreeIconName(SETTINGS_ICONNAME);

                myTreeNodes[currentServiceNode].setTreeIconName(currentServiceNodeIcon);
                myTreeNodes[currentServiceNode].add(myTreeNodes[serviceNodeCounter]);
                serviceNodeCounter++;

                myTreeNodes[serviceNodeCounter] = new IconNode(new TreeUserObject(DISPLAYNAME_STATUS,
                                                  currentServiceName));
                myTreeNodes[serviceNodeCounter].setTreeIconName(GRAPH_ICONNAME);
                myTreeNodes[currentServiceNode].add(myTreeNodes[serviceNodeCounter]);
                serviceNodeCounter++;

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        myTree = new JTree(myTreeNodes[0]);
        myTree.putClientProperty("JTree.icons", icons);
        myTree.setSelectionRow(1);
        myTree.expandRow(1);
        myTree.expandRow(2);
        myTree.setCellRenderer(new IconNodeRenderer());
        setImageObserver(myTree, myTreeNodes, icons);

        MouseListener ml = new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
//                myPopup.show( this, e.getX(), e.getY() );

                int selRow = myTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = myTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1)
                {
                    if (e.getClickCount() == 1)
                    {
                        singleClickInTree(selRow, selPath);
                    }
                    /*                    else if (e.getClickCount() == 2)
                                        {
                                            doubleClickInTree(selRow, selPath);
                                        }
                     */ if (e.getButton() == e.BUTTON3)
                    {
                        showPopup(e.getX(), e.getY(), selPath);
                    }
                }
            }
        };
        myTree.addMouseListener(ml);
    }

    public void setTreeNodeIconToRed(String nodeName)
    {
        myLogger.debug("Setting node to red : " + nodeName);
        if (myTreeNodes == null)
        {
            return;
        }
        for (int serviceNodeCounter = 0; serviceNodeCounter < myTreeNodes.length; serviceNodeCounter++)
        {
            Object userObject = myTreeNodes[serviceNodeCounter].getUserObject();
            if (userObject instanceof TreeUserObject)
            {
                TreeUserObject treeUserObject = (TreeUserObject) userObject;
                // if it is a service node and we got a match
                if (treeUserObject.getServiceName() != null && treeUserObject.getDisplayName().equals(nodeName))
                {
                    myLogger.debug("Found node setting it to red " + treeUserObject.getServiceName() + "  " +
                        treeUserObject.getDisplayName());
                    int position = myTreeNodes[serviceNodeCounter].getTreeIconName().indexOf("_");
                    String iconKey = myTreeNodes[serviceNodeCounter].getTreeIconName().substring(0,
                                     position) + "_RED_ICONNAME";
                    myLogger.debug("iconKey : " + iconKey);
                    myTreeNodes[serviceNodeCounter].setIcon( (ImageIcon) icons.get(iconKey));
                    //myTree.updateUI();
                    myTree.validate();
                    myTree.repaint();
                }
            }
        }
    }

    public void setTreeNodeIconToGreen(String nodeName)
    {
        if (myTreeNodes == null)
        {
            return;
        }
        for (int serviceNodeCounter = 0; serviceNodeCounter < myTreeNodes.length; serviceNodeCounter++)
        {
            Object userObject = myTreeNodes[serviceNodeCounter].getUserObject();
            if (userObject instanceof TreeUserObject)
            {
                TreeUserObject treeUserObject = (TreeUserObject) userObject;
                // if it is a service node and we got a match
                if (treeUserObject.getServiceName() != null && treeUserObject.getDisplayName().equals(nodeName))
                {
                    try
                    {
                        int position = myTreeNodes[serviceNodeCounter].getTreeIconName().indexOf("_");
                        String key = myTreeNodes[serviceNodeCounter].getTreeIconName().substring(0,
                                     position) + "_GREEN_ICONNAME";
                        myTreeNodes[serviceNodeCounter].setIcon( (ImageIcon) icons.get(key));
                    }
                    catch (Exception ex)
                    {
                        myLogger.warn("Failed setting node to green : " + nodeName + " ");
                    }
                }
            }
        }
        myTree.validate();
        myTree.repaint();
    }

    public void setTreeNodeIconAndDescendentsToRed(String nodeName)
    {
        if (myTreeNodes == null)
        {
            return;
        }
        for (int serviceNodeCounter = 0; serviceNodeCounter < myTreeNodes.length; serviceNodeCounter++)
        {
            if ( ( (String) myTreeNodes[serviceNodeCounter].getUserObject()).equals(nodeName))
            {
                String key = myTreeNodes[serviceNodeCounter].getTreeIconName().substring(0,
                             (myTreeNodes[serviceNodeCounter].getTreeIconName().length() - 5)) + "RED";
                myTreeNodes[serviceNodeCounter].setIcon( (ImageIcon) icons.get(key));
                Enumeration enum = myTreeNodes[serviceNodeCounter].children();
                while (enum.hasMoreElements())
                {
                    IconNode child = (IconNode)enum.nextElement();
                    key = child.getTreeIconName().substring(0, (child.getTreeIconName().length() - 5)) + "GREY";
                    System.out.println("key  " + key);
                    child.setIcon( (ImageIcon) icons.get(key));
                }
                serviceNodeCounter = myTreeNodes.length;
            }
        }
        myTree.validate();
        myTree.repaint();
    }

    private void showPopup(int x, int y, TreePath selPath)
    {
        if (selPath.getParentPath() != null && selPath.getPathCount() == 3)
        {
            myPopup.setBorderPainted(true);
            myPopup.setSelected(myPopup.getComponent(2));
            myPopup.show(this, x + 30, y - 15);
        }
    }

    private void doubleClickInTree(int row, TreePath selPath)
    {
        // myLogger.debug("row : " + row + "  selPath " + selPath);
    }

    private void singleClickInTree(int row, TreePath selPath)
    {
        //   myLogger.debug("row : " + row + "  selPath " + selPath + "  SELPATH -1 " + selPath.getPathCount());
        if (selPath.getParentPath() != null) //&&   selPath.getPathCount()  == 4 )
        {
            setSelectedTreeNode( (IconNode) selPath.getLastPathComponent());
            ApplicationEventHandler.getInstance().fireDataChanged(new ApplicationStateEvent(this, "Tree clicked",
                ApplicationStateEvent.TREECLICKED));
        }
    }

    class NodeImageObserver
        implements ImageObserver
    {
        JTree tree;
        DefaultTreeModel model;
        Vector myTreeNodes;

        NodeImageObserver(JTree tree, Vector myTreeNodes)
        {
            this.tree = tree;
            this.model = (DefaultTreeModel) tree.getModel();
            this.myTreeNodes = myTreeNodes;
        }

        public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h)
        {
            if ( (flags & (FRAMEBITS | ALLBITS)) != 0)
            {
                Enumeration enum = myTreeNodes.elements();
                while (enum.hasMoreElements())
                {
                    TreeNode node = (TreeNode)enum.nextElement();
                    TreePath path = new TreePath(model.getPathToRoot(node));
                    Rectangle rect = tree.getPathBounds(path);
                    if (rect != null)
                    {
                        tree.repaint(rect);
                    }
                }
            }
            return (flags & (ALLBITS | ABORT)) == 0;
        }
    }

    private void setImageObserver(JTree tree, IconNode[] myTreeNodes, Hashtable icons)
    {
        Hashtable observers = new Hashtable();

        for (int serviceNodeCounter = 0; serviceNodeCounter < myTreeNodes.length; serviceNodeCounter++)
        {
            if (myTreeNodes[serviceNodeCounter] == null)
            {
                return;
            }
            ImageIcon currentServiceNodeIcon = (ImageIcon) myTreeNodes[serviceNodeCounter].getIcon();
            if (currentServiceNodeIcon != null)
            {
                Vector repaintNodes = new Vector();
                repaintNodes.addElement(myTreeNodes[serviceNodeCounter]);
                currentServiceNodeIcon.setImageObserver(new NodeImageObserver(tree, repaintNodes));
            }
            else
            {
                String iconName = myTreeNodes[serviceNodeCounter].getTreeIconName();
                if (iconName != null)
                {
                    Vector repaintNodes = (Vector) observers.get(iconName);
                    if (repaintNodes == null)
                    {
                        repaintNodes = new Vector();
                        observers.put(iconName, repaintNodes);
                    }
                    repaintNodes.addElement(myTreeNodes[serviceNodeCounter]);
                }
            }
        }

        Enumeration enum = observers.keys();
        while (enum.hasMoreElements())
        {
            String iconName = (String)enum.nextElement();
            Vector repaintNodes = (Vector) observers.get(iconName);
            ImageIcon currentServiceNodeIcon = (ImageIcon) icons.get(iconName);
            currentServiceNodeIcon.setImageObserver(new NodeImageObserver(tree, repaintNodes));
        }
    }

    public IconNode getSelectedTreeNode()
    {
        return selectedTreeIconNode;
    }

    public void setSelectedTreeNode(IconNode selectedIconNode)
    {
        this.selectedTreeIconNode = selectedIconNode;
    }

    void menuItemStart_actionPerformed(ActionEvent e)
    {
        ApplicationEventHandler.getInstance().fireDataChanged(new ApplicationStateEvent(this, "START SERVICE",
            ApplicationStateEvent.STARTREMOTESERVICE));
        myLogger.info("Calling start for service ");
    }

    void menuItemStop_actionPerformed(ActionEvent e)
    {
        ApplicationEventHandler.getInstance().fireDataChanged(new ApplicationStateEvent(this, "STOP SERVICE",
            ApplicationStateEvent.STOPREMOTESERVICE));
        myLogger.info("Calling stop for service ");

    }

    private void initMenuPopUp()
    {
        menuItemStart.setIcon(ResourcesFactory.getImageIcon("Play24.gif"));
        menuItemStart.addActionListener(new TreePanel_menuItemStart_actionAdapter(this));
        menuItemStop.addActionListener(new TreePanel_menuItemStop_actionAdapter(this));
        menuIteRemotep.setEnabled(false);
        menuItemPing.setIcon(ResourcesFactory.getImageIcon("Play24.gif"));
        menuItemPing.addActionListener(new TreePanel_menuItemIsAlive_actionAdapter(this));
        menuIteCloneService.addActionListener(new TreePanel_menuIteCloneService_actionAdapter(this));
        myPopup.add(menuIteRemotep);
        myPopup.addSeparator();
        myPopup.add(menuItemStart);

        menuItemStop.setIcon(ResourcesFactory.getImageIcon("Stop24.gif"));
        myPopup.add(menuItemStop);
        myPopup.add(menuItemPing);

        menuIteCloneService.setIcon(ResourcesFactory.getImageIcon("Add24.gif"));
        myPopup.add(menuIteCloneService);

        add(myPopup);
    }

    void menuItemIsAlive_actionPerformed(ActionEvent e)
    {
        ApplicationEventHandler.getInstance().fireDataChanged(new ApplicationStateEvent(this, "PING SERVICE",
            ApplicationStateEvent.PINGREMOTESERVICE));
    }

    void menuIteCloneService_actionPerformed(ActionEvent e)
    {

        ApplicationEventHandler.getInstance().fireDataChanged(new ApplicationStateEvent(this, "CLONING SERVICE",
            ApplicationStateEvent.CLONINGSERVICEWIZARD));
        myLogger.info("Cloning service");

    }
}

class TreePanel_menuItemStart_actionAdapter
    implements java.awt.event.ActionListener
{
    TreePanel adaptee;

    TreePanel_menuItemStart_actionAdapter(TreePanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.menuItemStart_actionPerformed(e);
    }
}

class TreePanel_menuItemStop_actionAdapter
    implements java.awt.event.ActionListener
{
    TreePanel adaptee;

    TreePanel_menuItemStop_actionAdapter(TreePanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.menuItemStop_actionPerformed(e);
    }
}

class TreePanel_menuItemIsAlive_actionAdapter
    implements java.awt.event.ActionListener
{
    TreePanel adaptee;

    TreePanel_menuItemIsAlive_actionAdapter(TreePanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.menuItemIsAlive_actionPerformed(e);
    }
}

class TreePanel_menuIteCloneService_actionAdapter
    implements java.awt.event.ActionListener
{
    TreePanel adaptee;
    TreePanel_menuIteCloneService_actionAdapter(TreePanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.menuIteCloneService_actionPerformed(e);
    }
}
