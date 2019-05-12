/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.master.network.messages.objects.GameSelection;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_GameInfo extends AbstractMessage{
    
    public Message_GameInfo(){
        
    }
    
    public Message_GameInfo(GameSelection gameSelection, int playerEntity){
        this.gameSelection = gameSelection;
        this.playerEntity = playerEntity;
    }
    private GameSelection gameSelection;
    private int playerEntity;

    public GameSelection getGameSelection(){
        return gameSelection;
    }

    public int getPlayerEntity(){
        return playerEntity;
    }
}
