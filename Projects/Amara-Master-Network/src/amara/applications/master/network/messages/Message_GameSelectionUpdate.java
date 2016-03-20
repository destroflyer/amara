/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.master.network.messages.objects.GameSelection;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_GameSelectionUpdate extends AbstractMessage{
    
    public Message_GameSelectionUpdate(){
        
    }
    
    public Message_GameSelectionUpdate(GameSelection gameSelection){
        this.gameSelection = gameSelection;
    }
    private GameSelection gameSelection;

    public GameSelection getGameSelection(){
        return gameSelection;
    }
}
