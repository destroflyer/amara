/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.physics;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.physics.*;
import amara.game.entitysystem.components.physics.*;

/**
 *
 * @author Carl
 */
public class ApplyDeactivateHitboxSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, DeactivateHitboxComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            entityWorld.removeComponent(targetID, HitboxActiveComponent.class);
        }
    }
}
