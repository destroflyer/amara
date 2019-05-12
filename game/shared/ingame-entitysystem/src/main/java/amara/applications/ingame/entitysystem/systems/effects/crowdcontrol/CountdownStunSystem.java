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
public class CountdownStunSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(IsStunnedComponent.class)))
        {
            IsStunnedComponent isStunnedComponent = entityWrapper.getComponent(IsStunnedComponent.class);
            float duration = (isStunnedComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new IsStunnedComponent(duration));
            }
            else{
                entityWrapper.removeComponent(IsStunnedComponent.class);
            }
        }
    }
}
