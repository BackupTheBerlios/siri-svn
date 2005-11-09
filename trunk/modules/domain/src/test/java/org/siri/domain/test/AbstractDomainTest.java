package org.siri.domain.test;

import org.hibernate.cfg.Configuration;
import org.siri.common.hibernate.HibernateUtil;
import org.siri.common.logging.Log4JInit;
import org.siri.domain.Message;
import org.siri.domain.Receiver;
import org.siri.domain.Sender;
import org.siri.domain.systemuser.SystemUser;
import org.siri.domain.systemuser.SystemGroup;
import org.siri.domain.systemuser.Password;
import org.apache.log4j.Logger;
import junit.framework.TestCase;

import java.util.HashSet;

/**
 * Domain classes should extend this abstract class.
 * <p/>
 * AbstractDomainTest will create a sessionFactory from configuration
 * in this class. New domian classes must be added here for unit testing.
 */
public class AbstractDomainTest extends TestCase
{
    Log4JInit log4j = new Log4JInit();
    Logger logger = Logger.getLogger(AbstractDomainTest.class);

    static
    {
        Configuration config = new Configuration().
                setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
                setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
                setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:domaintest").
                setProperty("hibernate.connection.username", "sa").
                setProperty("hibernate.connection.password", "").
                setProperty("hibernate.connection.pool_size", "1").
                setProperty("hibernate.connection.autocommit", "true").
                setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
                setProperty("hibernate.hbm2ddl.auto", "create-drop").
                setProperty("hibernate.show_sql", "true").
                addClass(SystemUser.class).
                addClass(SystemGroup.class).
                addClass(Password.class).
                addClass(Message.class).
                addClass(Receiver.class).
                addClass(Sender.class);

        HibernateUtil.setSessionFactory(config.buildSessionFactory());
    }

/*
   public static void reset() throws SchemaException
   {

       Session session = HibernateUtil.getSession();
       try {
           Connection connection = session.connection();
           try {
               Statement statement = connection.createStatement();
               try {
                   statement.executeUpdate("delete from Batting");
                   statement.executeUpdate("delete from Fielding");
                   statement.executeUpdate("delete from Pitching");
                   statement.executeUpdate("delete from Player");
                   connection.commit();
               }
               finally {
                   statement.close();
               }
           }
           catch (HibernateException e) {
               connection.rollback();
               throw new SchemaException(e);
           }
           catch (SQLException e) {
               connection.rollback();
               throw new SchemaException(e);
           }
       }
       catch (SQLException e) {
           throw new SchemaException(e);
       }
       finally {
           session.close();
       }
   }
*/
}