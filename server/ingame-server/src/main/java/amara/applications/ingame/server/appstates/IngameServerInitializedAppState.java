/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.appstates;

import amara.applications.master.network.messages.Message_GameCreated;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.network.SubNetworkServer;

/**
 *
 * @author Carl
 */
public class IngameServerInitializedAppState extends ServerBaseAppState {

    private int frameIndex;
    
    @Override
    public void update(float lastTimePerFrame) {
        if (frameIndex == 1) {
            SubNetworkServer subNetworkServer = getAppState(SubNetworkServerAppState.class).getSubNetworkServer();
            subNetworkServer.broadcastMessage(new Message_GameCreated());
        }
        frameIndex++;
    }
}
