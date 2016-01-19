/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.network.messages.*;
import amara.applications.ingame.shared.games.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class InitializeClientBackend implements MessageBackend{

    public InitializeClientBackend(Game game){
        this.game = game;
    }
    private Game game;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_ClientInitialized){
            Message_ClientInitialized message = (Message_ClientInitialized) receivedMessage;
            GamePlayer player = game.getPlayer(messageResponse.getClientID());
            player.setInitialized(true);
            if(game.areAllPlayersInitialized()){
                game.start();
                messageResponse.addBroadcastMessage(new Message_GameStarted());
                System.out.println("All players connected, game started.");
            }
        }
    }
}
