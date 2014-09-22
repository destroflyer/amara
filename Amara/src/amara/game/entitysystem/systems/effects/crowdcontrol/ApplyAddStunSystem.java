/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.crowdcontrol;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.systems.units.UnitUtil;

/**
 *
 * @author Carl
 */
public class ApplyAddStunSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddStunComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            if(!entityWorld.hasComponent(targetID, IsStunnedImmuneComponent.class)){
                AddStunComponent addStunComponent = entityWrapper.getComponent(AddStunComponent.class);
                IsStunnedComponent isStunnedComponent = entityWorld.getComponent(targetID, IsStunnedComponent.class);
                if((isStunnedComponent == null) || (addStunComponent.getDuration() > isStunnedComponent.getRemainingDuration())){
                    entityWorld.setComponent(targetID, new IsStunnedComponent(addStunComponent.getDuration()));
                    UnitUtil.cancelAction(entityWorld, targetID);
                }
            }
        }
    }
}
