package org.iris.client.swing.panels.location;

import javax.swing.*;
import com.jgoodies.forms.layout.*;

public class LocationItemEditPanel extends JPanel
{
    public LocationItemEditPanel()
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

    private JPanel getPanel()
    {
        FormLayout layout = new FormLayout(
                            "right:pref, 6dlu, 50dlu, 4dlu, default", // columns
                            "pref, 3dlu, pref, 3dlu, pref"); // rows

        CellConstraints cc = new CellConstraints();
        JPanel panel = new JPanel(layout);
        panel.add(new JLabel("Name"), cc.xy(1, 1));
        panel.add(new JTextField(), cc.xywh(3, 1, 3, 1));
        panel.add(new JLabel("Description"), cc.xy(1, 3));
        panel.add(new JTextField(), cc.xy(3, 3));
        panel.add(new JLabel("Label3"), cc.xy(1, 5));
        panel.add(new JTextField(), cc.xy(3, 5));
        panel.add(new JButton("..."), cc.xy(5, 5));
        return panel;

    }

    private void jbInit() throws Exception
    {

        this.add(getPanel());
    }
}
