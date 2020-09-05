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
        String whereClause = (canSeeNonPublicCharacters(playerId) ? "" : " WHERE is_public = 1");
        QueryResult result_Characters = databaseAppState.getQueryResult("SELECT id, name, title, lore, is_public FROM characters" + whereClause);
        LinkedList<OwnedGameCharacter> ownedCharacters = new LinkedList<>();
        while (result_Characters.next()) {
            // Character
            int characterId = result_Characters.getInteger("id");
            String characterName = result_Characters.getString("name");
            String characterTitle = result_Characters.getString("title");
            String characterLore = result_Characters.getString("lore");
            boolean characterIsPublic = result_Characters.getBoolean("is_public");
            // Owned skins
            QueryResult result_Skins = databaseAppState.getQueryResult(
                "SELECT characters_skins.id as id, characters_skins.title as title FROM users_characters_skins " +
                "JOIN characters_skins ON characters_skins.id = users_characters_skins.skin_id " +
                "WHERE user_id = " + playerId + " AND characters_skins.character_id = " + characterId
            );
            tmpOwnedSkins.clear();
            while (result_Skins.next()) {
                int skinId = result_Skins.getInteger("id");
                String skinTitle = result_Skins.getString("title");
                tmpOwnedSkins.add(new GameCharacterSkin(skinId, skinTitle));
            }
            result_Skins.close();
            // Active skin
            Integer activeSkinIdResult = databaseAppState.getQueryResult("SELECT skin_id FROM users_characters_active_skins WHERE (user_id = " + playerId + ") AND (character_id = " + characterId + ") LIMIT 1").nextInteger_Close();
            int activeSkinId = ((activeSkinIdResult != null) ? activeSkinIdResult : 0);

            GameCharacter character = new GameCharacter(characterId, characterName, characterTitle, characterLore, characterIsPublic, tmpOwnedSkins.toArray(new GameCharacterSkin[0]));
            ownedCharacters.add(new OwnedGameCharacter(character, activeSkinId));
        }
        result_Characters.close();
        return ownedCharacters.toArray(new OwnedGameCharacter[0]);
    }

    private boolean canSeeNonPublicCharacters(int playerId) {
        // Should be configured somewhere else, e.g. in database
        return ((playerId == 1) || (playerId == 2));
    }
}
