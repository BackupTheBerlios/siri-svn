package org.siri.domain.test;

import org.siri.domain.Message;
import org.siri.domain.Sender;
import org.siri.common.hibernate.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.Session;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: 2005-nov-07
 * Time: 14:48:55
 * To change this template use File | Settings | File Templates.
 */
public class MessageTest extends AbstractDomainTest
{
    public void testAddMessage() throws Exception
    {
        Message transientMessage = new Message("a message", new HashSet(), new Sender("a sender"));

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(transientMessage);
            transaction.commit();
            Message persistentMessage = (Message) session.load(Message.class,transientMessage.getId());
            assertNotNull(persistentMessage);
        }
        finally {
            session.close();
        }



    }

}
