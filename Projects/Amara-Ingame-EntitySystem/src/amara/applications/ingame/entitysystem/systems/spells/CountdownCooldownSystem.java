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
        for(int entity : entityWorld.getEntitiesWithAll(RemainingCooldownComponent.class)){
            RemainingCooldownComponent remainingCooldownComponent = entityWorld.getComponent(entity, RemainingCooldownComponent.class);
            float duration = (remainingCooldownComponent.getDuration() - deltaSeconds);
            if(duration > 0){
                entityWorld.setComponent(entity, new RemainingCooldownComponent(duration));
            }
            else{
                entityWorld.removeComponent(entity, RemainingCooldownComponent.class);
            }
        }
    }
}
