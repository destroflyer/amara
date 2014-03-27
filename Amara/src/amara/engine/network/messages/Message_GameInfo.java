/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_GameInfo extends AbstractMessage{
    
    public Message_GameInfo(){
        
    }
    
    public Message_GameInfo(String mapName, int playerEntity){
        this.mapName = mapName;
        this.playerEntity = playerEntity;
    }
    private String mapName;
    private int playerEntity;

    public String getMapName(){
        return mapName;
    }

    public int getPlayerEntity(){
        return playerEntity;
    }
}
