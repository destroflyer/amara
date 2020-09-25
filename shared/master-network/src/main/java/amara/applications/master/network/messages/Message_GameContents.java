package amara.applications.master.network.messages;

import amara.applications.master.network.messages.objects.GameCharacter;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

@Serializable
public class Message_GameContents extends AbstractMessage {

    public Message_GameContents() {

    }

    public Message_GameContents(GameCharacter[] characters) {
        this.characters = characters;
    }
    private GameCharacter[] characters;

    public GameCharacter[] getCharacters() {
        return characters;
    }
}
