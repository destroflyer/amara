/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.buffs;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.buffs.status.RemainingBuffDurationComponent;

/**
 *
 * @author Carl
 */
public class CountdownBuffsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int buffStatus : entityWorld.getCurrent().getEntitiesWithAll(RemainingBuffDurationComponent.class))
        {
            RemainingBuffDurationComponent remainingBuffDurationComponent = entityWorld.getCurrent().getComponent(buffStatus, RemainingBuffDurationComponent.class);
            float duration = (remainingBuffDurationComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWorld.getCurrent().setComponent(buffStatus, new RemainingBuffDurationComponent(duration));
            }
            else{
                entityWorld.removeEntity(buffStatus);
            }
        }
    }
}
