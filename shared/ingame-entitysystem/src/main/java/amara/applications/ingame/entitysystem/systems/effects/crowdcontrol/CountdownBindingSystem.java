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
public class CountdownBindingSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for (int entity : entityWorld.getEntitiesWithAny(IsBindedComponent.class)) {
            IsBindedComponent isBindedComponent = entityWorld.getComponent(entity, IsBindedComponent.class);
            float duration = (isBindedComponent.getRemainingDuration() - deltaSeconds);
            if (duration > 0) {
                entityWorld.setComponent(entity, new IsBindedComponent(duration));
            } else {
                entityWorld.removeComponent(entity, IsBindedComponent.class);
            }
        }
    }
}
