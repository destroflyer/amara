package amara.applications.ingame.entitysystem.systems.effects.general;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.general.AddCustomCleanupComponent;
import amara.applications.ingame.entitysystem.components.general.CustomCleanupComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyAddCustomCleanupSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddCustomCleanupComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            entityWorld.setComponent(targetEntity, new CustomCleanupComponent());
        }
    }
}
