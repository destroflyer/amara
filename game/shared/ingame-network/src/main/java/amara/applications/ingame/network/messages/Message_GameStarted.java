/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_GameStarted extends AbstractMessage {

    public Message_GameStarted() {

    }

    public Message_GameStarted(int playerEntity) {
        this.playerEntity = playerEntity;
    }
    private int playerEntity;

    public int getPlayerEntity() {
        return playerEntity;
    }
}
