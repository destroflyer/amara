package amara.applications.ingame.entitysystem.systems.effects.physics;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.physics.SwapPositionsComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ApplySwapPositionsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, SwapPositionsComponent.class)) {
            int targetEntity1 = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            int targetEntity2 = entityWorld.getComponent(effectImpactEntity, SwapPositionsComponent.class).getTargetEntity();
            PositionComponent positionComponent1 = entityWorld.getComponent(targetEntity1, PositionComponent.class);
            PositionComponent positionComponent2 = entityWorld.getComponent(targetEntity2, PositionComponent.class);
            // Targets might already be removed from the world
            if ((positionComponent1 != null) && (positionComponent2 != null)) {
                entityWorld.setComponent(targetEntity1, new PositionComponent(positionComponent2.getPosition().clone()));
                entityWorld.setComponent(targetEntity2, new PositionComponent(positionComponent1.getPosition().clone()));
            }
        }
    }
}
