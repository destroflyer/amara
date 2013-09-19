/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.attributes;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.items.*;

/**
 *
 * @author Carl
 */
public class UpdateAttributesSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getCurrent().getEntitiesWithAll(RequestUpdateAttributesComponent.class)))
        {
            float maximumHealth = 0;
            float attackDamage = 0;
            float abilityPower = 0;
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
            InventoryComponent inventoryComponent = entityWrapper.getComponent(InventoryComponent.class);
            if(inventoryComponent != null){
                int[] itemEntites = inventoryComponent.getItemEntities();
                float bonusFlatMaximumHealth = 0;
                float bonusFlatAttackDamage = 0;
                float bonusFlatAbilityPower = 0;
                for(int i=0;i<itemEntites.length;i++){
                    EntityWrapper itemWrapper = entityWorld.getWrapped(itemEntites[i]);
                    BonusFlatMaximumHealthComponent bonusFlatMaximumHealthComponent = itemWrapper.getComponent(BonusFlatMaximumHealthComponent.class);
                    if(bonusFlatMaximumHealthComponent != null){
                        bonusFlatMaximumHealth += bonusFlatMaximumHealthComponent.getValue();
                    }
                    BonusFlatAttackDamageComponent bonusFlatAttackDamageComponent = itemWrapper.getComponent(BonusFlatAttackDamageComponent.class);
                    if(bonusFlatAttackDamageComponent != null){
                        bonusFlatAttackDamage += bonusFlatAttackDamageComponent.getValue();
                    }
                    BonusFlatAbilityPowerComponent bonusFlatAbilityPowerComponent = itemWrapper.getComponent(BonusFlatAbilityPowerComponent.class);
                    if(bonusFlatAbilityPowerComponent != null){
                        bonusFlatAbilityPower += bonusFlatAbilityPowerComponent.getValue();
                    }
                }
                maximumHealth += bonusFlatMaximumHealth;
                attackDamage += bonusFlatAttackDamage;
                abilityPower += bonusFlatAbilityPower;
            }
            entityWrapper.setComponent(new MaximumHealthComponent(maximumHealth));
            if(entityWrapper.getComponent(HealthComponent.class) == null){
                entityWrapper.setComponent(new HealthComponent(maximumHealth));
            }
            entityWrapper.setComponent(new AttackDamageComponent(attackDamage));
            entityWrapper.setComponent(new AbilityPowerComponent(abilityPower));
            entityWrapper.removeComponent(RequestUpdateAttributesComponent.class);
        }
    }
}
