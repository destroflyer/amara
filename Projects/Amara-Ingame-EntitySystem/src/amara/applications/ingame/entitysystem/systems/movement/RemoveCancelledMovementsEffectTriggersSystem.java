/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class RemoveCancelledMovementsEffectTriggersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, MovementComponent.class, TriggerSourceComponent.class);
        for(int entity : observer.getChanged().getEntitiesWithAll(MovementComponent.class)){
            removeMovementEffectTriggers(entityWorld, observer, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(MovementComponent.class)){
            removeMovementEffectTriggers(entityWorld, observer, entity);
        }
    }
    
    private void removeMovementEffectTriggers(EntityWorld entityWorld, ComponentMapObserver observer, int entity){
        for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, TargetReachedTriggerComponent.class, TriggerTemporaryComponent.class)){
            int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
            if((sourceEntity == entity) && (!observer.getNew().hasEntity(effectTriggerEntity))){
                entityWorld.removeEntity(effectTriggerEntity);
            }
        }
    }
}
