package amara.applications.ingame.entitysystem.systems.effects;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.systems.cleanup.CleanupTemporaryTargetsUtil;
import amara.libraries.entitysystem.*;

public class RemoveAppliedEffectImpactsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAny(ApplyEffectImpactComponent.class)) {
            removeEffectImpact(entityWorld, effectImpactEntity);
        }
    }

    public static void removeEffectImpact(EntityWorld entityWorld, int effectImpactEntity) {
        int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
        entityWorld.removeEntity(effectImpactEntity);
        CleanupTemporaryTargetsUtil.tryRemoveTemporaryTargetEntity(entityWorld, targetEntity);
    }
}
