package amara.applications.ingame.entitysystem.systems.effects;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class RemoveAppliedEffectImpactsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAny(ApplyEffectImpactComponent.class)) {
            entityWorld.removeEntity(effectImpactEntity);
        }
    }
}
