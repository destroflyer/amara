/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.effects.heals.*;
import amara.game.entitysystem.components.effects.movement.*;

/**
 *
 * @author Carl
 */
public class CalculateEffectImpactSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(PrepareEffectComponent.class)))
        {
            EntityWrapper effect = entityWorld.getWrapped(entityWrapper.getComponent(PrepareEffectComponent.class).getEffectEntityID());
            EffectSourceComponent effectSourceComponent = entityWrapper.getComponent(EffectSourceComponent.class);
            int[] affectedTargetEntitesIDs = entityWrapper.getComponent(AffectedTargetsComponent.class).getTargetEntitiesIDs();
            for(int i=0;i<affectedTargetEntitesIDs.length;i++){
                EntityWrapper targetEntity = entityWorld.getWrapped(affectedTargetEntitesIDs[i]);
                EntityWrapper effectImpact = entityWorld.getWrapped(entityWorld.createEntity());
                float physicalDamage = 0;
                float magicDamage = 0;
                float heal = 0;
                FlatPhysicalDamageComponent flatPhysicalDamageComponent = effect.getComponent(FlatPhysicalDamageComponent.class);
                if(flatPhysicalDamageComponent != null){
                    physicalDamage += flatPhysicalDamageComponent.getValue();
                }
                FlatMagicDamageComponent flatMagicDamageComponent = effect.getComponent(FlatMagicDamageComponent.class);
                if(flatMagicDamageComponent != null){
                    magicDamage += flatMagicDamageComponent.getValue();
                }
                FlatHealComponent flatHealComponent = effect.getComponent(FlatHealComponent.class);
                if(flatHealComponent != null){
                    heal += flatHealComponent.getValue();
                }
                if(effectSourceComponent != null){
                    EntityWrapper effectSource = entityWorld.getWrapped(effectSourceComponent.getSourceEntityID());
                    ScalingAttackDamagePhysicalDamageComponent scalingAttackDamagePhysicalDamageComponent = effect.getComponent(ScalingAttackDamagePhysicalDamageComponent.class);
                    if((scalingAttackDamagePhysicalDamageComponent != null) && effectSource.hasComponent(AttackDamageComponent.class)){
                        physicalDamage += (effectSource.getComponent(AttackDamageComponent.class).getValue() * scalingAttackDamagePhysicalDamageComponent.getRatio());
                    }
                    ScalingAbilityPowerMagicDamageComponent scalingAbilityPowerMagicDamageComponent = effect.getComponent(ScalingAbilityPowerMagicDamageComponent.class);
                    if((scalingAbilityPowerMagicDamageComponent != null) && effectSource.hasComponent(AbilityPowerComponent.class)){
                        magicDamage += (effectSource.getComponent(AbilityPowerComponent.class).getValue() * scalingAbilityPowerMagicDamageComponent.getRatio());
                    }
                }
                if(physicalDamage != 0){
                    effectImpact.setComponent(new PhysicalDamageComponent(physicalDamage));
                }
                if(magicDamage != 0){
                    effectImpact.setComponent(new MagicDamageComponent(magicDamage));
                }
                if(heal != 0){
                    effectImpact.setComponent(new HealComponent(heal));
                }
                BindingComponent bindingComponent = effect.getComponent(BindingComponent.class);
                if(bindingComponent != null){
                    effectImpact.setComponent(bindingComponent);
                }
                SilenceComponent silenceComponent = effect.getComponent(SilenceComponent.class);
                if(silenceComponent != null){
                    effectImpact.setComponent(silenceComponent);
                }
                StunComponent stunComponent = effect.getComponent(StunComponent.class);
                if(stunComponent != null){
                    effectImpact.setComponent(stunComponent);
                }
                MoveToEntityPositionComponent moveToEntityPositionComponent = effect.getComponent(MoveToEntityPositionComponent.class);
                if(moveToEntityPositionComponent != null){
                    effectImpact.setComponent(moveToEntityPositionComponent);
                }
                effectImpact.setComponent(new ApplyEffectImpactComponent(targetEntity.getId()));
            }
            entityWorld.removeEntity(entityWrapper.getId());
        }
    }
}
