/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.spells;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.spells.*;
import amara.applications.ingame.entitysystem.components.spells.triggers.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyRemoveSpellEffectsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveSpellEffectsComponent.class)))
        {
            for(int spellEffectEntity : entityWrapper.getComponent(RemoveSpellEffectsComponent.class).getSpellEffectEntities()){
                entityWorld.removeComponent(spellEffectEntity, CastedSpellComponent.class);
                for(int childSpellEffectEntity : entityWorld.getEntitiesWithAny(SpellEffectParentComponent.class)){
                    int parentSpellEffectEntity = entityWorld.getComponent(childSpellEffectEntity, SpellEffectParentComponent.class).getSpellEffectEntity();
                    if(parentSpellEffectEntity == spellEffectEntity){
                        for(int castedEffectTriggerEntity : entityWorld.getComponent(spellEffectEntity, CastedEffectTriggersComponent.class).getEffectTriggerEntities()){
                            entityWorld.removeEntity(castedEffectTriggerEntity);
                        }
                        entityWorld.removeEntity(childSpellEffectEntity);
                    }
                }
            }
        }
    }
}
