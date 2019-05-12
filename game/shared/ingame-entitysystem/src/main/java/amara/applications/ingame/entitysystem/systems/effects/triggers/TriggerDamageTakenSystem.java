/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.components.effects.damage.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers.*;
import amara.libraries.entitysystem.*;
import amara.libraries.expressions.Values;
import amara.libraries.expressions.values.*;

/**
 *
 * @author Carl
 */
public class TriggerDamageTakenSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class)){
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            for(int effectTriggerEntity : entityWorld.getEntitiesWithAll(TriggerSourceComponent.class, DamageTakenTriggerComponent.class)){
                int sourceEntity = entityWorld.getComponent(effectTriggerEntity, TriggerSourceComponent.class).getSourceEntity();
                if((sourceEntity == targetEntity) && (!entityWorld.hasComponent(effectTriggerEntity, RemainingCooldownComponent.class))){
                    if(isDamageAccepted(entityWorld, effectImpactEntity, entityWorld.getComponent(effectTriggerEntity, DamageTakenTriggerComponent.class))){
                        int castSourceEntity = -1;
                        EffectCastSourceComponent effectCastSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectCastSourceComponent.class);
                        if(effectCastSourceComponent != null){
                            castSourceEntity = effectCastSourceComponent.getSourceEntity();
                        }
                        EntityWrapper effectCast = EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, castSourceEntity);
                        if(effectCast != null){
                            float physicalDamage = 0;
                            float magicDamage = 0;
                            float totalDamage = 0;
                            ResultingPhysicalDamageComponent resultingPhysicalDamageComponent = entityWorld.getComponent(effectImpactEntity, ResultingPhysicalDamageComponent.class);
                            if(resultingPhysicalDamageComponent != null){
                                physicalDamage = resultingPhysicalDamageComponent.getValue();
                                totalDamage += physicalDamage;
                            }
                            ResultingMagicDamageComponent resultingMagicDamageComponent = entityWorld.getComponent(effectImpactEntity, ResultingMagicDamageComponent.class);
                            if(resultingMagicDamageComponent != null){
                                magicDamage = resultingMagicDamageComponent.getValue();
                                totalDamage += magicDamage;
                            }
                            Values values = new Values();
                            values.setVariable("physicalDamage", new NumericValue(physicalDamage));
                            values.setVariable("magicDamage", new NumericValue(magicDamage));
                            values.setVariable("totalDamage", new NumericValue(totalDamage));
                            effectCast.setComponent(new CustomEffectValuesComponent(values));
                        }
                    }
                }
            }
        }
    }
    
    private boolean isDamageAccepted(EntityWorld entityWorld, int effectImpactEntity, DamageTakenTriggerComponent damageTakenTriggerComponent){
        return ((damageTakenTriggerComponent.isPhysicalDamage() && entityWorld.hasComponent(effectImpactEntity, ResultingPhysicalDamageComponent.class))
             || (damageTakenTriggerComponent.isMagicDamage() && entityWorld.hasComponent(effectImpactEntity, ResultingMagicDamageComponent.class)));
    }
}
