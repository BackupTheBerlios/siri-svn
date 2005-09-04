package org.iris.client.swing.util.guidgenerator;

import java.net.*;
import java.security.*;

/**
 *  J-GUID produces sequences that fulfills the UID requirements of
 *  uniqueness in space and time needed to ensure non-clashing identifiers
 *  by using a combination of the following four properties
 *
 *  1) unique to the millisecond
 *  2) unique to the machine
 *  3) unique to the object instance creating it
 *  4) unique to the method call for the same object
 *
 * @author     Georges Polyzois
 * @created    den 27 mars 2002
 */
public class GUIDGenerator
{
    private SecureRandom seeder;
    private String midValue;
    private String midValueUnformated;
    private long checkTime;

    /**
     *  Constructor for the GUIDGenerator object
     */
    public GUIDGenerator()
    {
        init();
    }

    /**
     *  The main program for the GUIDGenerator class
     *
     * @param  args  The command line arguments
     */
    public static void main(String[] args)
    {
        GUIDGenerator test = new GUIDGenerator();
        System.out.println(" size = " + test.getUUID().length() + "   " + test.getUUID());
        System.out.println(" size = " + test.getUUID().length() + "   " + test.getUUID());
    }

    /*
      public  String getUnformatedUUID()
      {
       return getVal(midValueUnformated);
      }
     */
    /**
     *  Gets the {3} attribute of the GUIDGenerator object
     *
     * @return    The {3} value
     */
    public String getUUID()
    {
        return getVal(midValue);
    }

    /**
     *  Gets the {3} attribute of the GUIDGenerator object
     *
     * @param  s  Description of Parameter
     * @return    The {3} value
     */
    private String getVal(String s)
    {
        long l = System.currentTimeMillis();
        int i = (int) l & 0xffffffff;
        int j = seeder.nextInt();
        return hexFormat(i, 8) + s + hexFormat(j, 8);
    }

    /**
     *  Gets the {3} attribute of the GUIDGenerator object
     *
     * @param  abyte0  Description of Parameter
     * @return         The {3} value
     */
    private int getInt(byte abyte0[])
    {
        int i = 0;
        int j = 24;
        for (int k = 0; j >= 0; k++)
        {
            int l = abyte0[k] & 0xff;
            i += l << j;
            j -= 8;
        }
        return i;
    }

    /**
     *  Method is responsible for
     */
    private void init()
    {
        try
        {
            StringBuffer stringbuffer = new StringBuffer();
            seeder = new SecureRandom();

            InetAddress inetaddress = InetAddress.getLocalHost();
            byte abyte0[] = inetaddress.getAddress();
            String s = hexFormat(getInt(abyte0), 8);
            stringbuffer.append("-");
            stringbuffer.append(s.substring(0, 4));

            stringbuffer.append("-");
            stringbuffer.append(s.substring(4));

            stringbuffer.append("-");
            stringbuffer.append(s.substring(0, 4));

            //       stringbuffer.append("-");
            stringbuffer.append(s.substring(4));
            midValue = stringbuffer.toString();

            checkTime = System.currentTimeMillis();

            int i = seeder.nextInt();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     *  Method is responsible for
     *
     * @param  i  Description of Parameter
     * @param  j  Description of Parameter
     * @return    Description of the Returned Value
     */
    private String hexFormat(int i, int j)
    {
        String s = Integer.toHexString(i);
        return padHex(s, j) + s;
    }

    /**
     *  Method is responsible for
     *
     * @param  s  Description of Parameter
     * @param  i  Description of Parameter
     * @return    Description of the Returned Value
     */
    private String padHex(String s, int i)
    {
        StringBuffer stringbuffer = new StringBuffer();
        if (s.length() < i)
        {
            for (int j = 0; j < i - s.length(); j++)
            {
                stringbuffer.append("0");
            }
        }
        return stringbuffer.toString();
    }

}
