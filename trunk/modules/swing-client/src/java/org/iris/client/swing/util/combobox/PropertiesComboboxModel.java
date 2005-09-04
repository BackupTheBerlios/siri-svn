package org.iris.client.swing.util.combobox;

import java.util.*;

import javax.swing.*;

import org.iris.client.swing.util.*;

/**
 *  Class is responsible for
 *
 * @author     Georges Polyzois
 * @created    den 27 mars 2002
 */
public class PropertiesComboboxModel
    extends DefaultComboBoxModel
{
    /**
     *  Description of the Field
     */
    public HashMap comboboxMap = new HashMap();

    /**
     *  Constructor for the PropertiesComboboxModel object
     *
     * @param  nameOfPropertyFile  Description of Parameter
     * @param  sortByKey           Description of Parameter
     */
    public PropertiesComboboxModel(String nameOfPropertyFile, boolean sortByKey)
    {
        init(nameOfPropertyFile, sortByKey);
    }

    /**
     *  Sets the {3} attribute of the PropertiesComboboxModel object
     *
     * @param  aHashMap   The new {3} value
     * @param  sortByKey  The new {3} value
     */
    private void setComboHashMap(HashMap aHashMap, boolean sortByKey)
    {
        Iterator iter = aHashMap.keySet().iterator();
        while (iter.hasNext())
        {
            String tKey = (String) iter.next();
            comboboxMap.put(tKey, new ComboboxItem(tKey, (String) aHashMap.get(tKey)));
            if (!sortByKey)
            {
                addElement(comboboxMap.get(tKey));
            }
        }

        if (sortByKey)
        {
            removeAllElements();
            SortedMap sortMap = getSortedTreeMapByValue(comboboxMap);
            Iterator iter2 = sortMap.values().iterator();
            while (iter2.hasNext())
            {
                String pKey = ( (ComboboxItem) iter2.next()).value;
                addElement(comboboxMap.get(pKey));
            }
        }
        fireContentsChanged(this, 0, comboboxMap.size());
    }

    /**
     *  Gets the {3} attribute of the PropertiesComboboxModel object
     *
     * @param  map  Description of Parameter
     * @return      The {3} value
     */
    private SortedMap getSortedTreeMapByValue(HashMap map)
    {
        HashMap sortedByValue = new HashMap();

        Iterator iter = map.keySet().iterator();
        while (iter.hasNext())
        {
            String key = (String) iter.next();
            sortedByValue.put( ( (ComboboxItem) map.get(key)).display, (ComboboxItem) map.get(key));
        }

        SortedMap aTreeMap = new TreeMap(sortedByValue);
        return aTreeMap;
    }

    /**
     *  Gets the {3} attribute of the PropertiesComboboxModel object
     *
     * @param  map  Description of Parameter
     * @return      The {3} value
     */
    private SortedMap getSortedTreeMapByKey(HashMap map)
    {
        SortedMap aTreeMap = new TreeMap(map);
        return aTreeMap;
    }

    /**
     *  Method is responsible for
     *
     * @param  nameOfPropertyFile  Description of Parameter
     * @param  sortByKey           Description of Parameter
     */
    private void init(String nameOfPropertyFile, boolean sortByKey)
    {
        Properties options = ResourcesFactory.getProperties(nameOfPropertyFile);
        Enumeration proplist = options.propertyNames();
        HashMap aHashMap = new HashMap();
        while (proplist.hasMoreElements())
        {
            String tKey = (String) proplist.nextElement();
            aHashMap.put(tKey, (String) options.get(tKey));
        }
        setComboHashMap(aHashMap, sortByKey);
    }

}
