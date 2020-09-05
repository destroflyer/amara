/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CleanupBuffsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, RepeatingEffectComponent.class);
        for (int entity : observer.getRemoved().getEntitiesWithAny(RepeatingEffectComponent.class)) {
            int effectEntity = observer.getRemoved().getComponent(entity, RepeatingEffectComponent.class).getEffectEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, effectEntity);
        }
    }
}
