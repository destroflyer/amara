/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.buffs;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.buffs.*;

/**
 *
 * @author Carl
 */
public class ApplyAddBuffsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddBuffComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            AddBuffComponent addBuffComponent = entityWrapper.getComponent(AddBuffComponent.class);
            int buffStatusEntity = -1;
            for(int entity : entityWorld.getEntitiesWithAll(ActiveBuffComponent.class)){
                ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(entity, ActiveBuffComponent.class);
                if((activeBuffComponent.getTargetEntityID() == targetEntity) && (activeBuffComponent.getBuffEntityID() == addBuffComponent.getBuffEntity())){
                    buffStatusEntity = entity;
                    break;
                }
            }
            if(buffStatusEntity == -1){
                buffStatusEntity = entityWorld.createEntity();
                entityWorld.setComponent(buffStatusEntity, new ActiveBuffComponent(targetEntity, addBuffComponent.getBuffEntity()));
            }
            entityWorld.setComponent(buffStatusEntity, new RemainingBuffDurationComponent(addBuffComponent.getDuration()));
            entityWorld.setComponent(targetEntity, new RequestUpdateAttributesComponent());
        }
    }
}
