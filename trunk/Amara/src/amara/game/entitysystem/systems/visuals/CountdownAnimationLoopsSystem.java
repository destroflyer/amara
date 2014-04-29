/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.visuals;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.visuals.*;
import amara.game.entitysystem.components.visuals.animations.*;

/**
 *
 * @author Carl
 */
public class CountdownAnimationLoopsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AnimationComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(AnimationComponent.class)){
            int animationEntity = observer.getNew().getComponent(entity, AnimationComponent.class).getAnimationEntity();
            entityWorld.removeComponent(animationEntity, PassedLoopTimeComponent.class);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(AnimationComponent.class)){
            int animationEntity = observer.getChanged().getComponent(entity, AnimationComponent.class).getAnimationEntity();
            entityWorld.removeComponent(animationEntity, PassedLoopTimeComponent.class);
        }
        observer.reset();
        for(int entity : entityWorld.getEntitiesWithAll(AnimationComponent.class))
        {
            EntityWrapper animation = entityWorld.getWrapped(entityWorld.getComponent(entity, AnimationComponent.class).getAnimationEntity());
            float passedLoopTime = deltaSeconds;
            PassedLoopTimeComponent passedLoopTimeComponent = animation.getComponent(PassedLoopTimeComponent.class);
            if(passedLoopTimeComponent != null){
                passedLoopTime += passedLoopTimeComponent.getPassedTime();
            }
            if(passedLoopTime >= animation.getComponent(LoopDurationComponent.class).getDuration()){
                animation.removeComponent(PassedLoopTimeComponent.class);
                RemainingLoopsComponent remainingLoopsComponent = animation.getComponent(RemainingLoopsComponent.class);
                if(remainingLoopsComponent != null){
                    int remainingLoops = (remainingLoopsComponent.getLoopsCount() - 1);
                    if(remainingLoops > 0){
                        animation.setComponent(new RemainingLoopsComponent(remainingLoops));
                    }
                    else{
                        entityWorld.removeComponent(entity, AnimationComponent.class);
                    }
                }
            }
            else{
                animation.setComponent(new PassedLoopTimeComponent(passedLoopTime));
            }
        }
    }
}
