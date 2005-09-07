package codesnipps.designpatterns.template;

/**
 * Test class.
 * @author gepo
 */
public class MessageTester
{
    public static void main(String[] args)
    {
        MessageSender sender = new EmailSender();
        sender.handleMessage("this is a message");
    }

}
