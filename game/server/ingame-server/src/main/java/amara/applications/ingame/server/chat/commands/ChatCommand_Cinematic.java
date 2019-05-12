/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.chat.commands;

import amara.applications.ingame.entitysystem.components.game.CinematicComponent;
import amara.applications.ingame.server.chat.ChatCommand;
import amara.applications.ingame.shared.games.*;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ChatCommand_Cinematic extends ChatCommand{

    @Override
    public void execute(String optionString, EntityWorld entityWorld, Game game, GamePlayer gamePlayer){
        try{
            int cinematicIndex = Integer.parseInt(optionString);
            switch(cinematicIndex){
                case 0:
                    entityWorld.setComponent(Game.ENTITY, new CinematicComponent("amara.applications.ingame.maps.TestMap_TestCinematic"));
                    break;

                case 1:
                    entityWorld.setComponent(Game.ENTITY, new CinematicComponent("amara.applications.ingame.maps.Map_Destroforest_CinematicIntro"));
                    break;
            }
        }catch(NumberFormatException ex){
        }
    }
}
