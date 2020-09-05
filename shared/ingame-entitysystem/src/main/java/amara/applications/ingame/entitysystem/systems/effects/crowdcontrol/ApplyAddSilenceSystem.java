/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.crowdcontrol;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddSilenceSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddSilenceComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            if (entityWorld.hasComponent(targetEntity, IsSilencableComponent.class)) {
                AddSilenceComponent addSilenceComponent = entityWorld.getComponent(effectImpactEntity, AddSilenceComponent.class);
                IsSilencedComponent isSilencedComponent = entityWorld.getComponent(targetEntity, IsSilencedComponent.class);
                if ((isSilencedComponent == null) || (addSilenceComponent.getDuration() > isSilencedComponent.getRemainingDuration())) {
                    entityWorld.setComponent(targetEntity, new IsSilencedComponent(addSilenceComponent.getDuration()));
                }
            }
        }
    }
}
