/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.players;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.maps.*;
import amara.game.entitysystem.components.maps.playerdeathrules.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.players.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.systems.buffs.RemoveBuffsSystem;
import amara.game.entitysystem.systems.units.UnitUtil;
import amara.game.maps.Map;

/**
 *
 * @author Carl
 */
public class PlayerDeathSystem implements EntitySystem{

    public PlayerDeathSystem(Map map){
        this.map = map;
    }
    private Map map;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, IsAliveComponent.class);
        for(int playerEntity : entityWorld.getEntitiesWithAll(SelectedUnitComponent.class))
        {
            int selectedEntity = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class).getEntityID();
            if(observer.getRemoved().hasComponent(selectedEntity, IsAliveComponent.class)){
                onSelectedUnitDeath(entityWorld, selectedEntity);
                onPlayerDeath(entityWorld, playerEntity);
            }
        }
        observer.reset();
    }
    
    private void onSelectedUnitDeath(EntityWorld entityWorld, int selectedEntity){
        Class[] componentClassesToReomve = new Class[]{
            HitboxActiveComponent.class,
            MaximumHealthComponent.class,
            HealthComponent.class,
            //General
            IsTargetableComponent.class,
            IsVulnerableComponent.class,
            MovementComponent.class,
            AggroTargetComponent.class,
            IsCastingComponent.class,
            //Crowdcontrol
            IsBindedComponent.class,
            IsBindedImmuneComponent.class,
            IsSilencedComponent.class,
            IsSilencedImmuneComponent.class,
            IsStunnedComponent.class,
            IsStunnedImmuneComponent.class
        };
        for(Class componentClass : componentClassesToReomve){
            entityWorld.removeComponent(selectedEntity, componentClass);
        }
        RemoveBuffsSystem.removeAllBuffs(entityWorld, selectedEntity);
        UnitUtil.cancelAction(entityWorld, selectedEntity);
        DeathAnimationComponent deathAnimationComponent = entityWorld.getComponent(selectedEntity, DeathAnimationComponent.class);
        if(deathAnimationComponent != null){
            entityWorld.setComponent(selectedEntity, new AnimationComponent(deathAnimationComponent.getAnimationEntity()));
        }
        else{
            entityWorld.removeComponent(selectedEntity, AnimationComponent.class);
        }
    }
    
    private void onPlayerDeath(EntityWorld entityWorld, int playerEntity){
        int rulesEntity = entityWorld.getComponent(map.getEntity(), PlayerDeathRulesComponent.class).getRulesEntity();
        if(entityWorld.hasComponent(rulesEntity, RespawnPlayersComponent.class)){
            RespawnTimerComponent respawnTimerComponent = entityWorld.getComponent(rulesEntity, RespawnTimerComponent.class);
            if(respawnTimerComponent != null){
                float remainingDuration = respawnTimerComponent.getInitialDuration();
                entityWorld.setComponent(playerEntity, new WaitingToRespawnComponent(remainingDuration));
            }
            else{
                entityWorld.setComponent(playerEntity, new RespawnComponent());
            }
        }
    }
}
