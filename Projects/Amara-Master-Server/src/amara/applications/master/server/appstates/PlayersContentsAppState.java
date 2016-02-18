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
            QueryResult results_UserCharacters = databaseAppState.getQueryResult("SELECT characterid, skinid, inventory FROM users_characters WHERE userid = " + playerID + " ORDER BY characterid");
            LinkedList<OwnedGameCharacter> ownedCharacters = new LinkedList<OwnedGameCharacter>();
            while(results_UserCharacters.next()){
                int characterID = results_UserCharacters.getInteger("characterid");
                QueryResult results_Characters = databaseAppState.getQueryResult("SELECT name, title FROM characters WHERE id = " + characterID + " LIMIT 1");
                results_Characters.next();
                String characterName = results_Characters.getString("name");
                String characterTitle = results_Characters.getString("title");
                results_Characters.close();
                int activeSkinID = results_UserCharacters.getInteger("skinid");
                QueryResult results_Skins = databaseAppState.getQueryResult("SELECT id, title FROM characters_skins WHERE characterid = " + characterID);
                tmpOwnedSkins.clear();
                while(results_Skins.next()){
                    int skinID = results_Skins.getInteger("id");
                    int id = databaseAppState.getQueryResult("SELECT id FROM users_characters_skins WHERE (userid = " + playerID + ") AND (skinid = " + skinID + ") LIMIT 1").nextInteger_Close();
                    if(id != 0){
                        String skinTitle = results_Skins.getString("title");
                        tmpOwnedSkins.add(new GameCharacterSkin(skinID, skinTitle));
                    }
                }
                results_Skins.close();
                ResultSet results_Inventory = results_UserCharacters.getArray("inventory").getResultSet();
                tmpInventory.clear();
                while(results_Inventory.next()){
                    tmpInventory.add(results_Inventory.getInt(2));
                }
                results_Inventory.close();
                GameCharacter character = new GameCharacter(characterID, characterName, characterTitle, tmpOwnedSkins.toArray(new GameCharacterSkin[tmpOwnedItems.size()]));
                ownedCharacters.add(new OwnedGameCharacter(character, activeSkinID, Util.convertToArray(tmpInventory)));
            }
            results_UserCharacters.close();
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
