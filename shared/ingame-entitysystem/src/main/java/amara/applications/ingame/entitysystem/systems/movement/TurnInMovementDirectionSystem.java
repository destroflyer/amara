/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.movements.MovementDirectionComponent;
import amara.applications.ingame.entitysystem.components.movements.MovementTurnInDirectionComponent;
import amara.applications.ingame.entitysystem.components.physics.DirectionComponent;
import amara.applications.ingame.entitysystem.components.units.MovementComponent;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class TurnInMovementDirectionSystem implements EntitySystem{

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for (int entity : entityWorld.getEntitiesWithAny(MovementComponent.class)) {
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            if (entityWorld.hasComponent(movementEntity, MovementTurnInDirectionComponent.class)) {
                MovementDirectionComponent movementDirectionComponent = entityWorld.getComponent(movementEntity, MovementDirectionComponent.class);
                if (movementDirectionComponent != null) {
                    entityWorld.setComponent(entity, new DirectionComponent(movementDirectionComponent.getDirection()));
                }
            }
        }
    }
}
