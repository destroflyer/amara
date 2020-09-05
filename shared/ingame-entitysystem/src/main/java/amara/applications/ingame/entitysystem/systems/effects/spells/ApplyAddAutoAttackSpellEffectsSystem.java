/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.spells;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.spells.triggers.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddAutoAttackSpellEffectsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for (int entity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddAutoAttackSpellEffectsComponent.class)) {
            int targetEntity = entityWorld.getComponent(entity, ApplyEffectImpactComponent.class).getTargetEntity();
            AutoAttackComponent autoAttackComponent = entityWorld.getComponent(targetEntity, AutoAttackComponent.class);
            if (autoAttackComponent != null) {
                for (int spellEffect : entityWorld.getComponent(entity, AddAutoAttackSpellEffectsComponent.class).getSpellEffectEntities()) {
                    entityWorld.setComponent(spellEffect, new CastedSpellComponent(autoAttackComponent.getAutoAttackEntity()));
                }
            }
        }
    }
}
