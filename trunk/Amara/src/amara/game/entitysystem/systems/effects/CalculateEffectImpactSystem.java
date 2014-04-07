/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects;

import amara.game.entitysystem.components.effects.casts.EffectCastTargetComponent;
import amara.game.entitysystem.components.effects.casts.EffectCastDirectionComponent;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.buffs.*;
import amara.game.entitysystem.components.effects.crowdcontrol.*;
import amara.game.entitysystem.components.effects.damage.*;
import amara.game.entitysystem.components.effects.general.*;
import amara.game.entitysystem.components.effects.heals.*;
import amara.game.entitysystem.components.effects.movement.*;
import amara.game.entitysystem.components.effects.spells.*;
import amara.game.entitysystem.components.movements.*;
import amara.game.entitysystem.components.spells.targets.*;

/**
 *
 * @author Carl
 */
public class CalculateEffectImpactSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(PrepareEffectComponent.class))){
            EntityWrapper effect = entityWorld.getWrapped(entityWrapper.getComponent(PrepareEffectComponent.class).getEffectEntityID());
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
                EffectSourceComponent effectSourceComponent = entityWrapper.getComponent(EffectSourceComponent.class);
                if(effectSourceComponent != null){
                    EntityWrapper effectSource = entityWorld.getWrapped(effectSourceComponent.getSourceEntity());
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
                EffectCastTargetComponent effectCastTargetComponent = entityWrapper.getComponent(EffectCastTargetComponent.class);
                EffectCastDirectionComponent effectCastDirectionComponent = entityWrapper.getComponent(EffectCastDirectionComponent.class);
                MoveComponent moveComponent = effect.getComponent(MoveComponent.class);
                if(moveComponent != null){
                    int movementEntity = entityWorld.createEntity();
                    for(Object component : entityWorld.getComponents(moveComponent.getMovementEntity())){
                        if(component instanceof TargetedMovementTargetComponent){
                            entityWorld.setComponent(movementEntity, new MovementTargetComponent(effectCastTargetComponent.getTargetEntity()));
                        }
                        else if(component instanceof TargetedMovementDirectionComponent){
                            entityWorld.setComponent(movementEntity, new MovementDirectionComponent(effectCastDirectionComponent.getDirection()));
                        }
                        else{
                            entityWorld.setComponent(movementEntity, component);
                        }
                    }
                    effectImpact.setComponent(new MoveComponent(movementEntity));
                }
                transferComponents(effect, effectImpact, new Class[]{
                    AddComponentsComponent.class,
                    RemoveEntityComponent.class,
                    AddBuffComponent.class,
                    RemoveBuffComponent.class,
                    AddBindingComponent.class,
                    RemoveBindingComponent.class,
                    AddBindingImmuneComponent.class,
                    AddSilenceComponent.class,
                    RemoveSilenceComponent.class,
                    AddSilenceImmuneComponent.class,
                    AddStunComponent.class,
                    RemoveStunComponent.class,
                    AddStunImmuneComponent.class,
                    AddTargetabilityComponent.class,
                    RemoveTargetabilityComponent.class,
                    AddVulnerabilityComponent.class,
                    RemoveVulnerabilityComponent.class,
                    StopComponent.class,
                    ReplaceSpellWithExistingSpellComponent.class,
                    ReplaceSpellWithNewSpellComponent.class
                });
                effectImpact.setComponent(new ApplyEffectImpactComponent(targetEntity.getId()));
            }
            entityWorld.removeEntity(entityWrapper.getId());
        }
    }
    
    private void transferComponents(EntityWrapper effect, EntityWrapper effectImpact, Class[] componentClasses){
        for(Class componentClass : componentClasses){
            Object component = effect.getComponent(componentClass);
            if(component != null){
                effectImpact.setComponent(component);
            }
        }
    }
}
