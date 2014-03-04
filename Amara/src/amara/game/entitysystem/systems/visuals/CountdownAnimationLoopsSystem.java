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
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(AnimationComponent.class)))
        {
            EntityWrapper animation = entityWorld.getWrapped(entityWrapper.getComponent(AnimationComponent.class).getAnimationEntity());
            float passedLoopTime = deltaSeconds;
            PassedLoopTimeComponent passedLoopTimeComponent = animation.getComponent(PassedLoopTimeComponent.class);
            if(passedLoopTimeComponent != null){
                passedLoopTime += passedLoopTimeComponent.getPassedTime();
            }
            if(passedLoopTime >= animation.getComponent(LoopDurationComponent.class).getDuration()){
                animation.setComponent(new PassedLoopTimeComponent(0));
                RemainingLoopsComponent remainingLoopsComponent = animation.getComponent(RemainingLoopsComponent.class);
                if(remainingLoopsComponent != null){
                    int remainingLoops = (remainingLoopsComponent.getLoopsCount() - 1);
                    if(remainingLoops > 0){
                        animation.setComponent(new RemainingLoopsComponent(remainingLoops));
                    }
                    else{
                        entityWrapper.setComponent(new StopPlayingAnimationComponent());
                    }
                }
            }
            else{
                animation.setComponent(new PassedLoopTimeComponent(passedLoopTime));
            }
        }
    }
}
