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
public class ApplyStunSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(ApplyEffectComponent.class, StunComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectComponent.class).getTargetID();
            StunComponent stunComponent = entityWrapper.getComponent(StunComponent.class);
            IsStunnedComponent isStunnedComponent = entityWorld.getCurrent().getComponent(targetID, IsStunnedComponent.class);
            if((isStunnedComponent == null) || (stunComponent.getDuration() > isStunnedComponent.getRemainingDuration())){
                entityWorld.getCurrent().setComponent(targetID, new IsStunnedComponent(stunComponent.getDuration()));
            }
        }
    }
}
