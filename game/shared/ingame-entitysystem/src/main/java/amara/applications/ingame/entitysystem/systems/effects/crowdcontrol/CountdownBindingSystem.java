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
public class CountdownBindingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAny(IsBindedComponent.class)))
        {
            IsBindedComponent isBindedComponent = entityWrapper.getComponent(IsBindedComponent.class);
            float duration = (isBindedComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new IsBindedComponent(duration));
            }
            else{
                entityWrapper.removeComponent(IsBindedComponent.class);
            }
        }
    }
}
