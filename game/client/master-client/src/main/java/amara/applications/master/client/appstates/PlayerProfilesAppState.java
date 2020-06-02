/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.client.appstates;

import java.util.HashMap;
import java.util.LinkedList;
import amara.applications.master.client.network.backends.ReceivePlayerProfilesDataBackend;
import amara.applications.master.network.messages.Message_GetPlayerProfileData_ById;
import amara.applications.master.network.messages.Message_GetPlayerProfileData_ByLogin;
import amara.applications.master.network.messages.objects.PlayerProfileData;
import amara.libraries.applications.headless.applications.*;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.NetworkClient;

/**
 *
 * @author Carl
 */
public class PlayerProfilesAppState extends ClientBaseAppState {

    private LinkedList<Integer> updatingIds = new LinkedList<>();
    private LinkedList<String> updatingLogins = new LinkedList<>();
    private HashMap<Integer, PlayerProfileData> profilesByIDs = new HashMap<>();
    private HashMap<String, PlayerProfileData> profilesByLogins = new HashMap<>();

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        networkClient.addMessageBackend(new ReceivePlayerProfilesDataBackend(this));
    }

    public PlayerProfileData getPlayerProfile(int playerId) {
        return getPlayerProfile(playerId, null);
    }

    public PlayerProfileData getPlayerProfile(String login) {
        return getPlayerProfile(null, login);
    }

    private PlayerProfileData getPlayerProfile(Integer playerId, String login) {
        NetworkClient networkClient = getAppState(NetworkClientHeadlessAppState.class).getNetworkClient();
        if (playerId != null) {
            updatingIds.add(playerId);
            networkClient.sendMessage(new Message_GetPlayerProfileData_ById(playerId));
        } else {
            updatingLogins.add(login);
            networkClient.sendMessage(new Message_GetPlayerProfileData_ByLogin(login));
        }
        while (true) {
            boolean isUpdating;
            if (playerId != null) {
                isUpdating = updatingIds.contains(playerId);
            } else {
                isUpdating = updatingLogins.contains(login);
            }
            if (!isUpdating) {
                if (playerId != null) {
                    return profilesByIDs.get(playerId);
                } else {
                    return profilesByLogins.get(login);
                }
            }
            try {
                Thread.sleep(100);
            } catch(Exception ex) {
            }
        }
    }

    public void saveProfile(PlayerProfileData playerProfileData) {
        profilesByIDs.put(playerProfileData.getId(), playerProfileData);
        profilesByLogins.put(playerProfileData.getLogin(), playerProfileData);
        updatingIds.remove((Integer) playerProfileData.getId());
        updatingLogins.remove(playerProfileData.getLogin());
    }

    public void onProfileNotExistent(String login) {
        updatingLogins.remove(login);
    }
}
