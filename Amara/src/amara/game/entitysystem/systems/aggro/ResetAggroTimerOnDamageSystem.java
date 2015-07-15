/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.aggro;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.units.RemainingAggroResetDurationComponent;

/**
 *
 * @author Carl
 */
public class ResetAggroTimerOnDamageSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, PhysicalDamageComponent.class)){
            resetAggroTimer(entityWorld, effectImpactEntity);
        }
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, MagicDamageComponent.class)){
            resetAggroTimer(entityWorld, effectImpactEntity);
        }
    }
    
    private void resetAggroTimer(EntityWorld entityWorld, int effectImpactEntity){
        EffectCastSourceComponent effectCastSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectCastSourceComponent.class);
        if(effectCastSourceComponent != null){
            int damagingEntity = effectCastSourceComponent.getSourceEntity();
            if(entityWorld.hasComponent(damagingEntity, RemainingAggroResetDurationComponent.class)){
                StartAggroResetTimersSystem.resetAggroTimer(entityWorld, damagingEntity);
            }
        }
    }
}
