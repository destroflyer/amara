/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.network.messages.objects;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class LobbyPlayer_Bot extends LobbyPlayer{

    public LobbyPlayer_Bot(){
        
    }

    public LobbyPlayer_Bot(BotType botType, String name, GameSelectionPlayerData gameSelectionPlayerData){
        this.botType = botType;
        this.name = name;
        this.gameSelectionPlayerData = gameSelectionPlayerData;
    }
    private BotType botType;
    private GameSelectionPlayerData gameSelectionPlayerData;
    private String name;

    public BotType getBotType(){
        return botType;
    }

    public GameSelectionPlayerData getGameSelectionPlayerData(){
        return gameSelectionPlayerData;
    }

    public String getName() {
        return name;
    }
}
