package amara.applications.master.network.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class Message_AvailableMaps extends AbstractMessage {

    public Message_AvailableMaps() {

    }

    public Message_AvailableMaps(String[] mapNames) {
        this.mapNames = mapNames;
    }
    private String[] mapNames;

    public String[] getMapNames() {
        return mapNames;
    }
}
