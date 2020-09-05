/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.appstates;

import amara.applications.ingame.client.IngameClientApplication;
import amara.libraries.applications.display.appstates.BaseDisplayAppState;
import amara.libraries.applications.headless.appstates.NetworkClientHeadlessAppState;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.NetworkClient;
import com.jme3.network.Message;

import java.util.LinkedList;

/**
 *
 * @author Carl
 */
public class IngameNetworkAppState extends BaseDisplayAppState<IngameClientApplication>{

    private LinkedList<MessageBackend> messageBackends = new LinkedList<>();

    public void addMessageBackend(MessageBackend messageBackend){
        getNetworkClient().addMessageBackend(messageBackend);
        messageBackends.add(messageBackend);
    }

    public void sendMessage(Message message){
        getNetworkClient().sendMessage(message);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        NetworkClient networkClient = getNetworkClient();
        for (MessageBackend messageBackend : messageBackends) {
            networkClient.removeMessageBackend(messageBackend);
        }
    }

    private NetworkClient getNetworkClient() {
        return mainApplication.getMasterserverClient().getState(NetworkClientHeadlessAppState.class).getNetworkClient();
    }
}
