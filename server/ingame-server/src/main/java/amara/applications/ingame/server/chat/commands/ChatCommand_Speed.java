/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.chat.commands;

import amara.applications.ingame.entitysystem.components.game.GameSpeedComponent;
import amara.applications.ingame.server.chat.ChatCommand;
import amara.applications.ingame.shared.maps.Map;
import amara.applications.master.server.games.Game;
import amara.applications.master.server.games.GamePlayer;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ChatCommand_Speed extends ChatCommand {

    @Override
    public void execute(String optionString, EntityWorld entityWorld, Game game, GamePlayer gamePlayer) {
        try {
            float speed = Float.parseFloat(optionString);
            entityWorld.setComponent(Map.GAME_ENTITY, new GameSpeedComponent(speed));
        } catch (NumberFormatException ex) {
        }
    }
}
