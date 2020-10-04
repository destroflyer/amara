package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.effects.PrepareEffectComponent;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.libraries.entitysystem.*;

public class CleanupEffectTriggersSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(
            this,
            TriggeredEffectComponent.class,
            CollisionTriggerComponent.class,
            TriggerConditionsComponent.class
        );
        for (int effectTriggerEntity : observer.getRemoved().getEntitiesWithAny(TriggeredEffectComponent.class)) {
            int effectEntity = observer.getRemoved().getComponent(effectTriggerEntity, TriggeredEffectComponent.class).getEffectEntity();
            // Only remove the effect entity, if it's not referenced in an ongoing effect cast
            // If it is, it will be cleanuped when the effect impact is calculated
            boolean canEffectBeCleanuped = entityWorld.getEntitiesWithAny(PrepareEffectComponent.class).stream()
                    .map(effectCastEntity -> entityWorld.getComponent(effectCastEntity, PrepareEffectComponent.class).getEffectEntity())
                    .noneMatch(castingEffectEntity -> castingEffectEntity == effectEntity);
            if (canEffectBeCleanuped) {
                CleanupUtil.tryCleanupEntity(entityWorld, effectEntity);
            }
        }
        for (int effectTriggerEntity : observer.getRemoved().getEntitiesWithAny(CollisionTriggerComponent.class)) {
            int targetRulesEntity = observer.getRemoved().getComponent(effectTriggerEntity, CollisionTriggerComponent.class).getTargetRulesEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, targetRulesEntity);
        }
        for (int effectTriggerEntity : observer.getRemoved().getEntitiesWithAny(TriggerConditionsComponent.class)) {
            int[] conditionEntities = observer.getRemoved().getComponent(effectTriggerEntity, TriggerConditionsComponent.class).getConditionEntities();
            CleanupUtil.tryCleanupEntities(entityWorld, conditionEntities);
        }
        for (int effectTriggerEntity : entityWorld.getEntitiesWithAny(TriggerSourceComponent.class)) {
            int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if (!entityWorld.hasEntity(sourceEntity)) {
                int effectEntity = entityWorld.getComponent(effectTriggerEntity, TriggeredEffectComponent.class).getEffectEntity();
                CleanupUtil.tryCleanupEntity(entityWorld, effectEntity);
                entityWorld.removeEntity(effectTriggerEntity);
            }
        }
    }
}
