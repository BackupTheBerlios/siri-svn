package org.iris.client.swing.panels.graph;

/*
 * @(#)SVGGraph.java	1.0.5 06/01/03
 *
 * Copyright (C) 2002 Gaudenz Alder
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */


import java.io.*;
import java.util.*;
import java.util.List;
import javax.xml.parsers.*;

import java.awt.*;
import javax.swing.*;

import org.apache.batik.dom.*;
import org.apache.batik.svggen.*;
import org.jgraph.*;
import org.jgraph.graph.*;
import org.w3c.dom.*;

public class SVGGraph
{

    public static void main(String[] args)
    {

        if (args.length < 2)
        {
            System.out.println("Usage: SVGGraph gxl-file svg-file");
        }
        else if (args[0] != null)
        {
            try
            {
                // Construct the myJGraph (so the model has a view
                // which is able to hold the attributes)
                JGraph graph = new JGraph(new DefaultGraphModel());
                // Read the GXL file into the model
                read(new File(args[0]), graph.getModel());
                // Apply Layout
                layout(graph);
                // Resize (Otherwise not Visible)
                graph.setSize(graph.getPreferredSize());
                // Create file output stream
//                FileOutputStream fos = new FileOutputStream(new File(args[1]));
                // Created writer with UTF-8 encoding
//                Writer out = new OutputStreamWriter(fos, "UTF-8");
                // Write the SVG file
//                write(myJGraph, out);
                // Close the file output stream
//                fos.flush();
//                fos.close();


                JFrame frame = new JFrame();
                frame.getContentPane().add(new JScrollPane(graph));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

                // Write Message on Console
                System.out.println("done");
            }
            catch (Exception e)
            {
                // Display error message on stderr
                System.err.println(e.toString());
            }
        }
    }

    public static void write(JGraph graph, Writer out) throws Exception
    {
        // Get a DOMImplementation
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        // Create an instance of org.w3c.dom.Document
        Document document = domImpl.createDocument(null, "svg", null);
        // Create an instance of the SVG Generator
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
        // Render into the SVG Graphics2D implementation
        graph.paint(svgGenerator);
        // Use CSS style attribute
        boolean useCSS = true;
        // Finally, stream out SVG to the writer
        svgGenerator.stream(out, useCSS);
    }

    public static void read(File f, GraphModel model) throws Exception
    {
        // Create a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // Create a DocumentBuilder
        DocumentBuilder db = dbf.newDocumentBuilder();
        // Parse the input file to get a Document object
        Document doc = db.parse(f);
        // Get the first child (the myJGraph-element)
        Node node = doc.getDocumentElement().getChildNodes().item(0);
        // Get Graph's Child Nodes (the cells)
        NodeList list = node.getChildNodes();
        // List for the new Cells
        List newCells = new ArrayList();
        // ConnectionSet for the Insert method
        ConnectionSet cs = new ConnectionSet();
        // Hashtable for the ID lookup (ID to Vertex)
        Map ids = new Hashtable();
        // Hashtable for Attributes (Vertex to Map)
        Hashtable attributes = new Hashtable();
        // Loop Children
        for (int i = 0; i < list.getLength(); i++)
        {
            node = list.item(i);
            // Fetch Label
            String label = getLabel(node);
            // If Valid Node
            if (node.getAttributes() != null && node.getNodeName() != null)
            {
                // Fetch Type
                String type = node.getNodeName().toString().toLowerCase();

                // Create Vertex
                if (type.equals("node"))
                {
                    // Fetch ID Node
                    String id = null;
                    Node tmp = node.getAttributes().getNamedItem("id");
                    // Fetch ID Value
                    if (tmp != null)
                    {
                        id = tmp.getNodeValue();
                    }
                    // Need unique valid ID
                    if (id != null && !ids.keySet().contains(id))
                    {
                        // Create Vertex with label
                        DefaultGraphCell vertex = new DefaultGraphCell(label);
                        // Add One Floating Port
                        vertex.add(new DefaultPort());
                        // Add ID, Vertex pair to Hashtable
                        ids.put(id, vertex);
                        // Add Default Attributes
                        attributes.put(vertex, createDefaultAttributes());
                        // Add Vertex to new Cells
                        newCells.add(vertex);
                    }

                    // Create Edge
                }
                else if (type.equals("edge"))
                {
                    // Fetch Source ID Node
                    Node tmp = node.getAttributes().getNamedItem("from");
                    // Fetch Source ID Value
                    String source = null;
                    if (tmp != null)
                    {
                        source = tmp.getNodeValue();
                    }
                    // Fetch Target ID Node
                    tmp = node.getAttributes().getNamedItem("to");
                    // Fetch Target ID Value
                    String target = null;
                    if (tmp != null)
                    {
                        target = tmp.getNodeValue();
                    }
                    // Create Edge with label
                    DefaultEdge edge = new DefaultEdge(label);
                    // Find Source Port
                    if (source != null)
                    {
                        // Fetch Vertex for Source ID
                        DefaultGraphCell vertex = (DefaultGraphCell) ids.get(source);
                        if (vertex != null)
                        {
                            // Connect to Source Port
                            cs.connect(edge, vertex.getChildAt(0), true);
                        }
                    }
                    // Find Target Port
                    if (target != null)
                    {
                        // Fetch Vertex for Target ID
                        DefaultGraphCell vertex = (DefaultGraphCell) ids.get(target);
                        if (vertex != null)
                        {
                            // Connect to Target Port
                            cs.connect(edge, vertex.getChildAt(0), false);
                        }
                    }
                    // Add Edge to new Cells
                    newCells.add(edge);
                }
            }
        }
        // Insert the cells (View stores attributes)
        model.insert(newCells.toArray(), attributes, cs, null, null);
    }

