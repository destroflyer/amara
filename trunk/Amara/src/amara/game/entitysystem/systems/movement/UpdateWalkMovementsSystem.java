/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.movement;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;

/**
 *
 * @author Carl
 */
public class UpdateWalkMovementsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, WalkSpeedComponent.class);
        for(int entity : entityWorld.getEntitiesWithAll(MovementComponent.class)){
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            if(entityWorld.hasComponent(movementEntity, WalkMovementComponent.class)){
                if(entityWorld.hasComponent(movementEntity, MovementSpeedComponent.class)){
                    setMovementWalkSpeed(entityWorld, entity, observer.getChanged().getComponent(entity, WalkSpeedComponent.class));
                }
                else{
                    setMovementWalkSpeed(entityWorld, entity, entityWorld.getComponent(entity, WalkSpeedComponent.class));
                }
            }
        }
        observer.reset();
    }
    
    private void setMovementWalkSpeed(EntityWorld entityWorld, int movingEntity, WalkSpeedComponent walkSpeedComponent){
        if(walkSpeedComponent != null){
            float walkSpeed = walkSpeedComponent.getValue();
            int movementEntity = entityWorld.getComponent(movingEntity, MovementComponent.class).getMovementEntity();
            entityWorld.setComponent(movementEntity, new MovementSpeedComponent(walkSpeed));
            MovementAnimationComponent movementAnimationComponent = entityWorld.getComponent(movementEntity, MovementAnimationComponent.class);
            if(movementAnimationComponent != null){
                float walkStepDistance = 3.75f;
                WalkStepDistanceComponent walkStepDistanceComponent = entityWorld.getComponent(movingEntity, WalkStepDistanceComponent.class);
                if(walkStepDistanceComponent != null){
                    walkStepDistance = walkStepDistanceComponent.getDistance();
                }
                entityWorld.setComponent(movementAnimationComponent.getAnimationEntity(), new LoopDurationComponent(walkStepDistance / walkSpeed));
                entityWorld.setComponent(movingEntity, new AnimationComponent(movementAnimationComponent.getAnimationEntity()));
            }
        }
    }
}