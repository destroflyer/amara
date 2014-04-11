/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.units.*;

/**
 *
 * @author Carl
 */
public class ApplyReplaceSpellsWithExistingSpellsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ReplaceSpellWithExistingSpellComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            ReplaceSpellWithExistingSpellComponent replaceSpellWithExistingSpellComponent = entityWrapper.getComponent(ReplaceSpellWithExistingSpellComponent.class);
            int[] spellsEntities = entityWorld.getComponent(targetEntity, SpellsComponent.class).getSpellsEntities();
            spellsEntities[replaceSpellWithExistingSpellComponent.getSpellIndex()] = replaceSpellWithExistingSpellComponent.getSpellEntity();
            entityWorld.setComponent(targetEntity, new SpellsComponent(spellsEntities));
        }
    }
}
