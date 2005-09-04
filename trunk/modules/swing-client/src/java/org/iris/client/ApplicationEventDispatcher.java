package org.iris.client;

import java.net.*;
import java.rmi.*;
import java.util.*;

import java.awt.*;

import org.apache.log4j.*;
import org.iris.client.settings.*;
import org.iris.client.swing.panels.*;
import org.iris.client.swing.panels.config.*;
import org.iris.client.swing.panels.logging.*;
import org.iris.client.swing.panels.servicediagram.*;
import org.iris.client.swing.panels.servicestatistics.*;
import org.iris.client.swing.panels.transform.*;
import org.iris.client.swing.panels.tree.*;
import org.iris.client.swing.panels.wizards.cloneservice.*;
import org.iris.client.swing.util.dialogs.*;
import org.iris.client.swing.util.events.*;
import org.iris.client.swing.util.statuspanel.*;
import org.iris.server.filehandlers.*;
import org.iris.server.remoteservices.rmi.*;
import org.iris.server.statistics.*;
import org.iris.server.util.*;
import org.iris.server.util.config.*;

public class ApplicationEventDispatcher
    implements ApplicationStateEventListener
{
    private Logger myLogger = Logger.getLogger(ApplicationEventDispatcher.class.getName());
    public IrisOperations irisServer = null;
    private MainFrame myMainFrame;

    public ApplicationEventDispatcher(MainFrame frame)
    {
        ApplicationEventHandler.getInstance().addApplicationStateEventListener(this);
        myMainFrame = frame;
        initRemote();
    }

    private void initRemote()
    {
        String rmiServerLocation = myMainFrame.getConnectedServer().getRmiservice();
        try
        {
            myLogger.info("Looking up Iris" + System.getProperty("iris.rmi.service.name"));
            StatusJPanel.getInstance().startProgressBar();
            StatusJPanel.getInstance().setStatusText("Looking up iris...");

            irisServer = (IrisOperations) Naming.lookup( rmiServerLocation );
            StatusJPanel.getInstance().setStatusText("Connected to Iris server");
            StatusJPanel.getInstance().stopProgressBar();
            myLogger.info("Looking up ok");

        }
        catch (RemoteException ex)
        {
            myLogger.error(ex);
            StatusJPanel.getInstance().setStatusErrorText("Could not lookup Iris service", 2000);
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("Look up failed");
            dlg.setInformationText(
                "Iris could not be contacted - verify RMI server is running and Iris started and binded to RMI. RMI url : " +
                rmiServerLocation);
            dlg.setStackTrace(ex);
            dlg.setVisible(true);
        }
        catch (MalformedURLException ex)
        {
            myLogger.error(ex);

            StatusJPanel.getInstance().setStatusErrorText("Could not lookup Iris service", 2000);
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("Look up failed");
            dlg.setInformationText(
                "Iris could not be contacted - verify RMI server is running and Iris started and binded to RMI. RMI url : " +
                rmiServerLocation);
            dlg.setStackTrace(ex);
            dlg.setVisible(true);
        }
        catch (NotBoundException ex)
        {
            myLogger.error(ex);

            StatusJPanel.getInstance().setStatusErrorText("Could not lookup Iris service", 2000);
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("Look up failed");
            dlg.setInformationText(
                "Iris could not be contacted - verify RMI server is running and Iris started and binded to RMI. RMI url : " +
                rmiServerLocation);
            dlg.setStackTrace(ex);
            dlg.setVisible(true);
        }
    }

    /**
     * Handles all operations
     *
     * @param e
     */
    public void dataChanged(ApplicationStateEvent e)
    {
        myLogger.debug("New action caught : " + e.getEventName());
        if (e.getEventName() == ApplicationStateEvent.SAVE_SETTINGS)
        {
            SettingFileChooser chooser = new SettingFileChooser(SettingsFascade.getInstance().
                                         getIrisclientSettingsAsString(),
                                         SettingsFascade.getInstance().getDefaultSettingsFile());
            chooser.setVisible(true);
        }
        else if (e.getEventName() == ApplicationStateEvent.EXIT_APPLICATION)
        {
            MainFrame.getInstance().onExitApplication();
        }
        else if (e.getEventName() == ApplicationStateEvent.POLLSERVERFORSERVICEDATA)
        {
            getServiceData();
        }
        else if (e.getEventName() == ApplicationStateEvent.VERIFYREMOTESERVICES)
        {
            verifyRemoteServices();
        }
        else if (e.getEventName() == ApplicationStateEvent.PINGREMOTESERVICE)
        {
            pingRemoteService(e);
        }
        else if (e.getEventName() == ApplicationStateEvent.STOPREMOTESERVICE)
        {
            stopRemoteService(e);
        }
        else if (e.getEventName() == ApplicationStateEvent.STARTREMOTESERVICE)
        {
            startRemoteService(e);
        }
        else if (e.getEventName() == ApplicationStateEvent.CLONINGSERVICEWIZARD)
        {
            myLogger.debug("Call clone on server");
            cloneServiceWizard(e);
        }
        else if (e.getEventName() == ApplicationStateEvent.CLONINGSERVICEONREMOTESERVER)
        {
            myLogger.debug("Call clone on server");
            cloneService(e);
        }
        else if (e.getEventName() == ApplicationStateEvent.TREECLICKED)
        {
            handleTreeEvents(e);
        }
    }

    /**
     * Handle all tree events
     *
     * @param e
     */
    private void handleTreeEvents(ApplicationStateEvent e)
    {
        myLogger.debug("Tree clicked");
        Object source = e.getSource();
        if (source instanceof TreePanel)
        {
            IconNode iconNodeSelected = ( (TreePanel) source).getSelectedTreeNode();
            if (iconNodeSelected.getUserObject() instanceof TreeUserObject)
            {
                TreeUserObject selectedUserObject = (TreeUserObject) iconNodeSelected.getUserObject();
                if (selectedUserObject.getDisplayName().equals(TreePanel.DISPLAYNAME_SERVICES))
                {
                    StatusJPanel.getInstance().setStatusText("Initializing summarypanel");
                    StatusJPanel.getInstance().startProgressBar();
                    myMainFrame.getMainFrameRightPanel().removeAll();
                    myMainFrame.getMainFrameRightPanel().invalidate();
                    myMainFrame.getMainFrameRightPanel().add(ServiceSummaryPanel.getInstance(), BorderLayout.CENTER);
                    myMainFrame.getMainFrameRightPanel().validate();
                    myMainFrame.getMainFrameRightPanel().repaint();
                    StatusJPanel.getInstance().stopProgressBar();
                }
                else if (selectedUserObject.getDisplayName().equals(TreePanel.DISPLAYNAME_SETTINGS))
                {
                    StatusJPanel.getInstance().setStatusText("Initializing globalpanel");
                    StatusJPanel.getInstance().startProgressBar();
                    GlobalSettingsPanel.getInstance().init();
                    myMainFrame.getMainFrameRightPanel().removeAll();
                    myMainFrame.getMainFrameRightPanel().invalidate();
                    myMainFrame.getMainFrameRightPanel().add(GlobalSettingsPanel.getInstance(), BorderLayout.CENTER);
                    myMainFrame.getMainFrameRightPanel().validate();
                    myMainFrame.getMainFrameRightPanel().repaint();
                    StatusJPanel.getInstance().stopProgressBar();
                }

                else if (selectedUserObject.getDisplayName().equals(TreePanel.DISPLAYNAME_LOGGING))
                {
                    StatusJPanel.getInstance().setStatusText("Initializing logpanel");
                    StatusJPanel.getInstance().startProgressBar();
                    myMainFrame.getMainFrameRightPanel().removeAll();
                    myMainFrame.getMainFrameRightPanel().invalidate();
                    myMainFrame.getMainFrameRightPanel().add(GlobalLoggingPanel.getInstance(), BorderLayout.CENTER);
                    myMainFrame.getMainFrameRightPanel().validate();
                    myMainFrame.getMainFrameRightPanel().repaint();
                    StatusJPanel.getInstance().stopProgressBar();

                }
                else if (selectedUserObject.getDisplayName().equals(TreePanel.DISPLAYNAME_SERVER))
                {
                    StatusJPanel.getInstance().setStatusText("Reading server configuration");
                    StatusJPanel.getInstance().startProgressBar();
                    myMainFrame.getMainFrameRightPanel().removeAll();
                    myMainFrame.getMainFrameRightPanel().invalidate();
                    myMainFrame.getMainFrameRightPanel().add(myMainFrame.getServerConfigXMLPanel(), BorderLayout.CENTER);
                    myMainFrame.getMainFrameRightPanel().validate();
                    myMainFrame.getMainFrameRightPanel().repaint();
                    StatusJPanel.getInstance().stopProgressBar();
                }
                else if (selectedUserObject.getDisplayName().equals(TreePanel.DISPLAYNAME_CLIENT))
                {
                    myLogger.debug("Reading client settings....");
                    StatusJPanel.getInstance().setStatusText("Reading configuration for client settings");
                    StatusJPanel.getInstance().startProgressBar();
                    myMainFrame.getMainFrameRightPanel().removeAll();
                    myMainFrame.getMainFrameRightPanel().invalidate();
                    try
                    {
                        myMainFrame.getMainFrameRightPanel().add(ClientConfigXMLPanel.getInstance(),
                            BorderLayout.CENTER);
                    }
                    catch (Exception ex)
                    {
                        myLogger.warn(ex.getMessage());
                    }
                    myMainFrame.getMainFrameRightPanel().validate();
                    myMainFrame.getMainFrameRightPanel().repaint();
                    StatusJPanel.getInstance().stopProgressBar();
                }
                else if (selectedUserObject.getDisplayName().equals(TreePanel.DISPLAYNAME_STATUS))
                {
                    myLogger.debug("Reading client settings....");
                    StatusJPanel.getInstance().setStatusText("Reading configuration for client settings");
                    StatusJPanel.getInstance().startProgressBar();
                    myMainFrame.getMainFrameRightPanel().removeAll();
                    myMainFrame.getMainFrameRightPanel().invalidate();
                    try
                    {
                        myLogger.info("Getting statistics data for service : " + selectedUserObject.getServiceName());
                        getStatisticsForService(selectedUserObject.getServiceName());
                        myMainFrame.getMainFrameRightPanel().add(ServiceStatusSummaryPanel.getInstance(),
                            BorderLayout.CENTER);
                    }
                    catch (Exception ex)
                    {
                        myLogger.warn(ex.getMessage());
                    }
                    myMainFrame.getMainFrameRightPanel().validate();
                    myMainFrame.getMainFrameRightPanel().repaint();
                    StatusJPanel.getInstance().stopProgressBar();
                }
                else if (selectedUserObject.getDisplayName().equals("Status"))
                {
                    StatusJPanel.getInstance().setStatusText("Reading configuration");
                    StatusJPanel.getInstance().startProgressBar();
                    myMainFrame.getMainFrameRightPanel().removeAll();
                    myMainFrame.getMainFrameRightPanel().invalidate();
                    myMainFrame.getMainFrameRightPanel().add(ServiceStatusSummaryPanel.getInstance(),
                        BorderLayout.CENTER);
                    myMainFrame.getMainFrameRightPanel().validate();
                    myMainFrame.getMainFrameRightPanel().repaint();
                    StatusJPanel.getInstance().stopProgressBar();
                }
                else if (selectedUserObject.getServiceName() != null)
                {
                    String key = selectedUserObject.getServiceName();

//                    int startAt = key.indexOf("-") + 1;
                    //                  key = key.substring(startAt).trim();
                    //Service selectedService = (Service) myMainFrame.getInstance().getMyServicesMap().get( ( (TreePanel) e.getSource()).getSelectedService());
                    Service selectedService = (Service) MainFrame.getInstance().getMyServicesMap().get(key);
                    if (selectedService == null)
                    {
                        return;
                    }
                    else if (selectedService instanceof ServiceFileFile)
                    {
                        updateFileToFilePanel(selectedService);
                    }
                    else if (selectedService instanceof ServiceFileEJB)
                    {
                        myLogger.debug("Calling update on ejb panel");
                        updateFileToEJBPanel(selectedService);
                    }
                    else if (selectedService instanceof ServicePollEJB)
                    {
                        updatePollEJB(selectedService);
                    }

                }
            }

        }
    }

    private void getServiceData()
    {
        try
        {
            ServiceDetailsVO[] arrVos = irisServer.getDetailsOfServices();
            myLogger.debug("Called server and got " + arrVos);
            ServiceSummaryPanel.getInstance().reInit(arrVos);
        }
        catch (RemoteException ex1)
        {
            myLogger.error(ex1);
        }
//
    }

    /**
     * Calls server to get statistics and status information for selected service
     *
     */
    private void getStatisticsForService(String serviceName)
    {
        myLogger.debug("Calling server for update on statistics model to reinit diagram");
        try
        {
            StatisticsVO[] arrVos = irisServer.getStatisticsForService(serviceName);
            ServiceStatusSummaryPanel.getInstance().reInitDiagram(arrVos);
        }
        catch (RemoteException ex1)
        {
            myLogger.error(ex1);
        }

    }


    /**
     *
     *
     */
    private void verifyRemoteServices()
    {
        try
        {
            myLogger.debug("Verifying remote services alive or not");
            if (irisServer == null)
            {
                initRemote();
            }
            StatusJPanel.getInstance().setStatusText("Pinging remote services ");
            StatusJPanel.getInstance().startProgressBar();
            HashMap map = irisServer.pingServices();
            Set set = map.keySet();
            Iterator keyIter = set.iterator();
            while (keyIter.hasNext())
            {
                String serviceName = (String) keyIter.next();
                Integer status = (Integer) map.get(serviceName);
                if (status.intValue() == FileMessageHandler.DOESNOTEXIST ||
                    status.intValue() == FileMessageHandler.NOTSTARTED ||
                    status.intValue() == FileMessageHandler.STOPPED)
                {
                    myLogger.debug("NOT RUNNING");
                    TreePanel.getInstance().setTreeNodeIconToRed(serviceName);
                }
                else if (status.intValue() == FileMessageHandler.RUNNING)
                {
                    myLogger.debug("RUNNING");
                    TreePanel.getInstance().setTreeNodeIconToGreen(serviceName);

                }
            }
            StatusJPanel.getInstance().stopProgressBar();
            StatusJPanel.getInstance().setStatusText("Services are alive!");
            StatusJPanel.getInstance().setStatusConnectedGif();
        }
        catch (RemoteException ex)
        {
            StatusJPanel.getInstance().setStatusDisConnectedGif();
            StatusJPanel.getInstance().setStatusErrorText("Could not verify service ", 20000);
            InformationDialog dlg = new InformationDialog();
            TreePanel.getInstance().setTreeNodeIconAndDescendentsToRed("Services");
            dlg.setTitle("Failed stopping service");
            dlg.setInformationText("Remote message: " + ex.getMessage() +
                " Iris could not be contacted - verify RMI server is running and Iris started and binded to RMI. " +
                "Could not verify servicea ");
            dlg.setStackTrace(ex);
            dlg.setVisible(true);
        }
    }

    /**
     * Use to ping a remote Iris service
     *
     * @param e
     */
    private void pingRemoteService(ApplicationStateEvent e)
    {
        try
        {
            if (irisServer == null)
            {
                throw new RemoteException();
            }
            IconNode selectedNode = (IconNode) ( (TreePanel) e.getSource()).getSelectedTreeNode();
            TreeUserObject selectedUserObject = (TreeUserObject) selectedNode.getUserObject();
            if (selectedUserObject.getServiceName() == null)
            {
                myLogger.info("Trying to ping non service is not possible and utterly stupid :-)");
                return;
            }
            StatusJPanel.getInstance().setStatusText("Pinging service " + selectedUserObject.getServiceName());
            StatusJPanel.getInstance().startProgressBar();
            myLogger.debug("Pinging remote service");
            //boolean alive = irisServer.isAlive(  selectedUserObject.getServiceName() ) ;
            StatusJPanel.getInstance().stopProgressBar();
            StatusJPanel.getInstance().setStatusText("Service is alive!");
        }
        catch (RemoteException ex)
        {
            StatusJPanel.getInstance().setStatusText("Service is NOT alive!");
            StatusJPanel.getInstance().setStatusErrorText("Could not stop service : " +
                ( (TreePanel) e.getSource()).getSelectedTreeNode(), 2000);
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("Failed stopping service");
            dlg.setInformationText("Remote message: " + ex.getMessage() +
                " Iris could not be contacted - verify RMI server is running and Iris started and binded to RMI. " +
                "Could not stop service : " + ( (TreePanel) e.getSource()).getSelectedTreeNode());
            dlg.setStackTrace(ex);
            dlg.setVisible(true);
        }
    }

    /**
     * Use to stop a remote Iris service
     *
     * @param e
     */
    private void stopRemoteService(ApplicationStateEvent e)
    {
        try
        {
            if (irisServer == null)
            {
                throw new RemoteException();
            }
            IconNode selectedNode = (IconNode) ( (TreePanel) e.getSource()).getSelectedTreeNode();
            TreeUserObject selectedUserObject = (TreeUserObject) selectedNode.getUserObject();
            StatusJPanel.getInstance().setStatusText("Stoping service " + selectedUserObject.getServiceName());
            StatusJPanel.getInstance().startProgressBar();
            irisServer.stopService(selectedUserObject.getServiceName());
            StatusJPanel.getInstance().stopProgressBar();
        }
        catch (RemoteException ex)
        {
            StatusJPanel.getInstance().setStatusErrorText("Could not stop service : " +
                ( (TreePanel) e.getSource()).getSelectedTreeNode(), 2000);
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("Failed stopping service");
            dlg.setInformationText("Remote message: " + ex.getMessage() +
                " Iris could not be contacted - verify RMI server is running and Iris started and binded to RMI. " +
                "Could not stop service : " + ( (TreePanel) e.getSource()).getSelectedTreeNode());
            dlg.setStackTrace(ex);
            dlg.setVisible(true);

        }
    }

    /**
     * Get the remote reference to the Iris server
     * @return
     */
    public IrisOperations getIrisServer()
    {
        return irisServer;
    }

    /**
     * Use to start a remote Iris service
     * @param e
     */
    private void startRemoteService(ApplicationStateEvent e)
    {
        try
        {
            if (irisServer == null)
            {
                throw new RemoteException();
            }
            IconNode selectedNode = (IconNode) ( (TreePanel) e.getSource()).getSelectedTreeNode();
            TreeUserObject selectedUserObject = (TreeUserObject) selectedNode.getUserObject();
            StatusJPanel.getInstance().setStatusText("Starting service " + selectedUserObject.getServiceName());
            StatusJPanel.getInstance().startProgressBar();
            irisServer.startService(selectedUserObject.getServiceName());
            StatusJPanel.getInstance().stopProgressBar();
        }
        catch (RemoteException ex)
        {
            StatusJPanel.getInstance().setStatusErrorText("Could not start service : " +
                ( (TreePanel) e.getSource()).getSelectedTreeNode(), 2000);
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("Failed starting service");
            dlg.setInformationText("Remote message: " + ex.getMessage() +
                " Iris could not be contacted - verify RMI server is running and Iris started and binded to RMI. " +
                "Could not stop service : " + ( (TreePanel) e.getSource()).getSelectedTreeNode());
            dlg.setStackTrace(ex);
            dlg.setVisible(true);
        }
    }

    private void updateFileToEJBPanel(Service selectedService)
    {
        ServiceFileEJB fejbservice = (ServiceFileEJB) selectedService;
        ServiceFileToEJBPanel myServiceFileToEJBPanel = null;
        if (!MainFrame.getInstance().getServicePanels().containsKey(fejbservice.getId()))
        {
            myServiceFileToEJBPanel = new ServiceFileToEJBPanel();
            myServiceFileToEJBPanel.setInPathStr(selectedService.getInfolderPath());
            myServiceFileToEJBPanel.setFolderScanIntervall(String.valueOf(selectedService.getInFolderIntervall()));
            myServiceFileToEJBPanel.setSessionEJB(fejbservice.getServiceName());
            myServiceFileToEJBPanel.setSessionEJBMethod(fejbservice.getServiceMethod());

            setLoggingPanel(myServiceFileToEJBPanel.getMyLoggingPanel(), selectedService);
            setArchivingPanel(myServiceFileToEJBPanel.getMyArchivingPanel(), selectedService);
            setErrorPanel(myServiceFileToEJBPanel.getMyErrorPanel(), selectedService);
            setTransformPanel(myServiceFileToEJBPanel.getMyTransformPanel(), selectedService);
            setInactivityPanel(myServiceFileToEJBPanel.getMyInactivityPanel(), selectedService);
            setBackupPanel(myServiceFileToEJBPanel.getMyBackupPanel(), selectedService);

            MainFrame.getInstance().getServicePanels().put(fejbservice.getId(), myServiceFileToEJBPanel);
        }
        else
        {
            myServiceFileToEJBPanel = (ServiceFileToEJBPanel) MainFrame.getInstance().getServicePanels().get(
                                      fejbservice.getId());
        }
        myMainFrame.getMainFrameRightPanel().removeAll();
        myMainFrame.getMainFrameRightPanel().invalidate();
        myMainFrame.getMainFrameRightPanel().add(myServiceFileToEJBPanel, BorderLayout.CENTER);
        myMainFrame.getMainFrameRightPanel().validate();
        myMainFrame.getMainFrameRightPanel().repaint();
    }

    private void updatePollEJB(Service selectedService)
    {
        ServicePollEJB pollejbservice = (ServicePollEJB) selectedService;
        ServicePollEJBPanel myServicePollEJBPanel = null;

        if (!MainFrame.getInstance().getServicePanels().containsKey(pollejbservice.getId()))
        {
            myServicePollEJBPanel = new ServicePollEJBPanel();
            myServicePollEJBPanel.setSessionEJB(pollejbservice.getServiceName());
            myServicePollEJBPanel.setSessionEJBMethod(pollejbservice.getServiceMethod());
            myServicePollEJBPanel.setFolderScanIntervall(pollejbservice.getPollintervall());

            setLoggingPanel(myServicePollEJBPanel.getMyLoggingPanel(), pollejbservice);
            setArchivingPanel(myServicePollEJBPanel.getMyArchivingPanel(), pollejbservice);
            setErrorPanel(myServicePollEJBPanel.getMyErrorPanel(), pollejbservice);
            setInactivityPanel(myServicePollEJBPanel.getMyInactivityPanel(), selectedService);
            setBackupPanel(myServicePollEJBPanel.getMyBackupPanel(), selectedService);

            MainFrame.getInstance().getServicePanels().put(pollejbservice.getId(), myServicePollEJBPanel);
        }
        else
        {
            myServicePollEJBPanel = (ServicePollEJBPanel) MainFrame.getInstance().getServicePanels().get(
                                    selectedService.getId());
        }
        myMainFrame.getMainFrameRightPanel().removeAll();
        myMainFrame.getMainFrameRightPanel().invalidate();
        myMainFrame.getMainFrameRightPanel().add(myServicePollEJBPanel, BorderLayout.CENTER);
        myMainFrame.getMainFrameRightPanel().validate();
        myMainFrame.getMainFrameRightPanel().repaint();
    }

    private void updateFileToFilePanel(Service selectedService)
    {
        ServiceFileFile ffservice = (ServiceFileFile) selectedService;
        ServiceFileToFilePanel myServiceFileToFilePanel = null;
        if (!MainFrame.getInstance().getServicePanels().containsKey(selectedService.getId()))
        {
            myServiceFileToFilePanel = new ServiceFileToFilePanel();
            myServiceFileToFilePanel.setInPathStr(selectedService.getInfolderPath());
            myServiceFileToFilePanel.setFolderScanIntervall(String.valueOf(selectedService.getInFolderIntervall()));
            myServiceFileToFilePanel.setOutPathStr(ffservice.getOutfolderPath());

            setLoggingPanel(myServiceFileToFilePanel.getMyLoggingPanel(), selectedService);
            setArchivingPanel(myServiceFileToFilePanel.getMyArchivingPanel(), selectedService);
            setErrorPanel(myServiceFileToFilePanel.getMyErrorPanel(), selectedService);
            setTransformPanel(myServiceFileToFilePanel.getMyTransformPanel(), selectedService);
            setInactivityPanel(myServiceFileToFilePanel.getMyInactivityPanel(), selectedService);
            setBackupPanel(myServiceFileToFilePanel.getMyBackupPanel(), selectedService);

        }
        else
        {
            myServiceFileToFilePanel = (ServiceFileToFilePanel) MainFrame.getInstance().getServicePanels().get(
                                       ffservice.getId());

        }
        myMainFrame.getMainFrameRightPanel().removeAll();
        myMainFrame.getMainFrameRightPanel().invalidate();
        myMainFrame.getMainFrameRightPanel().add(myServiceFileToFilePanel, BorderLayout.CENTER);
        myMainFrame.getMainFrameRightPanel().validate();
        myMainFrame.getMainFrameRightPanel().repaint();
    }

    private void setLoggingPanel(ServiceLoggingPanel panel, Service service)
    {
        panel.initPanel(service);
    }

    private void cloneServiceWizard(ApplicationStateEvent e)
    {
        IconNode selectedNode = (IconNode) ( (TreePanel) e.getSource()).getSelectedTreeNode();
        TreeUserObject selectedUserObject = (TreeUserObject) selectedNode.getUserObject();
        StatusJPanel.getInstance().setStatusText("Cloning service " + selectedUserObject.getServiceName());
        HashMap map = ClientSystemConfigHandler.getInstance().getServicesMap();
        Service aService = (Service) map.get(selectedUserObject.getServiceName());
        CloneFrame fr = new CloneFrame(aService);
        fr.setVisible(true);
    }

    /**
     * Initialize the panel for the selected service
     *
     * @param panel
     * @param selectedService
     */
    private void setArchivingPanel(ArchivePanel panel, Service selectedService)
    {
        panel.init(selectedService, true);
    }

    /**
     * Initialize the panel for the selected service
     *
     * @param panel
     * @param selectedService
     */
    private void setBackupPanel(BackupPanel panel, Service selectedService)
    {
        panel.init(selectedService, true);
    }

    /**
     * Initialize the panel for the selected service
     *
     * @param panel
     * @param selectedService
     */
    private void setErrorPanel(ErrorPanel panel, Service selectedService)
    {
        panel.initPanel(selectedService, true);
    }

    /**
     * Initialize the panel for the selected service
     *
     * @param panel
     * @param selectedService
     */
    private void setTransformPanel(TransformPanel panel, Service selectedService)
    {
        panel.initPanel(selectedService);
    }

    /**
     * Initialize the panel for the selected service
     *
     * @param panel
     * @param selectedService
     */
    private void setInactivityPanel(InactivityPanel panel, Service selectedService)
    {
        panel.initPanel(selectedService, true);
    }

    /**
     *
     * TODO finsih remote call operations
     *
     * @param e
     */
    private void cloneService(ApplicationStateEvent e)
    {
        try
        {
            if (irisServer == null)
            {
                throw new RemoteException();
            }
//            IconNode selectedNode = (IconNode) ( (TreePanel) e.getSource()).getSelectedTreeNode();
            /*        TreeUserObject selectedUserObject = (TreeUserObject) selectedNode.getUserObject();
                         if (selectedUserObject.getServiceName() == null)
                         {
                         myLogger.info("Trying to ping non service is not possible and utterly stupid :-)");
                         return;
                         }
             */
            StatusJPanel.getInstance().setStatusText("Cloning service ");
            StatusJPanel.getInstance().startProgressBar();
            myLogger.debug("Cloning on remote server");
            //boolean alive = irisServer.isAlive(  selectedUserObject.getServiceName() ) ;
            StatusJPanel.getInstance().stopProgressBar();
            StatusJPanel.getInstance().setStatusText("Service is alive!");
        }
        catch (RemoteException ex)
        {
            StatusJPanel.getInstance().setStatusText("Service is NOT alive!");
            StatusJPanel.getInstance().setStatusErrorText("Could not stop service : " +
                ( (TreePanel) e.getSource()).getSelectedTreeNode(), 2000);
            InformationDialog dlg = new InformationDialog();
            dlg.setTitle("Failed stopping service");
            dlg.setInformationText("Remote message: " + ex.getMessage() +
                " Iris could not be contacted - verify RMI server is running and Iris started and binded to RMI. " +
                "Could not stop service : " + ( (TreePanel) e.getSource()).getSelectedTreeNode());
            dlg.setStackTrace(ex);
            dlg.setVisible(true);
        }
    }

}
