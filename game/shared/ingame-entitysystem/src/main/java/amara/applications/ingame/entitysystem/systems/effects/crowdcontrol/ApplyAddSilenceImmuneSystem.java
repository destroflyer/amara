/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.crowdcontrol;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddSilenceImmuneSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddSilenceImmuneComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            AddSilenceImmuneComponent addSilenceImmuneComponent = entityWrapper.getComponent(AddSilenceImmuneComponent.class);
            IsSilencedImmuneComponent isSilencedImmuneComponent = entityWorld.getComponent(targetID, IsSilencedImmuneComponent.class);
            if((isSilencedImmuneComponent == null) || (addSilenceImmuneComponent.getDuration() > isSilencedImmuneComponent.getRemainingDuration())){
                entityWorld.setComponent(targetID, new IsSilencedImmuneComponent(addSilenceImmuneComponent.getDuration()));
            }
        }
    }
}
