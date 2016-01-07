/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.visuals;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.visuals.*;
import amara.game.entitysystem.components.visuals.*;

/**
 *
 * @author Carl
 */
public class ApplyStopAnimationsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, StopAnimationComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            entityWorld.removeComponent(targetID, AnimationComponent.class);
        }
    }
}
