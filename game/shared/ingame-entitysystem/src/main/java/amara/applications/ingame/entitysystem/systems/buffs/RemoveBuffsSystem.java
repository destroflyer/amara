/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.buffs;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.systems.effects.triggers.EffectTriggerUtil;
import amara.libraries.entitysystem.*;

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
        for(int buffStatus : entityWorld.getEntitiesWithAny(ActiveBuffComponent.class)){
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
