/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.buffs;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.systems.effects.triggers.EffectTriggerUtil;

/**
 *
 * @author Carl
 */
public class RemoveBuffsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int buffStatus : entityWorld.getEntitiesWithAll(ActiveBuffComponent.class, RemoveFromTargetComponent.class)){
            removeBuff(entityWorld, buffStatus);
        }
    }
    
    public static void removeAllBuffs(EntityWorld entityWorld, int entity){
        for(int buffStatus : entityWorld.getEntitiesWithAll(ActiveBuffComponent.class)){
            int targetEntity = entityWorld.getComponent(buffStatus, ActiveBuffComponent.class).getTargetEntity();
            if(targetEntity == entity){
                removeBuff(entityWorld, buffStatus);
            }
        }
    }
    
    public static void removeBuff(EntityWorld entityWorld, int buffStatusEntity){
        ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class);
        entityWorld.removeEntity(buffStatusEntity);
        entityWorld.setComponent(activeBuffComponent.getTargetEntity(), new RequestUpdateAttributesComponent());
        OnBuffRemoveEffectTriggersComponent onBuffRemoveEffectTriggersComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntity(), OnBuffRemoveEffectTriggersComponent.class);
        if(onBuffRemoveEffectTriggersComponent != null){
            EffectTriggerUtil.triggerEffects(entityWorld, onBuffRemoveEffectTriggersComponent.getEffectTriggerEntities(), activeBuffComponent.getTargetEntity());
        }
    }
}