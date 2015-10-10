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
import amara.game.entitysystem.components.effects.casts.*;

/**
 *
 * @author Carl
 */
public class ApplyAddBuffsSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, AddBuffComponent.class)))
        {
            int targetEntity = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetEntity();
            AddBuffComponent addBuffComponent = entityWrapper.getComponent(AddBuffComponent.class);
            int buffStatusEntity = addBuff(entityWorld, targetEntity, addBuffComponent.getBuffEntity(), addBuffComponent.getDuration());
            EntityUtil.transferComponents(entityWorld, entityWrapper.getId(), buffStatusEntity, new Class[]{
                EffectCastSourceComponent.class,
                EffectCastSourceSpellComponent.class
            });
        }
    }
    
    public static int addBuff(EntityWorld entityWorld, int targetEntity, int buffEntity){
        return addBuff(entityWorld, targetEntity, buffEntity, -1);
    }
    
    public static int addBuff(EntityWorld entityWorld, int targetEntity, int buffEntity, float duration){
        int buffStatusEntity = -1;
        for(Integer entity : entityWorld.getEntitiesWithAll(ActiveBuffComponent.class)){
            ActiveBuffComponent activeBuffComponent = entityWorld.getComponent(entity, ActiveBuffComponent.class);
            if((activeBuffComponent.getTargetEntity() == targetEntity) && (activeBuffComponent.getBuffEntity() == buffEntity)){
                buffStatusEntity = entity;
                break;
            }
        }
        if(buffStatusEntity == -1){
            buffStatusEntity = entityWorld.createEntity();
            entityWorld.setComponent(buffStatusEntity, new ActiveBuffComponent(targetEntity, buffEntity));
        }
        if(duration != -1){
            entityWorld.setComponent(buffStatusEntity, new RemainingBuffDurationComponent(duration));
        }
        else{
            entityWorld.removeComponent(buffStatusEntity, RemainingBuffDurationComponent.class);
        }
        entityWorld.setComponent(targetEntity, new RequestUpdateAttributesComponent());
        return buffStatusEntity;
    }
}
