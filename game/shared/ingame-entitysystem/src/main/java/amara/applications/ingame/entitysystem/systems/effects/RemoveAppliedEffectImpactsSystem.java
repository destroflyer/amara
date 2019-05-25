/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class RemoveAppliedEffectImpactsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class)){
            entityWorld.removeEntity(entity);
        }
    }
}
