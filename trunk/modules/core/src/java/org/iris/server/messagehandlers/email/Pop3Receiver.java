package org.iris.server.messagehandlers.email;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


// TODO finish this class.....

public class Pop3Receiver
{
    public Pop3Receiver()
    {
    }

    public static void getEmail()
    {
        //  String host = "tdvms01";
        //    String username = "gepo";
//      String password = "Dmnb336";
        String host = "gollum";
        String username = "gepo";
        String password = "dmnb330";

        String provider = "pop3";

        Store store = null;
        Folder folder = null;

        try
        {
            Properties props = System.getProperties();
            props.put("mail.pop3.timeout", "60000");

            Session session = Session.getDefaultInstance(props, null);

            store = session.getStore("pop3");
            store.connect(host, username, password);

            folder = store.getDefaultFolder();
            if (folder == null)
            {
                throw new Exception("No default folder");
            }

            folder = folder.getFolder("INBOX");
            if (folder == null)
            {
                throw new Exception("No POP3 INBOX");
            }

            folder.open(Folder.READ_WRITE);

            FetchProfile fp = new FetchProfile();
            fp.add(UIDFolder.FetchProfileItem.UID);
            folder.fetch(folder.getMessages(), fp);

            Message[] msgs = folder.getMessages();

            System.out.println("LENGTH " + msgs.length);
            long before = System.currentTimeMillis();

            for (int msgNum = 0; msgNum < msgs.length; msgNum++)
            {

                if (folder instanceof com.sun.mail.pop3.POP3Folder)
                {
                    com.sun.mail.pop3.POP3Folder pf = (com.sun.mail.pop3.POP3Folder) folder;
                    String uid = pf.getUID(msgs[msgNum]);
                    if (uid != null)
                    {
                        System.out.println("########   " + uid);

                    }
                }

                printMessage(msgs[msgNum]);

                msgs[msgNum].setFlag(Flags.Flag.DELETED, true);
            }
            long after = System.currentTimeMillis();

            System.out.println("TIME " + (after - before));

            folder.close(true); // true makes it deleted if flag set
            store.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private static void printFlags(Message msgs)
    {
        try
        {
            Flags flags = msgs.getFlags();
            Flags.Flag[] sf = flags.getSystemFlags();
            for (int i = 0; i < sf.length; i++)
            {
                if (sf[i] == Flags.Flag.DELETED)
                {
                    System.out.println("DELETED message");
                }
                else if (sf[i] == Flags.Flag.SEEN)
                {
                    System.out.println("SEEN message");
                }
            }
        }
        catch (MessagingException ex)
        {
            ex.printStackTrace();
        }
    }

    private static void saveMessageToFile()
    {

    }

    public static void printMessage(Message message)
    {
        try
        {
            // Get the header information
            String from = ( (InternetAddress) message.getFrom()[0]).getPersonal();
            if (from == null)
            {
                from = ( (InternetAddress) message.getFrom()[0]).getAddress();
            }
            System.out.println("FROM: " + from);

            String subject = message.getSubject();
            System.out.println("SUBJECT: " + subject);

            // -- Get the message part (i.e. the message itself) --
            Part messagePart = message;
            Object content = messagePart.getContent();

            // -- or its first body part if it is a multipart message --
            if (content instanceof Multipart)
            {
                messagePart = ( (Multipart) content).getBodyPart(0);
                System.out.println("[ Multipart Message ]");
            }

            // -- Get the content type --
            String contentType = messagePart.getContentType();

            // -- If the content is plain text, we can print it --
            System.out.println("CONTENT:" + contentType);

            if (contentType.startsWith("text/plain") || contentType.startsWith("text/html"))
            {
                InputStream is = messagePart.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String thisLine = reader.readLine();
                while (thisLine != null)
                {
                    System.out.println(thisLine);
                    thisLine = reader.readLine();
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        getEmail();
    }

}