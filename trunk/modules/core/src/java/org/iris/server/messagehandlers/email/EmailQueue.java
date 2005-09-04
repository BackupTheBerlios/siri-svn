package org.iris.server.messagehandlers.email;

import org.apache.commons.collections.*;
import org.apache.log4j.*;

public class EmailQueue
{
    static Logger myLogger = Logger.getLogger(EmailQueue.class.getName());
    Buffer myfifo = BufferUtils.synchronizedBuffer(new UnboundedFifoBuffer());

    public EmailQueue()
    {
    }

    public void push(EmailQueueItem email)
    {
        myfifo.add(email);
    }

    public EmailQueueItem pop()
    {
        Object obj = null;
        try
        {
            obj = myfifo.get();
        }
        catch (Exception ex)
        {
            myLogger.debug("Queue empty");
        }
        EmailQueueItem item = null;
        if (obj != null)
        {
            item = (EmailQueueItem) obj;
            myfifo.remove(obj);
        }
        return item;
    }

    public EmailQueueItem[] popCollection(int maxSize)
    {
        int collSize = maxSize;
        if (maxSize > myfifo.size())
        {
            collSize = myfifo.size();
        }
        EmailQueueItem[] items = new EmailQueueItem[collSize];
        for (int i = 0; i < collSize; i++)
        {
            items[i] = pop();
        }

        return items;
    }

    public Buffer getQueue()
    {
        return myfifo;
    }

}
