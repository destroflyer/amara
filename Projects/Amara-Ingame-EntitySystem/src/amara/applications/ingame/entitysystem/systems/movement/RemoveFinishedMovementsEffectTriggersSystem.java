/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.movement;

import java.util.HashMap;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class RemoveFinishedMovementsEffectTriggersSystem implements EntitySystem{
    
    private HashMap<Integer, Integer> movementEntities = new HashMap<Integer, Integer>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, MovementComponent.class, MovementTargetComponent.class, TriggerSourceComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(MovementComponent.class)){
            addNewMovement(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(MovementComponent.class)){
            removeOldMovement(entityWorld, entity);
            removeMovementEffectTriggers(entityWorld, observer, entity);
            addNewMovement(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(MovementComponent.class)){
            removeOldMovement(entityWorld, entity);
            removeMovementEffectTriggers(entityWorld, observer, entity);
            movementEntities.remove(entity);
        }
    }
    
    private void addNewMovement(EntityWorld entityWorld, int entity){
        int movementEntity = entityWorld.getComponent(entity, MovementComponent.class).getMovementEntity();
        movementEntities.put(entity, movementEntity);
    }
    
    private void removeOldMovement(EntityWorld entityWorld, int entity){
        int movementEntity = movementEntities.get(entity);
        entityWorld.removeEntity(movementEntity);
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
