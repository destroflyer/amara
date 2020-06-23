/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.movements.MovementAnimationComponent;
import amara.applications.ingame.entitysystem.components.movements.MovementIsCancelableComponent;
import amara.applications.ingame.entitysystem.components.movements.MovementTurnInDirectionComponent;
import amara.applications.ingame.entitysystem.components.movements.WalkMovementComponent;
import amara.applications.ingame.entitysystem.components.units.animations.WalkAnimationComponent;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class WalkUtil {

    public static int createWalkMovementEntity(EntityWorld entityWorld, int unitEntity) {
        int movementEntity = entityWorld.createEntity();
        entityWorld.setComponent(movementEntity, new WalkMovementComponent());
        entityWorld.setComponent(movementEntity, new MovementIsCancelableComponent());
        entityWorld.setComponent(movementEntity, new MovementTurnInDirectionComponent());
        WalkAnimationComponent walkAnimationComponent = entityWorld.getComponent(unitEntity, WalkAnimationComponent.class);
        if (walkAnimationComponent != null) {
            entityWorld.setComponent(movementEntity, new MovementAnimationComponent(walkAnimationComponent.getAnimationEntity()));
        }
        return movementEntity;
    }
}
