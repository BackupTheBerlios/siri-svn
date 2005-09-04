package org.iris.server.messagehandlers;

/**
 * Factory for creating message handlers
 *
 * @author Georges Polyzois
 * @version 1.0
 */

public class MessageHandlerFactory
{
    public static MessageHandler createHandler(String messageHandlerType)
    {
        MessageHandler service = null;
        if (messageHandlerType.equals(MessageHandler.EMAIL))
        {
            return new InstantEmailMessageHandler();
        }
        return null;
    }
}