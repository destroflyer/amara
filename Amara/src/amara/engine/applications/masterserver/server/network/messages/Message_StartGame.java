/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import amara.game.games.PlayerData;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_StartGame extends AbstractMessage{
    
    public Message_StartGame(){
        
    }
    
    public Message_StartGame(int mapID, PlayerData[] playerDatas){
        this.mapID = mapID;
        this.playerDatas = playerDatas;
    }
    private int mapID;
    //May the gods forgive me this spelling mistake in order to have a nice iterator name
    private PlayerData[] playerDatas;

    public int getMapID(){
        return mapID;
    }

    public PlayerData[] getPlayerDatas(){
        return playerDatas;
    }
}
