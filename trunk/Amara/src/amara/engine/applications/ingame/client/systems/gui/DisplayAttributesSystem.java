/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.Util;
import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;

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
    protected void update(EntityWorld entityWorld, float deltaSeconds, int selectedEntity){
        String healthText = NON_EXISTING_ATTRIBUTE_TEXT;
        String attackDamageText = NON_EXISTING_ATTRIBUTE_TEXT;
        String abilityPowerText = NON_EXISTING_ATTRIBUTE_TEXT;
        String attackSpeedText = NON_EXISTING_ATTRIBUTE_TEXT;
        String walkSpeedText = NON_EXISTING_ATTRIBUTE_TEXT;
        if(entityWorld.hasComponent(selectedEntity, HealthComponent.class)){
            healthText = ("" + (int) entityWorld.getComponent(selectedEntity, HealthComponent.class).getValue());
        }
        if(entityWorld.hasComponent(selectedEntity, AttackDamageComponent.class)){
            attackDamageText = ("" + (int) entityWorld.getComponent(selectedEntity, AttackDamageComponent.class).getValue());
        }
        if(entityWorld.hasComponent(selectedEntity, AbilityPowerComponent.class)){
            abilityPowerText = ("" + (int) entityWorld.getComponent(selectedEntity, AbilityPowerComponent.class).getValue());
        }
        if(entityWorld.hasComponent(selectedEntity, AttackSpeedComponent.class)){
            attackSpeedText = ("" + Util.round(entityWorld.getComponent(selectedEntity, AttackSpeedComponent.class).getValue(), 2));
        }
        if(entityWorld.hasComponent(selectedEntity, WalkSpeedComponent.class)){
            walkSpeedText = ("" + Util.round(entityWorld.getComponent(selectedEntity, WalkSpeedComponent.class).getValue(), 2));
        }
        screenController_HUD.setAttributeValue_Health(healthText);
        screenController_HUD.setAttributeValue_AttackDamage(attackDamageText);
        screenController_HUD.setAttributeValue_AbilityPower(abilityPowerText);
        screenController_HUD.setAttributeValue_AttackSpeed(attackSpeedText);
        screenController_HUD.setAttributeValue_WalkSpeed(walkSpeedText);
    }
}
