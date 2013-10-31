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
            float attackDamage = 0;
            float abilityPower = 0;
            float attackSpeed = 0;
            BaseMaximumHealthComponent baseMaximumHealthComponent = entityWrapper.getComponent(BaseMaximumHealthComponent.class);
            if(baseMaximumHealthComponent != null){
                maximumHealth += baseMaximumHealthComponent.getValue();
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
            attackDamage += attributeBonus.getFlatAttackDamage();
            abilityPower += attributeBonus.getFlatAbilityPower();
            attackSpeed += (attackSpeed * attributeBonus.getPercentageAttackSpeed());
            entityWrapper.setComponent(new MaximumHealthComponent(maximumHealth));
            if(entityWrapper.getComponent(HealthComponent.class) == null){
                entityWrapper.setComponent(new HealthComponent(maximumHealth));
            }
            entityWrapper.setComponent(new AttackDamageComponent(attackDamage));
            entityWrapper.setComponent(new AbilityPowerComponent(abilityPower));
            entityWrapper.setComponent(new AttackSpeedComponent(attackSpeed));
            AutoAttackComponent autoAttackComponent = entityWrapper.getComponent(AutoAttackComponent.class);
            if(autoAttackComponent != null){
                entityWorld.setComponent(autoAttackComponent.getAutoAttackEntityID(), new CooldownComponent(1 / attackSpeed));
            }
            entityWrapper.removeComponent(RequestUpdateAttributesComponent.class);
        }
    }
    
    private void addAttributeBonus(EntityWorld entityWorld, AttributeBonus attributeBonus, int bonusAttributesEntity){
        EntityWrapper itemWrapper = entityWorld.getWrapped(bonusAttributesEntity);
        BonusFlatMaximumHealthComponent bonusFlatMaximumHealthComponent = itemWrapper.getComponent(BonusFlatMaximumHealthComponent.class);
        if(bonusFlatMaximumHealthComponent != null){
            attributeBonus.addFlatMaximumHealth(bonusFlatMaximumHealthComponent.getValue());
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
    }
}
