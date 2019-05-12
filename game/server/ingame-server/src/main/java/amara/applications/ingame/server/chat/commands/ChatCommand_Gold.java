/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.chat.commands;

import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.server.chat.ChatCommand;
import amara.applications.ingame.shared.games.*;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ChatCommand_Gold extends ChatCommand{

    @Override
    public void execute(String optionString, EntityWorld entityWorld, Game game, GamePlayer gamePlayer){
        try{
            int gold = Integer.parseInt(optionString);
            if(gold >= 0){
                int characterEntity = entityWorld.getComponent(gamePlayer.getEntity(), PlayerCharacterComponent.class).getEntity();
                entityWorld.setComponent(characterEntity, new GoldComponent(gold));
            }
            else{
                setResponseMessage("No negative gold values allowed");
            }
        }catch(NumberFormatException ex){
        }
    }
}
