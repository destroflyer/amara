/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.gui;

/**
 *
 * @author Carl
 */
public class ScreenController_HUD extends GameScreenController{
    
    public void setAttributeValue_Health(String text){
        setAttributeValue("health", text);
    }
    
    public void setAttributeValue_AttackDamage(String text){
        setAttributeValue("attack_damage", text);
    }
    
    public void setAttributeValue_AbilityPower(String text){
        setAttributeValue("ability_power", text);
    }
    
    public void setAttributeValue_AttackSpeed(String text){
        setAttributeValue("attack_speed", text);
    }
    
    public void setAttributeValue_MovementSpeed(String text){
        setAttributeValue("movement_speed", text);
    }
    
    private void setAttributeValue(String attributeName, String text){
        getTextRenderer("attribute_value_" + attributeName).setText(text);
    }
    
    public void setInventoryItemImage(int index, String imagePath){
        getImageRenderer("inventory_item_" + index + "_image").setImage(createImage(imagePath));
    }
}
