/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.appstates;

import java.util.HashMap;
import java.util.LinkedList;
import amara.applications.master.client.network.backends.ReceivePlayerStatusesBackend;
import amara.applications.master.network.messages.Message_GetPlayerStatus;
import amara.applications.master.network.messages.objects.PlayerStatus;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class PlayerStatiAppState extends ClientBaseAppState {

    private LinkedList<Integer> updatingStatuses = new LinkedList<>();
    private HashMap<Integer, PlayerStatus> statuses = new HashMap<>();

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new ReceivePlayerStatusesBackend(this));
    }

    public PlayerStatus getPlayerStatus(int playerId) {
        updatingStatuses.add(playerId);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.sendMessage(new Message_GetPlayerStatus(playerId));
        while (true) {
            if (!updatingStatuses.contains(playerId)) {
                return statuses.get(playerId);
            }
            try {
                Thread.sleep(100);
            } catch(Exception ex) {
            }
        }
    }

    public void setStatus(int playerId, PlayerStatus playerStatus) {
        statuses.put(playerId, playerStatus);
        updatingStatuses.remove((Integer) playerId);
    }
}
