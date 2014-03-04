/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.visuals;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.animations.*;
import amara.game.entitysystem.components.visuals.*;

/**
 *
 * @author Carl
 */
public class StopAnimationsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(StopPlayingAnimationComponent.class)))
        {
            IdleAnimationComponent idleAnimationComponent = entityWrapper.getComponent(IdleAnimationComponent.class);
            if(idleAnimationComponent != null){
                entityWrapper.setComponent(new AnimationComponent(idleAnimationComponent.getAnimationEntity()));
            }
            else{
                entityWrapper.removeComponent(AnimationComponent.class);
            }
            entityWrapper.removeComponent(StopPlayingAnimationComponent.class);
        }
    }
}
