/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.spells;

import amara.Util;
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
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            ReplaceSpellWithExistingSpellComponent replaceSpellWithExistingSpellComponent = entityWrapper.getComponent(ReplaceSpellWithExistingSpellComponent.class);
            SpellsComponent spellsComponent = entityWorld.getComponent(targetEntity, SpellsComponent.class);
            if(spellsComponent != null){
                int[] spellsEntities = spellsComponent.getSpellsEntities();
                int[] newSpellsEntities = Util.cloneArray(spellsEntities);
                newSpellsEntities[replaceSpellWithExistingSpellComponent.getSpellIndex()] = replaceSpellWithExistingSpellComponent.getSpellEntity();
                entityWorld.setComponent(targetEntity, new SpellsComponent(newSpellsEntities));
            }
        }
    }
}
