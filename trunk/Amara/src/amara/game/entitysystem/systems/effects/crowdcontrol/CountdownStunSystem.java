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
public class CountdownStunSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(IsStunnedComponent.class)))
        {
            IsStunnedComponent isStunnedComponent = entityWrapper.getComponent(IsStunnedComponent.class);
            float duration = (isStunnedComponent.getRemainingDuration() - deltaSeconds);
            if(duration >= 0){
                entityWrapper.setComponent(new IsStunnedComponent(duration));
            }
            else{
                entityWrapper.removeComponent(IsStunnedComponent.class);
            }
        }
    }
}
