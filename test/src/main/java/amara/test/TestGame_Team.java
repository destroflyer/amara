package amara.test;

import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;

public class TestGame_Team extends TestGame {

    public static void main(String[] args) {
        String authToken = args[0];
        startServerAndLogin(authToken, networkClient -> {
            networkClient.sendMessage(new Message_CreateLobby());
            networkClient.sendMessage(new Message_SetLobbyData(new LobbyData("testmap", new TeamFormat(1, 0))));
            // networkClient.sendMessage(new Message_AddLobbyBot(new LobbyPlayer_Bot(BotType.EASY, "Bot", new GameSelectionPlayerData(11, null))));
            networkClient.sendMessage(new Message_StartLobbyQueue());
            networkClient.sendMessage(new Message_AcceptGameSelection(true));
            networkClient.sendMessage(new Message_SetGameSelectionPlayerData(new GameSelectionPlayerData(17, null)));
            networkClient.sendMessage(new Message_LockInGameSelection());
        });
    }
}
