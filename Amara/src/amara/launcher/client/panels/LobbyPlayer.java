/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client.panels;

import amara.game.games.PlayerData;
import amara.launcher.client.protocol.PlayerProfileData;

/**
 *
 * @author Carl
 */
public class LobbyPlayer{

    public LobbyPlayer(PlayerProfileData playerProfileData, PlayerData playerData){
        this.playerProfileData = playerProfileData;
        this.playerData = playerData;
    }
    private PlayerProfileData playerProfileData;
    private PlayerData playerData;

    public PlayerProfileData getPlayerProfileData(){
        return playerProfileData;
    }

    public PlayerData getPlayerData(){
        return playerData;
    }

    public void setPlayerData(PlayerData playerData){
        this.playerData = playerData;
    }
}
