/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.specials.erika;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.specials.erika.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyTriggerErikaPassivesSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, TriggerErikaPassiveComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            EffectSourceSpellComponent effectSourceSpellComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceSpellComponent.class);
            if (effectSourceSpellComponent != null) {
                int spellEntity = effectSourceSpellComponent.getSpellEntity();
                ErikaPassiveEffectTriggersComponent erikaPassiveEffectTriggersComponent = entityWorld.getComponent(spellEntity, ErikaPassiveEffectTriggersComponent.class);
                if (erikaPassiveEffectTriggersComponent != null) {
                    EffectTriggerUtil.triggerEffects(entityWorld, erikaPassiveEffectTriggersComponent.getEffectTriggerEntities(), targetEntity);
                }
            }
        }
    }
}
