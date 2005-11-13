package org.siri.dao.hibernate.test;

/**
 * Unit test MessageDAO interface.
 *
 * @author Georges Polyzois
 */

import org.siri.dao.hibernate.MessageDAOHibernate;
import org.siri.dao.test.TestCaseWithData;
import org.siri.dao.MessageDAO;
import org.siri.domain.test.AbstractDomainTest;
import org.siri.domain.Sender;
import org.siri.domain.Message;
import org.siri.common.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.context.ThreadLocalSessionContext;

import java.util.HashSet;

public class MessageDAOHibernateTest extends TestCaseWithData
{
    MessageDAOHibernate messageDAOHibernate;


    public void testSaveMessage() throws Exception
    {

        Message message = new Message("a test message",null,null);
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        ThreadLocalSessionContext.bind(s);
s.beginTransaction();

s.save(message);

s.getTransaction().commit();

ThreadLocalSessionContext.unbind( HibernateUtil.getSessionFactory()).close(); // Only needed for non-JTA

        //MessageDAO messageDao = DAOFACTORY.getMessageDAO();

        //messageDao.makePersistent(message);

    }
}