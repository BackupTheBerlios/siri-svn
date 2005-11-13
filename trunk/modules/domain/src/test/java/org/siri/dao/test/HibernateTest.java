package org.siri.dao.test;

import org.hibernate.*;
import org.hibernate.context.ThreadLocalSessionContext;
import org.hibernate.cfg.Configuration;
//import org.hibernate.context.ThreadLocalSessionContext;
import org.apache.commons.logging.*;
import org.siri.dao.DAOFactory;
import org.siri.common.logging.Log4JInit;
import org.siri.common.hibernate.HibernateUtil;
import org.siri.domain.systemuser.SystemUser;
import org.siri.domain.systemuser.SystemGroup;
import org.siri.domain.systemuser.Password;
import org.siri.domain.Message;
import org.siri.domain.Receiver;
import org.siri.domain.Sender;
import junit.framework.TestCase;

public abstract class HibernateTest extends TestCase
{
    Log4JInit log4j = new Log4JInit();
    private static Log log = LogFactory.getLog(HibernateTest.class);

    protected final DAOFactory DAOFACTORY = DAOFactory.HIBERNATE;
    protected static SessionFactory sessionFactory;

    protected boolean wrapInTransaction = true;
    protected boolean rollback = true;

    static
    {
        HibernateUtil.setOfflineMode();

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



        sessionFactory = HibernateUtil.getSessionFactory();
    }


    protected void runTest() throws Throwable
    {
        Session session = null;
        if (wrapInTransaction)
        {
            log.debug("Wrapping test in a transaction");
            try
            {
                log.debug("Begin transaction");
                session = sessionFactory.getCurrentSession();
                session.beginTransaction();
                log.debug("Executing inTransaction() supplement");
                inTransaction();
                log.debug("Running test");
                super.runTest();
            }
            catch(Throwable thr)
            {
                log.error(thr,thr);
            }
            finally
            {
                if(session == null || session.getTransaction() == null)
                {
                    log.error("SessionFactory not properly initialised - no session retrieved!");
                    return;
                }
                if (!rollback)
                {
                    log.debug("Committing transaction");
                    session.getTransaction().commit();
                } else
                {
                    log.debug("Rolling back transaction");
                    session.getTransaction().rollback();
                }
            }
        } else
        {
            super.runTest();
        }
    }

    // Use thread-binding for Long Session
    protected Session disconnectContext()
    {
        log.debug("Disconnecting session from thread");
        return ThreadLocalSessionContext.unbind(sessionFactory);
    }

    protected void reconnectContext(Session s)
    {
        log.debug("Reconnecting session to thread");
        ThreadLocalSessionContext.bind(s);
    }

    /**
     * Executes inside the transaction.
     * <p/>
     * Override this method to execute extra operations, such as test data import.
     */
    public void inTransaction()
    {
    }

}
