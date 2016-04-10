/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.gui;

import com.jme3.math.Vector2f;
import com.jme3.texture.Texture2D;
import amara.applications.ingame.client.appstates.SendPlayerCommandsAppState;
import amara.applications.ingame.client.gui.objects.SpellInformation;
import amara.applications.ingame.shared.maps.MapMinimapInformation;
import amara.core.Util;
import amara.libraries.applications.display.appstates.NiftyAppState;
import amara.libraries.applications.display.gui.GameScreenController;
import amara.libraries.applications.display.ingame.appstates.*;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;
import de.lessvoid.nifty.effects.Effect;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.effects.impl.Hint;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.tools.SizeValue;

/**
 *
 * @author Carl
 */
public class ScreenController_HUD extends GameScreenController{

    public ScreenController_HUD(){
        super("start");
    }
    private int currentUpgradeSpellIndex = -1;
    private SpellInformation[] spellInformations_Passives = new SpellInformation[0];
    private SpellInformation[] spellInformations_Spells = new SpellInformation[0];
    private SpellInformation[] spellInformations_MapSpells = new SpellInformation[0];
    //Show/Hide the information by saving the actions and checking them in an update loop
    //(Since NiftyGUI sometimes seems to be ordering start/end effect methods wrong)
    private SpellInformation action_ShowSpellInformation;
    private boolean action_HideSpellInformation;
    private int deathRecapPageID;
    
    @Override
    public void onStartup(){
        super.onStartup();
        setExperience(0);
        hidePassiveCooldown();
        for(int i=0;i<4;i++){
            hideSpellCooldown(i);
        }
        for(int i=0;i<6;i++){
            hideInventoryItem_Description(i);
            hideItemCooldown(i);
        }
        for(int i=0;i<2;i++){
            hideMapSpellCooldown(i);
        }
        setUpgradeSpellsLayerVisible(false);
        hideUpgradeSpell();
    }

    @Override
    protected void initialize(){
        super.initialize();
        generateScoreboard();
    }
    
    public void setPlayerName(String name){
        getTextRenderer("player_name").setText(name);
    }
    
    public void setLevel(int level){
        getTextRenderer("level").setText("" + level);
    }
    
