/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.server.chat.commands;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyAddBuffsSystem;
import amara.applications.ingame.server.chat.ChatCommand;
import amara.applications.ingame.shared.games.*;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ChatCommand_Urf extends ChatCommand{

    @Override
    public void execute(String optionString, EntityWorld entityWorld, Game game, GamePlayer gamePlayer){
        try{
            float cooldownSpeed = Float.parseFloat(optionString);
            int buffEntity = entityWorld.createEntity();
            int buffAttributesEntity = entityWorld.createEntity();
            entityWorld.setComponent(buffAttributesEntity, new BonusPercentageCooldownSpeedComponent(cooldownSpeed));
            entityWorld.setComponent(buffEntity, new ContinuousAttributesComponent(buffAttributesEntity));
            entityWorld.setComponent(buffEntity, new KeepOnDeathComponent());
            int characterEntity = entityWorld.getComponent(gamePlayer.getEntity(), PlayerCharacterComponent.class).getEntity();
            ApplyAddBuffsSystem.addBuff(entityWorld, characterEntity, buffEntity);
        }catch(NumberFormatException ex){
        }
    }
}
