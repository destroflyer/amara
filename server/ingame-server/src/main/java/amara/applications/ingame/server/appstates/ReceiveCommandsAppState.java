package amara.applications.ingame.server.appstates;

import amara.applications.ingame.network.messages.objects.commands.PlayerCommand;
import amara.applications.ingame.server.network.backends.ReceiveCommandsBackend;
import amara.applications.master.server.games.TeamGame;
import amara.core.Queue;
import amara.libraries.applications.headless.applications.HeadlessAppStateManager;
import amara.libraries.applications.headless.applications.HeadlessApplication;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.network.SubNetworkServer;

public class ReceiveCommandsAppState extends ServerBaseAppState {

    private static final float MAXIMUM_TIME_SINCE_LAST_COMMAND = (60 * 30);

    private Queue<PlayerCommand> playerCommandsQueue = new Queue<>();
    private float timeSinceLastCommand;

    @Override
    public void initialize(HeadlessAppStateManager stateManager, HeadlessApplication application) {
        super.initialize(stateManager, application);
        SubNetworkServer subNetworkServer = getAppState(SubNetworkServerAppState.class).getSubNetworkServer();
        subNetworkServer.addMessageBackend(new ReceiveCommandsBackend(this));
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        timeSinceLastCommand += lastTimePerFrame;
        if ((mainApplication.getGame() instanceof TeamGame) && (timeSinceLastCommand > MAXIMUM_TIME_SINCE_LAST_COMMAND)) {
            System.out.println("Maximum time since last command reached - Closing ingame server...");
            mainApplication.onGameOver();
        }
    }

    public void onCommandReceived(final PlayerCommand playerCommand){
        mainApplication.enqueueTask(() -> {
            timeSinceLastCommand = 0;
            playerCommandsQueue.add(playerCommand);
        });
    }

    public Queue<PlayerCommand> getPlayerCommandsQueue(){
        return playerCommandsQueue;
    }
}
