package org.siri.dao.hibernate;

import org.hibernate.Session;
import org.siri.dao.MessageDAO;
import org.siri.dao.DAOFactory;
import org.siri.common.hibernate.HibernateUtil;

/**
 * Returns Hibernate-specific instances of DAOs.
 * <p>
 * One of the responsiblities of the factory is to inject a Hibernate Session
 * into the DAOs. You can customize the getCurrentSession() method if you
 * are not using the default strategy, which simply delegates to
 * Hibernates built-in "current Session" mechanism.
 * <p>
 * If for a particular DAO there is no additional non-CRUD functionality, we use
 * an inner class to implement the interface in a generic way. This allows clean
 * refactoring later on, should the interface implement business data access
 * methods at some later time. Then, we would externalize the implementation into
 * its own first-level class. We can't use anonymous inner classes for this trick
 * because they can't extend or implement an interface and they can't include
 * constructors.
 *
 * @author christian.bauer@jboss.com
 * @author gepo01
 */
public class HibernateDAOFactory extends DAOFactory
{
    protected Session getCurrentSession() {
     //   HibernateUtil.beginTransaction();
        return HibernateUtil.getSessionFactory().getCurrentSession();
   }

    // Add your DAO interfaces here
    public MessageDAO getMessageDAO() {
        return new MessageDAOHibernate(getCurrentSession());
    }
}
