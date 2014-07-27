/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.launcher.client;

import amara.engine.applications.HeadlessAppState;
import amara.engine.applications.masterserver.client.MasterserverClientApplication;
import amara.engine.applications.masterserver.client.appstates.*;
import amara.engine.applications.masterserver.server.protocol.*;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class MasterserverClientUtil{
    
    public static NetworkClient getNetworkClient(){
        return getState(NetworkClientHeadlessAppState.class).getNetworkClient();
    }
    
    public static int getPlayerID(){
        return getState(LoginAppState.class).getPlayerID();
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
