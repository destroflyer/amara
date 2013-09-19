/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.crowdcontrol;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;

/**
 *
 * @author Carl
 */
public class CountdownSilenceSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(IsSilencedComponent.class)))
        {
            IsSilencedComponent isSilencedComponent = entityWrapper.getComponent(IsSilencedComponent.class);
            float duration = (isSilencedComponent.getRemainingDuration() - deltaSeconds);
            if(duration >= 0){
                entityWrapper.setComponent(new IsSilencedComponent(duration));
            }
            else{
                entityWrapper.removeComponent(IsSilencedComponent.class);
            }
        }
    }
}
