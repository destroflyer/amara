/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.spells;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.spells.triggers.*;

/**
 *
 * @author Carl
 */
public class ApplyRemoveSpellEffectsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveSpellEffectsComponent.class)))
        {
            for(int spellEffect : entityWrapper.getComponent(RemoveSpellEffectsComponent.class).getSpellEffectEntities()){
                entityWorld.removeComponent(spellEffect, CastedSpellComponent.class);
            }
        }
    }
}
