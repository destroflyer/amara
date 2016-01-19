/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.triggers;

import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.casts.*;
import amara.applications.ingame.entitysystem.components.effects.damage.*;
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
                if(sourceEntity == targetEntity){
                    if(isDamageAccepted(entityWorld, effectImpactEntity, entityWorld.getComponent(effectTriggerEntity, DamageTakenTriggerComponent.class))){
                        int castSourceEntity = -1;
                        EffectCastSourceComponent effectCastSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectCastSourceComponent.class);
                        if(effectCastSourceComponent != null){
                            castSourceEntity = effectCastSourceComponent.getSourceEntity();
                        }
                        EntityWrapper effectCast = EffectTriggerUtil.triggerEffect(entityWorld, effectTriggerEntity, castSourceEntity);
                        if(effectCast != null){
                            Values values = new Values();
                            float totalDamage = 0;
                            ResultingPhysicalDamageComponent resultingPhysicalDamageComponent = entityWorld.getComponent(effectImpactEntity, ResultingPhysicalDamageComponent.class);
                            if(resultingPhysicalDamageComponent != null){
                                values.setVariable("physicalDamage", new NumericValue(resultingPhysicalDamageComponent.getValue()));
                                totalDamage += resultingPhysicalDamageComponent.getValue();
                            }
                            ResultingMagicDamageComponent resultingMagicDamageComponent = entityWorld.getComponent(effectImpactEntity, ResultingMagicDamageComponent.class);
                            if(resultingMagicDamageComponent != null){
                                values.setVariable("magicDamage", new NumericValue(resultingMagicDamageComponent.getValue()));
                                totalDamage += resultingMagicDamageComponent.getValue();
                            }
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
