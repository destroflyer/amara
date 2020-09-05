/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.crowdcontrol;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddStunSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddStunComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            if (entityWorld.hasComponent(targetEntity, IsStunnableComponent.class)) {
                AddStunComponent addStunComponent = entityWorld.getComponent(effectImpactEntity, AddStunComponent.class);
                IsStunnedComponent isStunnedComponent = entityWorld.getComponent(targetEntity, IsStunnedComponent.class);
                if ((isStunnedComponent == null) || (addStunComponent.getDuration() > isStunnedComponent.getRemainingDuration())) {
                    entityWorld.setComponent(targetEntity, new IsStunnedComponent(addStunComponent.getDuration()));
                    UnitUtil.cancelAction(entityWorld, targetEntity);
                }
            }
        }
    }
}
