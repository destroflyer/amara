/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.buffs.stacks;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.buffs.stacks.*;

/**
 *
 * @author Carl
 */
public class MaximumStacksSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(MaximumStacksComponent.class, StacksComponent.class)){
            int stacks = entityWorld.getComponent(entity, StacksComponent.class).getStacks();
            int maximumStacks = entityWorld.getComponent(entity, MaximumStacksComponent.class).getStacks();
            if(stacks > maximumStacks){
                entityWorld.setComponent(entity, new StacksComponent(maximumStacks));
            }
        }
    }
}