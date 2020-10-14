package amara.applications.master.server.network.backends;

import amara.applications.master.server.games.Game;
import amara.applications.master.server.games.GamePlayer;
import amara.applications.master.server.games.GamePlayerInfo_Human;
import amara.applications.master.network.messages.Message_GameCreated;
import amara.applications.master.network.messages.Message_JoinMMOMap;
import amara.applications.master.server.games.RunningGames;
import amara.applications.master.server.players.ConnectedPlayers;
import amara.libraries.network.MessageBackend;
import amara.libraries.network.MessageResponse;
import com.jme3.network.Message;

public class JoinMMOMapsBackend implements MessageBackend {

    public JoinMMOMapsBackend(RunningGames runningGames, ConnectedPlayers connectedPlayers) {
        this.runningGames = runningGames;
        this.connectedPlayers = connectedPlayers;
    }
    private RunningGames runningGames;
    private ConnectedPlayers connectedPlayers;

    @Override
    public void onMessageReceived(Message receivedMessage, MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_JoinMMOMap) {
            Message_JoinMMOMap message = (Message_JoinMMOMap) receivedMessage;
            Game mmoGame = runningGames.getMMOGame(message.getMapName());
            if (mmoGame != null) {
                int clientId = messageResponse.getClientId();
                int playerId = connectedPlayers.getPlayer(clientId).getID();
                GamePlayer<GamePlayerInfo_Human> gamePlayer = new GamePlayer<>(new GamePlayerInfo_Human(playerId, clientId), message.getGameSelectionPlayerData());
                mmoGame.addPlayer(gamePlayer);
                mmoGame.getSubNetworkServer().add(clientId);
                messageResponse.addAnswerMessage(new Message_GameCreated());
            }
        }
    }
}
