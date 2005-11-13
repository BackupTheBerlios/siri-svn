package org.siri.dao.test;

import org.siri.dao.SystemUserDAO;
import org.siri.dao.MessageDAO;
import org.siri.domain.systemuser.SystemUser;
import org.siri.domain.systemuser.Password;

import java.util.*;

/**
 * No actual test, but only test data initialization.
 */
public abstract class TestCaseWithData extends HibernateTest
{
    SystemUser systemUser1;
    SystemUser systemUser2;
    Calendar inThreeDays = GregorianCalendar.getInstance();
    Calendar inFiveDays = GregorianCalendar.getInstance();
    Calendar nextWeek = GregorianCalendar.getInstance();
    Calendar today;


    /**
     * Create test data for our domain model.
     */
    protected void initData()
    {
        SystemUserDAO systemUserDAO = DAOFACTORY.getSystemUserDAO();
        MessageDAO messageDAO = DAOFACTORY.getMessageDAO();

        inThreeDays.roll(Calendar.DAY_OF_YEAR, 3);
        inFiveDays.roll(Calendar.DAY_OF_YEAR, 5);
        nextWeek.roll(Calendar.WEEK_OF_YEAR, true);

        // Categories
        systemUser1 = new SystemUser("Donald", "Donald Duck", "is funny", true, 3, today, nextWeek);
        Password password = new Password(systemUser1, "password");
        systemUser1.addPassword(password);

        systemUserDAO.makePersistent(systemUser1);
    }

    public void inTransaction()
    {
        initData();
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }

}