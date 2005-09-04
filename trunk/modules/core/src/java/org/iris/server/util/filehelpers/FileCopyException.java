package org.iris.server.util.filehelpers;

import java.io.*;

class FileCopyException
    extends IOException
{
    public FileCopyException(String msg)
    {
        super(msg);
    }
}
