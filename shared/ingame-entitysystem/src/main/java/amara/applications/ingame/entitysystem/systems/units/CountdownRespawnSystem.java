/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CountdownRespawnSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAll(WaitingToRespawnComponent.class)) {
            WaitingToRespawnComponent respawnComponent = entityWorld.getComponent(entity, WaitingToRespawnComponent.class);
            float duration = (respawnComponent.getRemainingDuration() - deltaSeconds);
            if (duration < 0) {
                duration = 0;
            }
            entityWorld.setComponent(entity, new WaitingToRespawnComponent(duration));
        }
    }
}
