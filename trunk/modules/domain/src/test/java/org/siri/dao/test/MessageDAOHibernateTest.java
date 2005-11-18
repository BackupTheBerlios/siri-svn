package org.siri.dao.test;

/**
 * Unit test MessageDAO interface.
 *
 * @author Georges Polyzois
 */

import org.siri.dao.hibernate.MessageDAOHibernate;
import org.siri.dao.test.TestCaseWithData;
import org.siri.dao.MessageDAO;
import org.siri.domain.Message;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageDAOHibernateTest extends TestCaseWithData
{
    MessageDAOHibernate messageDAOHibernate;
    private static Log log = LogFactory.getLog(MessageDAOHibernateTest.class);

    public void finder() throws Exception
    {
         MessageDAO messageDAO = DAOFACTORY.getMessageDAO();


        Message amessage = messageDAO.findById(super.message.getId(), false);

    }


    public void testSaveMessage() throws Exception
    {

        Message message = new Message("a test message",null,null);
        Session s = sessionFactory.getCurrentSession();
      //  ThreadLocalSessionContext.bind(s);
        s.beginTransaction();

        s.save(message);

        s.getTransaction().commit();

     //   ThreadLocalSessionContext.unbind(sessionFactory).close(); // Only needed for non-JTA

//MessageDAO messageDao = DAOFACTORY.getMessageDAO();

//messageDao.saveOrUpdate(message);

    }
}