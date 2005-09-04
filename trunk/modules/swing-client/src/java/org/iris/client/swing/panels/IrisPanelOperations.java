package org.iris.client.swing.panels;

import org.iris.server.util.config.*;

/**
 * Operations enforced upon some panels.
 */
public interface IrisPanelOperations
{

    public void init(Service selectedService, boolean setFieldsEditable);

    /**
     * Called when the options dialog's "ok" button is clicked.
     * This should save any properties being edited in this option
     * pane.
     */
    public void save();

//    public String getTabTitle();
}
