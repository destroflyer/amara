/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.launcher.network.backends;

import java.awt.EventQueue;
import com.jme3.network.Message;
import amara.applications.master.client.launcher.panels.PanPlay;
import amara.applications.master.network.messages.Message_GameSelectionUpdate;
import amara.applications.master.network.messages.objects.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class UpdateGameSelectionBackend implements MessageBackend{

    public UpdateGameSelectionBackend(PanPlay panPlay){
        this.panPlay = panPlay;
    }
    private PanPlay panPlay;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GameSelectionUpdate){
            Message_GameSelectionUpdate message = (Message_GameSelectionUpdate) receivedMessage;
            final GameSelection gameSelection = message.getGameSelection();
            repair(gameSelection);
            EventQueue.invokeLater(new Runnable(){

                @Override
                public void run(){
                    panPlay.getPanGameSelection().update(gameSelection);
                    panPlay.displayGameSelectionPanel();
                }
            });
        }
    }
    
    //[jME 3.1 SNAPSHOT] A nested array with length 0 isn't sent correctly
    private static void repair(GameSelection gameSelection){
        for(int i=0;i<gameSelection.getTeams().length;i++){
            GameSelectionPlayer[] team = gameSelection.getTeams()[i];
            if((team.length > 0) && (team[0] == null)){
                gameSelection.getTeams()[i] = new GameSelectionPlayer[0];
            }
        }
    }
}
