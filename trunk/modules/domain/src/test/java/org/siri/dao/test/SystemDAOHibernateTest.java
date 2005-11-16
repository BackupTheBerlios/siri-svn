package org.siri.dao.test;

/**
 * Unit test MessageDAO interface.
 *
 * @author Georges Polyzois
 */

import org.siri.dao.hibernate.MessageDAOHibernate;
import org.siri.dao.hibernate.SystemUserDAOHibernate;
import org.siri.dao.test.TestCaseWithData;
import org.siri.dao.MessageDAO;
import org.siri.dao.SystemUserDAO;
import org.siri.domain.test.AbstractDomainTest;
import org.siri.domain.Sender;
import org.siri.domain.Message;
import org.siri.domain.systemuser.SystemUser;
import org.siri.common.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.context.ThreadLocalSessionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.Collection;
import java.util.Iterator;

public class SystemDAOHibernateTest extends TestCaseWithData
{
    private static Log log = LogFactory.getLog(SystemDAOHibernateTest.class);

    /**
     * Checks if the loaded user is found.
     * @throws Exception
     */
    public void finder() throws Exception
    {
        SystemUserDAO systemUserDAO = DAOFACTORY.getSystemUserDAO();
        SystemUser systemUser = systemUserDAO.findById(super.systemUser1.getId(), false);
        assertNotNull("One user with id : " + super.systemUser1.getId()  ,systemUser);
    }

    /**
     * Checks if the loaded user is found modifies user store and find again - a stupid
     * unit test...
     *
     * @throws Exception
     */
    public void update() throws Exception
    {
        SystemUserDAO systemUserDAO = DAOFACTORY.getSystemUserDAO();
        SystemUser systemUser = systemUserDAO.findById(super.systemUser1.getId(), false);
        assertNotNull("One user with id : " + super.systemUser1.getId()  ,systemUser);
        String expected = "Sir Donald";
        systemUser.setName("Sir Donald");
        systemUserDAO.makePersistent(systemUser);
        SystemUser systemUserAltered = systemUserDAO.findById(super.systemUser1.getId(), false);
        assertEquals(expected,systemUserAltered.getName());
    }

}