/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.gui;

import amara.engine.client.gui.ScreenController_HUD;
import amara.engine.client.systems.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;

/**
 *
 * @author Carl
 */
public class DisplayAttributesSystem implements EntitySystem{

    public DisplayAttributesSystem(PlayerInformationSystem playerInformationSystem, ScreenController_HUD screenController_HUD){
        this.playerInformationSystem = playerInformationSystem;
        this.screenController_HUD = screenController_HUD;
    }
    private final static String NON_EXISTING_ATTRIBUTE_TEXT = "-";
    private PlayerInformationSystem playerInformationSystem;
    private ScreenController_HUD screenController_HUD;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        int selectedEntity = playerInformationSystem.getSelectedEntity();
        if(selectedEntity != -1){
            String healthText = NON_EXISTING_ATTRIBUTE_TEXT;
            String attackDamageText = NON_EXISTING_ATTRIBUTE_TEXT;
            String abilityPowerText = NON_EXISTING_ATTRIBUTE_TEXT;
            String attackSpeedText = NON_EXISTING_ATTRIBUTE_TEXT;
            String movementSpeedText = NON_EXISTING_ATTRIBUTE_TEXT;
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
                attackSpeedText = ("" + entityWorld.getComponent(selectedEntity, AttackSpeedComponent.class).getValue());
            }
            screenController_HUD.setAttributeValue_Health(healthText);
            screenController_HUD.setAttributeValue_AttackDamage(attackDamageText);
            screenController_HUD.setAttributeValue_AbilityPower(abilityPowerText);
            screenController_HUD.setAttributeValue_AttackSpeed(attackSpeedText);
            screenController_HUD.setAttributeValue_MovementSpeed(movementSpeedText);
        }
    }
}
