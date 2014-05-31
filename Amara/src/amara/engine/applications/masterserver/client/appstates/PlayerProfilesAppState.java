/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.appstates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import amara.engine.applications.*;;
import amara.engine.applications.masterserver.client.network.backends.*;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.NetworkClient;
import amara.engine.applications.masterserver.server.protocol.*;

/**
 *
 * @author Carl
 */
public class PlayerProfilesAppState extends ClientBaseAppState{

    private LinkedList<Integer> updatingIDs = new LinkedList<Integer>();
    private LinkedList<String> updatingLogins = new LinkedList<String>();
    private ArrayList<Integer> notExistingIDs = new ArrayList<Integer>();
    private ArrayList<String> notExistingLogins = new ArrayList<String>();
    private HashMap<Integer, PlayerProfileData> profilesByIDs = new HashMap<Integer, PlayerProfileData>();
    private HashMap<String, PlayerProfileData> profilesByLogins = new HashMap<String, PlayerProfileData>();
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new ReceivePlayerProfilesDataBackend(this));
    }
    
    public void onUpdateStarted(int playerID){
        updatingIDs.add(playerID);
    }
    
    public void onUpdateStarted(String login){
        updatingLogins.add(login);
    }
    
    public boolean isUpdating(int playerID){
        return updatingIDs.contains(playerID);
    }
    
    public boolean isUpdating(String login){
        return updatingLogins.contains(login);
    }
    
    public void onUpdateFinished(int playerID){
        updatingIDs.remove((Integer) playerID);
    }
    
    public void onUpdateFinished(String login){
        updatingLogins.remove(login);
    }
    
    public void setProfileNotExistant(int playerID){
        notExistingIDs.add(playerID);
    }
    
    public void setProfileNotExistant(String login){
        notExistingLogins.add(login);
    }
    
    public boolean isProfileNotExistant(int playerID){
        return notExistingIDs.contains(playerID);
    }
    
    public boolean isProfileNotExistant(String login){
        return notExistingLogins.contains(login);
    }
    
    public void setProfile(PlayerProfileData playerProfileData){
        profilesByIDs.put(playerProfileData.getID(), playerProfileData);
        profilesByLogins.put(playerProfileData.getLogin(), playerProfileData);
    }
    
    public PlayerProfileData getProfile(int playerID){
        return profilesByIDs.get(playerID);
    }
    
    public PlayerProfileData getProfile(String login){
        return profilesByLogins.get(login);
    }
}
