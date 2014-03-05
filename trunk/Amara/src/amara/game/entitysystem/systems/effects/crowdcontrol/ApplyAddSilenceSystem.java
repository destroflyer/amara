/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.crowdcontrol;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;

/**
 *
 * @author Carl
 */
public class ApplyAddSilenceSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddSilenceComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            if(!entityWorld.hasComponent(targetID, IsSilencedImmuneComponent.class)){
                AddSilenceComponent addSilenceComponent = entityWrapper.getComponent(AddSilenceComponent.class);
                IsSilencedComponent isSilencedComponent = entityWorld.getComponent(targetID, IsSilencedComponent.class);
                if((isSilencedComponent == null) || (addSilenceComponent.getDuration() > isSilencedComponent.getRemainingDuration())){
                    entityWorld.setComponent(targetID, new IsSilencedComponent(addSilenceComponent.getDuration()));
                }
            }
        }
    }
}
