/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.general;

import amara.applications.ingame.entitysystem.components.effects.ApplyEffectImpactComponent;
import amara.applications.ingame.entitysystem.components.effects.FinishObjectiveComponent;
import amara.applications.ingame.entitysystem.systems.objectives.CheckOpenObjectivesSystem;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ApplyFinishObjectivesSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, FinishObjectiveComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            CheckOpenObjectivesSystem.finishObjective(entityWorld, targetEntity);
        }
    }
}
