/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.triggers;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.movements.*;
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
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, TargetReachedTriggerComponent.class))
        {
            int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            MovementComponent movementComponent = entityWorld.getComponent(sourceEntity, MovementComponent.class);
            if(movementComponent != null){
                if(entityWorld.hasComponent(movementComponent.getMovementEntity(), MovementTargetReachedComponent.class)){
                    int targetEntity = -1;
                    MovementTargetComponent movementTargetComponent = entityWorld.getComponent(movementComponent.getMovementEntity(), MovementTargetComponent.class);
                    if(movementTargetComponent != null){
                        targetEntity = movementTargetComponent.getTargetEntity();
                    }
                    EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, targetEntity);
                }
            }
        }
    }
}
