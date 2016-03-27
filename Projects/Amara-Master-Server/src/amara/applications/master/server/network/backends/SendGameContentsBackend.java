/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.network.backends;

import java.util.LinkedList;
import com.jme3.network.Message;
import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;
import amara.applications.master.server.appstates.PlayersContentsAppState;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.applications.master.server.appstates.DatabaseAppState;
import amara.libraries.database.QueryResult;
import amara.libraries.network.*;

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
    private LinkedList<GameCharacter> tmpCharacters = new LinkedList<GameCharacter>();
    private LinkedList<GameCharacterSkin> tmpSkins = new LinkedList<GameCharacterSkin>();
    private LinkedList<Item> tmpItems = new LinkedList<Item>();
    
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
            QueryResult result_Characters = databaseAppState.getQueryResult("SELECT id, name, title, is_public FROM characters");
            tmpCharacters.clear();
            while(result_Characters.next()){
                int characterID = result_Characters.getInteger("id");
                String characterName = result_Characters.getString("name");
                String characterTitle = result_Characters.getString("title");
                boolean characterIsPublic = result_Characters.getBoolean("is_public");
                QueryResult result_Skins = databaseAppState.getQueryResult("SELECT id, title FROM characters_skins WHERE characterid = " + characterID);
                tmpSkins.clear();
                while(result_Skins.next()){
                    int skinID = result_Skins.getInteger("id");
                    String skinTitle = result_Skins.getString("title");
                    tmpSkins.add(new GameCharacterSkin(skinID, skinTitle));
                }
                result_Skins.close();
                tmpCharacters.add(new GameCharacter(characterID, characterName, characterTitle, characterIsPublic, tmpSkins.toArray(new GameCharacterSkin[tmpSkins.size()])));
            }
            result_Characters.close();
            characters = tmpCharacters.toArray(new GameCharacter[tmpCharacters.size()]);
        }
        return characters;
    }
    
    private Item[] getItems(){
        if(items == null){
            QueryResult result_Items = databaseAppState.getQueryResult("SELECT id, name, title FROM items");
            tmpItems.clear();
            while(result_Items.next()){
                int itemID = result_Items.getInteger("id");
                String itemName = result_Items.getString("name");
                String itemTitle = result_Items.getString("title");
                tmpItems.add(new Item(itemID, itemName, itemTitle));
            }
            result_Items.close();
            items = tmpItems.toArray(new Item[tmpItems.size()]);
        }
        return items;
    }
}
