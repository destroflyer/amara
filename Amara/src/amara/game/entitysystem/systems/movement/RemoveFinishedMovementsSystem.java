/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.movement;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class RemoveFinishedMovementsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(MovementComponent.class)){
            int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
            MovementTargetComponent movementTargetComponent = entityWorld.getComponent(movementEntity, MovementTargetComponent.class);
            if(movementTargetComponent != null){
                Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                Vector2f targetPosition = entityWorld.getComponent(movementTargetComponent.getTargetEntity(), PositionComponent.class).getPosition();
                if(position.equals(targetPosition)){
                    entityWorld.removeComponent(entity, MovementComponent.class);
                }
            }
        }
    }
}
