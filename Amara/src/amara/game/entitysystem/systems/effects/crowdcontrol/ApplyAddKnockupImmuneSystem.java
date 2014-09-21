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
public class ApplyAddKnockupImmuneSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddKnockupImmuneComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            AddKnockupImmuneComponent addKnockupImmuneComponent = entityWrapper.getComponent(AddKnockupImmuneComponent.class);
            IsKnockupedImmuneComponent isKnockupedImmuneComponent = entityWorld.getComponent(targetID, IsKnockupedImmuneComponent.class);
            if((isKnockupedImmuneComponent == null) || (addKnockupImmuneComponent.getDuration() > isKnockupedImmuneComponent.getRemainingDuration())){
                entityWorld.setComponent(targetID, new IsKnockupedImmuneComponent(addKnockupImmuneComponent.getDuration()));
            }
        }
    }
}
