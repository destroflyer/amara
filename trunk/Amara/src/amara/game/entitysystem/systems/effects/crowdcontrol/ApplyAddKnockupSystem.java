/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.crowdcontrol;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.crowdcontrol.knockup.*;
import amara.game.entitysystem.components.units.crowdcontrol.*;
import amara.game.entitysystem.systems.units.UnitUtil;

/**
 *
 * @author Carl
 */
public class ApplyAddKnockupSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddKnockupComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            if(!entityWorld.hasComponent(targetID, IsKnockupedImmuneComponent.class)){
                AddKnockupComponent addKnockupComponent = entityWrapper.getComponent(AddKnockupComponent.class);
                IsKnockupedComponent isKnockupedComponent = entityWorld.getComponent(targetID, IsKnockupedComponent.class);
                float duration = entityWorld.getComponent(addKnockupComponent.getKnockupEntity(), KnockupDurationComponent.class).getDuration();
                if((isKnockupedComponent == null) || (duration > isKnockupedComponent.getRemainingDuration())){
                    entityWorld.setComponent(targetID, new IsKnockupedComponent(addKnockupComponent.getKnockupEntity(), duration));
                    UnitUtil.cancelAction(entityWorld, targetID);
                }
            }
        }
    }
}
