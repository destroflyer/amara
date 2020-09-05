/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells;

import amara.applications.ingame.entitysystem.components.spells.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class LinkedCooldownsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, CooldownComponent.class, RemainingCooldownComponent.class);
        for(int targetEntity : entityWorld.getEntitiesWithAny(LinkedCooldownComponent.class)){
            int sourceEntity = entityWorld.getComponent(targetEntity, LinkedCooldownComponent.class).getSourceEntity();
            copyChangedComponent(entityWorld, observer, sourceEntity, targetEntity, CooldownComponent.class);
            copyChangedComponent(entityWorld, observer, sourceEntity, targetEntity, RemainingCooldownComponent.class);
        }
    }
    
    private static void copyChangedComponent(EntityWorld entityWorld, ComponentMapObserver observer, int sourceEntity, int targetEntity, Class componentClass){
        Object changedComponent = observer.getChanged().getComponent(sourceEntity, componentClass);
        if(changedComponent != null){
            entityWorld.setComponent(targetEntity, changedComponent);
        }
        else if(observer.getRemoved().hasComponent(sourceEntity, componentClass)){
            entityWorld.removeComponent(targetEntity, componentClass);
        }
        else{
            Object newComponent = observer.getNew().getComponent(sourceEntity, componentClass);
            if(newComponent != null){
                entityWorld.setComponent(targetEntity, newComponent);
            }
        }
    }
}
