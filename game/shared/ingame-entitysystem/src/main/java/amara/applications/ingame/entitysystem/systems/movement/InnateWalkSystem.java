/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.movements.MovementDirectionComponent;
import amara.applications.ingame.entitysystem.components.units.InnateWalkDirectionComponent;
import amara.applications.ingame.entitysystem.components.units.MovementComponent;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class InnateWalkSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAny(InnateWalkDirectionComponent.class)) {
            if ((!entityWorld.hasComponent(entity, MovementComponent.class)) && MovementSystem.canMove(entityWorld, entity)) {
                if (UnitUtil.tryCancelAction(entityWorld, entity)) {
                    Vector2f direction = entityWorld.getComponent(entity, InnateWalkDirectionComponent.class).getDirection();
                    int movementEntity = WalkUtil.createWalkMovementEntity(entityWorld, entity);
                    entityWorld.setComponent(movementEntity, new MovementDirectionComponent(direction));
                    entityWorld.setComponent(entity, new MovementComponent(movementEntity));
                }
            }
        }
    }
}