    /**
     * Returns an attributeMap for the specified position and color.
     */
    public static Map createDefaultAttributes()
    {
        // Create an AttributeMap
        Map map = GraphConstants.createMap();
        // Set a Black Line Border (the Border-Attribute must be Null!)
        GraphConstants.setBorderColor(map, Color.black);

        GraphConstants.setBackground(map, Color.yellow);

        // Return the Map
        return map;
    }

    public static void layout(JGraph graph)
    {
        // Fetch All Views
        CellView[] views = graph.getGraphLayoutCache().getRoots();

        // Create list to hold vertices
        List vertices = new ArrayList();
        // Maximum width or height
        int max = 0;
        // Loop through all views
        for (int i = 0; i < views.length; i++)
        {
            // Add vertex to list
            if (views[i] instanceof VertexView)
            {
                vertices.add(views[i]);
                // Fetch Bounds
                Rectangle bounds = views[i].getBounds();
                // Update Maximum
                if (bounds != null)
                {
                    max = Math.max(Math.max(bounds.width, bounds.height), max);
                }
            }
        }
        // Compute Radius
        int r = (int) Math.max(vertices.size() * max / Math.PI, 100);
        // Compute angle step
        double phi = 2 * Math.PI / vertices.size();
        // Arrange vertices in a circle
        for (int i = 0; i < vertices.size(); i++)
        {
            Rectangle bounds = ( (CellView) vertices.get(i)).getBounds();
            // Update Location
            if (bounds != null)
            {
                bounds.setLocation(r + (int) (r * Math.sin(i * phi)), r + (int) (r * Math.cos(i * phi)));
            }
        }
    }

    // Fetch Cell Label from Node
    protected static String getLabel(Node node)
    {
        String lab = null;
        NodeList children = node.getChildNodes();
        for (int j = 0; j < children.getLength(); j++)
        {
            Node attr = children.item(j);
            if (attr.getNodeName().equals("attr") &&
                attr.getAttributes().getNamedItem("name").getNodeValue().equals("Label"))
            {
                NodeList values = attr.getChildNodes();
                for (int k = 0; k < values.getLength(); k++)
                {
                    if (values.item(k).getNodeName().equals("string"))
                    {
                        Node labelNode = values.item(k).getFirstChild();
                        if (labelNode != null)
                        {
                            lab = labelNode.getNodeValue();
                        }
                    }
                }
            }
        }
        return (lab != null) ? lab : new String("");
    }

}
