/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.core.Util;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class DisplayAttributesSystem extends GUIDisplaySystem{

    public DisplayAttributesSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        super(playerEntity, screenController_HUD);
    }
    private final static String NON_EXISTING_ATTRIBUTE_TEXT = "-";
    
    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        String healthText = NON_EXISTING_ATTRIBUTE_TEXT;
        String attackDamageText = NON_EXISTING_ATTRIBUTE_TEXT;
        String abilityPowerText = NON_EXISTING_ATTRIBUTE_TEXT;
        String attackSpeedText = NON_EXISTING_ATTRIBUTE_TEXT;
        String cooldownSpeedText = NON_EXISTING_ATTRIBUTE_TEXT;
        String armorText = NON_EXISTING_ATTRIBUTE_TEXT;
        String magicResistanceText = NON_EXISTING_ATTRIBUTE_TEXT;
        String walkSpeedText = NON_EXISTING_ATTRIBUTE_TEXT;
        float healthPortion = 0;
        if(entityWorld.hasComponent(characterEntity, HealthComponent.class) && entityWorld.hasComponent(characterEntity, MaximumHealthComponent.class)){
            float health = entityWorld.getComponent(characterEntity, HealthComponent.class).getValue();
            float maximumHealth = entityWorld.getComponent(characterEntity, MaximumHealthComponent.class).getValue();
            healthText = (((int) health) + " / " + ((int) maximumHealth));
            healthPortion = (health / maximumHealth);
        }
        if(entityWorld.hasComponent(characterEntity, AttackDamageComponent.class)){
            attackDamageText = ("" + (int) entityWorld.getComponent(characterEntity, AttackDamageComponent.class).getValue());
        }
        if(entityWorld.hasComponent(characterEntity, AbilityPowerComponent.class)){
            abilityPowerText = ("" + (int) entityWorld.getComponent(characterEntity, AbilityPowerComponent.class).getValue());
        }
        if(entityWorld.hasComponent(characterEntity, AttackSpeedComponent.class)){
            attackSpeedText = ("" + Util.round(entityWorld.getComponent(characterEntity, AttackSpeedComponent.class).getValue(), 2));
        }
        if(entityWorld.hasComponent(characterEntity, CooldownSpeedComponent.class)){
            cooldownSpeedText = ("" + Util.round(entityWorld.getComponent(characterEntity, CooldownSpeedComponent.class).getValue(), 2));
        }
        if(entityWorld.hasComponent(characterEntity, ArmorComponent.class)){
            armorText = ("" + (int) entityWorld.getComponent(characterEntity, ArmorComponent.class).getValue());
        }
        if(entityWorld.hasComponent(characterEntity, MagicResistanceComponent.class)){
            magicResistanceText = ("" + (int) entityWorld.getComponent(characterEntity, MagicResistanceComponent.class).getValue());
        }
        if(entityWorld.hasComponent(characterEntity, WalkSpeedComponent.class)){
            walkSpeedText = ("" + Util.round(entityWorld.getComponent(characterEntity, WalkSpeedComponent.class).getValue(), 2));
        }
        screenController_HUD.setAttributeValue_AttackDamage(attackDamageText);
        screenController_HUD.setAttributeValue_AbilityPower(abilityPowerText);
        screenController_HUD.setAttributeValue_AttackSpeed(attackSpeedText);
        screenController_HUD.setAttributeValue_CooldownSpeed(cooldownSpeedText);
        screenController_HUD.setAttributeValue_Armor(armorText);
        screenController_HUD.setAttributeValue_MagicResistance(magicResistanceText);
        screenController_HUD.setAttributeValue_WalkSpeed(walkSpeedText);
        screenController_HUD.setResourceBarWidth_Health(healthPortion);
        screenController_HUD.setResourceBarText_Health(healthText);
    }
}
