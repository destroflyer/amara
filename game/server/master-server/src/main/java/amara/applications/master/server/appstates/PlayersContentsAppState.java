/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import java.util.LinkedList;
import amara.applications.master.network.messages.objects.*;
import amara.libraries.database.QueryResult;

/**
 *
 * @author Carl
 */
public class PlayersContentsAppState extends ServerBaseAppState {

    private LinkedList<GameCharacterSkin> tmpOwnedSkins = new LinkedList<>();

    public OwnedGameCharacter[] getOwnedCharacters(int playerId) {
        DatabaseAppState databaseAppState = getAppState(DatabaseAppState.class);
        QueryResult result_UserCharacters = databaseAppState.getQueryResult("SELECT character_id, skin_id FROM users_characters WHERE user_id = " + playerId + " ORDER BY character_id");
        LinkedList<OwnedGameCharacter> ownedCharacters = new LinkedList<>();
        while (result_UserCharacters.next()) {
            int characterId = result_UserCharacters.getInteger("character_id");
            QueryResult result_Characters = databaseAppState.getQueryResult("SELECT name, title, lore, is_public FROM characters WHERE id = " + characterId + " LIMIT 1");
            result_Characters.next();
            String characterName = result_Characters.getString("name");
            String characterTitle = result_Characters.getString("title");
            String characterLore = result_Characters.getString("lore");
            boolean characterIsPublic = result_Characters.getBoolean("is_public");
            result_Characters.close();
            int activeSkinId = result_UserCharacters.getInteger("skin_id");
            QueryResult result_Skins = databaseAppState.getQueryResult("SELECT id, title FROM characters_skins WHERE character_id = " + characterId);
            tmpOwnedSkins.clear();
            while (result_Skins.next()) {
                int skinID = result_Skins.getInteger("id");
                int id = databaseAppState.getQueryResult("SELECT id FROM users_characters_skins WHERE (user_id = " + playerId + ") AND (skin_id = " + skinID + ") LIMIT 1").nextInteger_Close();
                if (id != 0) {
                    String skinTitle = result_Skins.getString("title");
                    tmpOwnedSkins.add(new GameCharacterSkin(skinID, skinTitle));
                }
            }
            result_Skins.close();
            GameCharacter character = new GameCharacter(characterId, characterName, characterTitle, characterLore, characterIsPublic, tmpOwnedSkins.toArray(new GameCharacterSkin[tmpOwnedSkins.size()]));
            ownedCharacters.add(new OwnedGameCharacter(character, activeSkinId));
        }
        result_UserCharacters.close();
        return ownedCharacters.toArray(new OwnedGameCharacter[0]);
    }
}
