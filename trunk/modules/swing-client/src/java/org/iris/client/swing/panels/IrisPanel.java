package org.iris.client.swing.panels;

import javax.swing.*;

import org.iris.server.util.config.*;

public class IrisPanel
    extends JPanel implements IrisPanelOperations
{
    protected String tabtitle;
    protected Icon tabIcon;

    /**
     * Has the panel been initialized?
     */
    protected boolean initialized;

    /**
     * Set all fields to editable or not, depends on when you want to have them
     * editable as in the cloning of a service or you just want to display
     * a service config settings
     *
     * @param setFieldsEditable
     */
    public void setComponentsEnabled(JComponent[] components, boolean setFieldsEditable)
    {
        for (int i = 0; i < components.length; i++)
        {
            components[i].setEnabled(setFieldsEditable);
        }
    }

    public void initPanel(Service selectedService, boolean setFieldsEditable)
    {};

    /**
     * Locates service id in textfield string and replaces with new value
     *
     * @param txtField
     * @param newServiceId
     * @param oldServiceId
     */
    protected void changeServiceId(JTextField txtField, String newServiceId, String oldServiceId)
    {
        String path = txtField.getText();
        path = org.apache.commons.lang.StringUtils.replace(path, oldServiceId, newServiceId);
        txtField.setText(path);
    }

    /**
     * Called when the panels  "ok" button is clicked.
     * This should save any properties being edited in this option pane.
     */
    public void save()
    {
        if (initialized)
        {
            savePanelData();
        }
    }

    protected void savePanelData()
    {
    }

    public void init(Service selectedService, boolean setFieldsEditable)
    {
        if (!initialized)
        {
            initialized = true;
            initPanel(selectedService, setFieldsEditable);
        }
    }

    public String getTabTitle()
    {
        return tabtitle;
    }

    public Icon getTabIcon()
    {
        return tabIcon;
    }

}
