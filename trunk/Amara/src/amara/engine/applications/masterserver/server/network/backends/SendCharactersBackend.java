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

/**
 *
 * @author Carl
 */
public class SendCharactersBackend implements MessageBackend{

    public SendCharactersBackend(DatabaseAppState databaseAppState){
        this.databaseAppState = databaseAppState;
    }
    private DatabaseAppState databaseAppState;
    
    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse){
        if(receivedMessage instanceof Message_GetCharacters){
            Message_GetCharacters message = (Message_GetCharacters) receivedMessage;
            try{
                ResultSet charactersResultSet = databaseAppState.getResultSet("SELECT id, title FROM characters");
                LinkedList<GameCharacter> characters = new LinkedList<GameCharacter>();
                while(charactersResultSet.next()){
                    int characterID = charactersResultSet.getInt(1);
                    String characterTitle = charactersResultSet.getString(2);
                    LinkedList<GameCharacterSkin> skins = new LinkedList<GameCharacterSkin>();
                    ResultSet skinsResultSet = databaseAppState.getResultSet("SELECT id, title FROM characters_skins WHERE characterid = " + characterID);
                    while(skinsResultSet.next()){
                        int skinID = skinsResultSet.getInt(1);
                        String skinTitle = skinsResultSet.getString(2);
                        skins.add(new GameCharacterSkin(skinID, skinTitle));
                    }
                    skinsResultSet.close();
                    characters.add(new GameCharacter(characterID, characterTitle, skins.toArray(new GameCharacterSkin[0])));
                }
                charactersResultSet.close();
                messageResponse.addAnswerMessage(new Message_Characters(characters.toArray(new GameCharacter[0])));
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    }
}
