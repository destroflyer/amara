/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.buffs;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.buffs.*;

/**
 *
 * @author Carl
 */
public class ApplyRemoveBuffsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, RemoveBuffComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            int buffEntity = entityWrapper.getComponent(RemoveBuffComponent.class).getBuffEntity();
            for(int buffStatus : entityWorld.getEntitiesWithAll(ActiveBuffComponent.class)){
                ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatus, ActiveBuffComponent.class);
                if((activeBuffComponent.getTargetEntity() == targetEntity) && (activeBuffComponent.getBuffEntity() == buffEntity)){
                    entityWorld.setComponent(buffStatus, new RemoveFromTargetComponent());
                    break;
                }
            }
        }
    }
}
