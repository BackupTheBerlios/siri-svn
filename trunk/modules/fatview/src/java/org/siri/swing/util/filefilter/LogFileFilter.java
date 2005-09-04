package org.siri.swing.util.filefilter;

import java.io.*;
import java.util.*;

import javax.swing.filechooser.FileFilter;

/**
 *  A convenience implementation of FileFilter that filters out
 *  all files except for those type extensions that it knows about.
 *
 *  Example - create a new filter that filerts out all files
 *  but gif and jpg image files:
 *
 *  JFileChooser chooser = new JFileChooser();
 *  ExampleFileFilter filter = new ExampleFileFilter(
 *  new String{"gif", "jpg"}, "JPEG & GIF Images")
 *  chooser.addChoosableFileFilter(filter);
 *  chooser.showOpenDialog(this);
 *
 * @author     Georges Polyzois
 * @created    den 27 mars 2002
 */

public class LogFileFilter
    extends FileFilter
{

    private static String TYPE_UNKNOWN = "Type Unknown";
    private static String HIDDEN_FILE = "Hidden File";

    private Hashtable filters = null;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;

    /**
     *  Creates a file filter. If no filters are added, then all
     *  files are accepted.
     */
    public LogFileFilter()
    {
        this.filters = new Hashtable();
    }

    /**
     *  Creates a file filter that accepts files with the given extension.
     *  Example: new ExampleFileFilter("jpg");
     *
     * @param  extension  Description of Parameter
     */
    public LogFileFilter(String extension)
    {
        this(extension, null);
    }

    /**
     *  Creates a file filter that accepts the given file type.
     *  Example: new ExampleFileFilter("jpg", "JPEG Image Images");
     *
     *  Note that the "." before the extension is not needed. If
     *  provided, it will be ignored.
     *
     * @param  extension    Description of Parameter
     * @param  description  Description of Parameter
     */
    public LogFileFilter(String extension, String description)
    {
        this();
        if (extension != null)
        {
            addExtension(extension);
        }
        if (description != null)
        {
            setDescription(description);
        }
    }

    /**
     *  Creates a file filter from the given string array.
     *  Example: new ExampleFileFilter(String {"gif", "jpg"});
     *
     *  Note that the "." before the extension is not needed adn
     *  will be ignored.
     *
     * @param  filters  Description of Parameter
     */
    public LogFileFilter(String[] filters)
    {
        this(filters, null);
    }

    /**
     *  Creates a file filter from the given string array and description.
     *  Example: new ExampleFileFilter(String {"gif", "jpg"}, "Gif and JPG Images");
     *
     *  Note that the "." before the extension is not needed and will be ignored.
     *
     * @param  filters      Description of Parameter
     * @param  description  Description of Parameter
     */
    public LogFileFilter(String[] filters, String description)
    {
        this();
        for (int i = 0; i < filters.length; i++)
        {
            // add filters one by one
            addExtension(filters[i]);
        }
        if (description != null)
        {
            setDescription(description);
        }
    }

    /**
     *  Sets the human readable description of this filter. For
     *  example: filter.setDescription("Gif and JPG Images");
     *
     * @param  description  The new {3} value
     */
    public void setDescription(String description)
    {
        this.description = description;
        fullDescription = null;
    }

    /**
     *  Determines whether the extension list (.jpg, .gif, etc) should
     *  show up in the human readable description.
     *
     *  Only relevent if a description was provided in the constructor
     *  or using setDescription();
     *
     * @param  b  The new {3} value
     */
    public void setExtensionListInDescription(boolean b)
    {
        useExtensionsInDescription = b;
        fullDescription = null;
    }

    /**
     *  Return the extension portion of the file's name .
     *
     * @param  f  Description of Parameter
     * @return    The {3} value
     */
    public String getExtension(File f)
    {
        if (f != null)
        {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1)
            {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }

    /**
     *  Returns the human readable description of this filter. For
     *  example: "JPEG and GIF Image Files (*.jpg, *.gif)"
     *
     * @return    The {3} value
     */
    public String getDescription()
    {
        if (fullDescription == null)
        {
            if (description == null || isExtensionListInDescription())
            {
                fullDescription = description == null ? "(" : description + " (";
                // build the description from the extension list
                Enumeration extensions = filters.keys();
                if (extensions != null)
                {
                    fullDescription += "." + (String) extensions.nextElement();
                    while (extensions.hasMoreElements())
                    {
                        fullDescription += ", " + (String) extensions.nextElement();
                    }
                }
                fullDescription += ")";
            }
            else
            {
                fullDescription = description;
            }
        }
        return fullDescription;
    }

    /**
     *  Returns whether the extension list (.jpg, .gif, etc) should
     *  show up in the human readable description.
     *
     *  Only relevent if a description was provided in the constructor
     *  or using setDescription();
     *
     * @return    The {3} value
     */
    public boolean isExtensionListInDescription()
    {
        return useExtensionsInDescription;
    }

    /**
     *  Return true if this file should be shown in the directory pane,
     *  false if it shouldn't.
     *
     *  Files that begin with "." are ignored.
     *
     * @param  f  Description of Parameter
     * @return    Description of the Returned Value
     */
    public boolean accept(File f)
    {
        if (f != null)
        {
            if (f.isDirectory())
            {
                return true;
            }
            String extension = getExtension(f);
            if (extension != null && filters.get(getExtension(f)) != null)
            {
                return true;
            }
        }
        return false;
    }

    /**
     *  Adds a filetype "dot" extension to filter against.
     *
     *  For example: the following code will create a filter that filters
     *  out all files except those that end in ".jpg" and ".tif":
     *
     *  ExampleFileFilter filter = new ExampleFileFilter();
     *  filter.addExtension("jpg");
     *  filter.addExtension("tif");
     *
     *  Note that the "." before the extension is not needed and will be ignored.
     *
     * @param  extension  The feature to be added to the Extension attribute
     */
    public void addExtension(String extension)
    {
        if (filters == null)
        {
            filters = new Hashtable(5);
        }
        filters.put(extension.toLowerCase(), this);
        fullDescription = null;
    }
}
