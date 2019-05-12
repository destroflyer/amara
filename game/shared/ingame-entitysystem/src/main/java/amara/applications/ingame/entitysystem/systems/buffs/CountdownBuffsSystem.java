/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.buffs;

import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CountdownBuffsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int buffStatus : entityWorld.getEntitiesWithAll(RemainingBuffDurationComponent.class))
        {
            RemainingBuffDurationComponent remainingBuffDurationComponent = entityWorld.getComponent(buffStatus, RemainingBuffDurationComponent.class);
            float duration = (remainingBuffDurationComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWorld.setComponent(buffStatus, new RemainingBuffDurationComponent(duration));
            }
            else{
                entityWorld.setComponent(buffStatus, new RemoveFromTargetComponent());
            }
        }
    }
}
