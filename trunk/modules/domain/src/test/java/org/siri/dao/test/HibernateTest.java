package org.siri.dao.test;
import org.hibernate.*;
import org.hibernate.context.ThreadLocalSessionContext;
import org.apache.commons.logging.*;
import org.siri.dao.DAOFactory;
import org.siri.common.hibernate.HibernateUtil;
import junit.framework.TestCase;

public abstract class HibernateTest extends TestCase {

    private static Log log = LogFactory.getLog(HibernateTest.class);

    protected final DAOFactory DAOFACTORY = DAOFactory.HIBERNATE;
    protected SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    protected boolean wrapInTransaction = true;
    protected boolean rollback = true;

    protected void runTest() throws Throwable {
        if (wrapInTransaction) {
            log.debug("Wrapping test in a transaction");
            try {
                log.debug("Begin transaction");
                sessionFactory.getCurrentSession().beginTransaction();
                log.debug("Executing inTransaction() supplement");
                inTransaction();
                log.debug("Running test");
                super.runTest();
            } finally {
                if (!rollback) {
                    log.debug("Committing transaction");
                    sessionFactory.getCurrentSession().getTransaction().commit();
                } else {
                    log.debug("Rolling back transaction");
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            }
        } else {
            super.runTest();
        }
    }

    // Use thread-binding for Long Session
    protected Session disconnectContext() {
        log.debug("Disconnecting session from thread");
        return ThreadLocalSessionContext.unbind(sessionFactory);
    }

    protected void reconnectContext(Session s) {
        log.debug("Reconnecting session to thread");
        ThreadLocalSessionContext.bind(s);
    }

    /**
     * Executes inside the transaction.
     * <p>
     * Override this method to execute extra operations, such as test data import.
     */
    public void inTransaction() {}

}
