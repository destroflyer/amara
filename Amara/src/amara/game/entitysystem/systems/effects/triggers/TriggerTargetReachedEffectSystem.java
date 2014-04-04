/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;

/**
 *
 * @author Philipp
 */
public class TriggerTargetReachedEffectSystem implements EntitySystem{

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggeredEffectComponent.class, TargetReachedTriggerComponent.class))
        {
            int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggeredEffectComponent.class).getSourceEntity();
            MovementComponent movementComponent = entityWorld.getComponent(sourceEntity, MovementComponent.class);
            if(movementComponent != null){
                int targetEntity = entityWorld.getComponent(movementComponent.getMovementEntity(), MovementTargetComponent.class).getTargetEntity();
                PositionComponent targetPositionComponent = entityWorld.getComponent(targetEntity, PositionComponent.class);
                if(targetPositionComponent != null){
                    Vector2f position = entityWorld.getComponent(sourceEntity, PositionComponent.class).getPosition();
                    Vector2f targetPosition = targetPositionComponent.getPosition();
                    float maximumDistance = entityWorld.getComponent(effectTriggerEntity, TargetReachedTriggerComponent.class).getMaximumDistance();
                    if(targetPosition.subtract(position).length() <= maximumDistance){
                        EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, targetEntity);
                        entityWorld.removeEntity(effectTriggerEntity);
                    }
                }
            }
        }
    }
}
