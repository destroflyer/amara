/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.network.backends;

import java.util.HashMap;

import com.jme3.network.Message;
import amara.applications.ingame.network.messages.*;
import amara.applications.ingame.server.IngameServerApplication;
import amara.applications.ingame.server.appstates.ServerEntitySystemAppState;
import amara.applications.ingame.server.chat.ChatCommand;
import amara.applications.ingame.server.chat.commands.*;
import amara.applications.master.server.games.*;
import amara.libraries.applications.headless.appstates.SubNetworkServerAppState;
import amara.libraries.entitysystem.*;
import amara.libraries.network.*;

/**
 *
 * @author Carl
 */
public class ReceiveChatMessagesBackend implements MessageBackend {

    public ReceiveChatMessagesBackend(IngameServerApplication ingameServerApplication) {
        this.ingameServerApplication = ingameServerApplication;
        addChatCommand("speed", new ChatCommand_Speed());
        addChatCommand("cinematic", new ChatCommand_Cinematic());
        addChatCommand("gold", new ChatCommand_Gold());
        addChatCommand("deathtimer", new ChatCommand_Deathtimer());
        addChatCommand("urf", new ChatCommand_Urf());
        addChatCommand("chickens", new ChatCommand_Chickens());
        addChatCommand("nochickens", new ChatCommand_NoChickens());
        // addChatCommand("envies", new ChatCommand_Envies());
        // addChatCommand("noenvies", new ChatCommand_NoEnvies());
    }
    private IngameServerApplication ingameServerApplication;
    private HashMap<String, ChatCommand> chatCommands = new HashMap<>();

    private void addChatCommand(String name, ChatCommand command) {
        chatCommands.put(name, command);
    }

    @Override
    public void onMessageReceived(Message receivedMessage, final MessageResponse messageResponse) {
        if (receivedMessage instanceof Message_SendChatMessage) {
            final Message_SendChatMessage message = (Message_SendChatMessage) receivedMessage;
            Game game = ingameServerApplication.getGame();
            GamePlayer<GamePlayerInfo_Human> gamePlayer = game.getPlayerByClientId(messageResponse.getClientId());
            if (gamePlayer != null) {
                Message_ChatMessage chatMessage = new Message_ChatMessage(gamePlayer.getGamePlayerInfo().getPlayerId(), null, message.getText());
                messageResponse.addBroadcastMessage(chatMessage);
                ingameServerApplication.enqueueTask(() -> {
                    MessageResponse chatCommandResponse = new MessageResponse(messageResponse.getClientId());
                    if (message.getText().equals("such chat")) {
                        chatCommandResponse.addAnswerMessage(new Message_ChatMessage("very responsive, wow"));
                    } else {
                        for (String commandName : chatCommands.keySet()) {
                            boolean isStandaloneCommand = message.getText().equals("/" + commandName);
                            if (isStandaloneCommand || message.getText().startsWith("/" + commandName + " ")) {
                                String optionsString = (isStandaloneCommand?"":message.getText().substring(commandName.length() + 2));
                                ChatCommand chatCommand = chatCommands.get(commandName);
                                EntityWorld entityWorld = ingameServerApplication.getStateManager().getState(ServerEntitySystemAppState.class).getEntityWorld();
                                chatCommand.setResponseMessage(null);
                                chatCommand.execute(optionsString, entityWorld, game, gamePlayer);
                                String responseMessage = chatCommand.getResponseMessage();
                                if (responseMessage != null) {
                                    chatCommandResponse.addAnswerMessage(new Message_ChatMessage(responseMessage));
                                }
                                break;
                            }
                        }
                    }
                    SubNetworkServer subNetworkServer = ingameServerApplication.getStateManager().getState(SubNetworkServerAppState.class).getSubNetworkServer();
                    subNetworkServer.sendMessageResponse(chatCommandResponse);
                });
            }
        }
    }
}
