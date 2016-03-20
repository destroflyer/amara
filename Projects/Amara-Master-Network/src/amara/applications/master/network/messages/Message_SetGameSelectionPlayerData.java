/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.applications.master.network.messages.objects.GameSelectionPlayerData;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_SetGameSelectionPlayerData extends AbstractMessage{
    
    public Message_SetGameSelectionPlayerData(){
        
    }
    
    public Message_SetGameSelectionPlayerData(GameSelectionPlayerData gameSelectionPlayerData){
        this.gameSelectionPlayerData = gameSelectionPlayerData;
    }
    private GameSelectionPlayerData gameSelectionPlayerData;

    public GameSelectionPlayerData getGameSelectionPlayerData(){
        return gameSelectionPlayerData;
    }
}
