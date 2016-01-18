/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.buffs.stacks;

import amara.game.entitysystem.components.buffs.stacks.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.buffs.stacks.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyAddStacksSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddStacksComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            int stacks = entityWorld.getComponent(targetEntity, StacksComponent.class).getStacks();
            int newStacks = (stacks + entityWrapper.getComponent(AddStacksComponent.class).getStacks());
            entityWorld.setComponent(targetEntity, new StacksComponent(newStacks));
        }
    }
}
