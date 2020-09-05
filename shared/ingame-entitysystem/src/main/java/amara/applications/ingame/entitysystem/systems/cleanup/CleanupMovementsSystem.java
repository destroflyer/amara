/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.movements.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CleanupMovementsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, MovementTargetComponent.class);
        for (int entity : observer.getRemoved().getEntitiesWithAny(MovementTargetComponent.class)) {
            int targetEntity = observer.getRemoved().getComponent(entity, MovementTargetComponent.class).getTargetEntity();
            if (entityWorld.hasComponent(targetEntity, TemporaryComponent.class)) {
                CleanupUtil.tryCleanupEntity(entityWorld, targetEntity);
            }
        }
    }
}
