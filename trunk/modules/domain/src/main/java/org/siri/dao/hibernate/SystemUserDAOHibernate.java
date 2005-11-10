package org.siri.dao.hibernate;

import org.hibernate.*;
import org.siri.dao.MessageDAO;
import org.siri.dao.SystemUserDAO;
import org.siri.domain.Message;
import org.siri.domain.systemuser.SystemUser;
import org.siri.common.hibernate.HibernateUtil;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Hibernate-specific implementation of the <tt>SystemUserDAO</tt>
 * non-CRUD data access object.
 *
 */
public class SystemUserDAOHibernate extends GenericHibernateDAO<SystemUser, Long> implements SystemUserDAO
{
    Logger logger = Logger.getLogger(SystemUserDAOHibernate.class);


    public SystemUserDAOHibernate(Session session)
    {
        super(SystemUser.class, session);
    }

    @SuppressWarnings("unchecked")
    public List<Message> findConcrete(Class concreteClass)
    {
        return getSession().createCriteria(concreteClass).list();
    }

    public void createSystemUser(String userName, String password, String fullName, String description, Calendar validFrom,
                                 Calendar validTo, boolean active, boolean temporaryPassword, int loginRetries, Calendar passwordLastChanged)
    {
        logger.debug("Trying to store new user");
         SystemUser systemUser = new SystemUser("a user name","Donald Duck","is a duck",true,3,validFrom,validTo);
        //Session session = HibernateUtil.getSession();

        //getSession().beginTransaction();

        try
        {
            getSession().save(systemUser);

        }
        finally
        {
        }

    }
}
