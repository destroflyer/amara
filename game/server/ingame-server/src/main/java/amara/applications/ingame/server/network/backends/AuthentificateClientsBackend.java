/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.entitysystem.components.players.ClientComponent;
import amara.applications.ingame.network.messages.*;
import amara.applications.ingame.shared.games.*;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class AuthentificateClientsBackend implements MessageBackend{

    public AuthentificateClientsBackend(Game game, EntityWorld entityWorld){
        this.game = game;
        this.entityWorld = entityWorld;
    }
    private Game game;
    private EntityWorld entityWorld;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_PlayerAuthentification){
            Message_PlayerAuthentification message = (Message_PlayerAuthentification) receivedMessage;
            GamePlayer player = game.onClientConnected(messageResponse.getClientID(), message.getAuthentificationKey());
            if(player != null){
                entityWorld.setComponent(player.getEntity(), new ClientComponent(messageResponse.getClientID()));
                messageResponse.addAnswerMessage(new Message_GameInfo(game.getGameSelection(), player.getEntity()));
            }
        }
    }
}
