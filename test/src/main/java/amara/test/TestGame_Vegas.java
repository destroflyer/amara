package amara.test;

import amara.applications.master.network.messages.*;
import amara.applications.master.network.messages.objects.*;

public class TestGame_Vegas extends TestGame {

    public static void main(String[] args) {
        String authToken = args[0];
        startServerAndLogin(authToken, networkClient -> {
            networkClient.sendMessage(new Message_CreateLobby());
            networkClient.sendMessage(new Message_SetLobbyData(new LobbyData("vegas", new TeamFormat(8, 0, 0, 0, 0, 0, 0, 0))));
            networkClient.sendMessage(new Message_AddLobbyBot(new LobbyPlayer_Bot(BotType.EASY_VEGAS, "Bot", new GameSelectionPlayerData(16, null))));
            networkClient.sendMessage(new Message_AddLobbyBot(new LobbyPlayer_Bot(BotType.EASY_VEGAS, "Bot", new GameSelectionPlayerData(17, null))));
            networkClient.sendMessage(new Message_AddLobbyBot(new LobbyPlayer_Bot(BotType.EASY_VEGAS, "Bot", new GameSelectionPlayerData(18, null))));
            networkClient.sendMessage(new Message_AddLobbyBot(new LobbyPlayer_Bot(BotType.EASY_VEGAS, "Bot", new GameSelectionPlayerData(19, null))));
            networkClient.sendMessage(new Message_AddLobbyBot(new LobbyPlayer_Bot(BotType.EASY_VEGAS, "Bot", new GameSelectionPlayerData(20, null))));
            networkClient.sendMessage(new Message_AddLobbyBot(new LobbyPlayer_Bot(BotType.EASY_VEGAS, "Bot", new GameSelectionPlayerData(21, null))));
            networkClient.sendMessage(new Message_AddLobbyBot(new LobbyPlayer_Bot(BotType.EASY_VEGAS, "Bot", new GameSelectionPlayerData(22, null))));
            networkClient.sendMessage(new Message_StartLobbyQueue());
            networkClient.sendMessage(new Message_AcceptGameSelection(true));
            networkClient.sendMessage(new Message_SetGameSelectionPlayerData(new GameSelectionPlayerData(23, null)));
            networkClient.sendMessage(new Message_LockInGameSelection());
        });
    }
}
