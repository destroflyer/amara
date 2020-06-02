/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client;

import amara.applications.master.client.appstates.*;
import amara.applications.master.network.messages.objects.*;
import amara.libraries.applications.headless.applications.HeadlessAppState;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class MasterserverClientUtil{
    
    public static NetworkClient getNetworkClient(){
        return getState(NetworkClientHeadlessAppState.class).getNetworkClient();
    }
    
    public static int getPlayerId(){
        return getState(LoginAppState.class).getPlayerId();
    }
    
    public static PlayerProfileData getPlayerProfile(int playerID){
        return getState(PlayerProfilesAppState.class).getPlayerProfile(playerID, null);
    }
    
    public static PlayerProfileData getPlayerProfile(String login){
        return getState(PlayerProfilesAppState.class).getPlayerProfile(0, login);
    }
    
    public static PlayerStatus getPlayerStatus(int playerID){
        return getState(PlayerStatusesAppState.class).getPlayerStatus(playerID);
    }
    
    public static GameCharacter getCharacter(int characterID){
        return getState(CharactersAppState.class).getCharacter(characterID);
    }
    
    public static GameCharacter[] getCharacters(){
        return getState(CharactersAppState.class).getCharacters();
    }
    
    public static GameCharacter[] getPublicCharacters(){
        return getState(CharactersAppState.class).getPublicCharacters();
    }
    
    public static OwnedGameCharacter[] getOwnedCharacters(){
        return getState(CharactersAppState.class).getOwnedCharacters();
    }
    
    public static Item getItem(int itemID){
        return getState(ItemsAppState.class).getItem(itemID);
    }
    
    public static Item[] getItems(){
        return getState(ItemsAppState.class).getItems();
    }
    
    public static OwnedItem[] getOwnedItems(){
        return getState(ItemsAppState.class).getOwnedItems();
    }
    
    private static <T extends HeadlessAppState> T getState(Class<T> stateClass){
        return MasterserverClientApplication.getInstance().getStateManager().getState(stateClass);
    }
}
