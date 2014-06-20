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
public class SendOwnedCharactersBackend implements MessageBackend{

    public SendOwnedCharactersBackend(DatabaseAppState databaseAppState, ConnectedPlayers connectedPlayers){
        this.databaseAppState = databaseAppState;
        this.connectedPlayers = connectedPlayers;
    }
    private DatabaseAppState databaseAppState;
    private ConnectedPlayers connectedPlayers;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GetOwnedCharacters){
            Message_GetOwnedCharacters message = (Message_GetOwnedCharacters) receivedMessage;
            int playerID = connectedPlayers.getPlayer(messageResponse.getClientID()).getID();
            try{
                ResultSet charactersResultSet = databaseAppState.getResultSet("SELECT characterid, skinid FROM users_characters WHERE userid = " + playerID);
                LinkedList<OwnedGameCharacter> ownedCharacters = new LinkedList<OwnedGameCharacter>();
                while(charactersResultSet.next()){
                    int characterID = charactersResultSet.getInt(1);
                    String characterTitle = databaseAppState.getString("SELECT title FROM characters WHERE id = " + characterID);
                    int activeSkinID = charactersResultSet.getInt(2);
                    LinkedList<GameCharacterSkin> skins = new LinkedList<GameCharacterSkin>();
                    ResultSet skinsResultSet = databaseAppState.getResultSet("SELECT id, title FROM characters_skins WHERE characterid = " + characterID);
                    while(skinsResultSet.next()){
                        int skinID = skinsResultSet.getInt(1);
                        int id = databaseAppState.getInteger("SELECT id FROM users_characters_skins WHERE (userid = " + playerID + ") AND (skinid = " + skinID + ")");
                        if(id != 0){
                            String skinTitle = skinsResultSet.getString(2);
                            skins.add(new GameCharacterSkin(skinID, skinTitle));
                        }
                    }
                    skinsResultSet.close();
                    GameCharacter character = new GameCharacter(characterID, characterTitle, skins.toArray(new GameCharacterSkin[0]));
                    ownedCharacters.add(new OwnedGameCharacter(character, activeSkinID));
                }
                charactersResultSet.close();
                messageResponse.addAnswerMessage(new Message_OwnedCharacters(ownedCharacters.toArray(new OwnedGameCharacter[0])));
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}
