/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.crowdcontrol;

import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CountdownSilenceSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(IsSilencedComponent.class)))
        {
            IsSilencedComponent isSilencedComponent = entityWrapper.getComponent(IsSilencedComponent.class);
            float duration = (isSilencedComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new IsSilencedComponent(duration));
            }
            else{
                entityWrapper.removeComponent(IsSilencedComponent.class);
            }
        }
    }
}
