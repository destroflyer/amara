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
public class CountdownStunSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for (int entity : entityWorld.getEntitiesWithAny(IsStunnedComponent.class)) {
            IsStunnedComponent isStunnedComponent = entityWorld.getComponent(entity, IsStunnedComponent.class);
            float duration = (isStunnedComponent.getRemainingDuration() - deltaSeconds);
            if (duration > 0) {
                entityWorld.setComponent(entity, new IsStunnedComponent(duration));
            } else {
                entityWorld.removeComponent(entity, IsStunnedComponent.class);
            }
        }
    }
}
