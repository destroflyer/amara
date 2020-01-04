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
public class ApplyAddBindingSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddBindingComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            if (entityWorld.hasComponent(targetEntity, IsBindableComponent.class)) {
                AddBindingComponent addBindingComponent = entityWorld.getComponent(effectImpactEntity, AddBindingComponent.class);
                IsBindedComponent isBindedComponent = entityWorld.getComponent(targetEntity, IsBindedComponent.class);
                if ((isBindedComponent == null) || (addBindingComponent.getDuration() > isBindedComponent.getRemainingDuration())) {
                    entityWorld.setComponent(targetEntity, new IsBindedComponent(addBindingComponent.getDuration()));
                    UnitUtil.cancelMovement(entityWorld, targetEntity);
                }
            }
        }
    }
}
