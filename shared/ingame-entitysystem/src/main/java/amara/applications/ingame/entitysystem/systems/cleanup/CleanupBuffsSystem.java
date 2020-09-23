package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class CleanupBuffsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(
            this,
            ContinuousAttributesComponent.class,
            ContinuousAttributesPerStackComponent.class,
            RepeatingEffectComponent.class,
            OnBuffAddEffectTriggersComponent.class,
            OnBuffRemoveEffectTriggersComponent.class
        );
        for (int entity : observer.getRemoved().getEntitiesWithAny(ContinuousAttributesComponent.class)) {
            int bonusAttributesEntity = observer.getRemoved().getComponent(entity, ContinuousAttributesComponent.class).getBonusAttributesEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, bonusAttributesEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(ContinuousAttributesPerStackComponent.class)) {
            int bonusAttributesEntity = observer.getRemoved().getComponent(entity, ContinuousAttributesPerStackComponent.class).getBonusAttributesEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, bonusAttributesEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(RepeatingEffectComponent.class)) {
            int effectEntity = observer.getRemoved().getComponent(entity, RepeatingEffectComponent.class).getEffectEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, effectEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(OnBuffAddEffectTriggersComponent.class)) {
            int[] effectTriggerEntities = observer.getRemoved().getComponent(entity, OnBuffAddEffectTriggersComponent.class).getEffectTriggerEntities();
            CleanupUtil.tryCleanupEntities(entityWorld, effectTriggerEntities);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(OnBuffRemoveEffectTriggersComponent.class)) {
            int[] effectTriggerEntities = observer.getRemoved().getComponent(entity, OnBuffRemoveEffectTriggersComponent.class).getEffectTriggerEntities();
            CleanupUtil.tryCleanupEntities(entityWorld, effectTriggerEntities);
        }
    }
}
