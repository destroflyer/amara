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
    
    private static <T extends HeadlessAppState> T getState(Class<T> stateClass){
        return MasterserverClientApplication.getInstance().getStateManager().getState(stateClass);
    }
}
