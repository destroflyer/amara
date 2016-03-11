/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.players;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.maps.*;
import amara.applications.ingame.entitysystem.components.maps.playerdeathrules.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.players.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.systems.buffs.RemoveBuffsSystem;
import amara.applications.ingame.entitysystem.systems.game.UpdateGameTimeSystem;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class PlayerDeathSystem implements EntitySystem{

    public PlayerDeathSystem(Map map){
        this.map = map;
    }
    private Map map;
    private Class[] componentClassesToReomve = new Class[]{
        HitboxActiveComponent.class,
        MaximumHealthComponent.class,
        HealthComponent.class,
        //General
        IsTargetableComponent.class,
        IsVulnerableComponent.class,
        //Crowdcontrol
        IsBindedComponent.class,
        IsBindedImmuneComponent.class,
        IsSilencedComponent.class,
        IsSilencedImmuneComponent.class,
        IsStunnedComponent.class,
        IsStunnedImmuneComponent.class
    };
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for(int playerEntity : entityWorld.getEntitiesWithAll(PlayerCharacterComponent.class)){
            int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
            if(observer.getRemoved().hasComponent(characterEntity, IsAliveComponent.class)){
                onCharacterDeath(entityWorld, characterEntity);
                onPlayerDeath(entityWorld, playerEntity);
            }
        }
    }
    
    private void onCharacterDeath(EntityWorld entityWorld, int characterEntity){
        for(Class componentClass : componentClassesToReomve){
            entityWorld.removeComponent(characterEntity, componentClass);
        }
        UnitUtil.cancelAction(entityWorld, characterEntity);
        for(int buffStatus : entityWorld.getEntitiesWithAll(ActiveBuffComponent.class)){
            ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatus, ActiveBuffComponent.class);
            if((activeBuffComponent.getTargetEntity() == characterEntity) && (!entityWorld.hasComponent(activeBuffComponent.getBuffEntity(), KeepOnDeathComponent.class))){
                RemoveBuffsSystem.removeBuff(entityWorld, buffStatus);
            }
        }
        DeathAnimationComponent deathAnimationComponent = entityWorld.getComponent(characterEntity, DeathAnimationComponent.class);
        if(deathAnimationComponent != null){
            entityWorld.setComponent(characterEntity, new AnimationComponent(deathAnimationComponent.getAnimationEntity()));
        }
        else{
            entityWorld.removeComponent(characterEntity, AnimationComponent.class);
        }
    }
    
    private void onPlayerDeath(EntityWorld entityWorld, int playerEntity){
        int rulesEntity = entityWorld.getComponent(map.getEntity(), PlayerDeathRulesComponent.class).getRulesEntity();
        if(entityWorld.hasComponent(rulesEntity, RespawnPlayersComponent.class)){
            RespawnTimerComponent respawnTimerComponent = entityWorld.getComponent(rulesEntity, RespawnTimerComponent.class);
            if(respawnTimerComponent != null){
                float remainingDuration = (respawnTimerComponent.getInitialDuration() + (respawnTimerComponent.getDeltaDurationPerTime() * UpdateGameTimeSystem.getGameTime(entityWorld)));
                entityWorld.setComponent(playerEntity, new WaitingToRespawnComponent(remainingDuration));
            }
            else{
                entityWorld.setComponent(playerEntity, new RespawnComponent());
            }
        }
    }
}
