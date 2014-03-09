/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.server.network.backends;

import com.jme3.network.Message;
import amara.engine.network.*;
import amara.engine.network.messages.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.players.*;
import amara.game.games.*;

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
            entityWorld.setComponent(player.getEntityID(), new ClientComponent(messageResponse.getClientID()));
            messageResponse.addAnswerMessage(new Message_GameInfo(game.getMap().getName()));
        }
    }
}
