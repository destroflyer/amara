/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.network.backends;

import com.jme3.network.Message;
import amara.applications.master.client.launcher.panels.PanMainMenu;
import amara.applications.master.network.messages.Message_GenericInformation;
import amara.libraries.applications.windowed.FrameUtil;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class GenericInformationBackend implements MessageBackend{

    public GenericInformationBackend(PanMainMenu panMainMenu){
        this.panMainMenu = panMainMenu;
    }
    private PanMainMenu panMainMenu;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GenericInformation){
            Message_GenericInformation message = (Message_GenericInformation) receivedMessage;
            FrameUtil.MessageType messageType = (message.isError() ? FrameUtil.MessageType.ERROR : FrameUtil.MessageType.INFORMATION);
            FrameUtil.showMessageDialog(panMainMenu, message.getMessage(), messageType);
        }
    }
}
