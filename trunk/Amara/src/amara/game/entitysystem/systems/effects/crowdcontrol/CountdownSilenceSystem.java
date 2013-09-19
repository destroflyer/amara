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
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(IsSilencedComponentRenameDummy.class)))
        {
            IsSilencedComponentRenameDummy isSilencedComponent = entityWrapper.getComponent(IsSilencedComponentRenameDummy.class);
            float duration = (isSilencedComponent.getRemainingDuration() - deltaSeconds);
            if(duration >= 0){
                entityWrapper.setComponent(new IsSilencedComponentRenameDummy(duration));
            }
            else{
                entityWrapper.removeComponent(IsSilencedComponentRenameDummy.class);
            }
        }
    }
}
