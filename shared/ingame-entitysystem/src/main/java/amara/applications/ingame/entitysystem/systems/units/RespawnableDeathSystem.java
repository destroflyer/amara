package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyRemoveBuffsSystem;
import amara.libraries.entitysystem.*;

public class RespawnableDeathSystem implements EntitySystem {

    private Class[] componentClassesToRemove = new Class[] {
        HitboxActiveComponent.class,
        MaximumHealthComponent.class,
        HealthComponent.class,
        // General
        IsTargetableComponent.class,
        IsVulnerableComponent.class,
        // Crowdcontrol
        IsBindedComponent.class,
        IsSilencedComponent.class,
        IsStunnedComponent.class,
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

    private void onRespawnableDeath(EntityWorld entityWorld, int entity) {
        for (Class componentClass : componentClassesToRemove) {
            entityWorld.removeComponent(entity, componentClass);
        }
        UnitUtil.cancelAction(entityWorld, entity);
        BuffsComponent buffsComponent = entityWorld.getComponent(entity, BuffsComponent.class);
        if (buffsComponent != null) {
            for (int buffStatusEntity : buffsComponent.getBuffStatusEntities()) {
                int buffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();
                if (!entityWorld.hasComponent(buffEntity, KeepOnDeathComponent.class)) {
                    ApplyRemoveBuffsSystem.removeBuff(entityWorld, entity, buffEntity);
                }
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
