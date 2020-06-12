/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages;

import amara.applications.master.network.messages.objects.GameSelection;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class Message_GameInfo extends AbstractMessage {

    public Message_GameInfo() {
        
    }

    public Message_GameInfo(GameSelection gameSelection) {
        this.gameSelection = gameSelection;
    }
    private GameSelection gameSelection;

    public GameSelection getGameSelection() {
        return gameSelection;
    }
}
