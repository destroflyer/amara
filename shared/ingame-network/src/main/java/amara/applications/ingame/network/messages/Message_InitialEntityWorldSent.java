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
public class Message_InitialEntityWorldSent extends AbstractMessage {

    public Message_InitialEntityWorldSent() {

    }

    public Message_InitialEntityWorldSent(int playerEntity) {
        this.playerEntity = playerEntity;
    }
    private int playerEntity;

    public int getPlayerEntity() {
        return playerEntity;
    }
}
