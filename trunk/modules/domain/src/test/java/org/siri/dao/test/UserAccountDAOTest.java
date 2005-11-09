package org.siri.dao.test;

import org.siri.dao.UserAccountDAO;
import org.siri.domain.systemuser.SystemUser;
import org.siri.domain.Message;
import org.siri.domain.test.AbstractDomainTest;
import org.siri.common.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Calendar;
import java.util.HashSet;

public class UserAccountDAOTest extends AbstractDomainTest
{
    UserAccountDAO userAccountDAO;
    Calendar validFrom = Calendar.getInstance();
    Calendar validTo = Calendar.getInstance();


    public UserAccountDAOTest()
        {
        }

        protected void setUp() throws Exception
        {
                     
        }

        protected void tearDown() throws Exception
        {

        }


    public void testCreateUser() throws Exception
    {
        SystemUser systemUser = new SystemUser("a user name","Donald Duck","is a duck",true,3,validFrom,validTo);
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try
        {
            session.save(systemUser);
            transaction.commit();
            SystemUser persistentSystemUser = (SystemUser) session.load(SystemUser.class, systemUser.getId());
            assertNotNull("User created was incorrectly null", persistentSystemUser);
        }

        finally
        {
            session.close();
        }

    }



}