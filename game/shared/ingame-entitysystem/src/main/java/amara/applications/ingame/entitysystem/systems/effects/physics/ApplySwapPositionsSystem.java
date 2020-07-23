package amara.applications.ingame.entitysystem.systems.effects.physics;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.physics.SwapPositionsComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.math.Vector2f;

public class ApplySwapPositionsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, SwapPositionsComponent.class)) {
            int targetEntity1 = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            int targetEntity2 = entityWorld.getComponent(effectImpactEntity, SwapPositionsComponent.class).getTargetEntity();
            Vector2f position1 = entityWorld.getComponent(targetEntity1, PositionComponent.class).getPosition();
            Vector2f position2 = entityWorld.getComponent(targetEntity2, PositionComponent.class).getPosition();
            entityWorld.setComponent(targetEntity1, new PositionComponent(position2.clone()));
            entityWorld.setComponent(targetEntity2, new PositionComponent(position1.clone()));
        }
    }
}
