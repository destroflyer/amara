/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.network.backends;

import com.jme3.network.Message;
import amara.applications.ingame.client.appstates.LoadingScreenAppState;
import amara.applications.ingame.entitysystem.components.game.GameTimeComponent;
import amara.applications.ingame.network.messages.Message_GameStarted;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class GameStartedBackend implements MessageBackend {

    public GameStartedBackend(EntityWorld entityWorld, LoadingScreenAppState loadingScreenAppState) {
        this.entityWorld = entityWorld;
        this.loadingScreenAppState = loadingScreenAppState;
    }
    private EntityWorld entityWorld;
    private LoadingScreenAppState loadingScreenAppState;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_GameStarted) {
            entityWorld.setComponent(Map.GAME_ENTITY, new GameTimeComponent(0));
            loadingScreenAppState.onGameStarted();
        }
    }
}
