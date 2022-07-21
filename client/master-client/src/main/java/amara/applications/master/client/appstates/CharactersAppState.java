/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.appstates;

import java.util.LinkedList;
import amara.applications.master.client.network.backends.*;
import amara.applications.master.network.messages.objects.*;
import amara.core.Util;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class CharactersAppState extends ClientBaseAppState{

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new ReceiveCharactersBackend(this));
        networkClient.addMessageBackend(new ReceiveOwnedCharactersBackend(this));
    }
    private GameCharacter[] characters;
    private GameCharacter[] publicCharacters;
    private OwnedGameCharacter[] ownedCharacters;

    public void setCharacters(GameCharacter[] characters){
        this.characters = characters;
        LinkedList<GameCharacter> publicCharactersList = new LinkedList<>();
        for(GameCharacter character : characters){
            if(character.isPublic()){
                publicCharactersList.add(character);
            }
        }
        publicCharacters = publicCharactersList.toArray(GameCharacter[]::new);
    }
    
    public GameCharacter getCharacter(int characterID){
        for(GameCharacter character : characters){
            if(character.getID() == characterID){
                return character;
            }
        }
        return null;
    }

    public GameCharacter[] getCharacters(){
        return characters;
    }

    public GameCharacter[] getPublicCharacters(){
        return publicCharacters;
    }

    public void setOwnedCharacters(OwnedGameCharacter[] ownedCharacters){
        this.ownedCharacters = ownedCharacters;
    }

    public OwnedGameCharacter[] getOwnedCharacters(){
        return ownedCharacters;
    }
}
