package amara.applications.ingame.entitysystem.systems.visuals;

import amara.applications.ingame.entitysystem.components.movements.MovementAnimationComponent;
import amara.applications.ingame.entitysystem.components.units.MovementComponent;
import amara.applications.ingame.entitysystem.components.units.animations.IdleAnimationComponent;
import amara.applications.ingame.entitysystem.components.visuals.AnimationComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class ResetAnimationsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, AnimationComponent.class);
        for (int entity : observer.getRemoved().getEntitiesWithAny(AnimationComponent.class)) {
            int resetAnimationEntity = getResetAnimation(entityWorld, entity);
            if (resetAnimationEntity != -1) {
                entityWorld.setComponent(entity, new AnimationComponent(resetAnimationEntity));
            }
        }
        observer.refresh();
    }

    private int getResetAnimation(EntityWorld entityWorld, int entity) {
        MovementComponent movementComponent = entityWorld.getComponent(entity, MovementComponent.class);
        if (movementComponent != null) {
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            MovementAnimationComponent movementAnimationComponent = entityWorld.getComponent(movementEntity, MovementAnimationComponent.class);
            if (movementAnimationComponent != null) {
                return movementAnimationComponent.getAnimationEntity();
            }
            IdleAnimationComponent idleAnimationComponent = entityWorld.getComponent(entity, IdleAnimationComponent.class);
            if (idleAnimationComponent != null) {
               return idleAnimationComponent.getAnimationEntity();
            }
        }
        return -1;
    }
}
