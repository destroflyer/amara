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
public class CountdownKnockupSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(IsKnockupedComponent.class)))
        {
            IsKnockupedComponent isKnockupedComponent = entityWrapper.getComponent(IsKnockupedComponent.class);
            float duration = (isKnockupedComponent.getRemainingDuration() - deltaSeconds);
            if(duration > 0){
                entityWrapper.setComponent(new IsKnockupedComponent(isKnockupedComponent.getKnockupEntity(), duration));
            }
            else{
                entityWrapper.removeComponent(IsKnockupedComponent.class);
            }
        }
    }
}
