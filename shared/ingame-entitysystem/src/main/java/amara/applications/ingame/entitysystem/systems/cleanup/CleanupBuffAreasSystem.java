/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.buffs.areas.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CleanupBuffAreasSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, AreaBuffComponent.class, AreaBuffTargetRulesComponent.class);
        for (int entity : observer.getRemoved().getEntitiesWithAny(AreaBuffComponent.class)) {
            int buffEntity = observer.getRemoved().getComponent(entity, AreaBuffComponent.class).getBuffEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, buffEntity);
        }
        for (int entity : observer.getRemoved().getEntitiesWithAny(AreaBuffTargetRulesComponent.class)) {
            int targetRulesEntity = observer.getRemoved().getComponent(entity, AreaBuffTargetRulesComponent.class).getTargetRulesEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, targetRulesEntity);
        }
    }
}
