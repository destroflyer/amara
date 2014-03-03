/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.units.effecttriggers.triggers.*;
import amara.game.entitysystem.components.units.movement.*;

/**
 *
 * @author Philipp
 */
public class TriggerTargetReachedEffectSystem implements EntitySystem{

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(TriggeredEffectComponent.class, TargetReachedTriggerComponent.class))
        {
            TriggeredEffectComponent triggeredEffectComponent = entityWorld.getComponent(entity, TriggeredEffectComponent.class);
            TargetedMovementComponent targetedMovementComponent = entityWorld.getComponent(triggeredEffectComponent.getSourceEntity(), TargetedMovementComponent.class);
            if(targetedMovementComponent != null){
                PositionComponent targetPositionComponent = entityWorld.getComponent(targetedMovementComponent.getTargetEntity(), PositionComponent.class);
                if(targetPositionComponent != null){
                    Vector2f position = entityWorld.getComponent(triggeredEffectComponent.getSourceEntity(), PositionComponent.class).getPosition();
                    Vector2f targetPosition = targetPositionComponent.getPosition();
                    if(position.equals(targetPosition)){
                        EffectTriggerUtil.triggerEffect(entityWorld, entity, targetedMovementComponent.getTargetEntity());
                        entityWorld.removeEntity(triggeredEffectComponent.getSourceEntity());
                    }
                }
            }
        }
    }
}
