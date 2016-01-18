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
public class ApplyRemoveStacksSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveStacksComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            int stacks = entityWorld.getComponent(targetEntity, StacksComponent.class).getStacks();
            int newStacks = (stacks - entityWrapper.getComponent(RemoveStacksComponent.class).getStacks());
            if(newStacks < 0){
                newStacks = 0;
            }
            entityWorld.setComponent(targetEntity, new StacksComponent(newStacks));
        }
    }
}
