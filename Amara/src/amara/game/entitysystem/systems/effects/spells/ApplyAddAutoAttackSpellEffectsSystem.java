/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.spells.triggers.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class ApplyAddAutoAttackSpellEffectsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddAutoAttackSpellEffectsComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            int autoAttackEntity = entityWorld.getComponent(targetEntity, AutoAttackComponent.class).getAutoAttackEntity();
            for(int spellEffect : entityWrapper.getComponent(AddAutoAttackSpellEffectsComponent.class).getSpellEffectEntities()){
                entityWorld.setComponent(spellEffect, new CastedSpellComponent(autoAttackEntity));
            }
        }
    }
}
