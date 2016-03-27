/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import amara.applications.master.network.messages.objects.*;
import amara.core.Util;
import amara.libraries.database.QueryResult;

/**
 *
 * @author Carl
 */
public class PlayersContentsAppState extends ServerBaseAppState{
    
    private LinkedList<GameCharacterSkin> tmpOwnedSkins = new LinkedList<GameCharacterSkin>();
    private LinkedList<Integer> tmpInventory = new LinkedList<Integer>();
    private LinkedList<OwnedItem> tmpOwnedItems = new LinkedList<OwnedItem>();
    
    public OwnedGameCharacter[] getOwnedCharacters(int playerID){
        DatabaseAppState databaseAppState = getAppState(DatabaseAppState.class);
        try{
            QueryResult result_UserCharacters = databaseAppState.getQueryResult("SELECT characterid, skinid, inventory FROM users_characters WHERE userid = " + playerID + " ORDER BY characterid");
            LinkedList<OwnedGameCharacter> ownedCharacters = new LinkedList<OwnedGameCharacter>();
            while(result_UserCharacters.next()){
                int characterID = result_UserCharacters.getInteger("characterid");
                QueryResult result_Characters = databaseAppState.getQueryResult("SELECT name, title, is_public FROM characters WHERE id = " + characterID + " LIMIT 1");
                result_Characters.next();
                String characterName = result_Characters.getString("name");
                String characterTitle = result_Characters.getString("title");
                boolean characterIsPublic = result_Characters.getBoolean("is_public");
                result_Characters.close();
                int activeSkinID = result_UserCharacters.getInteger("skinid");
                QueryResult result_Skins = databaseAppState.getQueryResult("SELECT id, title FROM characters_skins WHERE characterid = " + characterID);
                tmpOwnedSkins.clear();
                while(result_Skins.next()){
                    int skinID = result_Skins.getInteger("id");
                    int id = databaseAppState.getQueryResult("SELECT id FROM users_characters_skins WHERE (userid = " + playerID + ") AND (skinid = " + skinID + ") LIMIT 1").nextInteger_Close();
                    if(id != 0){
                        String skinTitle = result_Skins.getString("title");
                        tmpOwnedSkins.add(new GameCharacterSkin(skinID, skinTitle));
                    }
                }
                result_Skins.close();
                ResultSet result_Inventory = result_UserCharacters.getArray("inventory").getResultSet();
                tmpInventory.clear();
                while(result_Inventory.next()){
                    tmpInventory.add(result_Inventory.getInt(2));
                }
                result_Inventory.close();
                GameCharacter character = new GameCharacter(characterID, characterName, characterTitle, characterIsPublic, tmpOwnedSkins.toArray(new GameCharacterSkin[tmpOwnedItems.size()]));
                ownedCharacters.add(new OwnedGameCharacter(character, activeSkinID, Util.convertToArray(tmpInventory)));
            }
            result_UserCharacters.close();
            return ownedCharacters.toArray(new OwnedGameCharacter[0]);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public OwnedItem[] getOwnedItems(int playerID){
        DatabaseAppState databaseAppState = getAppState(DatabaseAppState.class);
        QueryResult results_UserItems = databaseAppState.getQueryResult("SELECT itemid, amount FROM users_items WHERE userid = " + playerID);
        tmpOwnedItems.clear();
        while(results_UserItems.next()){
            int itemID = results_UserItems.getInteger("itemid");
            QueryResult results_Items = databaseAppState.getQueryResult("SELECT name, title FROM items WHERE id = " + itemID);
            results_Items.next();
            String itemName = results_Items.getString("name");
            String itemTitle = results_Items.getString("title");
            int amount = results_UserItems.getInteger("amount");
            tmpOwnedItems.add(new OwnedItem(new Item(itemID, itemName, itemTitle), amount));
            results_Items.close();
        }
        results_UserItems.close();
        return tmpOwnedItems.toArray(new OwnedItem[tmpOwnedItems.size()]);
    }
}
