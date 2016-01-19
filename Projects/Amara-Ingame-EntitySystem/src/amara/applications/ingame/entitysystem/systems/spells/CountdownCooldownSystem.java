/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.spells;

import amara.applications.ingame.entitysystem.components.spells.RemainingCooldownComponent;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CountdownCooldownSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(RemainingCooldownComponent.class))){
            RemainingCooldownComponent remainingCooldownComponent = entityWrapper.getComponent(RemainingCooldownComponent.class);
            float duration = (remainingCooldownComponent.getDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new RemainingCooldownComponent(duration));
            }
            else{
                entityWrapper.removeComponent(RemainingCooldownComponent.class);
            }
        }
    }
}
