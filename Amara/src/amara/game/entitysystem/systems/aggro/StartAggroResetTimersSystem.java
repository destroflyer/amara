/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.aggro;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class StartAggroResetTimersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AggroTargetComponent.class);
        for(Integer entity : observer.getNew().getEntitiesWithAll(AggroTargetComponent.class)){
            resetAggroTimer(entityWorld, entity);
        }
        for(Integer entity : observer.getChanged().getEntitiesWithAll(AggroTargetComponent.class)){
            resetAggroTimer(entityWorld, entity);
        }
        for(Integer entity : observer.getRemoved().getEntitiesWithAll(AggroTargetComponent.class)){
            entityWorld.removeComponent(entity, RemainingAggroResetDurationComponent.class);
        }
    }
    
    public static void resetAggroTimer(EntityWorld entityWorld, Integer entity){
        AggroResetTimerComponent aggroResetTimerComponent = entityWorld.getComponent(entity, AggroResetTimerComponent.class);
        if(aggroResetTimerComponent != null){
            entityWorld.setComponent(entity, new RemainingAggroResetDurationComponent(aggroResetTimerComponent.getDuration()));
        }
    }
}
