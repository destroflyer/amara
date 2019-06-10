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
public class CountdownStunImmuneSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAny(IsStunnedImmuneComponent.class)))
        {
            IsStunnedImmuneComponent isStunnedImmuneComponent = entityWrapper.getComponent(IsStunnedImmuneComponent.class);
            float duration = (isStunnedImmuneComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new IsStunnedImmuneComponent(duration));
            }
            else{
                entityWrapper.removeComponent(IsStunnedImmuneComponent.class);
            }
        }
    }
}
