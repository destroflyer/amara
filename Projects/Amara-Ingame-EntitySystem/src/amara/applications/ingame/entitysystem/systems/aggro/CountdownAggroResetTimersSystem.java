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
public class CountdownAggroResetTimersSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(RemainingAggroResetDurationComponent.class)){
            RemainingAggroResetDurationComponent aggroResetTimerComponent = entityWorld.getComponent(entity, RemainingAggroResetDurationComponent.class);
            float duration = (aggroResetTimerComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWorld.setComponent(entity, new RemainingAggroResetDurationComponent(duration));
            }
            else{
                entityWorld.removeComponent(entity, AggroTargetComponent.class);
            }
        }
    }
}
