/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.visuals;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.visuals.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyPlayAnimationsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, PlayAnimationComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            entityWorld.setComponent(targetID, new AnimationComponent(entityWrapper.getComponent(PlayAnimationComponent.class).getAnimationEntity()));
        }
    }
}
