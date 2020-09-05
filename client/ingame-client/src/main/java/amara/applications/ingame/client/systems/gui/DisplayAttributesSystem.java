/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.appstates.PlayerAppState;
import amara.applications.ingame.client.gui.ScreenController_HUD;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.core.Util;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class DisplayAttributesSystem extends GUIDisplaySystem<ScreenController_HUD> {

    public DisplayAttributesSystem(PlayerAppState playerAppState, ScreenController_HUD screenController_HUD){
        super(playerAppState, screenController_HUD);
    }
    private final static String NON_EXISTING_ATTRIBUTE_TEXT = "-";
    
    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity) {
        updateAttributes(entityWorld, "player", characterEntity);
        updateAttributes(entityWorld, "inspection", getInspectedEntity());
    }

    private void updateAttributes(EntityWorld entityWorld, String uiPrefix, int entity) {
        String healthText = NON_EXISTING_ATTRIBUTE_TEXT;
        String attackDamageText = NON_EXISTING_ATTRIBUTE_TEXT;
        String abilityPowerText = NON_EXISTING_ATTRIBUTE_TEXT;
        String attackSpeedText = NON_EXISTING_ATTRIBUTE_TEXT;
        String cooldownSpeedText = NON_EXISTING_ATTRIBUTE_TEXT;
        String armorText = NON_EXISTING_ATTRIBUTE_TEXT;
        String magicResistanceText = NON_EXISTING_ATTRIBUTE_TEXT;
        String walkSpeedText = NON_EXISTING_ATTRIBUTE_TEXT;
        float healthPortion = 0;
        if (entityWorld.hasComponent(entity, HealthComponent.class) && entityWorld.hasComponent(entity, MaximumHealthComponent.class)) {
            float health = entityWorld.getComponent(entity, HealthComponent.class).getValue();
            float maximumHealth = entityWorld.getComponent(entity, MaximumHealthComponent.class).getValue();
            healthText = (((int) health) + " / " + ((int) maximumHealth));
            healthPortion = (health / maximumHealth);
        }
        if (entityWorld.hasComponent(entity, AttackDamageComponent.class) ){
            attackDamageText = ("" + (int) entityWorld.getComponent(entity, AttackDamageComponent.class).getValue());
        }
        if (entityWorld.hasComponent(entity, AbilityPowerComponent.class)) {
            abilityPowerText = ("" + (int) entityWorld.getComponent(entity, AbilityPowerComponent.class).getValue());
        }
        if (entityWorld.hasComponent(entity, AttackSpeedComponent.class)) {
            attackSpeedText = ("" + Util.round(entityWorld.getComponent(entity, AttackSpeedComponent.class).getValue(), 2));
        }
        if (entityWorld.hasComponent(entity, CooldownSpeedComponent.class)) {
            cooldownSpeedText = ("" + Util.round(entityWorld.getComponent(entity, CooldownSpeedComponent.class).getValue(), 2));
        }
        if (entityWorld.hasComponent(entity, ArmorComponent.class)) {
            armorText = ("" + (int) entityWorld.getComponent(entity, ArmorComponent.class).getValue());
        }
        if (entityWorld.hasComponent(entity, MagicResistanceComponent.class)) {
            magicResistanceText = ("" + (int) entityWorld.getComponent(entity, MagicResistanceComponent.class).getValue());
        }
        if (entityWorld.hasComponent(entity, WalkSpeedComponent.class)) {
            walkSpeedText = ("" + Util.round(entityWorld.getComponent(entity, WalkSpeedComponent.class).getValue(), 2));
        }
        screenController.setAttributeValue_AttackDamage(uiPrefix, attackDamageText);
        screenController.setAttributeValue_AbilityPower(uiPrefix, abilityPowerText);
        screenController.setAttributeValue_AttackSpeed(uiPrefix, attackSpeedText);
        screenController.setAttributeValue_CooldownSpeed(uiPrefix, cooldownSpeedText);
        screenController.setAttributeValue_Armor(uiPrefix, armorText);
        screenController.setAttributeValue_MagicResistance(uiPrefix, magicResistanceText);
        screenController.setAttributeValue_WalkSpeed(uiPrefix, walkSpeedText);
        if ("player".equals(uiPrefix)) {
            screenController.setPlayer_ResourceBarWidth_Health(healthPortion);
            screenController.setPlayer_ResourceBarText_Health(healthText);
        }
    }
}
