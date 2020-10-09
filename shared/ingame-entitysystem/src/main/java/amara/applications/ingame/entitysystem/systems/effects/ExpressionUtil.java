package amara.applications.ingame.entitysystem.systems.effects;

import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.EntityWorld;
import amara.libraries.expressions.ExpressionSpace;
import amara.libraries.expressions.values.*;

import java.util.Map;

public class ExpressionUtil{

    private static final String SEPARATOR = ".";

    public static void setEntityValues(EntityWorld entityWorld, ExpressionSpace expressionSpace, String name, int entity){
        String fieldName;
        // MaximumHealth
        MaximumHealthComponent maximumHealthComponent = entityWorld.getComponent(entity, MaximumHealthComponent.class);
        fieldName = (name + SEPARATOR + "maximumHealth");
        if(maximumHealthComponent != null){
            expressionSpace.setValue(fieldName, new NumericValue(maximumHealthComponent.getValue()));
        } else{
            expressionSpace.unsetValue(fieldName);
        }
        // Health
        HealthComponent healthComponent = entityWorld.getComponent(entity, HealthComponent.class);
        fieldName = (name + SEPARATOR + "health");
        if(healthComponent != null){
            expressionSpace.setValue(fieldName, new NumericValue(healthComponent.getValue()));
        } else{
            expressionSpace.unsetValue(fieldName);
        }
        // MaximumMana
        MaximumManaComponent maximumManaComponent = entityWorld.getComponent(entity, MaximumManaComponent.class);
        fieldName = (name + SEPARATOR + "maximumMana");
        if(maximumManaComponent != null){
            expressionSpace.setValue(fieldName, new NumericValue(maximumManaComponent.getValue()));
        } else{
            expressionSpace.unsetValue(fieldName);
        }
        // Mana
        ManaComponent manaComponent = entityWorld.getComponent(entity, ManaComponent.class);
        fieldName = (name + SEPARATOR + "mana");
        if(manaComponent != null){
            expressionSpace.setValue(fieldName, new NumericValue(manaComponent.getValue()));
        } else{
            expressionSpace.unsetValue(fieldName);
        }
        // AttackDamage
        AttackDamageComponent attackDamageComponent = entityWorld.getComponent(entity, AttackDamageComponent.class);
        fieldName = (name + SEPARATOR + "attackDamage");
        if(attackDamageComponent != null){
            expressionSpace.setValue(fieldName, new NumericValue(attackDamageComponent.getValue()));
        } else{
            expressionSpace.unsetValue(fieldName);
        }
        // AttackSpeed
        AttackSpeedComponent attackSpeedComponent = entityWorld.getComponent(entity, AttackSpeedComponent.class);
        fieldName = (name + SEPARATOR + "attackSpeed");
        if(attackSpeedComponent != null){
            expressionSpace.setValue(fieldName, new NumericValue(attackSpeedComponent.getValue()));
        } else{
            expressionSpace.unsetValue(fieldName);
        }
        // AbilityPower
        AbilityPowerComponent abilityPowerComponent = entityWorld.getComponent(entity, AbilityPowerComponent.class);
        fieldName = (name + SEPARATOR + "abilityPower");
        if(abilityPowerComponent != null){
            expressionSpace.setValue(fieldName, new NumericValue(abilityPowerComponent.getValue()));
        } else{
            expressionSpace.unsetValue(fieldName);
        }
        // CooldownSpeed
        CooldownSpeedComponent cooldownSpeedComponent = entityWorld.getComponent(entity, CooldownSpeedComponent.class);
        fieldName = (name + SEPARATOR + "cooldownSpeed");
        if(cooldownSpeedComponent != null){
            expressionSpace.setValue(fieldName, new NumericValue(cooldownSpeedComponent.getValue()));
        } else{
            expressionSpace.unsetValue(fieldName);
        }
        // Armor
        ArmorComponent armorComponent = entityWorld.getComponent(entity, ArmorComponent.class);
        fieldName = (name + SEPARATOR + "armor");
        if(armorComponent != null){
            expressionSpace.setValue(fieldName, new NumericValue(armorComponent.getValue()));
        } else{
            expressionSpace.unsetValue(fieldName);
        }
        // MagicResistance
        MagicResistanceComponent magicResistanceComponent = entityWorld.getComponent(entity, MagicResistanceComponent.class);
        fieldName = (name + SEPARATOR + "magicResistance");
        if(magicResistanceComponent != null){
            expressionSpace.setValue(fieldName, new NumericValue(magicResistanceComponent.getValue()));
        } else{
            expressionSpace.unsetValue(fieldName);
        }
        // ShopGoldExpenses
        ShopGoldExpensesComponent shopGoldExpensesComponent = entityWorld.getComponent(entity, ShopGoldExpensesComponent.class);
        fieldName = (name + SEPARATOR + "shopGoldExpenses");
        if(shopGoldExpensesComponent != null){
            for (Map.Entry<String, Float> entry : shopGoldExpensesComponent.getGoldPerCategory().entrySet()) {
                String category = entry.getKey();
                Float gold = entry.getValue();
                expressionSpace.setValue(fieldName + "_" + category, new NumericValue(gold));
            }
        } else{
            expressionSpace.unsetValue(fieldName);
        }
    }
}
