/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.appstates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import amara.applications.master.client.network.backends.ReceivePlayerProfilesDataBackend;
import amara.applications.master.network.messages.Message_GetPlayerProfileData;
import amara.applications.master.network.messages.objects.PlayerProfileData;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class PlayerProfilesAppState extends ClientBaseAppState{

    private LinkedList<Integer> updatingIDs = new LinkedList<>();
    private LinkedList<String> updatingLogins = new LinkedList<>();
    private ArrayList<Integer> notExistingIDs = new ArrayList<>();
    private ArrayList<String> notExistingLogins = new ArrayList<>();
    private HashMap<Integer, PlayerProfileData> profilesByIDs = new HashMap<>();
    private HashMap<String, PlayerProfileData> profilesByLogins = new HashMap<>();
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new ReceivePlayerProfilesDataBackend(this));
    }
    
    public PlayerProfileData getPlayerProfile(int playerID){
        return getPlayerProfile(playerID, null);
    }
    
    public PlayerProfileData getPlayerProfile(String login){
        return getPlayerProfile(0, login);
    }
    
    public PlayerProfileData getPlayerProfile(int playerID, String login){
        PlayerProfileData playerProfileData;
        if(playerID != 0){
            updatingIDs.add(playerID);
            playerProfileData = profilesByIDs.get(playerID);
        }
        else{
            updatingLogins.add(login);
            playerProfileData = profilesByLogins.get(login);
        }
        long cachedTimestamp = ((playerProfileData != null)?playerProfileData.getTimestamp():-1);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.sendMessage(new Message_GetPlayerProfileData(playerID, login, cachedTimestamp));
        while(true){
            boolean isUpdating;
            if(playerID != 0){
                isUpdating = updatingIDs.contains(playerID);
            }
            else{
                isUpdating = updatingLogins.contains(login);
            }
            if(!isUpdating){
                if(playerID != 0){
                    playerProfileData = profilesByIDs.get(playerID);
                }
                else{
                    playerProfileData = profilesByLogins.get(login);
                }
                break;
            }
            try{
                Thread.sleep(100);
            }catch(Exception ex){
            }
        }
        return playerProfileData;
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
    
    public void setProfile(PlayerProfileData playerProfileData){
        profilesByIDs.put(playerProfileData.getID(), playerProfileData);
        profilesByLogins.put(playerProfileData.getLogin(), playerProfileData);
    }
}