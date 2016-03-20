/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.network.backends;

import com.jme3.network.Message;
import amara.applications.master.client.launcher.panels.PanPlay;
import amara.applications.master.network.messages.*;
import amara.libraries.applications.windowed.FrameUtil;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class GameSelectionAcceptBackend implements MessageBackend{

    public GameSelectionAcceptBackend(PanPlay panPlay){
        this.panPlay = panPlay;
    }
    private PanPlay panPlay;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameSelectionAcceptRequest){
            boolean isAccepted = FrameUtil.showYesNoDialog(panPlay, "Match found! Accept?");
            messageResponse.addAnswerMessage(new Message_AcceptGameSelection(isAccepted));
        }
    }
}
