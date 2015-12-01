/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.buffs.stacks;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.buffs.stacks.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.buffs.stacks.*;

/**
 *
 * @author Carl
 */
public class ApplyClearStacksSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, ClearStacksComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            entityWorld.setComponent(targetEntity, new StacksComponent(0));
        }
    }
}
