/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.crowdcontrol;

import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.systems.units.UnitUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddBindingSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddBindingComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            if(!entityWorld.hasComponent(targetID, IsBindedImmuneComponent.class)){
                AddBindingComponent addBindingComponent = entityWrapper.getComponent(AddBindingComponent.class);
                IsBindedComponent isBindedComponent = entityWorld.getComponent(targetID, IsBindedComponent.class);
                if((isBindedComponent == null) || (addBindingComponent.getDuration() > isBindedComponent.getRemainingDuration())){
                    entityWorld.setComponent(targetID, new IsBindedComponent(addBindingComponent.getDuration()));
                    UnitUtil.cancelMovement(entityWorld, targetID);
                }
            }
        }
    }
}
