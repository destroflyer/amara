/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.systems.buffs.RemoveBuffsSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class RespawnableDeathSystem implements EntitySystem {

    private Class[] componentClassesToReomve = new Class[]{
        HitboxActiveComponent.class,
        MaximumHealthComponent.class,
        HealthComponent.class,
        // General
        IsTargetableComponent.class,
        IsVulnerableComponent.class,
        // Crowdcontrol
        IsBindedComponent.class,
        IsBindedImmuneComponent.class,
        IsSilencedComponent.class,
        IsSilencedImmuneComponent.class,
        IsStunnedComponent.class,
        IsStunnedImmuneComponent.class
    };

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for (int entity : entityWorld.getEntitiesWithAny(IsRespawnableComponent.class)) {
            if (observer.getRemoved().hasComponent(entity, IsAliveComponent.class)){
                onRespawnableDeath(entityWorld, entity);
            }
        }
    }

    private void onRespawnableDeath(EntityWorld entityWorld, int entity){
        for (Class componentClass : componentClassesToReomve) {
            entityWorld.removeComponent(entity, componentClass);
        }
        UnitUtil.cancelAction(entityWorld, entity);
        for (int buffStatus : entityWorld.getEntitiesWithAny(ActiveBuffComponent.class)) {
            ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatus, ActiveBuffComponent.class);
            if ((activeBuffComponent.getTargetEntity() == entity) && (!entityWorld.hasComponent(activeBuffComponent.getBuffEntity(), KeepOnDeathComponent.class))) {
                RemoveBuffsSystem.removeBuff(entityWorld, buffStatus);
            }
        }
        DeathAnimationComponent deathAnimationComponent = entityWorld.getComponent(entity, DeathAnimationComponent.class);
        if (deathAnimationComponent != null) {
            entityWorld.setComponent(entity, new AnimationComponent(deathAnimationComponent.getAnimationEntity()));
        } else {
            entityWorld.removeComponent(entity, AnimationComponent.class);
        }
    }
}
