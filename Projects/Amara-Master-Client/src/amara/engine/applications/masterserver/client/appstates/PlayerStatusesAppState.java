/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.appstates;

import java.util.HashMap;
import java.util.LinkedList;
import amara.applications.master.network.messages.Message_GetPlayerStatus;
import amara.applications.master.network.messages.objects.PlayerStatus;
import amara.engine.applications.masterserver.client.network.backends.*;
import amara.engine.network.NetworkClient;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;

/**
 *
 * @author Carl
 */
public class PlayerStatusesAppState extends ClientBaseAppState{

    private LinkedList<Integer> updatingStatuses = new LinkedList<Integer>();
    private HashMap<Integer, PlayerStatus> statuses = new HashMap<Integer, PlayerStatus>();
    
    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application){
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new ReceivePlayerStatusesBackend(this));
    }
    
    public PlayerStatus getPlayerStatus(int playerID){
        updatingStatuses.add(playerID);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.sendMessage(new Message_GetPlayerStatus(playerID));
        while(true){
            if(!updatingStatuses.contains(playerID)){
                return statuses.get(playerID);
            }
            try{
                Thread.sleep(100);
            }catch(Exception ex){
            }
        }
    }
    
    public void setStatus(int playerID, PlayerStatus playerStatus){
        statuses.put(playerID, playerStatus);
        updatingStatuses.remove((Integer) playerID);
    }
}
