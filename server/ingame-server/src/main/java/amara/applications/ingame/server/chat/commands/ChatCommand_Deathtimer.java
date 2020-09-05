/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.chat.commands;

import amara.applications.ingame.entitysystem.components.maps.*;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.*;
import amara.applications.ingame.server.chat.ChatCommand;
import amara.applications.master.server.games.Game;
import amara.applications.master.server.games.GamePlayer;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ChatCommand_Deathtimer extends ChatCommand {

    @Override
    public void execute(String optionString, EntityWorld entityWorld, Game game, GamePlayer gamePlayer) {
        try {
            String[] parameters = optionString.split(" ");
            if (parameters.length == 2) {
                float initialDuration = Float.parseFloat(parameters[0]);
                float deltaDurationPerTime = Float.parseFloat(parameters[1]);
                if ((initialDuration >= 0) && (deltaDurationPerTime >= 0)) {
                    int playerDeathRulesEntity = entityWorld.getComponent(game.getMap().getEntity(), PlayerDeathRulesComponent.class).getRulesEntity();
                    entityWorld.setComponent(playerDeathRulesEntity, new RespawnTimerComponent(initialDuration, deltaDurationPerTime));
                } else {
                    setResponseMessage("No negative death timers allowed");
                }
            }
        } catch (NumberFormatException ex) {
        }
    }
}
