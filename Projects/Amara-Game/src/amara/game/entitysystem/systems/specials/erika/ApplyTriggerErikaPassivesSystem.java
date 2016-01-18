/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.specials.erika;

import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.specials.erika.*;
import amara.game.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyTriggerErikaPassivesSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, TriggerErikaPassiveComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            EffectCastSourceSpellComponent effectCastSourceSpellComponent = entityWrapper.getComponent(EffectCastSourceSpellComponent.class);
            if(effectCastSourceSpellComponent != null){
                int spellEntity = effectCastSourceSpellComponent.getSpellEntity();
                ErikaPassiveEffectTriggersComponent erikaPassiveEffectTriggersComponent = entityWorld.getComponent(spellEntity, ErikaPassiveEffectTriggersComponent.class);
                if(erikaPassiveEffectTriggersComponent != null){
                    EffectTriggerUtil.triggerEffects(entityWorld, erikaPassiveEffectTriggersComponent.getEffectTriggerEntities(), targetEntity);
                }
            }
        }
    }
}
