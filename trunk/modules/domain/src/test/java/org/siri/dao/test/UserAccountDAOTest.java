package org.siri.dao.test;

import org.siri.dao.SystemUserDAO;
import org.siri.dao.DAOFactory;
import org.siri.domain.systemuser.SystemUser;
import org.siri.domain.test.AbstractDomainTest;
import org.siri.common.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Calendar;

public class UserAccountDAOTest extends AbstractDomainTest
{
    SystemUserDAO systemUserDAO = DAOFactory.DEFAULT.getSystemUserDAO();
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
      //  Session session = HibernateUtil.getSession();
      //  Transaction transaction = session.beginTransaction();
        try
        {
            systemUserDAO.createSystemUser("a user name","pass","Donald Duck", "is a duck",validFrom,validTo,true,false,3,validTo);
      //      session.save(systemUser);
      //      transaction.commit();
      //      SystemUser persistentSystemUser = (SystemUser) session.load(SystemUser.class, systemUser.getId());
      //      assertNotNull("User created was incorrectly null", persistentSystemUser);
        }

        finally
        {
        //    session.close();
        }

    }



}