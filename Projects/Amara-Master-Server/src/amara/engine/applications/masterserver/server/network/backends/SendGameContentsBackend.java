/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.server.network.backends;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import com.jme3.network.Message;
import amara.engine.applications.masterserver.server.appstates.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.network.*;
import amara.engine.network.messages.protocol.*;
import amara.game.players.ConnectedPlayers;

/**
 *
 * @author Carl
 */
public class SendGameContentsBackend implements MessageBackend{

    public SendGameContentsBackend(DatabaseAppState databaseAppState, ConnectedPlayers connectedPlayers, PlayersContentsAppState playersContentsAppState){
        this.databaseAppState = databaseAppState;
        this.connectedPlayers = connectedPlayers;
        this.playersContentsAppState = playersContentsAppState;
    }
    private DatabaseAppState databaseAppState;
    private ConnectedPlayers connectedPlayers;
    private PlayersContentsAppState playersContentsAppState;
    private GameCharacter[] characters;
    private Item[] items;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GetGameContents){
            Message_GetGameContents message = (Message_GetGameContents) receivedMessage;
            messageResponse.addAnswerMessage(new Message_GameContents(getCharacters(), getItems()));
            int playerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            messageResponse.addAnswerMessage(new Message_OwnedCharacters(playersContentsAppState.getOwnedCharacters(playerID)));
            messageResponse.addAnswerMessage(new Message_OwnedItems(playersContentsAppState.getOwnedItems(playerID)));
        }
    }
    
    private GameCharacter[] getCharacters(){
        if(characters == null){
            try{
                ResultSet charactersResultSet = databaseAppState.getResultSet("SELECT id, name, title FROM characters");
                LinkedList<GameCharacter> charactersList = new LinkedList<GameCharacter>();
                while(charactersResultSet.next()){
                    int characterID = charactersResultSet.getInt(1);
                    String characterName = charactersResultSet.getString(2);
                    String characterTitle = charactersResultSet.getString(3);
                    LinkedList<GameCharacterSkin> skins = new LinkedList<GameCharacterSkin>();
                    ResultSet skinsResultSet = databaseAppState.getResultSet("SELECT id, title FROM characters_skins WHERE characterid = " + characterID);
                    while(skinsResultSet.next()){
                        int skinID = skinsResultSet.getInt(1);
                        String skinTitle = skinsResultSet.getString(2);
                        skins.add(new GameCharacterSkin(skinID, skinTitle));
                    }
                    skinsResultSet.close();
                    charactersList.add(new GameCharacter(characterID, characterName, characterTitle, skins.toArray(new GameCharacterSkin[0])));
                }
                charactersResultSet.close();
                characters = charactersList.toArray(new GameCharacter[0]);
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return characters;
    }
    
    private Item[] getItems(){
        if(items == null){
            try{
                ResultSet itemsResultSet = databaseAppState.getResultSet("SELECT id, name, title FROM items");
                LinkedList<Item> itemsList = new LinkedList<Item>();
                while(itemsResultSet.next()){
                    int itemID = itemsResultSet.getInt(1);
                    String itemName = itemsResultSet.getString(2);
                    String itemTitle = itemsResultSet.getString(3);
                    itemsList.add(new Item(itemID, itemName, itemTitle));
                }
                itemsResultSet.close();
                items = itemsList.toArray(new Item[0]);
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return items;
    }
}
