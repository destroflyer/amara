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
        ComponentMapObserver observer = entityWorld.requestObserver(this, AnimationComponent.class);
        for(Integer entity : observer.getNew().getEntitiesWithAll(AnimationComponent.class)){
            int animationEntity = observer.getNew().getComponent(entity, AnimationComponent.class).getAnimationEntity();
            entityWorld.removeComponent(animationEntity, PassedLoopTimeComponent.class);
        }
        for(Integer entity : observer.getChanged().getEntitiesWithAll(AnimationComponent.class)){
            int animationEntity = observer.getChanged().getComponent(entity, AnimationComponent.class).getAnimationEntity();
            entityWorld.removeComponent(animationEntity, PassedLoopTimeComponent.class);
        }
        for(Integer entity : entityWorld.getEntitiesWithAll(AnimationComponent.class)){
            int animationEntity = entityWorld.getComponent(entity, AnimationComponent.class).getAnimationEntity();
            RemainingLoopsComponent remainingLoopsComponent = entityWorld.getComponent(animationEntity, RemainingLoopsComponent.class);
            if(remainingLoopsComponent != null){
                float passedLoopTime = deltaSeconds;
                PassedLoopTimeComponent passedLoopTimeComponent = entityWorld.getComponent(animationEntity, PassedLoopTimeComponent.class);
                if(passedLoopTimeComponent != null){
                    passedLoopTime += passedLoopTimeComponent.getPassedTime();
                }
                if(passedLoopTime >= entityWorld.getComponent(animationEntity, LoopDurationComponent.class).getDuration()){
                    entityWorld.removeComponent(animationEntity, PassedLoopTimeComponent.class);
                    int remainingLoops = (remainingLoopsComponent.getLoopsCount() - 1);
                    if(remainingLoops > 0){
                        entityWorld.setComponent(entity, new RemainingLoopsComponent(remainingLoops));
                    }
                    else{
                        entityWorld.removeComponent(entity, AnimationComponent.class);
                    }
                }
                else{
                    entityWorld.setComponent(animationEntity, new PassedLoopTimeComponent(passedLoopTime));
                }
            }
        }
    }
}
