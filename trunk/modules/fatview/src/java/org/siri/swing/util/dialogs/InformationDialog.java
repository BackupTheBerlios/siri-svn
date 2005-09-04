package org.siri.swing.util.dialogs;

import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.siri.swing.util.*;

public class InformationDialog
    extends JDialog
{

    private static InformationDialog instance;
    JPanel panelRoot = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanelInformation = new JPanel();
    JPanel jPanelHidden = new JPanel();
    JPanel jPanelIcon = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JLabel jLabelIcon = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea jTextAreaInformation = new JTextArea();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanelHiddenCenter = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanelButtons = new JPanel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    JScrollPane jScrollPaneStackTrace = new JScrollPane();
    JTextArea jTextAreaStackTrace = new JTextArea();
    BorderLayout borderLayout4 = new BorderLayout();
    Dimension preferedSize = new Dimension(400, 400);
    boolean modal = true;

    public InformationDialog(JFrame frame, String title, boolean modal)
    {
        super(frame, title, modal);
        try
        {
            jbInit();
            pack();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        setCentered();
        setFrameIcon(frame);
    }

    public InformationDialog()
    {
        this(new JFrame(), "", true);
    }

    public void setInformationText(String text)
    {
        this.jTextAreaInformation.setText(text);
    }

    public void setTitleOfDialog(String text)
    {
        this.setTitle(text);
    }

    public void setStackTrace(Throwable thr)
    {
        StringWriter stackTraceWriter = new StringWriter();
        thr.printStackTrace(new PrintWriter(stackTraceWriter));
        String stackTraceAlsString = stackTraceWriter.toString();
        this.jTextAreaStackTrace.setText(stackTraceAlsString);
    }

    public static InformationDialog getInstance()
    {
        if (instance == null)
        {
            instance = new InformationDialog();
        }
        return instance;
    }

    public static void main(String[] args)
    {
        InformationDialog dlg = InformationDialog.getInstance();
        dlg.setInformationText("ttttttt");
        dlg.setStackTrace(new Exception());
    }

    void jbInit() throws Exception
    {
        this.panelRoot.setPreferredSize(new Dimension(400, 350));
        panelRoot.setLayout(borderLayout1);
        jPanelIcon.setLayout(gridLayout1);
        jTextAreaInformation.setWrapStyleWord(true);
        jTextAreaInformation.setRequestFocusEnabled(false);
        jTextAreaInformation.setEnabled(false);
        jTextAreaInformation.setCaretColor(Color.white);
        jTextAreaInformation.setSelectedTextColor(Color.white);
        jTextAreaInformation.setMinimumSize(new Dimension(200, 17));
        jTextAreaInformation.setSelectionColor(Color.white);
        jTextAreaInformation.setLineWrap(true);
        jTextAreaInformation.setToolTipText("");
        jTextAreaInformation.setForeground(Color.white);
        jTextAreaInformation.setDisabledTextColor(Color.white);
        jTextAreaInformation.setBackground(SystemColor.desktop);
        jTextAreaInformation.setEditable(false);
        jPanelInformation.setLayout(borderLayout3);
        jPanelHidden.setLayout(borderLayout2);
        jPanelIcon.setMinimumSize(new Dimension(20, 0));
        jButton1.setToolTipText("Close dialog");
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            /**
             *  Method is responsible for
             *
             * @param  e  Description of Parameter
             */
            public void actionPerformed(ActionEvent e)
            {
                jButton1_actionPerformed(e);
            }
        });
        jButton2.setToolTipText("Show stack trace if any");
        jButton2.setText(">>");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButton2_actionPerformed(e);
            }
        });
        jButton3.setToolTipText("Hide stack trace");
        jButton3.setText("<<");
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButton3_actionPerformed(e);
            }
        });
        jPanelHidden.setEnabled(false);
        jPanelHidden.setBorder(BorderFactory.createLineBorder(Color.black));
        jPanelHidden.setMinimumSize(new Dimension( -1, -1));
        jPanelHidden.setPreferredSize(new Dimension( (int) preferedSize.getWidth(),
            (int) (preferedSize.getHeight() / 1.6)));
        jPanelInformation.setBorder(BorderFactory.createLineBorder(Color.black));
        jPanelInformation.setMinimumSize(new Dimension(163, 30));
        jPanelInformation.setPreferredSize(new Dimension(400, 50));
        jTextAreaStackTrace.setText("No stack trace...");
        jPanelHiddenCenter.setLayout(borderLayout4);
        getContentPane().add(panelRoot);
        panelRoot.add(jPanelInformation, BorderLayout.CENTER);
        jPanelInformation.add(jPanelIcon, BorderLayout.WEST);
        jPanelIcon.add(jLabelIcon, null);

        jLabelIcon.setIcon(ResourcesFactory.getImageIcon("errorlightning.gif"));

        jPanelInformation.add(jScrollPane1, BorderLayout.CENTER);
        jPanelInformation.add(jPanelButtons, BorderLayout.SOUTH);
        jPanelButtons.add(jButton3, null);
        jPanelButtons.add(jButton2, null);
        jPanelButtons.add(jButton1, null);
        jScrollPane1.getViewport().add(jTextAreaInformation, null);
        panelRoot.add(jPanelHidden, BorderLayout.SOUTH);
        jPanelHidden.add(jPanelHiddenCenter, BorderLayout.CENTER);
        jPanelHiddenCenter.add(jScrollPaneStackTrace, BorderLayout.CENTER);
        jScrollPaneStackTrace.getViewport().add(jTextAreaStackTrace, null);
    }

    /**
     *  Method is responsible for
     *
     * @param  e  Description of Parameter
     */
    void jButton2_actionPerformed(ActionEvent e)
    {
        //getRootPane().setPreferredSize( preferedSize );
        jPanelHidden.setPreferredSize(new Dimension( (int) preferedSize.getWidth(),
            (int) (preferedSize.getHeight() / 1.6)));
        jPanelHidden.invalidate();
        pack();
        validateTree();
    }

    void jButton3_actionPerformed(ActionEvent e)
    {
        jPanelHidden.setPreferredSize(new Dimension(0, 0));
        jPanelHidden.invalidate();
        pack();
        validateTree();
    }

    void jButton1_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

    private void setFrameIcon(JFrame frame)
    {
        try
        {
            ImageIcon icon = ResourcesFactory.getImageIcon("errorlightning.gif");
            frame.setIconImage(icon.getImage());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void setCentered()
    {
        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        this.setLocation( (screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    public void setModal(boolean modal)
    {
        modal = modal;
    }

    public void setVisible(boolean visible)
    {
        super.setVisible(visible);
    }
}
