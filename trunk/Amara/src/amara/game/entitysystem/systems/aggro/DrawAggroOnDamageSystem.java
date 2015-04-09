/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.aggro;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.effects.damage.*;

/**
 *
 * @author Carl
 */
public class DrawAggroOnDamageSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, PhysicalDamageComponent.class)){
            drawAggro(entityWorld, effectImpactEntity);
        }
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, MagicDamageComponent.class)){
            drawAggro(entityWorld, effectImpactEntity);
        }
    }
    
    private void drawAggro(EntityWorld entityWorld, int effectImpactEntity){
        int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetID();
        EffectCastSourceComponent effectCastSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectCastSourceComponent.class);
        if(effectCastSourceComponent != null){
            AggroUtil.tryDrawAggro(entityWorld, targetEntity, effectCastSourceComponent.getSourceEntity());
        }
    }
}
