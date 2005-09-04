package org.iris.client.swing.util.statuspanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import org.iris.client.swing.util.*;

public class StatusJPanel
    extends JPanel
{
    private static Color infoColor_black = new Color(0, 0, 0);
    private static Color infoColor_red = new Color(255, 0, 0);

    private JPanel jPanelRoot = new JPanel();
    private JPanel jPanelStatusText = new JPanel();
    private MyJProgressBar statusBar = new MyJProgressBar(100, false);
    private JPanel jPanelStatusPanel = new JPanel();
    private static StatusJPanel instance;
    private Border border1;
    private Border border2;
    private Border border3;
    private JLabel jLabelConnectedGif = new JLabel();
    private JLabel jLabelStatusText = new JLabel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanelProgressBar = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanelEmptyNorth = new JPanel();
    private JPanel jPanelEmptySouth = new JPanel();
    private JPanel jPanelWest = new JPanel();
    private JPanel jPanelEast = new JPanel();
    private GCStatusPanel myGCStatusPanel = new GCStatusPanel();

    private int myTimedelayForInitializeTask = 10000;
    JPanel jPanelEasty = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();
    BorderLayout borderLayout5 = new BorderLayout();
    BorderLayout borderLayout6 = new BorderLayout();

    public StatusJPanel()
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

    public static StatusJPanel getInstance()
    {
        if (instance == null)
        {
            instance = new StatusJPanel();

        }
        return instance;
    }

    void jbInit() throws Exception
    {
        border1 = BorderFactory.createLineBorder(Color.gray, 2);
        border2 = BorderFactory.createLineBorder(Color.gray, 2);
        border3 = BorderFactory.createLineBorder(Color.gray, 2);
        statusBar.setForeground(SystemColor.inactiveCaption);
        statusBar.setForeground(SystemColor.activeCaption);
        statusBar.setBorder(null);
        statusBar.setPreferredSize(new Dimension(100, 10));
        statusBar.setMaximum(100);
        statusBar.setMinimum(0);
        this.setLayout(borderLayout5);
        jPanelRoot.setLayout(borderLayout6);
        jPanelStatusText.setLayout(borderLayout4);
        this.setMaximumSize(new Dimension(150, 0));
        this.setPreferredSize(new Dimension(154, 30));
        jPanelStatusText.setPreferredSize(new Dimension(500, 15));
        jPanelRoot.setBorder(BorderFactory.createLoweredBevelBorder());
        jPanelRoot.setPreferredSize(new Dimension(150, 20));
        jPanelStatusPanel.setLayout(borderLayout1);

        jLabelConnectedGif.setIcon(ResourcesFactory.getImageIcon("groups.gif"));

        jLabelStatusText.setHorizontalAlignment(SwingConstants.LEFT);
        jLabelStatusText.setText("Starting application....");
        jPanelProgressBar.setLayout(borderLayout2);
        jPanelEmptyNorth.setPreferredSize(new Dimension(4, 4));
        jPanelEmptySouth.setPreferredSize(new Dimension(4, 4));
        jPanelWest.setPreferredSize(new Dimension(4, 10));
        jPanelEast.setPreferredSize(new Dimension(4, 10));
        jPanelStatusPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        jPanelEasty.setLayout(borderLayout3);
        jPanelEasty.setPreferredSize(new Dimension(150, 30));
        this.add(jPanelRoot, BorderLayout.CENTER);
        jPanelRoot.add(jPanelStatusPanel, BorderLayout.CENTER);
        jPanelStatusPanel.add(jPanelStatusText, BorderLayout.CENTER);
        jPanelStatusText.add(jLabelStatusText, BorderLayout.CENTER);
        jPanelStatusPanel.add(jPanelEasty, BorderLayout.EAST);
        jPanelStatusPanel.add(jPanelProgressBar, BorderLayout.WEST);
        jPanelProgressBar.add(statusBar, BorderLayout.CENTER);
        jPanelProgressBar.add(jPanelEmptyNorth, BorderLayout.NORTH);
        jPanelProgressBar.add(jPanelEmptySouth, BorderLayout.SOUTH);
        jPanelProgressBar.add(jPanelWest, BorderLayout.WEST);
        jPanelProgressBar.add(jPanelEast, BorderLayout.EAST);
        jPanelEasty.add(myGCStatusPanel, BorderLayout.CENTER);
        jPanelEasty.add(jLabelConnectedGif, BorderLayout.WEST);

    }

    public static void main(String[] args)
    {
        testStatusPanel();
    }

    public void startProgressBar()
    {
        statusBar.start();
    }

    public void stopProgressBar()
    {
        statusBar.stop();
    }

    public void setProgressBarInStartPosition()
    {
        statusBar.setToMin();
    }

    public void setProgressBarInMaxPosition()
    {
        statusBar.setToMax();
    }

    public void setStatusText(String newStatusText)
    {
        jLabelStatusText.setForeground(infoColor_black);
        jLabelStatusText.setText(newStatusText);

        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new StatusPanelInitializeTask(this), myTimedelayForInitializeTask);

    }

    public void setStatusErrorText(String newStatusText, long delayTime)
    {
        jLabelStatusText.setForeground(infoColor_red);
        jLabelStatusText.setText(newStatusText);
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new StatusPanelInitializeTask(this), delayTime);

    }

    public void setStatusConnectedGif()
    {
        try
        {
            jLabelConnectedGif.setIcon(ResourcesFactory.getImageIcon("connected.gif"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void setStatusDisConnectedGif()
    {
        try
        {
            jLabelConnectedGif.setIcon(ResourcesFactory.getImageIcon("disconnected.gif"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static void testStatusPanel()
    {
        StatusJPanel panel = new StatusJPanel();
        panel.setStatusText("Testing ...");
        panel.setStatusDisConnectedGif();
        panel.startProgressBar();
        JFrame frm = new JFrame();
        frm.getContentPane().add(panel);
        frm.pack();
        frm.setVisible(true);
        try
        {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {

        }
        panel.stopProgressBar();

        try
        {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {

        }

        panel.startProgressBar();
        try
        {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {

        }
        panel.stopProgressBar();

        try
        {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {

        }

        panel.startProgressBar();
        try
        {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {

        }
        panel.stopProgressBar();

        try
        {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {

        }

        panel.startProgressBar();
        try
        {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {

        }
        panel.stopProgressBar();

    }
}
