package amara.applications.ingame.entitysystem.systems.attributes;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.buffs.*;
import amara.applications.ingame.entitysystem.components.buffs.status.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.effecttriggers.*;
import amara.applications.ingame.entitysystem.components.visuals.animations.*;
import amara.libraries.entitysystem.*;
import com.jme3.math.FastMath;

public class UpdateAttributesSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper entityWrapper : entityWorld.getWrapped(entityWorld.getEntitiesWithAny(RequestUpdateAttributesComponent.class))){
            AttributeBonus attributeBonus = new AttributeBonus();
            BaseAttributesComponent baseAttributesComponent = entityWrapper.getComponent(BaseAttributesComponent.class);
            if(baseAttributesComponent != null){
                addAttributeBonus(entityWorld, attributeBonus, baseAttributesComponent.getBonusAttributesEntity(), 1);
            }
            AttributesPerLevelComponent attributesPerLevelComponent = entityWrapper.getComponent(AttributesPerLevelComponent.class);
            if(attributesPerLevelComponent != null){
                LevelComponent levelComponent = entityWrapper.getComponent(LevelComponent.class);
                if(levelComponent != null){
                    float attributesPerLevelFactor;
                    if (attributesPerLevelComponent.getExponentialBase() != 0) {
                        attributesPerLevelFactor = FastMath.pow(attributesPerLevelComponent.getExponentialBase(), levelComponent.getLevel() - 1);
                    } else {
                        attributesPerLevelFactor = levelComponent.getLevel();
                    }
                    addAttributeBonus(entityWorld, attributeBonus, attributesPerLevelComponent.getBonusAttributesEntity(), attributesPerLevelFactor);
                }
            }
            InventoryComponent inventoryComponent = entityWrapper.getComponent(InventoryComponent.class);
            if(inventoryComponent != null){
                int[] itemEntities = inventoryComponent.getItemEntities();
                for (int itemEntity : itemEntities) {
                    addAttributeBonus(entityWorld, attributeBonus, itemEntity, 1);
                }
            }
            BuffsComponent buffsComponent = entityWrapper.getComponent(BuffsComponent.class);
            if (buffsComponent != null) {
                for (int buffStatusEntity : buffsComponent.getBuffStatusEntities()) {
                    int buffEntity = entityWorld.getComponent(buffStatusEntity, ActiveBuffComponent.class).getBuffEntity();
                    ContinuousAttributesComponent continuousAttributesComponent = entityWorld.getComponent(buffEntity, ContinuousAttributesComponent.class);
                    if (continuousAttributesComponent != null) {
                        addAttributeBonus(entityWorld, attributeBonus, continuousAttributesComponent.getBonusAttributesEntity(), 1);
                    }
                    ContinuousAttributesPerStackComponent continuousAttributesPerStackComponent = entityWorld.getComponent(buffEntity, ContinuousAttributesPerStackComponent.class);
                    if (continuousAttributesPerStackComponent != null) {
                        StacksComponent stacksComponent = entityWorld.getComponent(buffStatusEntity, StacksComponent.class);
                        if ((stacksComponent != null) && (stacksComponent.getStacks() > 0)) {
                            addAttributeBonus(entityWorld, attributeBonus, continuousAttributesPerStackComponent.getBonusAttributesEntity(), stacksComponent.getStacks());
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
            float incomingDamageAmplification = Math.max(-1, attributeBonus.getPercentageIncomingDamageAmplification());
            float outgoingDamageAmplification = Math.max(-1, attributeBonus.getPercentageOutgoingDamageAmplification());
            float goldPerSecond = attributeBonus.getFlatGoldPerSecond();
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
                float autoAttackDuration = Math.min(attackSpeed / 1.2f, 1) * autoAttackInterval;
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
            entityWrapper.setComponent(new IncomingDamageAmplificationComponent(incomingDamageAmplification));
            entityWrapper.setComponent(new OutgoingDamageAmplificationComponent(outgoingDamageAmplification));
            entityWrapper.setComponent(new GoldPerSecondComponent(goldPerSecond));
            entityWrapper.removeComponent(RequestUpdateAttributesComponent.class);
        }
    }
    
    private void addAttributeBonus(EntityWorld entityWorld, AttributeBonus attributeBonus, int bonusAttributesEntity, float factor){
        EntityWrapper bonusEntityWrapper = entityWorld.getWrapped(bonusAttributesEntity);
        BonusFlatMaximumHealthComponent bonusFlatMaximumHealthComponent = bonusEntityWrapper.getComponent(BonusFlatMaximumHealthComponent.class);
        if(bonusFlatMaximumHealthComponent != null){
            attributeBonus.addFlatMaximumHealth(bonusFlatMaximumHealthComponent.getValue() * factor);
        }
        BonusFlatHealthRegenerationComponent bonusFlatHealthRegenerationComponent = bonusEntityWrapper.getComponent(BonusFlatHealthRegenerationComponent.class);
        if(bonusFlatHealthRegenerationComponent != null){
            attributeBonus.addFlatHealthRegeneration(bonusFlatHealthRegenerationComponent.getValue() * factor);
        }
        BonusFlatAttackDamageComponent bonusFlatAttackDamageComponent = bonusEntityWrapper.getComponent(BonusFlatAttackDamageComponent.class);
        if(bonusFlatAttackDamageComponent != null){
            attributeBonus.addFlatAttackDamage(bonusFlatAttackDamageComponent.getValue() * factor);
        }
        BonusFlatAbilityPowerComponent bonusFlatAbilityPowerComponent = bonusEntityWrapper.getComponent(BonusFlatAbilityPowerComponent.class);
        if(bonusFlatAbilityPowerComponent != null){
            attributeBonus.addFlatAbilityPower(bonusFlatAbilityPowerComponent.getValue() * factor);
        }
        BonusFlatAttackSpeedComponent bonusFlatAttackSpeedComponent = bonusEntityWrapper.getComponent(BonusFlatAttackSpeedComponent.class);
        if(bonusFlatAttackSpeedComponent != null){
            attributeBonus.addFlatAttackSpeed(bonusFlatAttackSpeedComponent.getValue() * factor);
        }
        BonusPercentageAttackSpeedComponent bonusPercentageAttackSpeedComponent = bonusEntityWrapper.getComponent(BonusPercentageAttackSpeedComponent.class);
        if(bonusPercentageAttackSpeedComponent != null){
            attributeBonus.addPercentageAttackSpeed(bonusPercentageAttackSpeedComponent.getValue() * factor);
        }
        BonusPercentageCooldownSpeedComponent bonusPercentageCooldownSpeedComponent = bonusEntityWrapper.getComponent(BonusPercentageCooldownSpeedComponent.class);
        if(bonusPercentageCooldownSpeedComponent != null){
            attributeBonus.addPercentageCooldownSpeed(bonusPercentageCooldownSpeedComponent.getValue() * factor);
        }
        BonusFlatArmorComponent bonusFlatArmorComponent = bonusEntityWrapper.getComponent(BonusFlatArmorComponent.class);
        if(bonusFlatArmorComponent != null){
            attributeBonus.addFlatArmor(bonusFlatArmorComponent.getValue() * factor);
        }
        BonusFlatMagicResistanceComponent bonusFlatMagicResistanceComponent = bonusEntityWrapper.getComponent(BonusFlatMagicResistanceComponent.class);
        if(bonusFlatMagicResistanceComponent != null){
            attributeBonus.addFlatMagicResistance(bonusFlatMagicResistanceComponent.getValue() * factor);
        }
        BonusFlatWalkSpeedComponent bonusFlatWalkSpeedComponent = bonusEntityWrapper.getComponent(BonusFlatWalkSpeedComponent.class);
        if(bonusFlatWalkSpeedComponent != null){
            attributeBonus.addFlatWalkSpeed(bonusFlatWalkSpeedComponent.getValue() * factor);
        }
        BonusPercentageWalkSpeedComponent bonusPercentageWalkSpeedComponent = bonusEntityWrapper.getComponent(BonusPercentageWalkSpeedComponent.class);
        if(bonusPercentageWalkSpeedComponent != null){
            attributeBonus.multiplicatePercentageWalkSpeed(bonusPercentageWalkSpeedComponent.getValue() * factor);
        }
        BonusPercentageCriticalChanceComponent bonusPercentageCriticalChanceComponent = bonusEntityWrapper.getComponent(BonusPercentageCriticalChanceComponent.class);
        if(bonusPercentageCriticalChanceComponent != null){
            attributeBonus.addPercentageCriticalChance(bonusPercentageCriticalChanceComponent.getValue() * factor);
        }
        BonusPercentageLifestealComponent bonusPercentageLifestealComponent = bonusEntityWrapper.getComponent(BonusPercentageLifestealComponent.class);
        if(bonusPercentageLifestealComponent != null){
            attributeBonus.addPercentageLifesteal(bonusPercentageLifestealComponent.getValue() * factor);
        }
        BonusPercentageIncomingDamageAmplificationComponent bonusPercentageIncomingDamageAmplificationComponent = bonusEntityWrapper.getComponent(BonusPercentageIncomingDamageAmplificationComponent.class);
        if(bonusPercentageIncomingDamageAmplificationComponent != null){
            attributeBonus.addPercentageIncomingDamageAmplification(bonusPercentageIncomingDamageAmplificationComponent.getValue() * factor);
        }
        BonusPercentageOutgoingDamageAmplificationComponent bonusPercentageOutgoingDamageAmplificationComponent = bonusEntityWrapper.getComponent(BonusPercentageOutgoingDamageAmplificationComponent.class);
        if(bonusPercentageOutgoingDamageAmplificationComponent != null){
            attributeBonus.addPercentageOutgoingDamageAmplification(bonusPercentageOutgoingDamageAmplificationComponent.getValue() * factor);
        }
        BonusFlatGoldPerSecondComponent bonusFlatGoldPerSecondComponent = bonusEntityWrapper.getComponent(BonusFlatGoldPerSecondComponent.class);
        if(bonusFlatGoldPerSecondComponent != null){
            attributeBonus.addFlatGoldPerSecond(bonusFlatGoldPerSecondComponent.getValue() * factor);
        }
        if(bonusEntityWrapper.hasComponent(DisableGoldPerSecondComponent.class)){
            attributeBonus.setGoldPerSecondDisabled(true);
        }
    }
}
