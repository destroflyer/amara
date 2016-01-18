/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.aggro;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckMaximumAggroRangeSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(AggroTargetComponent.class, MaximumAggroRangeComponent.class)){
            int targetEntity = entityWorld.getComponent(entity, AggroTargetComponent.class).getTargetEntity();
            Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
            Vector2f targetPosition = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
            float maximumAggroRange = entityWorld.getComponent(entity, MaximumAggroRangeComponent.class).getRange();
            if(targetPosition.distanceSquared(position) > (maximumAggroRange * maximumAggroRange)){
                entityWorld.removeComponent(entity, AggroTargetComponent.class);
            }
        }
    }
}
