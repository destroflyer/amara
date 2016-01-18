/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.crowdcontrol;

import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddStunImmuneSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddStunImmuneComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            AddStunImmuneComponent addStunImmuneComponent = entityWrapper.getComponent(AddStunImmuneComponent.class);
            IsStunnedImmuneComponent isStunnedImmuneComponent = entityWorld.getComponent(targetID, IsStunnedImmuneComponent.class);
            if((isStunnedImmuneComponent == null) || (addStunImmuneComponent.getDuration() > isStunnedImmuneComponent.getRemainingDuration())){
                entityWorld.setComponent(targetID, new IsStunnedImmuneComponent(addStunImmuneComponent.getDuration()));
            }
        }
    }
}
