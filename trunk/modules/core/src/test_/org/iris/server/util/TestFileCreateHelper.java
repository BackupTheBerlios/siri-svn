package org.iris.server.util;

import java.io.*;

import org.iris.server.util.filehelpers.*;
import junit.framework.*;

public class TestFileCreateHelper
    extends TestCase
{
    private FileCreateHelper fileCreateHelper = null;

    public TestFileCreateHelper(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        fileCreateHelper = new FileCreateHelper();
    }

    protected void tearDown() throws Exception
    {
        fileCreateHelper = null;
        super.tearDown();
    }

    public void testDummy()
    {
    }


    /**
    public void testCreateFile()
    {
        try
        {
            fileCreateHelper.createFile("Message", "c:/irisererror.txt");
        }
        catch (IOException ex)
        {
            assertTrue(false);
            ex.printStackTrace();
        }

        try
        {
            fileCreateHelper.createFile("Message", "cq:/iriserror.txt");
        }
        catch (IOException ex)
        {
            assertTrue(true);
            ex.printStackTrace();
        }

    }

     */
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(TestFileCreateHelper.class);
    }

}
