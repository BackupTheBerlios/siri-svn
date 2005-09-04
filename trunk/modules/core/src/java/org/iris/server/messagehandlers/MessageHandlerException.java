package org.iris.server.messagehandlers;

public class MessageHandlerException
    extends Exception
{
    /**
     *  Holds original exception
     */
    public Exception originalException;

    /**
     *  Default constructor. Directly calls the super constructor with the same
     *  signature
     */
    public MessageHandlerException()
    {
        super();
    }

    /**
     *  Constructor that takes a message string. Directly calls the super
     *  constructor with the same signature
     *
     * @param  msg  Description of Parameter
     */
    public MessageHandlerException(String msg)
    {
        super(msg);
    }

    /**
     *  Constructor for the CreateException object
     *
     * @param  message            Description of Parameter
     * @param  originalException  Description of Parameter
     */
    public MessageHandlerException(String message, Exception originalException)
    {
        super(message);
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
            if ( originalException!= null)
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
            if ( originalException!= null)
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
            if ( originalException!= null)
                originalException.printStackTrace();
        }
    }
}