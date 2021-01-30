package amara.applications.ingame.client.network.backends;

import com.jme3.app.state.AppStateManager;
import com.jme3.network.Message;
import amara.applications.ingame.client.appstates.*;
import amara.applications.ingame.network.messages.Message_GameInfo;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.ingame.shared.maps.MapFileHandler;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.ingame.appstates.*;
import amara.libraries.network.*;

public class ReceiveGameInfoBackend implements MessageBackend {

    public ReceiveGameInfoBackend(DisplayApplication mainApplication) {
        this.mainApplication = mainApplication;
    }
    private DisplayApplication mainApplication;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_GameInfo) {
            final Message_GameInfo message = (Message_GameInfo) receivedMessage;
            message.repairOnUnserialize();
            final AppStateManager stateManager = mainApplication.getStateManager();
            mainApplication.enqueue(() -> {
                mainApplication.getStateManager().getState(LoadingScreenAppState.class).setTitle("Loading map...");
                new Thread(() -> {
                    String mapName = message.getMapName();
                    System.out.println("Loading map \"" + mapName + "\".");
                    Map map = MapFileHandler.load(mapName);
                    // This has to be created before attaching the LocalEntitySystemAppState, since it initializes the PlayerTeamSystem in the constructor
                    PlayerAppState playerAppState = new PlayerAppState(message.getTeams());
                    stateManager.attach(new MapAppState(map));
                    stateManager.attach(new MapObstaclesAppState());
                    stateManager.attach(new LocalEntitySystemAppState());
                    stateManager.attach(new SynchronizeEntityWorldAppState());
                    stateManager.attach(playerAppState);
                    mainApplication.getStateManager().getState(LoadingScreenAppState.class).setTitle("Waiting for all players to connect...");
                    stateManager.attach(new ClientInitializedAppState());
                }).start();
            });
        }
    }
}
