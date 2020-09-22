package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.movements.MovementTargetReachedComponent;
import amara.applications.ingame.entitysystem.components.units.MovementComponent;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class FinishTargetedMovementsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAny(MovementComponent.class)) {
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            if (entityWorld.hasComponent(movementEntity, MovementTargetReachedComponent.class)) {
                UnitUtil.cancelMovement(entityWorld, entity);
            }
        }
    }
}
