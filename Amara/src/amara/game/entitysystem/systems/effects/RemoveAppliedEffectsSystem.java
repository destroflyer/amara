/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.ApplyEffectImpactComponent;

/**
 *
 * @author Carl
 */
public class RemoveAppliedEffectsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(Integer entity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class)){
            entityWorld.removeEntity(entity);
        }
    }
}
