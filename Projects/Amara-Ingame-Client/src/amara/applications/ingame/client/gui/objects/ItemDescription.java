/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.gui.objects;

import amara.applications.ingame.client.gui.GUIUtil;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.core.Util;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class ItemDescription{
    
    public static String generate_NameAndDescription(EntityWorld entityWorld, int itemEntity){
        return generate_NameAndDescription(entityWorld, itemEntity, -1);
    }
    
    public static String generate_NameAndDescription(EntityWorld entityWorld, int itemEntity, int lineBreakLength){
        String description = "";
        NameComponent nameComponent = entityWorld.getComponent(itemEntity, NameComponent.class);
        if(nameComponent != null){
            description += nameComponent.getName();
            description = addNewLine(description);
        }
        description += generate_Description(entityWorld, itemEntity, lineBreakLength);
        return description;
    }
    
    public static String generate_Description(EntityWorld entityWorld, int itemEntity){
        return generate_Description(entityWorld, itemEntity, -1);
    }
    
    public static String generate_Description(EntityWorld entityWorld, int itemEntity, int lineBreakLength){
        String description = "";
        BonusFlatWalkSpeedComponent bonusFlatWalkSpeedComponent = entityWorld.getComponent(itemEntity, BonusFlatWalkSpeedComponent.class);
        if(bonusFlatWalkSpeedComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusFlatWalkSpeedComponent.getValue()) + " Walk Speed";
        }
        BonusPercentageWalkSpeedComponent bonusPercentageWalkSpeedComponent = entityWorld.getComponent(itemEntity, BonusPercentageWalkSpeedComponent.class);
        if(bonusPercentageWalkSpeedComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusPercentageWalkSpeedComponent.getValue() * 100) + "% Walk Speed";
        }
        BonusFlatMaximumHealthComponent bonusFlatMaximumHealthComponent = entityWorld.getComponent(itemEntity, BonusFlatMaximumHealthComponent.class);
        if(bonusFlatMaximumHealthComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusFlatMaximumHealthComponent.getValue()) + " Health";
        }
        BonusFlatHealthRegenerationComponent bonusFlatHealthRegenerationComponent = entityWorld.getComponent(itemEntity, BonusFlatHealthRegenerationComponent.class);
        if(bonusFlatHealthRegenerationComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusFlatHealthRegenerationComponent.getValue()) + " Health Regeneration (per Second)";
        }
        BonusFlatAttackDamageComponent bonusFlatAttackDamageComponent = entityWorld.getComponent(itemEntity, BonusFlatAttackDamageComponent.class);
        if(bonusFlatAttackDamageComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusFlatAttackDamageComponent.getValue()) + " Attack Damage";
        }
        BonusPercentageAttackSpeedComponent bonusPercentageAttackSpeedComponent = entityWorld.getComponent(itemEntity, BonusPercentageAttackSpeedComponent.class);
        if(bonusPercentageAttackSpeedComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusPercentageAttackSpeedComponent.getValue() * 100) + "% Attack Speed";
        }
        BonusPercentageCriticalChanceComponent bonusPercentageCriticalChanceComponent = entityWorld.getComponent(itemEntity, BonusPercentageCriticalChanceComponent.class);
        if(bonusPercentageCriticalChanceComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusPercentageCriticalChanceComponent.getValue() * 100) + "% Critical Chance";
        }
        BonusPercentageLifestealComponent bonusPercentageLifestealComponent = entityWorld.getComponent(itemEntity, BonusPercentageLifestealComponent.class);
        if(bonusPercentageLifestealComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusPercentageLifestealComponent.getValue() * 100) + "% Lifesteal";
        }
        BonusFlatAbilityPowerComponent bonusFlatAbilityPowerComponent = entityWorld.getComponent(itemEntity, BonusFlatAbilityPowerComponent.class);
        if(bonusFlatAbilityPowerComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusFlatAbilityPowerComponent.getValue()) + " Ability Power";
        }
        BonusPercentageCooldownSpeedComponent bonusPercentageCooldownSpeedComponent = entityWorld.getComponent(itemEntity, BonusPercentageCooldownSpeedComponent.class);
        if(bonusPercentageCooldownSpeedComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusPercentageCooldownSpeedComponent.getValue() * 100) + "% Cooldown Speed";
        }
        BonusFlatArmorComponent bonusFlatArmorComponent = entityWorld.getComponent(itemEntity, BonusFlatArmorComponent.class);
        if(bonusFlatArmorComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusFlatArmorComponent.getValue()) + " Armor";
        }
        BonusFlatMagicResistanceComponent bonusFlatMagicResistanceComponent = entityWorld.getComponent(itemEntity, BonusFlatMagicResistanceComponent.class);
        if(bonusFlatMagicResistanceComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusFlatMagicResistanceComponent.getValue()) + " Magic Resistance";
        }
        BonusPercentageIncomingDamageAmplificationComponent bonusPercentageIncomingDamageAmplificationComponent = entityWorld.getComponent(itemEntity, BonusPercentageIncomingDamageAmplificationComponent.class);
        if(bonusPercentageIncomingDamageAmplificationComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusPercentageIncomingDamageAmplificationComponent.getValue() * 100) + "% Incoming Damage Amplification";
        }
        BonusPercentageOutgoingDamageAmplificationComponent bonusPercentageOutgoingDamageAmplificationComponent = entityWorld.getComponent(itemEntity, BonusPercentageOutgoingDamageAmplificationComponent.class);
        if(bonusPercentageOutgoingDamageAmplificationComponent != null){
            description = addSeperator(description);
            description += GUIUtil.getValueText_Signed(bonusPercentageOutgoingDamageAmplificationComponent.getValue() * 100) + "% Outgoing Damage Amplification";
        }
        ItemPassivesComponent itemPassivesComponent = entityWorld.getComponent(itemEntity, ItemPassivesComponent.class);
        if(itemPassivesComponent != null){
            for(int passiveEntity : itemPassivesComponent.getPassiveEntities()){
                DescriptionComponent descriptionComponent = entityWorld.getComponent(passiveEntity, DescriptionComponent.class);
                if(descriptionComponent != null){
                    description = addNewLine(description);
                    if(entityWorld.hasComponent(passiveEntity, UniqueComponent.class)){
                        description += "UNIQUE ";
                    }
                    description += "Passive";
                    NameComponent nameComponent = entityWorld.getComponent(passiveEntity, NameComponent.class);
                    if(nameComponent !=  null){
                        description += " - " + nameComponent.getName();
                    }
                    description += ": " + descriptionComponent.getDescription();
                }
            }
        }
        ItemActiveComponent itemActiveComponent = entityWorld.getComponent(itemEntity, ItemActiveComponent.class);
        if(itemActiveComponent != null){
            int spellEntity = itemActiveComponent.getSpellEntity();
            DescriptionComponent descriptionComponent = entityWorld.getComponent(spellEntity, DescriptionComponent.class);
            if(descriptionComponent != null){
                description = addNewLine(description);
                if(entityWorld.hasComponent(spellEntity, UniqueComponent.class)){
                    description += "UNIQUE ";
                }
                if(itemActiveComponent.isConsumable()){
                    description += "Consumable";
                }
                else{
                    description += "Active";
                }
                NameComponent nameComponent = entityWorld.getComponent(spellEntity, NameComponent.class);
                if(nameComponent !=  null){
                    description += " - " + nameComponent.getName();
                }
                description += ": " + descriptionComponent.getDescription();
                BaseCooldownComponent baseCooldownComponent = entityWorld.getComponent(spellEntity, BaseCooldownComponent.class);
                if(baseCooldownComponent != null){
                    description += " (" + GUIUtil.getValueText(baseCooldownComponent.getDuration()) + " seconds cooldown)";
                }
            }
        }
        if(lineBreakLength != -1){
            description = Util.lineBreakText(description, lineBreakLength);
        }
        return description;
    }
    
    private static String addSeperator(String description){
        if(description.length() > 0){
            description += ", ";
        }
        return description;
    }
    
    private static String addNewLine(String description){
        if(description.length() > 0){
            description += "\n";
        }
        return description;
    }
}
