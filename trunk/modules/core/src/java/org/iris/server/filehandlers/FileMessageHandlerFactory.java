package org.iris.server.filehandlers;

import org.apache.log4j.*;

import org.iris.server.pollhandlers.*;
import org.iris.server.util.config.*;

/**
 * Factory for creating abstract FileMessageHandlers from Service config settings.
 *
 *
 * @author  Georges Polyzois
 */
public class FileMessageHandlerFactory
{
    static Logger myLogger = Logger.getLogger(FileMessageHandlerFactory.class.getName());

    public static void main(String[] args)
    {
        try
        {
            FileMessageHandlerFactory.createNotStartedInstance(null);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Create FileMessageHandler based on service config parameters fro this service.
     * Set statys flag of message handler to not started - we will start hese up later.
     *
     * @param service
     * @return
     * @throws Exception
     */
    public static FileMessageHandler createNotStartedInstance(Service service) throws Exception
    {
        if (service instanceof ServicePollEJB)
        {
            ServicePollEJB servicePollEjb = (ServicePollEJB) service;
            FileMessageHandler messanger = new PollEJBMessageHandler(servicePollEjb);
            messanger.setServiceStatus(FileMessageHandler.NOTSTARTED);
            return messanger;

        }
        else if (service instanceof ServiceFileEmail)
        {
            ServiceFileEmail serviceF = (ServiceFileEmail) service;
            FileMessageHandler messanger = new FileToEmailMessegeHandler(serviceF);
            messanger.setServiceStatus(FileMessageHandler.NOTSTARTED);
            return messanger;
        }
        else if (service instanceof ServiceFileFile)
        {
            ServiceFileFile serviceF = (ServiceFileFile) service;
            FileMessageHandler messanger = new FileToFileMessageHandler(serviceF);
            messanger.setServiceStatus(FileMessageHandler.NOTSTARTED);
            return messanger;
        }
        else if (service instanceof ServiceFileEJB)
        {
            ServiceFileEJB serviceEjb = (ServiceFileEJB) service;

            FileMessageHandler messanger = null;
            messanger = new FileToEJBMessegeHandler(serviceEjb);
            messanger.setServiceStatus(FileMessageHandler.NOTSTARTED);
            return messanger;
        }
        else
        {
            throw new Exception("This service is not handled - " + service.getId());
        }
    }
}