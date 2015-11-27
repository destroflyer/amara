/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui.objects;

import amara.Util;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.items.*;
import amara.game.entitysystem.components.spells.*;

/**
 *
 * @author Carl
 */
public class ItemRecipe{

    public ItemRecipe(EntityWorld entityWorld, int entity, int gold, ItemRecipe[] ingredientsRecipes, int depth){
        this.entityWorld = entityWorld;
        this.entity = entity;
        this.gold = gold;
        this.ingredientsRecipes = ingredientsRecipes;
        this.depth = depth;
        generateDescription();
        updateTotalGold();
    }
    private EntityWorld entityWorld;
    private int entity;
    private int gold;
    private ItemRecipe[] ingredientsRecipes;
    private int depth;
    private String description;
    private int totalGold;
    private int resolvedGold = -1;

    public int getEntity(){
        return entity;
    }

    public int getGold(){
        return gold;
    }

    public ItemRecipe[] getIngredients(){
        return ingredientsRecipes;
    }

    public int getDepth(){
        return depth;
    }
    
    private void generateDescription(){
        description = "";
        BonusFlatWalkSpeedComponent bonusFlatWalkSpeedComponent = entityWorld.getComponent(entity, BonusFlatWalkSpeedComponent.class);
        if(bonusFlatWalkSpeedComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusFlatWalkSpeedComponent.getValue()) + " Walk Speed";
        }
        BonusPercentageWalkSpeedComponent bonusPercentageWalkSpeedComponent = entityWorld.getComponent(entity, BonusPercentageWalkSpeedComponent.class);
        if(bonusPercentageWalkSpeedComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusPercentageWalkSpeedComponent.getValue() * 100) + "% Walk Speed";
        }
        BonusFlatMaximumHealthComponent bonusFlatMaximumHealthComponent = entityWorld.getComponent(entity, BonusFlatMaximumHealthComponent.class);
        if(bonusFlatMaximumHealthComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusFlatMaximumHealthComponent.getValue()) + " Health";
        }
        BonusFlatHealthRegenerationComponent bonusFlatHealthRegenerationComponent = entityWorld.getComponent(entity, BonusFlatHealthRegenerationComponent.class);
        if(bonusFlatHealthRegenerationComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusFlatHealthRegenerationComponent.getValue()) + " Health Regeneration (per Second)";
        }
        BonusFlatAttackDamageComponent bonusFlatAttackDamageComponent = entityWorld.getComponent(entity, BonusFlatAttackDamageComponent.class);
        if(bonusFlatAttackDamageComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusFlatAttackDamageComponent.getValue()) + " Attack Damage";
        }
        BonusPercentageAttackSpeedComponent bonusPercentageAttackSpeedComponent = entityWorld.getComponent(entity, BonusPercentageAttackSpeedComponent.class);
        if(bonusPercentageAttackSpeedComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusPercentageAttackSpeedComponent.getValue() * 100) + "% Attack Speed";
        }
        BonusPercentageCriticalChanceComponent bonusPercentageCriticalChanceComponent = entityWorld.getComponent(entity, BonusPercentageCriticalChanceComponent.class);
        if(bonusPercentageCriticalChanceComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusPercentageCriticalChanceComponent.getValue() * 100) + "% Critical Chance";
        }
        BonusPercentageLifestealComponent bonusPercentageLifestealComponent = entityWorld.getComponent(entity, BonusPercentageLifestealComponent.class);
        if(bonusPercentageLifestealComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusPercentageLifestealComponent.getValue() * 100) + "% Lifesteal";
        }
        BonusFlatAbilityPowerComponent bonusFlatAbilityPowerComponent = entityWorld.getComponent(entity, BonusFlatAbilityPowerComponent.class);
        if(bonusFlatAbilityPowerComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusFlatAbilityPowerComponent.getValue()) + " Ability Power";
        }
        BonusPercentageCooldownSpeedComponent bonusPercentageCooldownSpeedComponent = entityWorld.getComponent(entity, BonusPercentageCooldownSpeedComponent.class);
        if(bonusPercentageCooldownSpeedComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusPercentageCooldownSpeedComponent.getValue()) + " Cooldown Speed";
        }
        BonusFlatArmorComponent bonusFlatArmorComponent = entityWorld.getComponent(entity, BonusFlatArmorComponent.class);
        if(bonusFlatArmorComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusFlatArmorComponent.getValue()) + " Armor";
        }
        BonusFlatMagicResistanceComponent bonusFlatMagicResistanceComponent = entityWorld.getComponent(entity, BonusFlatMagicResistanceComponent.class);
        if(bonusFlatMagicResistanceComponent != null){
            addDescription_Seperator();
            description += getValueText_Signed(bonusFlatMagicResistanceComponent.getValue()) + " Magic Resistance";
        }
        ItemPassivesComponent itemPassivesComponent = entityWorld.getComponent(entity, ItemPassivesComponent.class);
        if(itemPassivesComponent != null){
            for(int itemPassiveEntity : itemPassivesComponent.getPassiveEntities()){
                DescriptionComponent descriptionComponent = entityWorld.getComponent(itemPassiveEntity, DescriptionComponent.class);
                if(descriptionComponent != null){
                    addDescription_NewLine();
                    description += "Passive: " + descriptionComponent.getDescription();
                }
            }
        }
        ItemActiveComponent itemActiveComponent = entityWorld.getComponent(entity, ItemActiveComponent.class);
        if(itemActiveComponent != null){
            DescriptionComponent descriptionComponent = entityWorld.getComponent(itemActiveComponent.getSpellEntity(), DescriptionComponent.class);
            if(descriptionComponent != null){
                addDescription_NewLine();
                description += "Active: " + descriptionComponent.getDescription();
                BaseCooldownComponent baseCooldownComponent = entityWorld.getComponent(itemActiveComponent.getSpellEntity(), BaseCooldownComponent.class);
                if(baseCooldownComponent != null){
                    description += " (" + getValueText(baseCooldownComponent.getDuration()) + " seconds cooldown)";
                }
            }
        }
    }
    
    private void addDescription_Seperator(){
        if(description.length() > 0){
            description += ", ";
        }
    }
    
    private void addDescription_NewLine(){
        if(description.length() > 0){
            description += "\n";
        }
    }
    
    private String getValueText_Signed(float value){
        value = Util.compensateFloatRoundingErrors(value);
        String signText = "";
        if(value >= 0){
            signText += "+";
        }
        return (signText + getValueText(value));
    }
    
    private String getValueText(float value){
        int valueInt = (int) value;
        if(value == valueInt){
            return ("" + valueInt);
        }
        else{
            return ("" + value);
        }
    }

    public String getDescription(){
        return description;
    }
    
    private void updateTotalGold(){
        totalGold = gold;
        for(ItemRecipe ingredientRecipe : ingredientsRecipes){
            ingredientRecipe.updateTotalGold();
            totalGold += ingredientRecipe.getTotalGold();
        }
    }

    public int getTotalGold(){
        return totalGold;
    }
    
    public void updateResolvedGold(ItemRecipe[] inventoryItemsRecipes){
        resolvedGold = resolveGold(inventoryItemsRecipes, new boolean[inventoryItemsRecipes.length]);
    }
    
    private int resolveGold(ItemRecipe[] inventoryItemsRecipes, boolean[] usedInventoryIngredients){
        int neededGold = gold;
        for(ItemRecipe ingredientRecipe : ingredientsRecipes){
            boolean ingrendientHasToBeBought = true;
            for(int r=0;r<inventoryItemsRecipes.length;r++){
                if((!usedInventoryIngredients[r]) && (inventoryItemsRecipes[r] == ingredientRecipe)){
                    ingrendientHasToBeBought = false;
                    usedInventoryIngredients[r] = true;
                    break;
                }
            }
            if(ingrendientHasToBeBought){
                neededGold += ingredientRecipe.resolveGold(inventoryItemsRecipes, usedInventoryIngredients);
            }
        }
        return neededGold;
    }

    public int getResolvedGold(){
        return resolvedGold;
    }
}
