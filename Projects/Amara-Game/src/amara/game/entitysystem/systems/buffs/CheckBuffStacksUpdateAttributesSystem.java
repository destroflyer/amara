/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.buffs;

import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.stacks.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class CheckBuffStacksUpdateAttributesSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, StacksComponent.class);
        for(int stacksEntity : observer.getChanged().getEntitiesWithAll(StacksComponent.class)){
            for(int buffStatusEntity : entityWorld.getEntitiesWithAll(ActiveBuffComponent.class)){
                ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
                BuffStacksComponent buffStacksComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntity(), BuffStacksComponent.class);
                if((buffStacksComponent != null) && (buffStacksComponent.getStacksEntity() == stacksEntity)){
                    if(entityWorld.hasComponent(activeBuffComponent.getBuffEntity(), ContinuousAttributesPerStackComponent.class)){
                        entityWorld.setComponent(activeBuffComponent.getTargetEntity(), new RequestUpdateAttributesComponent());
                    }
                    break;
                }
            }
        }
    }
}
