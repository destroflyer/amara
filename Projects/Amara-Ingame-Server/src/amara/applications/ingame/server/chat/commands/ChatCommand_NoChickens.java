/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.chat.commands;

import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.server.chat.ChatCommand;
import amara.applications.ingame.shared.games.*;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ChatCommand_NoChickens extends ChatCommand{

    @Override
    public void execute(String optionString, EntityWorld entityWorld, Game game, GamePlayer gamePlayer){
        for(int entity : entityWorld.getEntitiesWithAll(NameComponent.class, ModelComponent.class)){
            String name = entityWorld.getComponent(entity, NameComponent.class).getName();
            if(name.equals(ChatCommand_Chickens.NAME)){
                entityWorld.removeEntity(entityWorld.getComponent(entity, AnimationComponent.class).getAnimationEntity());
                entityWorld.removeEntity(entity);
            }
        }
    }
}
