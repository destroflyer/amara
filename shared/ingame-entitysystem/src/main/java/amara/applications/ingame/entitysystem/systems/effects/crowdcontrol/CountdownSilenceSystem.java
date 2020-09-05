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
public class CountdownSilenceSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for (int entity : entityWorld.getEntitiesWithAny(IsSilencedComponent.class)) {
            IsSilencedComponent isSilencedComponent = entityWorld.getComponent(entity, IsSilencedComponent.class);
            float duration = (isSilencedComponent.getRemainingDuration() - deltaSeconds);
            if (duration > 0) {
                entityWorld.setComponent(entity, new IsSilencedComponent(duration));
            } else {
                entityWorld.removeComponent(entity, IsSilencedComponent.class);
            }
        }
    }
}
