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
