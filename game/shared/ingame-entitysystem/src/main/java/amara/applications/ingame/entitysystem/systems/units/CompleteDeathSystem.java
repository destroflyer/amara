/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.buffs.RemoveBuffsSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CompleteDeathSystem implements EntitySystem {
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsAliveComponent.class);
        for (int entity : observer.getRemoved().getEntitiesWithAny(IsAliveComponent.class)) {
            if (!entityWorld.hasComponent(entity, IsRespawnableComponent.class)) {
                UnitUtil.cancelAction(entityWorld, entity);
                RemoveBuffsSystem.removeAllBuffs(entityWorld, entity);
                entityWorld.removeEntity(entity);
            }
        }
    }
}
