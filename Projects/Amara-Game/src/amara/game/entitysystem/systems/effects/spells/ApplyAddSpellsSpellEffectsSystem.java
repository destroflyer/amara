/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.spells;

import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.spells.triggers.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddSpellsSpellEffectsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddSpellsSpellEffectsComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            SpellsComponent spellsComponent = entityWorld.getComponent(targetEntity, SpellsComponent.class);
            if(spellsComponent != null){
                AddSpellsSpellEffectsComponent addSpellsSpellEffectsComponent =  entityWrapper.getComponent(AddSpellsSpellEffectsComponent.class);
                int[] spellEffectEntities = addSpellsSpellEffectsComponent.getSpellEffectEntities();
                for(int spellEntity : spellsComponent.getSpellsEntities()){
                    for(int spellEffectEntity : spellEffectEntities){
                        int newSpellEffectEntity = entityWorld.createEntity();
                        entityWorld.setComponent(newSpellEffectEntity, new SpellEffectParentComponent(spellEffectEntity));
                        entityWorld.setComponent(newSpellEffectEntity, new CastedSpellComponent(spellEntity));
                        int[] castedEffectTriggers = entityWorld.getComponent(spellEffectEntity, CastedEffectTriggersComponent.class).getEffectTriggerEntities();
                        int[] newCastedEffectTriggers = new int[castedEffectTriggers.length];
                        for(int i=0;i<newCastedEffectTriggers.length;i++){
                            int newEffectTrigger = entityWorld.createEntity();
                            for(Object component : entityWorld.getComponents(castedEffectTriggers[i])){
                                entityWorld.setComponent(newEffectTrigger, component);
                            }
                            if(addSpellsSpellEffectsComponent.isSetSourcesToSpells()){
                                entityWorld.setComponent(newEffectTrigger, new TriggerSourceComponent(spellEntity));
                            }
                            newCastedEffectTriggers[i] = newEffectTrigger;
                        }
                        entityWorld.setComponent(newSpellEffectEntity, new CastedEffectTriggersComponent(newCastedEffectTriggers));
                    }
                }
            }
        }
    }
}
