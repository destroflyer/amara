package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class Message_LoginResult extends AbstractMessage {

    public Message_LoginResult() {

    }

    public Message_LoginResult(int playerId, boolean isIngame) {
        this.playerId = playerId;
        this.isIngame = isIngame;
    }
    private int playerId;
    private boolean isIngame;

    public int getPlayerId() {
        return playerId;
    }

    public boolean isIngame() {
        return isIngame;
    }
}
