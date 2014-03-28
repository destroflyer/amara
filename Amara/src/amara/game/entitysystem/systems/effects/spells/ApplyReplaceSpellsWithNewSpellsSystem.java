/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.spells.ReplaceSpellWithNewSpellComponent;
import amara.game.entitysystem.components.units.SpellsComponent;

/**
 *
 * @author Carl
 */
public class ApplyReplaceSpellsWithNewSpellsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ReplaceSpellWithNewSpellComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            ReplaceSpellWithNewSpellComponent replaceSpellWithNewSpellComponent = entityWrapper.getComponent(ReplaceSpellWithNewSpellComponent.class);
            EntityWrapper newSpell = EntityTemplate.createFromTemplate(entityWorld, replaceSpellWithNewSpellComponent.getNewSpellTemplate());
            int[] spellsEntities = entityWorld.getComponent(targetEntity, SpellsComponent.class).getSpellsEntities();
            spellsEntities[replaceSpellWithNewSpellComponent.getSpellIndex()] = newSpell.getId();
            entityWorld.setComponent(targetEntity, new SpellsComponent(spellsEntities));
        }
    }
}
