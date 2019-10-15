/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.aggro;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckAggroTargetAttackabilitySystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAny(AggroTargetComponent.class)) {
            int targetEntity = entityWorld.getComponent(entity, AggroTargetComponent.class).getTargetEntity();
            if (!AggroUtil.isAttackable(entityWorld, entity, targetEntity)) {
                entityWorld.removeComponent(entity, AggroTargetComponent.class);
            }
        }
    }
}
