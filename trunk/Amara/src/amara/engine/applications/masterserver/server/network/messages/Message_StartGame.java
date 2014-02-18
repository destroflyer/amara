/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_StartGame extends AbstractMessage{
    
    public Message_StartGame(){
        
    }
    
    public Message_StartGame(int mapID, int[] playerIDs){
        this.mapID = mapID;
        this.playerIDs = playerIDs;
    }
    private int mapID;
    private int[] playerIDs;

    public int getMapID(){
        return mapID;
    }

    public int[] getPlayerIDs(){
        return playerIDs;
    }
}
