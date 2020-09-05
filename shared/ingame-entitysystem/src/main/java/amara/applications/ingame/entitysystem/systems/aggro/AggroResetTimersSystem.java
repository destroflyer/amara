/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.aggro;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class AggroResetTimersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AggroTargetComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(AggroTargetComponent.class)){
            resetAggroTimer(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(AggroTargetComponent.class)){
            resetAggroTimer(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(AggroTargetComponent.class)){
            entityWorld.removeComponent(entity, RemainingAggroResetDurationComponent.class);
        }
    }
    
    public static void resetAggroTimer(EntityWorld entityWorld, int entity){
        AggroResetTimerComponent aggroResetTimerComponent = entityWorld.getComponent(entity, AggroResetTimerComponent.class);
        if(aggroResetTimerComponent != null){
            entityWorld.setComponent(entity, new RemainingAggroResetDurationComponent(aggroResetTimerComponent.getDuration()));
        }
    }
}
