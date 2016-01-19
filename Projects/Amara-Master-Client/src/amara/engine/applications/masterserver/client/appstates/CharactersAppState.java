/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.appstates;

import amara.applications.master.network.messages.objects.*;
import amara.engine.applications.masterserver.client.network.backends.*;
import amara.engine.network.NetworkClient;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;

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
    private OwnedGameCharacter[] ownedCharacters;

    public void setCharacters(GameCharacter[] characters){
        this.characters = characters;
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

    public void setOwnedCharacters(OwnedGameCharacter[] ownedCharacters){
        this.ownedCharacters = ownedCharacters;
    }

    public OwnedGameCharacter[] getOwnedCharacters(){
        return ownedCharacters;
    }
}
