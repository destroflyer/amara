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

/**
 *
 * @author Carl
 */
public class PlayersContentsAppState extends ServerBaseAppState{
    
    public OwnedGameCharacter[] getOwnedCharacters(int playerID){
        DatabaseAppState databaseAppState = getAppState(DatabaseAppState.class);
        try{
            ResultSet charactersResultSet = databaseAppState.getResultSet("SELECT characterid, skinid, inventory FROM users_characters WHERE userid = " + playerID + " ORDER BY characterid");
            LinkedList<OwnedGameCharacter> ownedCharacters = new LinkedList<OwnedGameCharacter>();
            while(charactersResultSet.next()){
                int characterID = charactersResultSet.getInt(1);
                ResultSet characterInformationResultSet = databaseAppState.getResultSet("SELECT name, title FROM characters WHERE id = " + characterID + " LIMIT 1");
                characterInformationResultSet.next();
                String characterName = characterInformationResultSet.getString(1);
                String characterTitle = characterInformationResultSet.getString(2);
                characterInformationResultSet.close();
                int activeSkinID = charactersResultSet.getInt(2);
                LinkedList<GameCharacterSkin> skins = new LinkedList<GameCharacterSkin>();
                ResultSet skinsResultSet = databaseAppState.getResultSet("SELECT id, title FROM characters_skins WHERE characterid = " + characterID);
                while(skinsResultSet.next()){
                    int skinID = skinsResultSet.getInt(1);
                    int id = databaseAppState.getInteger("SELECT id FROM users_characters_skins WHERE (userid = " + playerID + ") AND (skinid = " + skinID + ") LIMIT 1");
                    if(id != 0){
                        String skinTitle = skinsResultSet.getString(2);
                        skins.add(new GameCharacterSkin(skinID, skinTitle));
                    }
                }
                LinkedList<Integer> inventory = new LinkedList<Integer>();
                ResultSet inventorySet = charactersResultSet.getArray(3).getResultSet();
                while(inventorySet.next()){
                    inventory.add(inventorySet.getInt(2));
                }
                inventorySet.close();
                skinsResultSet.close();
                GameCharacter character = new GameCharacter(characterID, characterName, characterTitle, skins.toArray(new GameCharacterSkin[0]));
                ownedCharacters.add(new OwnedGameCharacter(character, activeSkinID, Util.convertToArray(inventory)));
            }
            charactersResultSet.close();
            return ownedCharacters.toArray(new OwnedGameCharacter[0]);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public OwnedItem[] getOwnedItems(int playerID){
        DatabaseAppState databaseAppState = getAppState(DatabaseAppState.class);
        try{
            ResultSet itemsResultSet = databaseAppState.getResultSet("SELECT itemid, amount FROM users_items WHERE userid = " + playerID);
            LinkedList<OwnedItem> ownedItems = new LinkedList<OwnedItem>();
            while(itemsResultSet.next()){
                int itemID = itemsResultSet.getInt(1);
                ResultSet itemInformationResultSet = databaseAppState.getResultSet("SELECT name, title FROM items WHERE id = " + itemID);
                itemInformationResultSet.next();
                String itemName = itemInformationResultSet.getString(1);
                String itemTitle = itemInformationResultSet.getString(2);
                itemInformationResultSet.close();
                int amount = itemsResultSet.getInt(2);
                ownedItems.add(new OwnedItem(new Item(itemID, itemName, itemTitle), amount));
            }
            itemsResultSet.close();
            return ownedItems.toArray(new OwnedItem[0]);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
