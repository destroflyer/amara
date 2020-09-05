/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.visuals.animations.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class UpdateWalkMovementsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, WalkSpeedComponent.class);
        for(int entity : entityWorld.getEntitiesWithAny(MovementComponent.class)){
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
            }
        }
    }
}
