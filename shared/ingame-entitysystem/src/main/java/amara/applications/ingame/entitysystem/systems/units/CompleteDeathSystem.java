package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.TriggerSourceComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.RemoveTriggerComponent;
import amara.applications.ingame.entitysystem.systems.effects.buffs.ApplyRemoveBuffsSystem;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.libraries.entitysystem.*;

public class CompleteDeathSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for (int entity : observer.getRemoved().getEntitiesWithAny(IsAliveComponent.class)) {
            if (!entityWorld.hasComponent(entity, IsRespawnableComponent.class)) {
                killCompletely(entityWorld, entity);
            }
        }
    }

    public static void killCompletely(EntityWorld entityWorld, int entity) {
        // Checking if the entity even exists saves time and most importantly avoids multiple triggers of the same remove trigger
        if (entityWorld.hasEntity(entity)) {
            triggerRemoveEffects(entityWorld, entity);
            UnitUtil.cancelAction(entityWorld, entity);
            ApplyRemoveBuffsSystem.removeAllBuffs(entityWorld, entity);
            entityWorld.removeEntity(entity);
        }
    }

    private static void triggerRemoveEffects(EntityWorld entityWorld, int entity) {
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, RemoveTriggerComponent.class)){
            if (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class)) {
                int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                if (sourceEntity == entity) {
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, -1);
                }
            }
        }
    }
}
