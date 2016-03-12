/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.spells;

import java.util.LinkedList;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.spells.triggers.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddSpellsSpellEffectsSystem implements EntitySystem{
    
    private LinkedList<Integer> tmpSpellEntities = new LinkedList<Integer>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddSpellsSpellEffectsComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            AddSpellsSpellEffectsComponent addSpellsSpellEffectsComponent =  entityWrapper.getComponent(AddSpellsSpellEffectsComponent.class);
            LearnableSpellsComponent learnableSpellsComponent = entityWorld.getComponent(targetEntity, LearnableSpellsComponent.class);
            if(learnableSpellsComponent != null){
                addSpellEffects(entityWorld, learnableSpellsComponent.getSpellsEntities(), addSpellsSpellEffectsComponent);
            }
            SpellsComponent spellsComponent = entityWorld.getComponent(targetEntity, SpellsComponent.class);
            if(spellsComponent != null){
                addSpellEffects(entityWorld, spellsComponent.getSpellsEntities(), addSpellsSpellEffectsComponent);
            }
            tmpSpellEntities.clear();
        }
    }
    
    private void addSpellEffects(EntityWorld entityWorld, int[] spellEntities, AddSpellsSpellEffectsComponent addSpellsSpellEffectsComponent){
        for(int spellEntity : spellEntities){
            if((spellEntity != -1) && (!tmpSpellEntities.contains(spellEntity))){
                for(int spellEffectEntity : addSpellsSpellEffectsComponent.getSpellEffectEntities()){
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
                    tmpSpellEntities.add(spellEntity);
                }
            }
        }
    }
}
