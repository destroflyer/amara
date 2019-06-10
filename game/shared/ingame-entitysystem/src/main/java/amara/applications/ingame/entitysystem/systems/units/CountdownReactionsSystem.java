/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CountdownReactionsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAny(ReactionComponent.class))){
            ReactionComponent reactionComponent = entityWrapper.getComponent(ReactionComponent.class);
            float duration = (reactionComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new ReactionComponent(reactionComponent.getReaction(), duration));
            }
            else{
                entityWrapper.removeComponent(ReactionComponent.class);
            }
        }
    }
}
