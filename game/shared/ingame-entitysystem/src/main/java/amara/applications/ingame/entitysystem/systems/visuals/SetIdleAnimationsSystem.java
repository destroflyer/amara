/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.visuals;

import amara.applications.ingame.entitysystem.components.units.animations.*;
import amara.applications.ingame.entitysystem.components.visuals.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class SetIdleAnimationsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, AnimationComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAll(AnimationComponent.class)){
            IdleAnimationComponent idleAnimationComponent = entityWorld.getComponent(entity, IdleAnimationComponent.class);
            if(idleAnimationComponent != null){
                entityWorld.setComponent(entity, new AnimationComponent(idleAnimationComponent.getAnimationEntity()));
            }
        }
        observer.refresh();
    }
}
