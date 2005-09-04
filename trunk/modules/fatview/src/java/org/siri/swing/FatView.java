package org.siri.swing;



import com.jidesoft.docking.*;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideScrollPane;
import com.jidesoft.utils.Lm;
import com.jidesoft.utils.SystemInfo;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.Iterator;

/**
 * This is a sample program for JIDE Docking Framework. It will create a JFrame with about
 * 20 dockable frames to show the features of JIDE Docking Framework.
 * <br>
 * Required jar files: jide-common.jar, jide-dock.jar
 * <br>
 * Required L&F: Jide L&F extension required
 */
public class FatView
    extends DefaultDockableHolder
{

    private static FatView frame;

    private static final String PROFILE_NAME = "jidesoft";

    private static boolean autohideAll = false;
    private static WindowAdapter windowListener;
    public static JMenuItem redoMenuItem;
    public static JMenuItem undoMenuItem;
    public static FatViewToolbar fatViewToolbar;

    public FatView(String title) throws HeadlessException
    {
        super(title);
    }

    public static void main(String[] args)
    {
     //   FatView fv = new FatView("FatView");
     //   fv.
            initView();
    }

    private static void initView() throws HeadlessException
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException e)
        {
        }
        catch (InstantiationException e)
        {
        }
        catch (IllegalAccessException e)
        {
        }
        catch (UnsupportedLookAndFeelException e)
        {
        }

        // Add additional UIDefault used by JIDE to UIDefault table. You have an option to specify
        // a style. If leaving it as empty, it will use VSNET_STYLE.
        LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE);

        frame = new FatView("Demo of JIDE Docking Framework");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setIconImage(JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32).getImage());



        // Set the parent of message boxes that come from Lm class. You don't need to call it.
        Lm.setParent(frame);

        // add a widnow listener to do clear up when windows closing.
        windowListener = new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                clearUp();
            }
        };
        frame.addWindowListener(windowListener);

        // Set the profile key
        frame.getDockingManager().setProfileKey(PROFILE_NAME);

// comment this if you don't want to use javax pref
//        frame.getDockingManager().setUsePref(false);

        // Uses light-weight outline. There are several options here.
        frame.getDockingManager().setOutlineMode(DockingManager.PARTIAL_OUTLINE_MODE);

// uncomment following lines to adjust the sliding speed of autohide frame
        frame.getDockingManager().setInitDelay(100);
        frame.getDockingManager().setSteps(1);
        frame.getDockingManager().setStepDelay(0);





        // add menu bar
        frame.setJMenuBar(createMenuBar());



        // Sets the number of steps you allow user to undo.
        frame.getDockingManager().setUndoLimit(10);
        frame.getDockingManager().addUndoableEditListener(new UndoableEditListener()
        {
            public void undoableEditHappened(UndoableEditEvent e)
            {
                refreshUndoRedoMenuItems();
            }
        });

        // Now let's start to addFrame()
        frame.getDockingManager().beginLoadLayoutData();

        frame.getDockingManager().setInitSplitPriority(DefaultDockingManager.SPLIT_SOUTH_NORTH_EAST_WEST);

        // add all dockable frames
        frame.getDockingManager().addFrame(createServicesExplorerViewFrame());

        frame.getDockingManager().addFrame(createServiceViewFrame("Service X"));

        frame.getDockingManager().loadLayoutDataFrom("design");
        // load layout information from previous session. This indicates the end of beginLoadLayoutData() method above.
        frame.getDockingManager().loadLayoutData();

        // disallow drop dockable frame to workspace area
        frame.getDockingManager().getWorkspace().setAcceptDockableFrame(false);




