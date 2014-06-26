/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.attributes;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.status.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.components.units.effecttriggers.*;
import amara.game.entitysystem.components.visuals.animations.*;

/**
 *
 * @author Carl
 */
public class UpdateAttributesSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(RequestUpdateAttributesComponent.class)))
        {
            float maximumHealth = 0;
            float healthRegeneration = 0;
            float attackDamage = 0;
            float abilityPower = 0;
            float attackSpeed = 0;
            float walkSpeed = 0;
            BaseMaximumHealthComponent baseMaximumHealthComponent = entityWrapper.getComponent(BaseMaximumHealthComponent.class);
            if(baseMaximumHealthComponent != null){
                maximumHealth += baseMaximumHealthComponent.getValue();
            }
            BaseHealthRegenerationComponent baseHealthRegenerationComponent = entityWrapper.getComponent(BaseHealthRegenerationComponent.class);
            if(baseHealthRegenerationComponent != null){
                healthRegeneration += baseHealthRegenerationComponent.getValue();
            }
            BaseAttackDamageComponent baseAttackDamageComponent = entityWrapper.getComponent(BaseAttackDamageComponent.class);
            if(baseAttackDamageComponent != null){
                attackDamage += baseAttackDamageComponent.getValue();
            }
            BaseAbilityPowerComponent baseAbiliyPowerComponent = entityWrapper.getComponent(BaseAbilityPowerComponent.class);
            if(baseAbiliyPowerComponent != null){
                abilityPower += baseAbiliyPowerComponent.getValue();
            }
            BaseAttackSpeedComponent baseAttackSpeedComponent = entityWrapper.getComponent(BaseAttackSpeedComponent.class);
            if(baseAttackSpeedComponent != null){
                attackSpeed += baseAttackSpeedComponent.getValue();
            }
            BaseWalkSpeedComponent baseWalkSpeedComponent = entityWrapper.getComponent(BaseWalkSpeedComponent.class);
            if(baseWalkSpeedComponent != null){
                walkSpeed += baseWalkSpeedComponent.getValue();
            }
            AttributeBonus attributeBonus = new AttributeBonus();
            InventoryComponent inventoryComponent = entityWrapper.getComponent(InventoryComponent.class);
            if(inventoryComponent != null){
                int[] itemEntities = inventoryComponent.getItemEntities();
                for(int i=0;i<itemEntities.length;i++){
                    addAttributeBonus(entityWorld, attributeBonus, itemEntities[i]);
                }
            }
            for(EntityWrapper buffStatus : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ActiveBuffComponent.class)))
            {
                ActiveBuffComponent activeBuffComponent = buffStatus.getComponent(ActiveBuffComponent.class);
                if(activeBuffComponent.getTargetEntityID() == entityWrapper.getId()){
                    ContinuousEffectComponent continuousEffectComponent = entityWorld.getComponent(activeBuffComponent.getBuffEntityID(), ContinuousEffectComponent.class);
                    if(continuousEffectComponent != null){
                        addAttributeBonus(entityWorld, attributeBonus, continuousEffectComponent.getEffectEntityID());
                    }
                }
            }
            maximumHealth += attributeBonus.getFlatMaximumHealth();
            healthRegeneration += attributeBonus.getFlatHealthRegeneration();
            attackDamage += attributeBonus.getFlatAttackDamage();
            abilityPower += attributeBonus.getFlatAbilityPower();
            attackSpeed += (attackSpeed * attributeBonus.getPercentageAttackSpeed());
            walkSpeed += attributeBonus.getFlatWalkSpeed();
            walkSpeed *= attributeBonus.getPercentageWalkSpeed();
            entityWrapper.setComponent(new MaximumHealthComponent(maximumHealth));
            if((entityWrapper.getComponent(HealthComponent.class) == null) && entityWrapper.hasComponent(IsAliveComponent.class)){
                entityWrapper.setComponent(new HealthComponent(maximumHealth));
            }
            entityWrapper.setComponent(new HealthRegenerationComponent(healthRegeneration));
            entityWrapper.setComponent(new AttackDamageComponent(attackDamage));
            entityWrapper.setComponent(new AbilityPowerComponent(abilityPower));
            entityWrapper.setComponent(new AttackSpeedComponent(attackSpeed));
            AutoAttackComponent autoAttackComponent = entityWrapper.getComponent(AutoAttackComponent.class);
            if(autoAttackComponent != null){
                float autoAttackInterval = (1 / attackSpeed);
                entityWorld.setComponent(autoAttackComponent.getAutoAttackEntity(), new CooldownComponent(autoAttackInterval));
                CastAnimationComponent autoAttackCastAnimationComponent = entityWorld.getComponent(autoAttackComponent.getAutoAttackEntity(), CastAnimationComponent.class);
                if(autoAttackCastAnimationComponent != null){
                    entityWorld.setComponent(autoAttackCastAnimationComponent.getAnimationEntity(), new LoopDurationComponent(autoAttackInterval));
                }
                int[] instantEffectTriggerEntities = entityWorld.getComponent(autoAttackComponent.getAutoAttackEntity(), InstantEffectTriggersComponent.class).getEffectTriggerEntities();
                for(int effectTriggerEntity : instantEffectTriggerEntities){
                    entityWorld.setComponent(effectTriggerEntity, new TriggerDelayComponent(autoAttackInterval / 2));
                }
                entityWorld.setComponent(autoAttackComponent.getAutoAttackEntity(), new CastDurationComponent(autoAttackInterval));
            }
            entityWrapper.setComponent(new WalkSpeedComponent(walkSpeed));
            entityWrapper.removeComponent(RequestUpdateAttributesComponent.class);
        }
    }
    
    private void addAttributeBonus(EntityWorld entityWorld, AttributeBonus attributeBonus, int bonusAttributesEntity){
        EntityWrapper itemWrapper = entityWorld.getWrapped(bonusAttributesEntity);
        BonusFlatMaximumHealthComponent bonusFlatMaximumHealthComponent = itemWrapper.getComponent(BonusFlatMaximumHealthComponent.class);
        if(bonusFlatMaximumHealthComponent != null){
            attributeBonus.addFlatMaximumHealth(bonusFlatMaximumHealthComponent.getValue());
        }
        BonusFlatHealthRegenerationComponent bonusFlatHealthRegenerationComponen = itemWrapper.getComponent(BonusFlatHealthRegenerationComponent.class);
        if(bonusFlatHealthRegenerationComponen != null){
            attributeBonus.addFlatHealthRegeneration(bonusFlatHealthRegenerationComponen.getValue());
        }
        BonusFlatAttackDamageComponent bonusFlatAttackDamageComponent = itemWrapper.getComponent(BonusFlatAttackDamageComponent.class);
        if(bonusFlatAttackDamageComponent != null){
            attributeBonus.addFlatAttackDamage(bonusFlatAttackDamageComponent.getValue());
        }
        BonusFlatAbilityPowerComponent bonusFlatAbilityPowerComponent = itemWrapper.getComponent(BonusFlatAbilityPowerComponent.class);
        if(bonusFlatAbilityPowerComponent != null){
            attributeBonus.addFlatAbilityPower(bonusFlatAbilityPowerComponent.getValue());
        }
        BonusPercentageAttackSpeedComponent bonusPercentageAttackSpeedComponent = itemWrapper.getComponent(BonusPercentageAttackSpeedComponent.class);
        if(bonusPercentageAttackSpeedComponent != null){
            attributeBonus.addPercentageAttackSpeed(bonusPercentageAttackSpeedComponent.getValue());
        }
        BonusFlatWalkSpeedComponent bonusFlatWalkSpeedComponent = itemWrapper.getComponent(BonusFlatWalkSpeedComponent.class);
        if(bonusFlatWalkSpeedComponent != null){
            attributeBonus.addFlatWalkSpeed(bonusFlatWalkSpeedComponent.getValue());
        }
        BonusPercentageWalkSpeedComponent bonusPercentageWalkSpeedComponent = itemWrapper.getComponent(BonusPercentageWalkSpeedComponent.class);
        if(bonusPercentageWalkSpeedComponent != null){
            attributeBonus.multiplicatePercentageWalkSpeed(bonusPercentageWalkSpeedComponent.getValue());
        }
    }
}
