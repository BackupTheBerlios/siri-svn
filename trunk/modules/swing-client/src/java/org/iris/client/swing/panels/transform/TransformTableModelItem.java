package org.iris.client.swing.panels.transform;

import org.apache.commons.lang.builder.*;

public class TransformTableModelItem
{
    private String transformmessage;
    private String transformfile;

    public TransformTableModelItem()
    {
    }

    public void setTransformmessage(String transformmessage)
    {
        this.transformmessage = transformmessage;
    }

    public void setTransformfile(String transformfile)
    {
        this.transformfile = transformfile;
    }

    public String getTransformmessage()
    {
        return transformmessage;
    }

    public String getTransformfile()
    {
        return transformfile;
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

}
