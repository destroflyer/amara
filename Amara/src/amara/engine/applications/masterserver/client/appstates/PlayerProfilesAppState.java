/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.appstates;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import amara.engine.applications.*;;
import amara.engine.applications.masterserver.client.network.backends.ReceivePlayerProfileDataBackend;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.NetworkClient;
import amara.launcher.client.protocol.PlayerProfileData;

/**
 *
 * @author Carl
 */
public class PlayerProfilesAppState extends ClientBaseAppState{

    private LinkedList<String> updatingProfiles = new LinkedList<String>();
    private ArrayList<String> notExistingProfiles = new ArrayList<String>();
    private HashMap<String, PlayerProfileData> profiles = new HashMap<String, PlayerProfileData>();
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new ReceivePlayerProfileDataBackend(this));
    }
    
    public void onProfileUpdateStarted(String login){
        updatingProfiles.add(login);
    }
    
    public boolean isProfileUpdating(String login){
        return updatingProfiles.contains(login);
    }
    
    public void onProfileUpdateFinished(String login){
        updatingProfiles.remove(login);
    }
    
    public void setProfileNotExistant(String login){
        notExistingProfiles.add(login);
    }
    
    public boolean isProfileNotExistant(String login){
        return notExistingProfiles.contains(login);
    }
    
    public void setProfile(String login, PlayerProfileData playerProfileData){
        profiles.put(login, playerProfileData);
    }
    
    public PlayerProfileData getProfile(String login){
        return profiles.get(login);
    }
}
