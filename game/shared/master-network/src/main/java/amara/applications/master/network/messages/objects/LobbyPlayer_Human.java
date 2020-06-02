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
public class LobbyPlayer_Human extends LobbyPlayer{

    public LobbyPlayer_Human() {
        
    }

    public LobbyPlayer_Human(int playerId) {
        this.playerId = playerId;
    }
    private int playerId;

    public int getPlayerId() {
        return playerId;
    }
}
