package amara.applications.ingame.entitysystem.systems.effects.physics;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.physics.AddIntersectionPushedComponent;
import amara.applications.ingame.entitysystem.components.physics.IntersectionPushedComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplyAddIntersectionPushedSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddIntersectionPushedComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            entityWorld.setComponent(targetEntity, new IntersectionPushedComponent());
        }
    }
}
