/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.masterserver.client.appstates;

import java.util.HashMap;
import java.util.LinkedList;
import amara.engine.applications.*;
import amara.engine.applications.masterserver.client.network.backends.ReceivePlayerStatusesBackend;
import amara.engine.appstates.NetworkClientHeadlessAppState;
import amara.engine.network.NetworkClient;
import amara.engine.applications.masterserver.server.protocol.PlayerStatus;

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
    
    public void onUpdateStarted(int playerID){
        updatingStatuses.add(playerID);
    }
    
    public boolean isUpdating(int playerID){
        return updatingStatuses.contains(playerID);
    }
    
    public void setStatus(int playerID, PlayerStatus playerStatus){
        updatingStatuses.remove((Integer) playerID);
        statuses.put(playerID, playerStatus);
    }

    public PlayerStatus getStatus(int playerID){
        return statuses.get(playerID);
    }
}
