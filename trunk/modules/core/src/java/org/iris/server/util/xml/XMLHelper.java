package org.iris.server.util.xml;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class XMLHelper
{
    private static DocumentBuilderFactory documentBuilderFactory;
    static Logger myLogger = Logger.getLogger(XMLHelper.class.getName());

    public XMLHelper()
    {
    }

    public static void transform(String pathXmlToTransform, String pathToXSLFile,
                                 String pathToOutFile) throws TransformerException, IOException, SAXException
    {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer(new StreamSource(new File(pathToXSLFile)));
        InputSource insource = new InputSource(new FileReader(pathXmlToTransform));
        DOMSource source = new DOMSource(getDocumentBuilder().parse(insource));
        transformer.transform(source, new StreamResult(new FileOutputStream(new File(pathToOutFile))));
    }

    public static File transformToFile(File fileToTransform, String pathToXSLFile,
                                       String nameOfOutPutFile) throws TransformerException, IOException, SAXException
    {
        File result = new File(nameOfOutPutFile);
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer(new StreamSource(new File(pathToXSLFile)));
        InputSource insource = new InputSource(new FileReader(fileToTransform));
        DOMSource source = new DOMSource(getDocumentBuilder().parse(insource));
        transformer.transform(source, new StreamResult(result)); 
        return result;
    }

    private static DocumentBuilder getDocumentBuilder()
    {
        if (documentBuilderFactory == null)
        {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
        }

        documentBuilderFactory.setValidating(false);

        try
        {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //documentBuilder.setEntityResolver(createEntityResolver());
            return documentBuilder;

        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void writeXMLFile(String name, Document doc) throws Exception
    {
        try
        {
            File outputFile = new File(name);
            FileOutputStream fos = new FileOutputStream(outputFile);
            OutputFormat format = new OutputFormat(doc);
            format.setIndent(3);
            XMLSerializer serial = new XMLSerializer(fos, format);
            serial.asDOMSerializer();
            serial.serialize(doc.getDocumentElement());
            fos.close();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * Takes a document and converts it to a string.
     *
     * @param doc
     * @return
     * @throws Exception
     */
    public static String getXML(Document doc) throws Exception
    {
        StringWriter writer = null;
        try
        {
            writer =  new StringWriter();
            OutputFormat format = new OutputFormat(doc);
            format.setIndent(3);
            XMLSerializer serial = new XMLSerializer(writer, format);
            serial.asDOMSerializer();
            serial.serialize( doc.getDocumentElement());
            writer.flush();
            return writer.toString();
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if ( writer != null )
            {
                writer.close();
            }


        }
    }

}