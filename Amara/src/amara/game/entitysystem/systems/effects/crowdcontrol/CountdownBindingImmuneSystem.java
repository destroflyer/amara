/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.crowdcontrol;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;

/**
 *
 * @author Carl
 */
public class CountdownBindingImmuneSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(IsBindedImmuneComponent.class)))
        {
            IsBindedImmuneComponent isBindedImmuneComponent = entityWrapper.getComponent(IsBindedImmuneComponent.class);
            float duration = (isBindedImmuneComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new IsBindedImmuneComponent(duration));
            }
            else{
                entityWrapper.removeComponent(IsBindedImmuneComponent.class);
            }
        }
    }
}
