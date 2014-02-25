/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.visuals;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.visuals.AnimationComponent;
import amara.game.entitysystem.components.visuals.animations.*;

/**
 *
 * @author Carl
 */
public class CountdownAnimationLoopsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(PassedLoopTimeComponent.class, LoopDurationComponent.class, RemainingLoopsComponent.class)))
        {
            PassedLoopTimeComponent passedLoopTimeComponent = entityWrapper.getComponent(PassedLoopTimeComponent.class);
            float passedLoopTime = (passedLoopTimeComponent.getPassedTime() + deltaSeconds);
            if(passedLoopTime >= entityWrapper.getComponent(LoopDurationComponent.class).getDuration()){
                int remainingLoops = (entityWrapper.getComponent(RemainingLoopsComponent.class).getLoopsCount() - 1);
                if(remainingLoops > 0){
                    entityWrapper.setComponent(new RemainingLoopsComponent(remainingLoops));
                    entityWrapper.setComponent(new PassedLoopTimeComponent(0));
                }
                else{
                    entityWrapper.removeComponent(RemainingLoopsComponent.class);
                    entityWrapper.removeComponent(PassedLoopTimeComponent.class);
                    entityWrapper.removeComponent(AnimationComponent.class);
                }
            }
            else{
                entityWrapper.setComponent(new PassedLoopTimeComponent(passedLoopTime));
            }
        }
    }
}
