/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.spells.triggers.*;
import amara.game.entitysystem.systems.effects.triggers.EffectTriggerUtil;

/**
 *
 * @author Carl
 */
public class ApplyTriggerSpellEffectsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, TriggerSpellEffectsComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            int spellEntity = entityWrapper.getComponent(TriggerSpellEffectsComponent.class).getSpellEntity();
            for(int spellEffectEntity : entityWorld.getEntitiesWithAll(CastedSpellComponent.class, CastedEffectTriggersComponent.class)){
                if(entityWorld.getComponent(spellEffectEntity, CastedSpellComponent.class).getSpellEntity() == spellEntity){
                    int[] effectTriggerEntities = entityWorld.getComponent(spellEffectEntity, CastedEffectTriggersComponent.class).getEffectTriggerEntities();
                    EffectTriggerUtil.triggerEffects(entityWorld, effectTriggerEntities, targetEntity);
                }
            }
        }
    }
}
