package org.iris.client.swing.util.statuspanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

import org.iris.client.swing.util.*;

public class GCStatusPanel
    extends JPanel
{
    BorderLayout borderLayout1 = new BorderLayout();
    private static final Color edgeColor = new Color(82, 115, 214);
    private static final Color centerColor = new Color(180, 200, 230);
    private final Icon gcIcon = ResourcesFactory.getImageIcon("trash.gif");
    private MemoryPanel panelMemory = new MemoryPanel();
    private JButton invokeGCButton;
    private int updateInterval = 2000;
    private Timer timer;

    public GCStatusPanel()
    {
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        timer = new Timer(updateInterval, new ActionListener()
        {
            public void actionPerformed(ActionEvent actionevent)
            {
                GCStatusPanel.this.panelMemory.repaint();
            }
        });
        timer.start();
    }

    private void jbInit() throws Exception
    {
        this.setOpaque(false);
        setLayout(new BorderLayout(5, 0));
        panelMemory = new MemoryPanel();
        panelMemory.setOpaque(false);
        invokeGCButton = new JButton(gcIcon);
        invokeGCButton.setRequestFocusEnabled(false);
        invokeGCButton.setFocusable(false);
        setBorder(new EmptyBorder(1, 1, 1, 1));
        invokeGCButton.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        invokeGCButton.setOpaque(false);
        //  invokeGC.setUI(new BlueButtonUI());
        invokeGCButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.gc();
                GCStatusPanel.this.panelMemory.repaint();
            }
        });
        add(panelMemory, BorderLayout.CENTER);
        add(invokeGCButton, BorderLayout.WEST);
    }

    class MemoryPanel
        extends JPanel
    {
        public void paint(Graphics g)
        {
            super.paint(g);
            Dimension dimension = getSize();
            Insets insets = getInsets();
            int left = insets.left;
            int top = insets.top;
            Runtime runtime = Runtime.getRuntime();
            long freeMemory = runtime.freeMemory();
            long totalMemory = runtime.totalMemory();
            int insideWidth = dimension.width - (insets.left + insets.right);
            int usedAmount = insideWidth - (int) ( ( (long) insideWidth * freeMemory) / totalMemory);
            int insideHeight = dimension.height - (insets.bottom + insets.top);
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(new GradientPaint(left, top, edgeColor, left, insideHeight / 2, centerColor, true));
            g.fillRect(left, top, usedAmount, insideHeight);
            g.setColor(getBackground());
            g.fillRect(left + usedAmount, top, insideWidth - usedAmount, insideHeight);
            g.setFont(getFont());
            g.setColor(Color.black);
            String memory = (Long.toString( (totalMemory - freeMemory) / 1048576L) + "M of " +
                            Long.toString(totalMemory / 1048576L) + 'M');
            FontMetrics fontmetrics = g.getFontMetrics();
            int stringWidth = fontmetrics.charsWidth(memory.toCharArray(), 0, memory.length());
            int stringHeight = fontmetrics.getHeight() - fontmetrics.getDescent();
            g.drawString(memory, left + (insideWidth - stringWidth) / 2, top + (insideHeight + stringHeight) / 2);
        }
    }

    public int getPreferredWidth()
    {
        return 30 + getFontMetrics(getFont()).stringWidth("0000M of 0000M");
    }

    public String getItemName()
    {
        return "Memory";
    }

    public void setUpdateInterval(int i)
    {
        timer.stop();
        updateInterval = i;
        timer.setDelay(i);
        timer.start();
    }

    public int getUpdateInterval()
    {
        return updateInterval;
    }

    public void removeNotify()
    {
        super.removeNotify();
        stop();
        timer = null;
    }

    public void stop()
    {
        timer.stop();
    }

    public static void main(String[] args)
    {
        JFrame fr = new JFrame();
        fr.getContentPane().add(new GCStatusPanel());
        fr.setVisible(true);
    }
}