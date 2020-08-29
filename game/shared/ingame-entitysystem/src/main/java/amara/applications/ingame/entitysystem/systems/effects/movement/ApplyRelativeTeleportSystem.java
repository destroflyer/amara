package amara.applications.ingame.entitysystem.systems.effects.movement;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.movement.RelativeTeleportComponent;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.math.Vector2f;

public class ApplyRelativeTeleportSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RelativeTeleportComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            RelativeTeleportComponent relativeTeleportComponent = entityWorld.getComponent(effectImpactEntity, RelativeTeleportComponent.class);
            Vector2f oldPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
            Vector2f newPosition = oldPosition.subtract(relativeTeleportComponent.getSourceParentPosition()).addLocal(relativeTeleportComponent.getTargetParentPosition());
            entityWorld.setComponent(targetEntity, new PositionComponent(newPosition));
        }
    }
}
