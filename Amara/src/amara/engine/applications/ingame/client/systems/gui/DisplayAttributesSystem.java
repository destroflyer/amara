/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.gui;

import amara.engine.applications.ingame.client.gui.ScreenController_HUD;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.players.SelectedUnitComponent;

/**
 *
 * @author Carl
 */
public class DisplayAttributesSystem implements EntitySystem{

    public DisplayAttributesSystem(int playerEntity, ScreenController_HUD screenController_HUD){
        this.playerEntity = playerEntity;
        this.screenController_HUD = screenController_HUD;
    }
    private final static String NON_EXISTING_ATTRIBUTE_TEXT = "-";
    private int playerEntity;
    private ScreenController_HUD screenController_HUD;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        SelectedUnitComponent selectedUnitComponent = entityWorld.getComponent(playerEntity, SelectedUnitComponent.class);
        if(selectedUnitComponent != null){
            int selectedEntity = selectedUnitComponent.getEntityID();
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
