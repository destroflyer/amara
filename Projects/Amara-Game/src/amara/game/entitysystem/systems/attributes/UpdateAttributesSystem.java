/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.attributes;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.buffs.*;
import amara.game.entitysystem.components.buffs.stacks.*;
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
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(RequestUpdateAttributesComponent.class))){
            AttributeBonus attributeBonus = new AttributeBonus();
            BaseAttributesComponent baseAttributesComponent = entityWrapper.getComponent(BaseAttributesComponent.class);
            if(baseAttributesComponent != null){
                addAttributeBonus(entityWorld, attributeBonus, baseAttributesComponent.getBonusAttributesEntity());
            }
            AttributesPerLevelComponent attributesPerLevelComponent = entityWrapper.getComponent(AttributesPerLevelComponent.class);
            if(attributesPerLevelComponent != null){
                LevelComponent levelComponent = entityWrapper.getComponent(LevelComponent.class);
                if(levelComponent != null){
                    for(int i=0;i<levelComponent.getLevel();i++){
                        addAttributeBonus(entityWorld, attributeBonus, attributesPerLevelComponent.getBonusAttributesEntity());
                    }
                }
            }
            InventoryComponent inventoryComponent = entityWrapper.getComponent(InventoryComponent.class);
            if(inventoryComponent != null){
                int[] itemEntities = inventoryComponent.getItemEntities();
                for(int i=0;i<itemEntities.length;i++){
                    addAttributeBonus(entityWorld, attributeBonus, itemEntities[i]);
                }
            }
            for(EntityWrapper buffStatus : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ActiveBuffComponent.class))){
                ActiveBuffComponent activeBuffComponent = buffStatus.getComponent(ActiveBuffComponent.class);
                if(activeBuffComponent.getTargetEntity() == entityWrapper.getId()){
                    int buffEntity = activeBuffComponent.getBuffEntity();
                    ContinuousAttributesComponent continuousAttributesComponent = entityWorld.getComponent(buffEntity, ContinuousAttributesComponent.class);
                    if(continuousAttributesComponent != null){
                        addAttributeBonus(entityWorld, attributeBonus, continuousAttributesComponent.getBonusAttributesEntity());
                    }
                    ContinuousAttributesPerStackComponent continuousAttributesPerStackComponent = entityWorld.getComponent(buffEntity, ContinuousAttributesPerStackComponent.class);
                    if(continuousAttributesPerStackComponent != null){
                        BuffStacksComponent buffStacksComponent = entityWorld.getComponent(buffEntity, BuffStacksComponent.class);
                        if(buffStacksComponent != null){
                            StacksComponent stacksComponent = entityWorld.getComponent(buffStacksComponent.getStacksEntity(), StacksComponent.class);
                            if((stacksComponent != null) && (stacksComponent.getStacks() > 0)){
                                for(int i=0;i<stacksComponent.getStacks();i++){
                                    addAttributeBonus(entityWorld, attributeBonus, continuousAttributesPerStackComponent.getBonusAttributesEntity());
                                }
                            }
                        }
                    }
                }
            }
            float maximumHealth = attributeBonus.getFlatMaximumHealth();
            float healthRegeneration = attributeBonus.getFlatHealthRegeneration();
            float attackDamage = attributeBonus.getFlatAttackDamage();
            float abilityPower = attributeBonus.getFlatAbilityPower();
            float attackSpeed = attributeBonus.getFlatAttackSpeed();
            attackSpeed *= (1 + attributeBonus.getPercentageAttackSpeed());
            float cooldownSpeed = 1;
            if(attributeBonus.getPercentageCooldownSpeed() >= 0){
                cooldownSpeed *= (1 + attributeBonus.getPercentageCooldownSpeed());
            }
            else{
                cooldownSpeed /= (1 - attributeBonus.getPercentageCooldownSpeed());
            }
            float armor = attributeBonus.getFlatArmor();
            float magicResistance = attributeBonus.getFlatMagicResistance();
            float walkSpeed = attributeBonus.getFlatWalkSpeed();
            walkSpeed *= attributeBonus.getPercentageWalkSpeed();
            float criticalChance = attributeBonus.getPercentageCriticalChance();
            float lifesteal = attributeBonus.getPercentageLifesteal();
            float damageReduction = Math.min(attributeBonus.getPercentageDamageReduction(), 1);
            HealthComponent oldHealthComponent = entityWrapper.getComponent(HealthComponent.class);
            if(oldHealthComponent != null){
                MaximumHealthComponent oldMaximumHealthComponent = entityWrapper.getComponent(MaximumHealthComponent.class);
                if(oldMaximumHealthComponent != null){
                    float healthDifference = (maximumHealth - oldMaximumHealthComponent.getValue());
                    if(healthDifference > 0){
                        entityWrapper.setComponent(new HealthComponent(oldHealthComponent.getValue() + healthDifference));
                    }
                }
            }
            else if(entityWrapper.hasComponent(IsAliveComponent.class)){
                entityWrapper.setComponent(new HealthComponent(maximumHealth));
            }
            entityWrapper.setComponent(new MaximumHealthComponent(maximumHealth));
            entityWrapper.setComponent(new HealthRegenerationComponent(healthRegeneration));
            entityWrapper.setComponent(new AttackDamageComponent(attackDamage));
            entityWrapper.setComponent(new AbilityPowerComponent(abilityPower));
            entityWrapper.setComponent(new AttackSpeedComponent(attackSpeed));
            AutoAttackComponent autoAttackComponent = entityWrapper.getComponent(AutoAttackComponent.class);
            if(autoAttackComponent != null){
                float autoAttackInterval = (1 / attackSpeed);
                entityWorld.setComponent(autoAttackComponent.getAutoAttackEntity(), new CooldownComponent(autoAttackInterval));
                float autoAttackDuration = (float) (Math.min(attackSpeed / 1.2f, 1) * autoAttackInterval);
                CastAnimationComponent autoAttackCastAnimationComponent = entityWorld.getComponent(autoAttackComponent.getAutoAttackEntity(), CastAnimationComponent.class);
                if(autoAttackCastAnimationComponent != null){
                    entityWorld.setComponent(autoAttackCastAnimationComponent.getAnimationEntity(), new LoopDurationComponent(autoAttackDuration));
                }
                InstantEffectTriggersComponent instantEffectTriggersComponent = entityWorld.getComponent(autoAttackComponent.getAutoAttackEntity(), InstantEffectTriggersComponent.class);
                if(instantEffectTriggersComponent != null){
                    int[] instantEffectTriggerEntities = instantEffectTriggersComponent.getEffectTriggerEntities();
                    for(int effectTriggerEntity : instantEffectTriggerEntities){
                        entityWorld.setComponent(effectTriggerEntity, new TriggerDelayComponent(autoAttackDuration / 2));
                    }
                }
                entityWorld.setComponent(autoAttackComponent.getAutoAttackEntity(), new CastDurationComponent(autoAttackDuration));
            }
            entityWrapper.setComponent(new CooldownSpeedComponent(cooldownSpeed));
            SpellsComponent spellsComponent = entityWrapper.getComponent(SpellsComponent.class);
            if(spellsComponent != null){
                for(int spellEntity : spellsComponent.getSpellsEntities()){
                    BaseCooldownComponent baseCooldownComponent = entityWorld.getComponent(spellEntity, BaseCooldownComponent.class);
                    if(baseCooldownComponent != null){
                        float cooldownDuration = (baseCooldownComponent.getDuration() / cooldownSpeed);
                        entityWorld.setComponent(spellEntity, new CooldownComponent(cooldownDuration));
                    }
                }
            }
            entityWrapper.setComponent(new ArmorComponent(armor));
            entityWrapper.setComponent(new MagicResistanceComponent(magicResistance));
            entityWrapper.setComponent(new WalkSpeedComponent(walkSpeed));
            entityWrapper.setComponent(new CriticalChanceComponent(criticalChance));
            entityWrapper.setComponent(new LifestealComponent(lifesteal));
            entityWrapper.setComponent(new DamageReductionComponent(damageReduction));
            entityWrapper.removeComponent(RequestUpdateAttributesComponent.class);
        }
    }
    
    private void addAttributeBonus(EntityWorld entityWorld, AttributeBonus attributeBonus, int bonusAttributesEntity){
        EntityWrapper bonusEntityWrapper = entityWorld.getWrapped(bonusAttributesEntity);
        BonusFlatMaximumHealthComponent bonusFlatMaximumHealthComponent = bonusEntityWrapper.getComponent(BonusFlatMaximumHealthComponent.class);
        if(bonusFlatMaximumHealthComponent != null){
            attributeBonus.addFlatMaximumHealth(bonusFlatMaximumHealthComponent.getValue());
        }
        BonusFlatHealthRegenerationComponent bonusFlatHealthRegenerationComponen = bonusEntityWrapper.getComponent(BonusFlatHealthRegenerationComponent.class);
        if(bonusFlatHealthRegenerationComponen != null){
            attributeBonus.addFlatHealthRegeneration(bonusFlatHealthRegenerationComponen.getValue());
        }
        BonusFlatAttackDamageComponent bonusFlatAttackDamageComponent = bonusEntityWrapper.getComponent(BonusFlatAttackDamageComponent.class);
        if(bonusFlatAttackDamageComponent != null){
            attributeBonus.addFlatAttackDamage(bonusFlatAttackDamageComponent.getValue());
        }
        BonusFlatAbilityPowerComponent bonusFlatAbilityPowerComponent = bonusEntityWrapper.getComponent(BonusFlatAbilityPowerComponent.class);
        if(bonusFlatAbilityPowerComponent != null){
            attributeBonus.addFlatAbilityPower(bonusFlatAbilityPowerComponent.getValue());
        }
        BonusFlatAttackSpeedComponent bonusFlatAttackSpeedComponent = bonusEntityWrapper.getComponent(BonusFlatAttackSpeedComponent.class);
        if(bonusFlatAttackSpeedComponent != null){
            attributeBonus.addFlatAttackSpeed(bonusFlatAttackSpeedComponent.getValue());
        }
        BonusPercentageAttackSpeedComponent bonusPercentageAttackSpeedComponent = bonusEntityWrapper.getComponent(BonusPercentageAttackSpeedComponent.class);
        if(bonusPercentageAttackSpeedComponent != null){
            attributeBonus.addPercentageAttackSpeed(bonusPercentageAttackSpeedComponent.getValue());
        }
        BonusPercentageCooldownSpeedComponent bonusPercentageCooldownSpeedComponent = bonusEntityWrapper.getComponent(BonusPercentageCooldownSpeedComponent.class);
        if(bonusPercentageCooldownSpeedComponent != null){
            attributeBonus.addPercentageCooldownSpeed(bonusPercentageCooldownSpeedComponent.getValue());
        }
        BonusFlatArmorComponent bonusFlatArmorComponent = bonusEntityWrapper.getComponent(BonusFlatArmorComponent.class);
        if(bonusFlatArmorComponent != null){
            attributeBonus.addFlatArmor(bonusFlatArmorComponent.getValue());
        }
        BonusFlatMagicResistanceComponent bonusFlatMagicResistanceComponent = bonusEntityWrapper.getComponent(BonusFlatMagicResistanceComponent.class);
        if(bonusFlatMagicResistanceComponent != null){
            attributeBonus.addFlatMagicResistance(bonusFlatMagicResistanceComponent.getValue());
        }
        BonusFlatWalkSpeedComponent bonusFlatWalkSpeedComponent = bonusEntityWrapper.getComponent(BonusFlatWalkSpeedComponent.class);
        if(bonusFlatWalkSpeedComponent != null){
            attributeBonus.addFlatWalkSpeed(bonusFlatWalkSpeedComponent.getValue());
        }
        BonusPercentageWalkSpeedComponent bonusPercentageWalkSpeedComponent = bonusEntityWrapper.getComponent(BonusPercentageWalkSpeedComponent.class);
        if(bonusPercentageWalkSpeedComponent != null){
            attributeBonus.multiplicatePercentageWalkSpeed(bonusPercentageWalkSpeedComponent.getValue());
        }
        BonusPercentageCriticalChanceComponent bonusPercentageCriticalChanceComponent = bonusEntityWrapper.getComponent(BonusPercentageCriticalChanceComponent.class);
        if(bonusPercentageCriticalChanceComponent != null){
            attributeBonus.addPercentageCriticalChance(bonusPercentageCriticalChanceComponent.getValue());
        }
        BonusPercentageLifestealComponent bonusPercentageLifestealComponent = bonusEntityWrapper.getComponent(BonusPercentageLifestealComponent.class);
        if(bonusPercentageLifestealComponent != null){
            attributeBonus.addPercentageLifesteal(bonusPercentageLifestealComponent.getValue());
        }
        BonusPercentageDamageReductionComponent bonusPercentageDamageReductionComponent = bonusEntityWrapper.getComponent(BonusPercentageDamageReductionComponent.class);
        if(bonusPercentageDamageReductionComponent != null){
            attributeBonus.addPercentageDamageReduction(bonusPercentageDamageReductionComponent.getValue());
        }
    }
}
