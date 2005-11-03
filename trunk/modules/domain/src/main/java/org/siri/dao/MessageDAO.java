package org.siri.dao;

import org.siri.domain.Message;

import java.util.*;

/**
 * Business DAO operations related to the <tt>BillingDetails</tt> entity.
 * <p>
 *
 * @author christian.bauer@jboss.com
 */
public interface MessageDAO extends GenericDAO<Message, String>
{
    public List<Message> findConcrete(Class concreteClass);
}
