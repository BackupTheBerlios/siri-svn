package org.siri.dao;

import org.siri.common.hibernate.HibernateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: 2005-nov-03
 * Time: 16:16:13
 * To change this template use File | Settings | File Templates.
 */
public class TestClient {
    public static void main(String[] args) {
        HibernateUtil.setOfflineMode();

        MessageDAO messageDAO = DAOFactory.HIBERNATE.getMessageDAO();
        messageDAO.findById("",true);


    }

}
