/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.gui;

import com.jme3.math.Vector2f;
import com.jme3.texture.Texture2D;
import amara.applications.ingame.client.appstates.*;
import amara.applications.ingame.client.gui.objects.SpellInformation;
import amara.applications.ingame.shared.maps.MapMinimapInformation;
import amara.applications.master.network.messages.objects.GameSelectionPlayer;
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
public class ScreenController_HUD extends GameScreenController {

    public ScreenController_HUD(){
        super("start");
    }
    private int currentUpgradeSpellIndex = -1;
    private SpellInformation[] spellInformations_Passives = new SpellInformation[0];
    private SpellInformation[] spellInformations_LearnableSpells = new SpellInformation[0];
    private SpellInformation[] spellInformations_Spells = new SpellInformation[0];
    private SpellInformation[] spellInformations_MapSpells = new SpellInformation[0];
    //Show/Hide the information by saving the actions and checking them in an update loop
    //(Since NiftyGUI sometimes seems to be ordering start/end effect methods wrong)
    private SpellInformation action_ShowSpellInformation;
    private boolean action_HideSpellInformation;
    private int deathRecapPageID;

    @Override
    public void onStartup() {
        super.onStartup();
        hidePlayerAnnouncement();
        setInspectionVisible(false);
        hideLevel("player");
        setExperience(0);
        hidePlayer_PassiveCooldown();
        for (int i = 0; i < 4; i++) {
            hidePlayer_SpellCooldown(i);
        }
        for (int i = 0; i < 6; i++) {
            hideInventoryItem_Description("player", i);
            hidePlayer_ItemCooldown(i);
        }
        for (int i = 0; i < 2; i++) {
            hidePlayer_MapSpellCooldown(i);
        }
        setPlayer_UpgradeSpellsLayerVisible(false);
        hidePlayer_UpgradeSpell();
    }

    public void hidePlayerAnnouncement(){
        getElementByID("player_announcement").hide();
    }

    public void showPlayerAnnouncement(String text){
        getElementByID("player_announcement").show();
        getTextRenderer("player_announcement_text").setText(text);
    }

    public void setInspectionVisible(boolean isVisible) {
        getElementByID("inspection_attributes").setVisible(isVisible);
        getElementByID("inspection_inventory").setVisible(isVisible);
        getElementByID("inspection_inventory_background").setVisible(isVisible);
        getElementByID("inspection_background").setVisible(isVisible);
        getElementByID("inspection_level_layer").setVisible(isVisible);
    }

    public void setName(String prefix, String name){
        getTextRenderer(prefix + "_name").setText(name);
    }

    public void hideLevel(String prefix){
        setLevel(prefix, "");
    }

    public void setLevel(String prefix, int level){
        setLevel(prefix, "" + level);
    }

    private void setLevel(String prefix, String text){
        getTextRenderer(prefix + "_level").setText(text);
    }

    public void setExperience(float portion){
        int width = (int) (portion * 257);
        Element experienceBar = getElementByID("player_experience_bar");
        experienceBar.setConstraintWidth(new SizeValue(width + "px"));
        experienceBar.getParent().layoutElements();
    }

    public void setAttributeValue_Health(String prefix, String text){
        setAttributeValue(prefix, "health", text);
    }
    
    public void setAttributeValue_AttackDamage(String prefix, String text){
        setAttributeValue(prefix, "attack_damage", text);
    }

    public void setAttributeValue_AbilityPower(String prefix, String text){
        setAttributeValue(prefix, "ability_power", text);
    }

    public void setAttributeValue_AttackSpeed(String prefix, String text){
        setAttributeValue(prefix, "attack_speed", text);
    }

    public void setAttributeValue_CooldownSpeed(String prefix, String text){
        setAttributeValue(prefix, "cooldown_speed", text);
    }

    public void setAttributeValue_Armor(String prefix, String text){
        setAttributeValue(prefix, "armor", text);
    }

    public void setAttributeValue_MagicResistance(String prefix, String text){
        setAttributeValue(prefix, "magic_resistance", text);
    }

