/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.master.server.appstates;

import amara.applications.ingame.network.messages.Message_GameCrashed;
import amara.applications.ingame.network.messages.Message_GameOver;
import amara.applications.ingame.server.IngameServerApplication;
import amara.applications.master.network.messages.objects.GameSelection;
import amara.applications.master.network.messages.objects.Lobby;
import amara.applications.master.server.games.*;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.applications.headless.appstates.NetworkServerAppState;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.network.NetworkServer;
import amara.libraries.network.SubNetworkServer;

/**
 *
 * @author Carl
 */
public class GamesAppState extends ServerBaseAppState {

    public GamesAppState() {
        runningGames = new RunningGames();
    }
    private RunningGames runningGames;

    public void startTeamGame(GameSelection gameSelection) {
        LobbiesAppState lobbiesAppState = getAppState(LobbiesAppState.class);
        for (Lobby lobby : gameSelection.getLobbies()) {
            lobbiesAppState.removeLobby(lobby);
        }
        ConnectedPlayers connectedPlayers = getAppState(PlayersAppState.class).getConnectedPlayers();
        startGame(new TeamGame(gameSelection, connectedPlayers::getClientID));
    }

    public void startGame(Game game) {
        IngameServerApplication ingameServerApplication = null;
        try {
            NetworkServer networkServer = getAppState(NetworkServerAppState.class).getNetworkServer();
            ingameServerApplication = new IngameServerApplication(mainApplication, networkServer.createSubServer(), game);
            game.setIngameServerApplication(ingameServerApplication);
            ingameServerApplication.start();
            System.out.println("Ingame server of map '" + game.getMap().getName() + "' started.");
            runningGames.addGame(game);
        } catch (Exception ex) {
            onGameCrashed(ingameServerApplication, ex);
        }
    }

    public void onGameCrashed(IngameServerApplication ingameServerApplication, Exception exception) {
        LogsAppState logsAppState = mainApplication.getStateManager().getState(LogsAppState.class);
        logsAppState.writeLog(LogsAppState.Type.Crash, "The game server crashed. (" + LogsAppState.printStackTrace(exception) + ")");
        if (ingameServerApplication != null) {
            closeGame(ingameServerApplication);
            SubNetworkServer subNetworkServer = ingameServerApplication.getStateManager().getState(SubNetworkServerAppState.class).getSubNetworkServer();
            subNetworkServer.broadcastMessage(new Message_GameCrashed(exception.getClass().getSimpleName()));
            safelyCloseSubNetworkServer(subNetworkServer);
        }
        System.out.println("Game crashed:");
        exception.printStackTrace();
    }

    public void onGameOver(IngameServerApplication ingameServerApplication) {
        closeGame(ingameServerApplication);
        SubNetworkServer subNetworkServer = ingameServerApplication.getStateManager().getState(SubNetworkServerAppState.class).getSubNetworkServer();
        subNetworkServer.broadcastMessage(new Message_GameOver());
        safelyCloseSubNetworkServer(subNetworkServer);
    }

    private void safelyCloseSubNetworkServer(SubNetworkServer subNetworkServer) {
        // Wait to make sure all messages (e.g. game over) are sent
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
        }
        subNetworkServer.close();
        System.out.println("Ingame server closed.");
    }

    private void closeGame(IngameServerApplication ingameServerApplication) {
        runningGames.removeGame(ingameServerApplication.getGame());
        ingameServerApplication.stop();
    }

    public RunningGames getRunningGames() {
        return runningGames;
    }
}