// uncomment following line(s) if you want to limit certain feature.
// If you uncomment all four lines, then the dockable frame will not
// be moved anywhere.
//        frame.getDockingManager().setRearrangable(false);
//        frame.getDockingManager().setAutohidable(false);
//        frame.getDockingManager().setFloatable(false);
//        frame.getDockingManager().setHidable(false);
        frame.toFront();
    }

    private static void clearUp()
    {
        frame.removeWindowListener(windowListener);
        windowListener = null;

        if (frame.getDockingManager() != null)
        {
            frame.getDockingManager().saveLayoutData();
        }

        frame.dispose();
        Lm.setParent(null);
        frame = null;
    }

    protected static DockableFrame createServicesExplorerViewFrame()
    {
        DockableFrame frame = new DockableFrame("Services",
                              JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME5));
        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
        frame.getContext().setInitIndex(0);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(createScrollPane(new JTextArea()));
        frame.setPreferredSize(new Dimension(200, 200));
        return frame;

    }

    protected static DockableFrame createServiceViewFrame(String service)
    {
        DockableFrame frame = new DockableFrame(service,
                              JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME5));
        frame.getContext().setInitMode(DockContext.STATE_FLOATING);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_CENTER);
        frame.getContext().setInitIndex(1);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(createScrollPane(new JTextArea()));
        frame.setPreferredSize(new Dimension(200, 200));
        return frame;

    }


    protected static JMenuBar createMenuBar()
    {
        JMenuBar menu = new JMenuBar();

        JMenu fileMenu = createFileMenu();
        JMenu viewMenu = createViewMenu();
        JMenu windowMenu = createWindowsMenu();
        JMenu optionMenu = createOptionMenu();
        JMenu lnfMenu = createLnfMenu();
        JMenu helpMenu = createHelpMenu();

        menu.add(fileMenu);
        menu.add(viewMenu);
        menu.add(windowMenu);
        menu.add(optionMenu);
        menu.add(lnfMenu);
        menu.add(helpMenu);

        return menu;
    }

    private static JScrollPane createScrollPane(Component component)
    {
        JScrollPane pane = new JideScrollPane(component);
        return pane;
    }

    private static JMenu createHelpMenu()
    {
        JMenu menu = new JMenu("Help");
        menu.setMnemonic('H');

        JMenuItem item = new JMenuItem("About JIDE Products");
        item.setMnemonic('A');
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                Lm.showAboutMessageBox();
            }
        });
        menu.add(item);

        return menu;
    }

    private static JMenu createWindowsMenu()
    {
        JMenu menu = new JMenu("Window");
        menu.setMnemonic('W');

        JMenuItem item = null;

        undoMenuItem = new JMenuItem("Undo");
        menu.add(undoMenuItem);
        redoMenuItem = new JMenuItem("Redo");
        menu.add(redoMenuItem);
        undoMenuItem.setEnabled(false);
        redoMenuItem.setEnabled(false);

        undoMenuItem.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().undo();
                refreshUndoRedoMenuItems();
            }
        });
        redoMenuItem.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().redo();
                refreshUndoRedoMenuItems();
            }
        });

        menu.addSeparator();

        item = new JMenuItem("Load Default Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().loadLayoutData();
            }
        });
        menu.add(item);

        item = new JMenuItem("Load Design Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().loadLayoutDataFrom("design");
            }
        });
        menu.add(item);

        item = new JMenuItem("Load Debug Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().loadLayoutDataFrom("debug");
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Save as Default Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().saveLayoutData();
            }
        });
        menu.add(item);

        item = new JMenuItem("Save as Design Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().saveLayoutDataAs("design");
            }
        });
        menu.add(item);

        item = new JMenuItem("Save as Debug Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().saveLayoutDataAs("debug");
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Reset Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().setUseFrameBounds(false);
                frame.getDockingManager().setUseFrameState(false);
                frame.getDockingManager().resetToDefault();
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Toggle Auto Hide All");
        item.setMnemonic('T');
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!autohideAll)
                {
                    frame.getDockingManager().saveLayoutDataAs("fullscreen");
                    frame.getDockingManager().autohideAll();
                    autohideAll = true;
                }
                else
                {
                    // call next two methods so that the farme bounds and state will not change.
                    frame.getDockingManager().setUseFrameBounds(false);
                    frame.getDockingManager().setUseFrameState(false);
                    frame.getDockingManager().loadLayoutDataFrom("fullscreen");
                    autohideAll = false;

                }
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Show Workspace Area");
        item.setMnemonic('S');
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().showWorkspace();
            }
        });
        menu.add(item);

        item = new JMenuItem("Hide Workspace Area");
        item.setMnemonic('H');
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().hideWorkspace();
            }
        });
        menu.add(item);

        return menu;
    }

    private static void refreshUndoRedoMenuItems()
    {
        undoMenuItem.setEnabled(frame.getDockingManager().getUndoManager().canUndo());
        undoMenuItem.setText(frame.getDockingManager().getUndoManager().getUndoPresentationName());
        redoMenuItem.setEnabled(frame.getDockingManager().getUndoManager().canRedo());
        redoMenuItem.setText(frame.getDockingManager().getUndoManager().getRedoPresentationName());
    }

    private static JMenu createViewMenu()
    {
        JMenuItem item;
        JMenu menu = new JMenu("View");
        menu.setMnemonic('V');

        item = new JMenuItem("Project View", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME1));
        item.setMnemonic('P');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getDockingManager().showFrame("Project View");
            }
        });
        menu.add(item);
        menu.addSeparator();

        return menu;
    }

    private static JMenu createOptionMenu()
    {
        JMenu menu = new JMenu("Options");
        menu.setMnemonic('P');

        JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem("Frames Floatable");
        checkBoxMenuItem.setMnemonic('F');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                    frame.getDockingManager().setFloatable( ( (JCheckBoxMenuItem) e.getSource()).isSelected());
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isFloatable());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Autohidable");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                    frame.getDockingManager().setAutohidable( ( (JCheckBoxMenuItem) e.getSource()).isSelected());
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isAutohidable());

        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Hidable");
        checkBoxMenuItem.setMnemonic('H');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                    frame.getDockingManager().setHidable( ( (JCheckBoxMenuItem) e.getSource()).isSelected());
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isHidable());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Rearrangable");
        checkBoxMenuItem.setMnemonic('R');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                    frame.getDockingManager().setRearrangable( ( (JCheckBoxMenuItem) e.getSource()).isSelected());
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isHidable());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Resizable");
        checkBoxMenuItem.setMnemonic('S');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                    frame.getDockingManager().setResizable( ( (JCheckBoxMenuItem) e.getSource()).isSelected());
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isResizable());
        menu.add(checkBoxMenuItem);

        menu.addSeparator();

        JMenu buttonsMenu = new JMenu("Available Titlebar Buttons");

        checkBoxMenuItem = new JCheckBoxMenuItem("Close Button");
        checkBoxMenuItem.setMnemonic('C');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                boolean selected = ( (JCheckBoxMenuItem) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_CLOSE);
            }
        });
        checkBoxMenuItem.setSelected(true);
        buttonsMenu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Autohide Button");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                boolean selected = ( (JCheckBoxMenuItem) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_AUTOHIDE);
            }
        });
        checkBoxMenuItem.setSelected(true);
        buttonsMenu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Float Button");
        checkBoxMenuItem.setMnemonic('F');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                boolean selected = ( (JCheckBoxMenuItem) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_FLOATING);
            }
        });
        checkBoxMenuItem.setSelected(true);
        buttonsMenu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Maximize Button");
        checkBoxMenuItem.setMnemonic('M');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                boolean selected = ( (JCheckBoxMenuItem) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_MAXIMIZE);
            }
        });
        checkBoxMenuItem.setSelected(false);
        buttonsMenu.add(checkBoxMenuItem);

        menu.add(buttonsMenu);

        menu.addSeparator();

        checkBoxMenuItem = new JCheckBoxMenuItem("Continuous Layout");
        checkBoxMenuItem.setMnemonic('C');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    frame.getDockingManager().setContinuousLayout( ( (JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (frame.getDockingManager().isContinuousLayout())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                            "<B><FONT FACE='Tahoma' SIZE='4' COLOR='#0000FF'>Continuous Layout</FONT></B><FONT FACE='Tahoma'>" +
                            "<FONT FACE='Tahoma' SIZE='3'><BR><BR><B>An option to continuously layout affected components during resizing." +
                            "<BR></B><BR>This is the same option as in JSplitPane. If the option is true, when you resize" +
                            "<BR>the JSplitPane's divider, it will continuously redisplay and laid out during user" +
                            "<BR>intervention." +
                            "<BR><BR>Default: off</FONT>" +
                            "<BR></HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isContinuousLayout());
        menu.add(checkBoxMenuItem);

        menu.addSeparator();

        checkBoxMenuItem = new JCheckBoxMenuItem("Easy Tab Docking");
        checkBoxMenuItem.setMnemonic('E');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    frame.getDockingManager().setEasyTabDock( ( (JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (frame.getDockingManager().isEasyTabDock())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                            "<B><FONT COLOR='BLUE' FACE='Tahoma' SIZE='4'>Easy Tab Docking </FONT></B>" +
                            "<BR><BR><FONT FACE='Tahoma' SIZE='3'><B>An option to make the tab-docking of a dockable frame easier</B>" +
                            "<BR><BR>It used to be dragging a dockable frame and pointing to the title" +
                            "<BR>bar of another dockable frame to tab-dock with it. However if " +
                            "<BR>this option on, pointing to the middle portion of any dockable " +
                            "<BR>frame will tab-dock with that frame." +
                            "<BR><BR>Default: off</FONT>" +
                            "<BR></HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isEasyTabDock());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Title Bar Dragging All Tabs");
        checkBoxMenuItem.setMnemonic('T');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    frame.getDockingManager().setDragAllTabs( ( (JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (frame.getDockingManager().isDragAllTabs())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                            "<B><FONT COLOR='BLUE' FACE='Tahoma' SIZE='4'>Title Bar Dragging All Tabs</FONT></B>" +
                            "<BR><BR><FONT FACE='Tahoma' SIZE='3'><B>An option for title bar dragging</B>" +
                            "<BR><BR>If user drags the title bar, by default, all tabs in the tabbed pane will be dragged." +
                            "<BR>However if you set this options to be false, only the active tab will be dragged" +
                            "<BR>if user drags the title bar." +
                            "<BR><BR>Default: on</FONT>" +
                            "<BR></HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isDragAllTabs());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Allow Nested Floating Windows");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    frame.getDockingManager().setNestedFloatingAllowed( ( (JCheckBoxMenuItem) e.getSource()).
                        isSelected());
                    if (frame.getDockingManager().isNestedFloatingAllowed())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                            "<FONT FACE='Tahoma' SIZE='4'><B><FONT COLOR='#0000FF'>Nested Floating Windows<BR></FONT></B><BR></FONT>" +
                            "<FONT FACE='Tahoma' SIZE='3'><B>An option to allow nested windows when in floating mode</B>" +
                            "<BR><BR>JIDE Docking Framework can allow you to have as many nested windows in one floating " +
                            "<BR>container as you want. However, not all your users want to have that complexity. So we " +
                            "<BR>leave it as an option and you can choose to turn it on or off. " +
                            "<BR><BR>Default: off</FONT> <BR>" +
                            "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isNestedFloatingAllowed());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Show Gripper");
        checkBoxMenuItem.setMnemonic('S');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    frame.getDockingManager().setShowGripper( ( (JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (frame.getDockingManager().isShowGripper())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                            "<FONT FACE='Tahoma' SIZE='4'><FONT COLOR='#0000FF'><B>Show Gripper</B><BR></FONT><BR></FONT>" +
                            "<FONT FACE='Tahoma' SIZE='3'><B>An option to give user a visual hint that the dockable frame can be dragged<BR></B>" +
                            "<BR>Normal tabs in JTabbedPane can not be dragged. However in our demo, " +
                            "<BR>most of them can be dragged. To make it obvious to user, we added an " +
                            "<BR>option so that a gripper is painted on the tab or the title bar of those " +
                            "<BR>dockable frames which can be dragged." +
                            "<BR><BR>Default: off</FONT><BR>" +
                            "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isShowGripper());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Show TitleBar");
        checkBoxMenuItem.setMnemonic('T');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    frame.getDockingManager().getFrame("Property").setShowTitleBar( ( (JCheckBoxMenuItem) e.getSource()).
                        isSelected());
//                    frame.getDockingManager().setShowTitleBar(((JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (frame.getDockingManager().isShowTitleBar())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                            "<FONT FACE='Tahoma' SIZE='4'><FONT COLOR='#0000FF'><B>Show TitleBar</B><BR></FONT><BR></FONT>" +
                            "<FONT FACE='Tahoma' SIZE='3'><B>An option to show/hide dockable frame's title bar<BR></B>" +
                            "<BR><BR>Default: on</FONT><BR>" +
                            "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isShowTitleBar());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("SideBar Rollover");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    frame.getDockingManager().setSidebarRollover( ( (JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (frame.getDockingManager().isSidebarRollover())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                            "<FONT FACE='Tahoma' SIZE='4'><FONT COLOR='#0000FF'><B>SideBar Rollover</B><BR></FONT><BR></FONT>" +
                            "<FONT FACE='Tahoma' SIZE='3'><B>An option to control the sensibility of tabs on sidebar<BR></B>" +
                            "<BR>Each tab on four sidebars is corresponding to a dockable frame. Usually when " +
                            "<BR>user moves mouse over the tab, the dockable frame will show up. However in Eclipse" +
                            "<BR>you must click on it to show the dockable frame. This option will allow you to " +
                            "<BR>control the sensibility of it." +
                            "<BR><BR>Default: on</FONT><BR>" +
                            "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(frame.getDockingManager().isSidebarRollover());
        menu.add(checkBoxMenuItem);

        menu.addSeparator();

        JRadioButtonMenuItem radioButtonMenuItem1 = new JRadioButtonMenuItem("Draw Full Outline When Dragging");
        radioButtonMenuItem1.setMnemonic('D');
        radioButtonMenuItem1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JRadioButtonMenuItem)
                {
                    frame.getDockingManager().setOutlineMode(DefaultDockingManager.FULL_OUTLINE_MODE);
                    Lm.showPopupMessageBox("<HTML>" +
                        "<B><FONT FACE='Tahoma' SIZE='4' COLOR='#0000FF'>Outline Paint Mode</FONT></B><FONT FACE='Tahoma'>" +
                        "<FONT SIZE='4'>" +
                        "<FONT COLOR='#0000FF' SIZE='3'><BR><BR><B>An option of how to paint the outline during dragging.</B></FONT>" +
                        "<BR><BR><FONT SIZE='3'>Since our demo is purely based on Swing, and there is no way to have transparent native " +
                        "<BR>window using Swing. So we have to develop workarounds to paint the outline of a dragging frame. " +
                        "<BR>As a result, we get two ways to draw the outline. Since neither is perfect, we just leave it as " +
                        "<BR>an option to user to choose. You can try each of the option and see which one you like most." +
                        "<BR><B><BR>Option 1: PARTIAL_OUTLINE_MODE</B><BR>Pros: Fast, very smooth, works the best if user " +
                        "of your application always keeps it as full screen" +
                        "<BR>Cons: Partial outline or no outline at all if outside main frame although it's there wherever " +
                        "your mouse is." +
                        "<BR><BR><B>Option 2: FULL_OUTLINE_MODE</B>" +
                        "<BR>Pros: It always draw the full outline" +
                        "<BR>Cons: Sometimes it's flickering. Slower comparing with partial outline mode." +
                        "<BR><BR>Default: PARTIAL_OUTLINE_MODE</FONT>" +
                        "<BR></HTML>");
                }
            }
        });
        radioButtonMenuItem1.setSelected(frame.getDockingManager().getOutlineMode() ==
            DefaultDockingManager.FULL_OUTLINE_MODE);
        menu.add(radioButtonMenuItem1);

        JRadioButtonMenuItem radioButtonMenuItem2 = new JRadioButtonMenuItem("Draw Partial Outline When Dragging");
        radioButtonMenuItem2.setMnemonic('P');
        radioButtonMenuItem2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JRadioButtonMenuItem)
                {
                    frame.getDockingManager().setOutlineMode(DefaultDockingManager.PARTIAL_OUTLINE_MODE);
                    Lm.showPopupMessageBox("<HTML>" +
                        "<B><FONT FACE='Tahoma' SIZE='4' COLOR='#0000FF'>Outline Paint Mode</FONT></B><FONT FACE='Tahoma'>" +
                        "<FONT SIZE='4'><FONT COLOR='#0000FF'><BR></FONT><BR></FONT><B>An option of how to paint the outline during dragging. " +
                        "<BR><BR></B>Since our demo is purely based on Swing, and there is no way to have transparent native " +
                        "<BR>window using Swing. So we have to develop workarounds to paint the outline of a dragging frame. " +
                        "<BR>As a result, we get two ways to draw the outline. Since neither is perfect, we just leave it as " +
                        "<BR>an option to user to choose. You can try each of the option and see which one you like most." +
                        "<BR><B><BR>Option 1: PARTIAL_OUTLINE_MODE</B>" +
                        "<BR>Pros: Fast, very smooth" +
                        "<BR>Cons: Partial outline or no outline at all if outside main frame although it&#39;s there wherever your mouse is." +
                        "<BR><BR><B>Option 2: FULL_OUTLINE_MODE</B>" +
                        "<BR>Pros: It always draw the full outline<BR>Cons: Sometimes it&#39;s flickering. Slower comparing with partial outline mode.</FONT>" +
                        "<BR><BR><FONT FACE='Tahoma'>Default: PARTIAL_OUTLINE_MODE</FONT>" +
                        "<BR></HTML>");
                }
            }
        });
        radioButtonMenuItem2.setSelected(frame.getDockingManager().getOutlineMode() ==
            DefaultDockingManager.PARTIAL_OUTLINE_MODE);
        menu.add(radioButtonMenuItem2);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonMenuItem1);
        buttonGroup.add(radioButtonMenuItem2);

        return menu;
    }

    private static void toggleButton(boolean selected, int button)
    {
        Collection names = frame.getDockingManager().getAllFrames();
        for (Iterator iterator = names.iterator(); iterator.hasNext(); )
        {
            String name = (String) iterator.next();
            DockableFrame fr = frame.getDockingManager().getFrame(name);
            if (selected)
            {
                fr.setAvailableButtons(fr.getAvailableButtons() | button);
            }
            else
            {
                fr.setAvailableButtons(fr.getAvailableButtons() & ~button);
            }
        }
    }

    private static JMenu createFileMenu()
    {
        JMenuItem item;

        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');

        item = new JMenuItem("Exit");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                clearUp();
            }
        });
        menu.add(item);
        return menu;
    }

    private static JMenu createLnfMenu()
    {
        JMenuItem item;

        JMenu lnfMenu = new JMenu("Look And Feel");
        lnfMenu.setMnemonic('L');

        item = new JMenuItem("Window Look And Feel (VSNET style)");
        item.setEnabled(SystemInfo.isWindows());
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.WINDOWS_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE);
                frame.getDockingManager().updateComponentTreeUI();
                frame.getRootPane().getJMenuBar().updateUI();
            }
        });
        lnfMenu.add(item);

        item = new JMenuItem("Windows Look And Feel (Office 2003 style)");
        item.setEnabled(SystemInfo.isWindows());
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.WINDOWS_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.OFFICE2003_STYLE);
                frame.getDockingManager().updateComponentTreeUI();
                frame.getRootPane().getJMenuBar().updateUI();
            }
        });
        lnfMenu.add(item);

        item = new JMenuItem("Windows Look And Feel (Eclipse style)");
        lnfMenu.add(item);
        item.setEnabled(SystemInfo.isWindows());
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.WINDOWS_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.ECLIPSE_STYLE);
                frame.getDockingManager().updateComponentTreeUI();
                frame.getRootPane().getJMenuBar().updateUI();
            }
        });

        lnfMenu.addSeparator();

        item = new JMenuItem("Metal Look And Feel (VSNET style)");
        lnfMenu.add(item);
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.METAL_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE);
                frame.getDockingManager().updateComponentTreeUI();
                frame.getRootPane().getJMenuBar().updateUI();
            }
        });

        item = new JMenuItem("Metal Look And Feel (Eclipse style)");
        lnfMenu.add(item);
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.METAL_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.ECLIPSE_STYLE);
                frame.getDockingManager().updateComponentTreeUI();
                frame.getRootPane().getJMenuBar().updateUI();
            }
        });

        lnfMenu.addSeparator();

        item = new JMenuItem("Aqua Look And Feel (Mac OS X)");
        lnfMenu.add(item);
        item.setEnabled(SystemInfo.isMacOSX());
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.AQUA_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE);
                frame.getDockingManager().updateComponentTreeUI();
                frame.getRootPane().getJMenuBar().updateUI();
            }
        });

        // Shading effect is replaced by Office2003 L&F
        lnfMenu.addSeparator();

        final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem("Add Shading Effect (only on VSNET style)");
        lnfMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (menuItem.isSelected())
                {
                    System.setProperty("shadingtheme", "true");
                }
                else
                {
                    System.setProperty("shadingtheme", "false");
                }
                frame.getDockingManager().updateComponentTreeUI();
                frame.getRootPane().getJMenuBar().updateUI();
            }
        });

        return lnfMenu;
    }
}
