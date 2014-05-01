/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui;

import amara.Util;
import amara.engine.gui.GameScreenController;

/**
 *
 * @author Carl
 */
public class ScreenController_HUD extends GameScreenController{

    @Override
    public void onStartup(){
        super.onStartup();
        for(int i=0;i<4;i++){
            hideSpellCooldown(i);
        }
    }
    
    public void setPlayerName(String name){
        getTextRenderer("player_name").setText(name);
    }
    
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
    
    public void setAttributeValue_WalkSpeed(String text){
        setAttributeValue("walk_speed", text);
    }
    
    private void setAttributeValue(String attributeName, String text){
        getTextRenderer("attribute_value_" + attributeName).setText(text);
    }
    
    public void setInventoryItemImage(int index, String imagePath){
        getImageRenderer("inventory_item_" + index + "_image").setImage(createImage(imagePath));
    }
    
    public void setSpellImage(int index, String imagePath){
        getImageRenderer("spell_" + index + "_image").setImage(createImage(imagePath));
    }
    
    public void showSpellCooldown(int index, float remainingTime){
        getElementByID("spell_" + index + "_cooldown").show();
        getTextRenderer("spell_" + index + "_cooldown_time").setText("" + Util.round(remainingTime, 1));
    }
    
    public void hideSpellCooldown(int index){
        getElementByID("spell_" + index + "_cooldown").hide();
    }
}
