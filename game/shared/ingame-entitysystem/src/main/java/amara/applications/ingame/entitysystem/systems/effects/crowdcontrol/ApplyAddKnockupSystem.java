/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.crowdcontrol;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.systems.units.UnitUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddKnockupSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddKnockupComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            if (entityWorld.hasComponent(targetEntity, IsKnockupableComponent.class)) {
                AddKnockupComponent addKnockupComponent = entityWorld.getComponent(effectImpactEntity, AddKnockupComponent.class);
                IsKnockupedComponent isKnockupedComponent = entityWorld.getComponent(targetEntity, IsKnockupedComponent.class);
                float duration = entityWorld.getComponent(addKnockupComponent.getKnockupEntity(), KnockupDurationComponent.class).getDuration();
                if ((isKnockupedComponent == null) || (duration > isKnockupedComponent.getRemainingDuration())) {
                    entityWorld.setComponent(targetEntity, new IsKnockupedComponent(addKnockupComponent.getKnockupEntity(), duration));
                    UnitUtil.cancelAction(entityWorld, targetEntity);
                }
            }
        }
    }
}
