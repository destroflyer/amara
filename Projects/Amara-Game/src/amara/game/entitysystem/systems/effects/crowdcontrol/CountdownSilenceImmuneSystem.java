/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.crowdcontrol;

import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CountdownSilenceImmuneSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(IsSilencedImmuneComponent.class)))
        {
            IsSilencedImmuneComponent isSilencedImmuneComponent = entityWrapper.getComponent(IsSilencedImmuneComponent.class);
            float duration = (isSilencedImmuneComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new IsSilencedImmuneComponent(duration));
            }
            else{
                entityWrapper.removeComponent(IsSilencedImmuneComponent.class);
            }
        }
    }
}
