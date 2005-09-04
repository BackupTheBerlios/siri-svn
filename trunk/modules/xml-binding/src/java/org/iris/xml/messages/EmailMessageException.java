package org.iris.xml.messages;

/**
 *  Exception thrown in data layer when an insert went wrong.
 *
 * @author     Georges Polyzois
 * @created    den 22 januari 2002
 * @version    0.1 Jan 4 2001
 */

public class EmailMessageException extends Exception
{
    /**
     *  Holds original exception
     */
    public Exception originalException;


    /**
     *  Default constructor Directly calls the super constructor with the same
     *  signature
     */
    public EmailMessageException()
    {
        super();
    }


    /**
     *  Constructor that takes a message string. Directly calls the super
     *  constructor with the same signature
     *
     * @param  msg     Description of Parameter
     */
    public EmailMessageException(String msg)
    {
        super(msg);
    }


    /**
     *  Constructor for the FinderException object
     *
     * @param  message            Description of Parameter
     * @param  originalException  Description of Parameter
     */
    public EmailMessageException(String message, Exception originalException)
    {
        super(message + "\nOriginal message : " + originalException.getMessage());
        this.originalException = originalException;
        fillInStackTrace();
    }


    /**
     *  Method is responsible for
     *
     * @param  s  Description of Parameter
     */
    public void printStackTrace(java.io.PrintStream s)
    {
        synchronized (s)
        {
            super.printStackTrace(s);
            s.println("Original Exception:\n");
            originalException.printStackTrace(s);
        }
    }


    /**
     *  Method is responsible for
     *
     * @param  s  Description of Parameter
     */
    public void printStackTrace(java.io.PrintWriter s)
    {
        synchronized (s)
        {
            super.printStackTrace(s);
            s.println("Original Exception:\n");
            originalException.printStackTrace(s);
        }
    }


    /**
     *  Method is responsible for
     */
    public void printStackTrace()
    {
        synchronized (System.err)
        {
            super.printStackTrace();
            System.err.println("Original Exception:\n");
            originalException.printStackTrace();
        }
    }
}


