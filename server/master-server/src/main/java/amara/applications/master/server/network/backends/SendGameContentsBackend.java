package amara.applications.master.server.network.backends;

import java.util.LinkedList;

import amara.applications.master.network.messages.Message_AvailableMaps;
import amara.applications.master.network.messages.Message_GameContents;
import amara.applications.master.network.messages.Message_GetGameContents;
import amara.applications.master.network.messages.Message_OwnedCharacters;
import amara.applications.master.network.messages.objects.GameCharacter;
import amara.applications.master.network.messages.objects.GameCharacterSkin;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import amara.applications.master.server.appstates.PlayersContentsAppState;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.libraries.database.QueryResult;
import com.jme3.network.Message;

public class SendGameContentsBackend implements MessageBackend {

    public SendGameContentsBackend(DatabaseAppState databaseAppState, ConnectedPlayers connectedPlayers, PlayersContentsAppState playersContentsAppState) {
        this.databaseAppState = databaseAppState;
        this.connectedPlayers = connectedPlayers;
        this.playersContentsAppState = playersContentsAppState;
    }
    private DatabaseAppState databaseAppState;
    private ConnectedPlayers connectedPlayers;
    private PlayersContentsAppState playersContentsAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_GetGameContents) {
            messageResponse.addAnswerMessage(new Message_GameContents(getCharacters()));
            int playerId = connectedPlayers.getPlayer(messageResponse.getClientId()).getID();
            messageResponse.addAnswerMessage(new Message_OwnedCharacters(playersContentsAppState.getOwnedCharacters(playerId)));
            messageResponse.addAnswerMessage(new Message_AvailableMaps(playersContentsAppState.getAvailableMapNames(playerId)));
        }
    }

    private GameCharacter[] getCharacters() {
        LinkedList<GameCharacter> tmpCharacters = new LinkedList<>();
        LinkedList<GameCharacterSkin> tmpSkins = new LinkedList<>();
        QueryResult result_Characters = databaseAppState.getQueryResult("SELECT id, name, title, lore, is_public FROM characters ORDER by title");
        while (result_Characters.next()) {
            int characterId = result_Characters.getInteger("id");
            String characterName = result_Characters.getString("name");
            String characterTitle = result_Characters.getString("title");
            String characterLore = result_Characters.getString("lore");
            boolean characterIsPublic = result_Characters.getBoolean("is_public");
            QueryResult result_Skins = databaseAppState.getQueryResult("SELECT id, title FROM characters_skins WHERE character_id = " + characterId);
            tmpSkins.clear();
            while (result_Skins.next()) {
                int skinID = result_Skins.getInteger("id");
                String skinTitle = result_Skins.getString("title");
                tmpSkins.add(new GameCharacterSkin(skinID, skinTitle));
            }
            result_Skins.close();
            tmpCharacters.add(new GameCharacter(characterId, characterName, characterTitle, characterLore, characterIsPublic, tmpSkins.toArray(new GameCharacterSkin[tmpSkins.size()])));
        }
        result_Characters.close();
        return tmpCharacters.toArray(new GameCharacter[tmpCharacters.size()]);
    }
}