    public void setAttributeValue_WalkSpeed(String prefix, String text){
        setAttributeValue(prefix, "walk_speed", text);
    }

    private void setAttributeValue(String prefix, String attributeName, String text){
        getTextRenderer(prefix + "_attribute_value_" + attributeName).setText(text);
    }

    public void setPlayer_ResourceBarWidth_Health(float portion){
        setPlayer_ResourceBarWidth("health", portion);
    }
    
    private void setPlayer_ResourceBarWidth(String resourceName, float portion){
        Element resourceBar = getElementByID("player_resource_bar_" + resourceName);
        resourceBar.setConstraintWidth(new SizeValue((portion * 100) + "%"));
        resourceBar.getParent().layoutElements();
    }
    
    public void setPlayer_ResourceBarText_Health(String text){
        getTextRenderer("player_resource_bar_health_text").setText(text);
    }
    
    public void setPlayer_PassiveImage(String imagePath){
        getImageRenderer("player_passive_image").setImage(createImage(imagePath));
    }
    
    public void showPlayer_PassiveCooldown(float remainingTime){
        showCooldown("player_passive", 0, remainingTime);
    }

    public void hidePlayer_PassiveCooldown(){
        hideCooldown("player_passive", 0);
    }
    
    public void setPlayer_SpellImage(int index, String imagePath){
        getImageRenderer("player_spell_" + index + "_image").setImage(createImage(imagePath));
    }
    
    public void showPlayer_SpellCooldown(int index, float remainingTime){
        showCooldown("player_spell", index, remainingTime);
    }
    
    public void hidePlayer_SpellCooldown(int index){
        hideCooldown("player_spell", index);
    }

    public void setInventoryItem_Image(String prefix, int index, String imagePath){
        getImageRenderer(prefix + "_inventory_item_" + index + "_image").setImage(createImage(imagePath));
    }

    public void showInventoryItem_Description(String prefix, int index, String description){
        showHintText(prefix + "_inventory_item_" + index + "_image", description);
    }

    public void hideInventoryItem_Description(String prefix, int index){
        hideHintText(prefix + "_inventory_item_" + index + "_image");
    }
    
    public void showPlayer_ItemCooldown(int index, float remainingTime){
        showCooldown("player_inventory_item", index, remainingTime);
    }
    
    public void hidePlayer_ItemCooldown(int index){
        hideCooldown("player_inventory_item", index);
    }
    
    public void setPlayer_MapSpellImage(int index, String imagePath){
        getImageRenderer("player_map_spell_" + index + "_image").setImage(createImage(imagePath));
    }
    
    public void showPlayer_MapSpellCooldown(int index, float remainingTime){
        showCooldown("player_map_spell", index, remainingTime);
    }
    
    public void hidePlayer_MapSpellCooldown(int index){
        hideCooldown("player_map_spell", index);
    }
    
    private void showCooldown(String prefix, int index, float remainingTime){
        getElementByID(prefix + "_" + index + "_cooldown").show();
        getTextRenderer(prefix + "_" + index + "_cooldown_time").setText("" + Util.round(remainingTime, 1));
    }
    
    private void hideCooldown(String prefix, int index){
        getElementByID(prefix + "_" + index + "_cooldown").hide();
    }
    
