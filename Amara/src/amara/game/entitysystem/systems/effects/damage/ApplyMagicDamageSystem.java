/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.damage;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.aggro.AggroUtil;

/**
 *
 * @author Carl
 */
public class ApplyMagicDamageSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, MagicDamageComponent.class)))
        {
            int targetID = entityWrapper.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            if(entityWorld.hasComponent(targetID, IsVulnerableComponent.class)){
                HealthComponent healthComponent = entityWorld.getComponent(targetID, HealthComponent.class);
                if(healthComponent != null){
                    MagicDamageComponent magicDamageComponent = entityWrapper.getComponent(MagicDamageComponent.class);
                    float health = (healthComponent.getValue() - magicDamageComponent.getValue());
                    entityWorld.setComponent(targetID, new HealthComponent(health));
                    EffectCastSourceComponent effectCastSourceComponent = entityWrapper.getComponent(EffectCastSourceComponent.class);
                    if(effectCastSourceComponent != null){
                        AggroUtil.drawAggro(entityWorld, targetID, effectCastSourceComponent.getSourceEntity());
                    }
                }
            }
        }
    }
}
