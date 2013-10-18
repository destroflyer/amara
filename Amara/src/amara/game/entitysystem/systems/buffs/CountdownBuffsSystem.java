/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.buffs;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.status.*;

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
                int targetEntityID = entityWorld.getComponent(buffStatus, ActiveBuffComponent.class).getTargetEntityID();
                entityWorld.removeEntity(buffStatus);
                entityWorld.setComponent(targetEntityID, new RequestUpdateAttributesComponent());
            }
        }
    }
}