    public void setPlayer_Gold(float gold){
        getTextRenderer("player_gold").setText("" + ((int) gold));
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
    
    public void setDeathTimer(float deathTimer){
        getButton("toggle_death_recap_button").setText("Death Recap (Respawn: " + Util.round(deathTimer, 1) + ")");
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

    public void setPlayer_SpellInformations_Passives(SpellInformation[] spellInformations_Passives){
        this.spellInformations_Passives = spellInformations_Passives;
    }

    public void setPlayer_SpellInformations_LearnableSpells(SpellInformation[] spellInformations_LearnableSpells){
        this.spellInformations_LearnableSpells = spellInformations_LearnableSpells;
    }

    public void setPlayer_SpellInformations_Spells(SpellInformation[] spellInformations_Spells){
        this.spellInformations_Spells = spellInformations_Spells;
    }

    public void setPlayer_SpellInformations_MapSpells(SpellInformation[] spellInformations_MapSpells){
        this.spellInformations_MapSpells = spellInformations_MapSpells;
    }
    
    public void showPlayer_SpellInformation_Passive(String indexText){
        int index = Integer.parseInt(indexText);
        if(index < spellInformations_Passives.length){
            action_ShowSpellInformation = spellInformations_Passives[index];
        }
    }
    
    public void showPlayer_SpellInformation_LearnableSpell(String indexText){
        int index = Integer.parseInt(indexText);
        if(index < spellInformations_LearnableSpells.length){
            action_ShowSpellInformation = spellInformations_LearnableSpells[index];
        }
    }
    
    public void showPlayer_SpellInformation_Spell(String indexText){
        int index = Integer.parseInt(indexText);
        if(index < spellInformations_Spells.length){
            action_ShowSpellInformation = spellInformations_Spells[index];
        }
    }
    
    public void showPlayer_SpellInformation_MapSpell(String indexText){
        int index = Integer.parseInt(indexText);
        if(index < spellInformations_MapSpells.length){
            action_ShowSpellInformation = spellInformations_MapSpells[index];
        }
    }
    
    public void hidePlayer_SpellInformation(){
        action_HideSpellInformation = true;
    }
    
    public void checkAction_SpellInformation(){
        if(action_ShowSpellInformation != null){
            showPlayer_SpellInformation(action_ShowSpellInformation);
            action_ShowSpellInformation = null;
            action_HideSpellInformation = false;
        }
        else if(action_HideSpellInformation){
            getElementByID("player_spell_information_layer").setVisible(false);
            PlayerAppState playerAppState = mainApplication.getStateManager().getState(PlayerAppState.class);
            playerAppState.getSpellIndicatorSystem().hideIndicator();
            action_HideSpellInformation = false;
        }
    }
    
    private void showPlayer_SpellInformation(SpellInformation spellInformation){
        getElementByID("player_spell_information_layer").setVisible(true);
        getTextRenderer("player_spell_information_name").setText(spellInformation.getName());
        getTextRenderer("player_spell_information_description").setText(spellInformation.getDescription());
        boolean hasCooldown = (spellInformation.getCooldown() != -1);
        getElementByID("player_spell_information_cooldown").setVisible(hasCooldown);
        if(hasCooldown){
            getTextRenderer("player_spell_information_cooldown").setText(GUIUtil.getValueText(spellInformation.getCooldown()) + "s");
        }
        PlayerAppState playerAppState = mainApplication.getStateManager().getState(PlayerAppState.class);
        playerAppState.getSpellIndicatorSystem().showIndicator(spellInformation.getEntity());
    }
    
    public void setPlayer_UpgradeSpellsLayerVisible(boolean isVisible){
        getElementByID("upgrade_spells_layer").setVisible(isVisible);
    }
    
    public void setPlayer_UpgradeSpellsButtonVisible(int spellIndex, boolean isVisible){
        getElementByID("upgrade_spells_button_" + spellIndex).setVisible(isVisible);
    }
    
    public void setPlayer_UpgradeSpellsButtonImage(int spellIndex, String imagePath){
        getImageRenderer("upgrade_spells_button_" + spellIndex).setImage(createImage(imagePath));
    }
    
    public void learnOrUpgradeSpell(String spellIndexString){
        hidePlayer_SpellInformation();
        int spellIndex = Integer.parseInt(spellIndexString);
        SendPlayerCommandsAppState sendPlayerCommandsAppState = mainApplication.getStateManager().getState(SendPlayerCommandsAppState.class);
        sendPlayerCommandsAppState.learnOrUpgradeSpell(spellIndex);
    }
    
    public void showPlayer_UpgradeSpell(int spellIndex){
        currentUpgradeSpellIndex = spellIndex;
        getElementByID("upgrade_spell_layer_container").setVisible(true);
        getElementByID("upgrade_spell_layer_images").setVisible(true);
        int offset = (spellIndex * 60);
        getElementByID("upgrade_spell_offset_container").setConstraintWidth(new SizeValue(offset + "px"));
        getElementByID("upgrade_spell_offset_container").getParent().layoutElements();
        getElementByID("upgrade_spell_offset_images").setConstraintWidth(new SizeValue((7 + offset) + "px"));
        getElementByID("upgrade_spell_offset_images").getParent().layoutElements();
    }
    
    public void setPlayer_SpellUpgradeImage(int upgradeIndex, String imagePath){
        getImageRenderer("upgrade_spell_image_" + upgradeIndex).setImage(createImage(imagePath));
    }
    
    public void upgradeSpell(String upgradeIndexString){
        int upgradeIndex = Integer.parseInt(upgradeIndexString);
        SendPlayerCommandsAppState sendPlayerCommandsAppState = mainApplication.getStateManager().getState(SendPlayerCommandsAppState.class);
        sendPlayerCommandsAppState.upgradeSpell(currentUpgradeSpellIndex, upgradeIndex);
        hidePlayer_UpgradeSpell();
    }
    
    private void hidePlayer_UpgradeSpell(){
        getElementByID("upgrade_spell_layer_container").setVisible(false);
        getElementByID("upgrade_spell_layer_images").setVisible(false);
    }
    
    public void generateScoreboard(){
        new PanelBuilder(){{
            childLayoutVertical();
            
            PlayerAppState playerAppState = mainApplication.getStateManager().getState(PlayerAppState.class);
            GameSelectionPlayer[][] teams = playerAppState.getTeams();
            if (teams != null) {
                int _playerIndex = 0;
                for (int i = 0; i < teams.length; i++) {
                    final int teamIndex = i;
                    text(new TextBuilder() {{
                        height("30px");
                        textHAlignLeft();
                        font("Interface/fonts/Verdana_14.fnt");
                        text("Team #" + (teamIndex + 1));
                    }});
                    for (int r = 0; r < teams[i].length; r++) {
                        final int playerIndex = _playerIndex;
                        panel(new PanelBuilder("scoreboard_player_" + playerIndex) {{
                            childLayoutHorizontal();
                            height("30px");

                            text(new TextBuilder("scoreboard_player_" + playerIndex + "_name") {{
                                width("200px");
                                height("30px");
                                textHAlignLeft();
                                font("Interface/fonts/Verdana_14.fnt");
                                text("Player #" + (playerIndex + 1));
                            }});
                            text(new TextBuilder("scoreboard_player_" + playerIndex + "_kda") {{
                                width("40px");
                                height("30px");
                                textHAlignCenter();
                                font("Interface/fonts/Verdana_14.fnt");
                                text("K/D/A");
                            }});
                            panel(new PanelBuilder() {{
                                width("20px");
                            }});
                            text(new TextBuilder("scoreboard_player_" + playerIndex + "_creepscore") {{
                                width("40px");
                                height("30px");
                                textHAlignCenter();
                                font("Interface/fonts/Verdana_14.fnt");
                                text("CS");
                            }});
                            panel(new PanelBuilder() {{
                                width("20px");
                            }});
                            for (int i = 0; i < 6; i++) {
                                image(new ImageBuilder("scoreboard_player_" + playerIndex + "_item_" + i + "_image") {{
                                    width("30px");
                                    height("30px");
                                    filename("Interface/hud/items/unknown.png");

                                    onHoverEffect(new HoverEffectBuilder("hint") {{
                                        effectParameter("hintText", "?");
                                    }});
                                }});
                            }
                            panel(new PanelBuilder() {{
                                width("*");
                            }});
                        }});
                        panel(new PanelBuilder() {{
                            height("5px");
                        }});
                        _playerIndex++;
                    }
                }
            } else {
                // TODO: Scoreboard for MMO maps
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
