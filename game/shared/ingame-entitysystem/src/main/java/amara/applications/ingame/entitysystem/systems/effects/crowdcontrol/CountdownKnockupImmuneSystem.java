/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.crowdcontrol;

import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CountdownKnockupImmuneSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(IsKnockupedImmuneComponent.class)))
        {
            IsKnockupedImmuneComponent isKnockupedImmuneComponent = entityWrapper.getComponent(IsKnockupedImmuneComponent.class);
            float duration = (isKnockupedImmuneComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new IsKnockupedImmuneComponent(duration));
            }
            else{
                entityWrapper.removeComponent(IsKnockupedImmuneComponent.class);
            }
        }
    }
}
