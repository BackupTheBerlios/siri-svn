package org.iris.client.swing.panels.graph;

import java.io.*;

import java.awt.*;
import javax.swing.*;

import org.jgraph.*;
import org.jgraph.graph.*;

/**
 * THIS CLASS IS ONLYU EXPERIMENTAL. In the future I plan to add som sort of mapping of services
 * and how they relate to each other
 *
 *
 * @author not attributable
 * @version 1.0
 */

public class ServiceMapPanel
    extends JPanel
{
    BorderLayout borderLayout1 = new BorderLayout();
    JGraph myJGraph = new JGraph(new DefaultGraphModel());
    BorderLayout borderLayout2 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new ServiceMapPanel(""));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public ServiceMapPanel(String xmlfileContens)
    {
        try
        {
            // Read the GXL file into the model
            SVGGraph.read(new File(
                "C:/gepo/mycvsprojects/iris/modules/swing-client/src/conf/xml_test/svgexamples/graph3.gxl"),
                myJGraph.getModel());
            // Apply Layout
            SVGGraph.layout(myJGraph);
            // Resize (Otherwise not Visible)
            myJGraph.setSize(myJGraph.getPreferredSize());

        }
        catch (Exception ex1)
        {
            ex1.printStackTrace();
        }

        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    void jbInit() throws Exception
    {
        this.setLayout(borderLayout1);
        this.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(myJGraph, null);
    }

}