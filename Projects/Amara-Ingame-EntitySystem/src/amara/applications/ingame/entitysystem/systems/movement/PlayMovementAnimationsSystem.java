/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class PlayMovementAnimationsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, MovementComponent.class, MovementAnimationComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(MovementComponent.class)){
            updateAnimation(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(MovementComponent.class)){
            updateAnimation(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(MovementComponent.class)){
            int movementEntity = observer.getRemoved().getComponent(entity, MovementComponent.class).getMovementEntity();
            MovementAnimationComponent movementAnimationComponent = observer.getRemoved().getComponent(movementEntity, MovementAnimationComponent.class);
            AnimationComponent currentAnimationComponent = entityWorld.getComponent(entity, AnimationComponent.class);
            if((movementAnimationComponent != null) && (currentAnimationComponent != null) && (movementAnimationComponent.getAnimationEntity() == currentAnimationComponent.getAnimationEntity())){
                entityWorld.removeComponent(entity, AnimationComponent.class);
            }
        }
    }
    
    private void updateAnimation(EntityWorld entityWorld, int entity){
        int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
        MovementAnimationComponent movementAnimationComponent = entityWorld.getComponent(movementEntity, MovementAnimationComponent.class);
        if(movementAnimationComponent != null){
            entityWorld.setComponent(entity, new AnimationComponent(movementAnimationComponent.getAnimationEntity()));
        }
        else{
            entityWorld.removeComponent(entity, AnimationComponent.class);
        }
    }
}
