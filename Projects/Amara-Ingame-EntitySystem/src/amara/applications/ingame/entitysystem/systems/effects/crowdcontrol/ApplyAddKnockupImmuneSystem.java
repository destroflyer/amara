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
public class ApplyAddKnockupImmuneSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddKnockupImmuneComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            AddKnockupImmuneComponent addKnockupImmuneComponent = entityWrapper.getComponent(AddKnockupImmuneComponent.class);
            IsKnockupedImmuneComponent isKnockupedImmuneComponent = entityWorld.getComponent(targetID, IsKnockupedImmuneComponent.class);
            if((isKnockupedImmuneComponent == null) || (addKnockupImmuneComponent.getDuration() > isKnockupedImmuneComponent.getRemainingDuration())){
                entityWorld.setComponent(targetID, new IsKnockupedImmuneComponent(addKnockupImmuneComponent.getDuration()));
            }
        }
    }
}
