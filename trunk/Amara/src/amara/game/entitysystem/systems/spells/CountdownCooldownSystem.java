/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.spells.RemainingCooldownComponent;

/**
 *
 * @author Carl
 */
public class CountdownCooldownSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(RemainingCooldownComponent.class)))
        {
            RemainingCooldownComponent remainingCooldownComponent = entityWrapper.getComponent(RemainingCooldownComponent.class);
            float duration = (remainingCooldownComponent.getDuration() - deltaSeconds);
            if(duration >= 0){
                entityWrapper.setComponent(new RemainingCooldownComponent(duration));
            }
            else{
                entityWrapper.removeComponent(RemainingCooldownComponent.class);
            }
        }
    }
}
