/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.libraries.entitysystem.*;

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