    public void setExperience(float portion){
        int width = (int) (portion * 257);
        Element experienceBar = getElementByID("experience_bar");
        experienceBar.setConstraintWidth(new SizeValue(width + "px"));
        experienceBar.getParent().layoutElements();
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
    
    public void setAttributeValue_CooldownSpeed(String text){
        setAttributeValue("cooldown_speed", text);
    }
    
    public void setAttributeValue_Armor(String text){
        setAttributeValue("armor", text);
    }
    
    public void setAttributeValue_MagicResistance(String text){
        setAttributeValue("magic_resistance", text);
    }
    
    public void setAttributeValue_WalkSpeed(String text){
        setAttributeValue("walk_speed", text);
    }
    
    private void setAttributeValue(String attributeName, String text){
        getTextRenderer("attribute_value_" + attributeName).setText(text);
    }
    
    public void setResourceBarWidth_Health(float portion){
        setResourceBarWidth("health", portion);
    }
    
    private void setResourceBarWidth(String resourceName, float portion){
        Element resourceBar = getElementByID("resource_bar_" + resourceName);
        resourceBar.setConstraintWidth(new SizeValue((portion * 100) + "%"));
        resourceBar.getParent().layoutElements();
    }
    
    public void setResourceBarText_Health(String text){
        getTextRenderer("resource_bar_health_text").setText(text);
    }
    
    public void setPassiveImage(String imagePath){
        getImageRenderer("passive_image").setImage(createImage(imagePath));
    }
    
    public void showPassiveCooldown(float remainingTime){
        showCooldown("passive", 0, remainingTime);
    }
    
    public void hidePassiveCooldown(){
        hideCooldown("passive", 0);
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
    
    public void setInventoryItem_Image(int index, String imagePath){
        getImageRenderer("inventory_item_" + index + "_image").setImage(createImage(imagePath));
    }
    
    public void showInventoryItem_Description(int index, String description){
        showHintText("inventory_item_" + index + "_image", description);
    }
    
    public void hideInventoryItem_Description(int index){
        hideHintText("inventory_item_" + index + "_image");
    }
    
    public void showItemCooldown(int index, float remainingTime){
        showCooldown("inventory_item", index, remainingTime);
    }
    
    public void hideItemCooldown(int index){
        hideCooldown("inventory_item", index);
    }
    
    public void setMapSpellImage(int index, String imagePath){
        getImageRenderer("map_spell_" + index + "_image").setImage(createImage(imagePath));
    }
    
    public void showMapSpellCooldown(int index, float remainingTime){
        showCooldown("map_spell", index, remainingTime);
    }
    
    public void hideMapSpellCooldown(int index){
        hideCooldown("map_spell", index);
    }
    
    private void showCooldown(String prefix, int index, float remainingTime){
        getElementByID(prefix + "_" + index + "_cooldown").show();
        getTextRenderer(prefix + "_" + index + "_cooldown_time").setText("" + Util.round(remainingTime, 1));
    }
    
    private void hideCooldown(String prefix, int index){
        getElementByID(prefix + "_" + index + "_cooldown").hide();
    }
    
    public void setGold(float gold){
        getTextRenderer("gold").setText("" + ((int) gold));
    }
    
    private final int maximumPingBarWidth = 69;
    private final int maximumDisplayedPing = 400;
    public void setPing(int ping){
        int width = (int) ((((float) ping) / maximumDisplayedPing) * maximumPingBarWidth);
        if(width > maximumPingBarWidth){
            width = maximumPingBarWidth;
        }
        Element pingBar = getElementByID("ping_bar");
        boolean shouldBeVisible = (width > 0);
        if(pingBar.isVisible() != shouldBeVisible){
            pingBar.setVisible(shouldBeVisible);
        }
        pingBar.setConstraintWidth(new SizeValue(width + "px"));
        getElementByID("ping_bar_margin_right").setConstraintWidth(new SizeValue(((maximumPingBarWidth - width) + 32) + "px"));
        pingBar.getParent().layoutElements();
        Effect hoverEffect = getElementByID("ping_container").getEffects(EffectEventId.onHover, Hint.class).get(0);
        hoverEffect.getParameters().setProperty("hintText", "Ping: " + ping + " ms");
    }
    
    public void setDeathLayersVisible(boolean isVisible){
        getElementByID("death_recap_layer").setVisible(isVisible);
    }
    
    public void toggleDisplayDeathRecap(){
        setDeathRecapVisible(!getElementByID("death_recap_" + deathRecapPageID).isVisible());
    }
    
    public void setDeathRecapVisible(boolean isVisible){
        getElementByID("death_recap_" + deathRecapPageID).setVisible(isVisible);
    }
    
    public void setDeathRecapText(final String text){
        Element deathRecapScrollPanel = getElementByID("death_recap_" + deathRecapPageID);
        if(deathRecapScrollPanel != null){
            deathRecapScrollPanel.markForRemoval();
        }
        deathRecapPageID++;
        Element deathRecapContainer = getElementByID("death_recap_container");
        new ScrollPanelBuilder("death_recap_" + deathRecapPageID){{
            set("width", "100%");
            set("height", "250px");
            set("horizontal", "false");
            set("style", "nifty-listbox");
            set("visible","true");
            
            panel(new PanelBuilder(){{
                childLayoutCenter();
                padding("10px");
                
                text(new TextBuilder("death_recap_text"){{
                    width("100%");
                    text(text);
                    textHAlignLeft();
                    font("Interface/fonts/Verdana_14.fnt");
                }});
            }});
        }}.build(nifty, nifty.getCurrentScreen(), deathRecapContainer);
    }
    
    public void toggleShopVisible(){
        NiftyAppState niftyAppState = mainApplication.getStateManager().getState(NiftyAppState.class);
        niftyAppState.getScreenController(ScreenController_Shop.class).toggleShopVisible();
    }

    public void setSpellInformations_Passives(SpellInformation[] spellInformations_Passives){
        this.spellInformations_Passives = spellInformations_Passives;
    }

    public void setSpellInformations_Spells(SpellInformation[] spellInformations_Spells){
        this.spellInformations_Spells = spellInformations_Spells;
    }

    public void setSpellInformations_MapSpells(SpellInformation[] spellInformations_MapSpells){
        this.spellInformations_MapSpells = spellInformations_MapSpells;
    }
    
    public void showSpellInformation_Passive(String indexText){
        int index = Integer.parseInt(indexText);
        if(index < spellInformations_Passives.length){
            action_ShowSpellInformation = spellInformations_Passives[index];
        }
    }
    
    public void showSpellInformation_Spell(String indexText){
        int index = Integer.parseInt(indexText);
        if(index < spellInformations_Spells.length){
            action_ShowSpellInformation = spellInformations_Spells[index];
        }
    }
    
    public void showSpellInformation_MapSpell(String indexText){
        int index = Integer.parseInt(indexText);
        if(index < spellInformations_MapSpells.length){
            action_ShowSpellInformation = spellInformations_MapSpells[index];
        }
    }
    
    public void hideSpellInformation(){
        action_HideSpellInformation = true;
    }
    
    public void checkAction_SpellInformation(){
        if(action_ShowSpellInformation != null){
            showSpellInformation(action_ShowSpellInformation);
            action_ShowSpellInformation = null;
            action_HideSpellInformation = false;
        }
        else if(action_HideSpellInformation){
            getElementByID("spell_information_layer").setVisible(false);
            action_HideSpellInformation = false;
        }
    }
    
    private void showSpellInformation(SpellInformation spellInformation){
        getElementByID("spell_information_layer").setVisible(true);
        getTextRenderer("spell_information_name").setText(spellInformation.getName());
        getTextRenderer("spell_information_description").setText(spellInformation.getDescription());
        boolean hasCooldown = (spellInformation.getCooldown() != -1);
        getElementByID("spell_information_cooldown").setVisible(hasCooldown);
        if(hasCooldown){
            getTextRenderer("spell_information_cooldown").setText(GUIUtil.getValueText(spellInformation.getCooldown()) + "s");
        }
    }
    
    public void setUpgradeSpellsLayerVisible(boolean isVisible){
        getElementByID("upgrade_spells_layer").setVisible(isVisible);
    }
    
    public void setUpgradeSpellsButtonVisible(int spellIndex, boolean isVisible){
        getElementByID("upgrade_spells_button_" + spellIndex).setVisible(isVisible);
    }
    
    public void setUpgradeSpellsButtonImage(int spellIndex, String imagePath){
        getImageRenderer("upgrade_spells_button_" + spellIndex).setImage(createImage(imagePath));
    }
    
    public void learnOrUpgradeSpell(String spellIndexString){
        int spellIndex = Integer.parseInt(spellIndexString);
        SendPlayerCommandsAppState sendPlayerCommandsAppState = mainApplication.getStateManager().getState(SendPlayerCommandsAppState.class);
        sendPlayerCommandsAppState.learnOrUpgradeSpell(spellIndex);
    }
    
    public void showUpgradeSpell(int spellIndex){
        currentUpgradeSpellIndex = spellIndex;
        getElementByID("upgrade_spell_layer_container").setVisible(true);
        getElementByID("upgrade_spell_layer_images").setVisible(true);
        int offset = (spellIndex * 60);
        getElementByID("upgrade_spell_offset_container").setConstraintWidth(new SizeValue(offset + "px"));
        getElementByID("upgrade_spell_offset_container").getParent().layoutElements();
        getElementByID("upgrade_spell_offset_images").setConstraintWidth(new SizeValue((7 + offset) + "px"));
        getElementByID("upgrade_spell_offset_images").getParent().layoutElements();
    }
    
    public void setSpellUpgradeImage(int upgradeIndex, String imagePath){
        getImageRenderer("upgrade_spell_image_" + upgradeIndex).setImage(createImage(imagePath));
    }
    
    public void upgradeSpell(String upgradeIndexString){
        int upgradeIndex = Integer.parseInt(upgradeIndexString);
        SendPlayerCommandsAppState sendPlayerCommandsAppState = mainApplication.getStateManager().getState(SendPlayerCommandsAppState.class);
        sendPlayerCommandsAppState.upgradeSpell(currentUpgradeSpellIndex, upgradeIndex);
        hideUpgradeSpell();
    }
    
    private void hideUpgradeSpell(){
        getElementByID("upgrade_spell_layer_container").setVisible(false);
        getElementByID("upgrade_spell_layer_images").setVisible(false);
    }
    
    private void generateScoreboard(){
        new PanelBuilder(){{
            childLayoutVertical();
            
            for(int i=0;i<8;i++){
                final int playerIndex = i;
                panel(new PanelBuilder("scoreboard_player_" + playerIndex){{
                    childLayoutHorizontal();
                    height("30px");
                    
                    text(new TextBuilder("scoreboard_player_" + playerIndex + "_name"){{
                        width("200px");
                        height("30px");
                        textHAlignLeft();
                        font("Interface/fonts/Verdana_14.fnt");
                        text("Player #" + (playerIndex + 1));
                    }});
                    text(new TextBuilder("scoreboard_player_" + playerIndex + "_kda"){{
                        width("40px");
                        height("30px");
                        textHAlignCenter();
                        font("Interface/fonts/Verdana_14.fnt");
                        text("K/D/A");
                    }});
                    panel(new PanelBuilder(){{
                        width("20px");
                    }});
                    text(new TextBuilder("scoreboard_player_" + playerIndex + "_creepscore"){{
                        width("40px");
                        height("30px");
                        textHAlignCenter();
                        font("Interface/fonts/Verdana_14.fnt");
                        text("CS");
                    }});
                    panel(new PanelBuilder(){{
                        width("20px");
                    }});
                    for(int i=0;i<6;i++){
                        image(new ImageBuilder("scoreboard_player_" + playerIndex + "_item_" + i + "_image"){{
                            width("30px");
                            height("30px");
                            filename("Interface/hud/items/unknown.png");
                            
                            onHoverEffect(new HoverEffectBuilder("hint"){{
                                effectParameter("hintText", "?");
                            }});
                        }});
                    }
                    panel(new PanelBuilder(){{
                        width("*");
                    }});
                }});
                panel(new PanelBuilder(){{
                    height("5px");
                }});
            }
            panel(new PanelBuilder(){{
                height("*");
            }});
        }}.build(nifty, nifty.getCurrentScreen(), getElementByID("scoreboard_content"));
    }
    
    public void toggleScoreboardVisible(){
        setScoreboardVisible(!getElementByID("scoreboard_layer").isVisible());
    }
    
    public void setScoreboardVisible(boolean isVisible){
        Element scoreboardLayer = getElementByID("scoreboard_layer");
        scoreboardLayer.setVisible(isVisible);
    }
    
    public void setScoreboard_PlayerName(int playerIndex, String name){
        getTextRenderer("scoreboard_player_" + playerIndex + "_name").setText(name);
    }
    
    public void setScoreboard_KDA(int playerIndex, int kills, int deaths, int assists){
        setScoreboard_Score(playerIndex, "kda", (kills + "/" + deaths + "/" + assists));
    }
    
    public void setScoreboard_CreepScore(int playerIndex, int kills){
        setScoreboard_Score(playerIndex, "creepscore", kills);
    }
    
    private void setScoreboard_Score(int playerIndex, String suffix, float value){
        setScoreboard_Score(playerIndex, suffix, GUIUtil.getValueText(value));
    }
    
    private void setScoreboard_Score(int playerIndex, String suffix, String text){
        getTextRenderer("scoreboard_player_" + playerIndex + "_" + suffix).setText(text);
    }
    
    public void setScoreboard_InventoryItem_Image(int playerIndex, int itemIndex, String imagePath){
        getImageRenderer("scoreboard_player_" + playerIndex + "_item_" + itemIndex + "_image").setImage(createImage(imagePath));
    }
    
    public void showScoreboard_InventoryItem_Description(int playerIndex, int itemIndex, String description){
        showHintText("scoreboard_player_" + playerIndex + "_item_" + itemIndex + "_image", description);
    }
    
    public void hideScoreboard_InventoryItem_Description(int playerIndex, int itemIndex){
        hideHintText("scoreboard_player_" + playerIndex + "_item_" + itemIndex + "_image");
    }
    
    public void setStats_TeamsScore(int team1Kills, int team2Kills){
        getTextRenderer("stats_teams_score").setText(team1Kills + " : " + team2Kills);
    }
    
    public void setStats_PlayerScore_KDA(int kills, int deaths, int assists){
        getTextRenderer("stats_player_score_kda").setText(kills + " / " + deaths + " / " + assists);
    }
    
    public void setStats_PlayerScore_CreepScore(int kills){
        getTextRenderer("stats_player_score_creepscore").setText("" + kills);
    }
    
    public void setGameTime(float time){
        getTextRenderer("game_time").setText(GUIUtil.getFormattedTime(time));
    }
    
    public void setMinimapImage(Texture2D texture2D){
        getImageRenderer("minimap").setImage(createImage(texture2D));
    }
    
    private Vector2f tmpMapLocation = new Vector2f();
    public void lookAtMinimapMouseLocation(int mouseX, int mouseY){
        Element minimap = getElementByID("minimap");
        float portionX  = Math.max(0, Math.min(1 - (((float) (mouseX - minimap.getX())) / minimap.getWidth()), 1));
        float portionY  = Math.max(0, Math.min(1 - (((float) (mouseY - minimap.getY())) / minimap.getHeight()), 1));
        MapMinimapInformation minimapInformation = mainApplication.getStateManager().getState(MapAppState.class).getMap().getMinimapInformation();
        tmpMapLocation.setX(minimapInformation.getX() + (portionX * minimapInformation.getWidth()));
        tmpMapLocation.setY(minimapInformation.getY() + (portionY * minimapInformation.getHeight()));
        mainApplication.getStateManager().getState(IngameCameraAppState.class).lookAt(tmpMapLocation);
    }
}
