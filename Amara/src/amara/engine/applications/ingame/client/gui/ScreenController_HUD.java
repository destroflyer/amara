/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui;

import amara.Util;
import amara.engine.gui.GameScreenController;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.effects.Effect;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.effects.impl.Hint;
import de.lessvoid.nifty.elements.Element;

/**
 *
 * @author Carl
 */
public class ScreenController_HUD extends GameScreenController{

    public ScreenController_HUD(){
        super("start");
    }
    
    @Override
    public void onStartup(){
        super.onStartup();
        for(int i=0;i<4;i++){
            hideSpellCooldown(i);
        }
        for(int i=0;i<6;i++){
            hideItemCooldown(i);
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
    
    public void setSpellImage(int index, String imagePath){
        getImageRenderer("spell_" + index + "_image").setImage(createImage(imagePath));
    }
    
    public void showSpellCooldown(int index, float remainingTime){
        showCooldown("spell", index, remainingTime);
    }
    
    public void hideSpellCooldown(int index){
        hideCooldown("spell", index);
    }
    
    public void setInventoryItemImage(int index, String imagePath){
        getImageRenderer("inventory_item_" + index + "_image").setImage(createImage(imagePath));
    }
    
    public void showItemCooldown(int index, float remainingTime){
        showCooldown("inventory_item", index, remainingTime);
    }
    
    public void hideItemCooldown(int index){
        hideCooldown("inventory_item", index);
    }
    
    private void showCooldown(String prefix, int index, float remainingTime){
        getElementByID(prefix + "_" + index + "_cooldown").show();
        getTextRenderer(prefix + "_" + index + "_cooldown_time").setText("" + Util.round(remainingTime, 1));
    }
    
    private void hideCooldown(String prefix, int index){
        getElementByID(prefix + "_" + index + "_cooldown").hide();
    }
    
    public void setGold(int gold){
        getTextRenderer("gold").setText("" + gold);
    }
    
    public void setPing(int ping){
        int maximumWidth = 69;
        int maximumDisplayedPing = 400;
        int width = (int) ((((float) ping) / maximumDisplayedPing) * maximumWidth);
        if(width > maximumWidth){
            width = maximumWidth;
        }
        Element pingBar = getElementByID("ping_bar");
        pingBar.setWidth(width);
        boolean shouldBeVisible = (width > 0);
        if(pingBar.isVisible() != shouldBeVisible){
            pingBar.setVisible(shouldBeVisible);
        }
        Effect hoverEffect = getElementByID("ping_container").getEffects(EffectEventId.onHover, Hint.class).get(0);
        hoverEffect.getParameters().setProperty("hintText", "Ping: " + ping + " ms");
    }
    
    public void setDeathLayersVisible(boolean isVisible){
        getElementByID("death_recap_layer").setVisible(isVisible);
    }
    
    public void toggleDisplayDeathRecap(){
        setDeathRecapVisible(!getElementByID("death_recap").isVisible());
    }
    
    public void setDeathRecapVisible(boolean isVisible){
        getElementByID("death_recap").setVisible(isVisible);
    }
    
    public void setDeathRecapText(String text){
        Element textContainer = getElementByID("death_recap_text_container");
        removeAllChildElements(textContainer);
        new LabelBuilder("death-recap_text", text){{
            width("100%");
            textHAlignLeft();
            font("Interface/fonts/Verdana_14.fnt");
        }}.build(nifty, nifty.getCurrentScreen(), textContainer);
    }
}
