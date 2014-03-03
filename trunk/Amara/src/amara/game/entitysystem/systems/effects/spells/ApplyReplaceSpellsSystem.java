/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.spells.ReplaceSpellComponent;
import amara.game.entitysystem.components.units.SpellsComponent;

/**
 *
 * @author Carl
 */
public class ApplyReplaceSpellsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ReplaceSpellComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            ReplaceSpellComponent replaceSpellComponent = entityWrapper.getComponent(ReplaceSpellComponent.class);
            EntityWrapper newSpell = EntityTemplate.createFromTemplate(entityWorld, replaceSpellComponent.getNewSpellTemplate());
            int[] spellsEntities = entityWorld.getComponent(targetEntity, SpellsComponent.class).getSpellsEntitiesIDs();
            spellsEntities[replaceSpellComponent.getSpellIndex()] = newSpell.getId();
            entityWorld.setComponent(targetEntity, new SpellsComponent(spellsEntities));
        }
    }
}
