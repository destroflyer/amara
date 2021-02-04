package amara.applications.ingame.client.gui;

import amara.applications.ingame.client.appstates.*;
import amara.applications.ingame.client.gui.generators.*;
import amara.applications.ingame.client.gui.objects.SpellInformation;
import amara.applications.ingame.network.messages.objects.commands.ItemIndex;
import amara.applications.ingame.network.messages.objects.commands.SwapItemsCommand;
import amara.applications.ingame.shared.maps.MapMinimapInformation;
import amara.applications.master.network.messages.objects.GameSelectionPlayer;
import amara.core.Util;
import amara.libraries.applications.display.appstates.NiftyAppState;
import amara.libraries.applications.display.gui.GameScreenController;
import amara.libraries.applications.display.ingame.appstates.*;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.math.Vector2f;
import com.jme3.texture.Texture2D;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Droppable;
import de.lessvoid.nifty.controls.DroppableDroppedEvent;
import de.lessvoid.nifty.effects.Effect;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.effects.impl.Hint;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.tools.SizeValue;

public class ScreenController_HUD extends GameScreenController {

    public ScreenController_HUD(){
        super("start");
    }
    private int currentUpgradeSpellIndex = -1;
    private SpellInformation[] spellInformations_Passives = new SpellInformation[0];
    private SpellInformation[] spellInformations_LearnableSpells = new SpellInformation[0];
    private SpellInformation[] spellInformations_Spells = new SpellInformation[0];
    private SpellInformation[] spellInformations_UpgradedSpells = new SpellInformation[0];
    private SpellInformation[] spellInformations_MapSpells = new SpellInformation[0];
    // Show/Hide the information by saving the actions and checking them in an update loop
    // (Since NiftyGUI sometimes seems to be ordering start/end effect methods wrong)
    private SpellInformation action_ShowSpellInformation;
    private boolean action_HideSpellInformation;
    private BuffsPanelGenerator playerBuffsPanelGenerator = new BuffsPanelGenerator("player_buffs");
    private BuffsPanelGenerator inspectionBuffsPanelGenerator = new BuffsPanelGenerator("inspection_buffs");
    private PlayerInventoryPanelGenerator playerInventoryPanelGenerator = new PlayerInventoryPanelGenerator();
    private InspectionInventoryPanelGenerator inspectionPlayerInventoryPanelGenerator = new InspectionInventoryPanelGenerator();
    private BagPanelGenerator bagPanelGenerator = new BagPanelGenerator();
    private ScoreboardGenerator scoreboardGenerator = new ScoreboardGenerator();
    private DeathRecapGenerator deathRecapGenerator = new DeathRecapGenerator();

    public void hidePlayerAnnouncement(){
        getElementByID("player_announcement").hide();
    }

    public void showPlayerAnnouncement(String text){
        getElementByID("player_announcement").show();
        getTextRenderer("player_announcement_text").setText(text);
    }

    public void setInspectionVisible(boolean isVisible) {
        getElementByID("inspection_attributes").setVisible(isVisible);
        getElementByID("inspection_inventory_items_wrapper").setVisible(isVisible);
        getElementByID("inspection_inventory_background").setVisible(isVisible);
        getElementByID("inspection_background").setVisible(isVisible);
        getElementByID("inspection_level_layer").setVisible(isVisible);
        getElementByID("inspection_buffs_layer").setVisible(isVisible);
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

    public void setPlayer_ResourceBar_Health_Width(float portion) {
        setPlayer_ResourceBarWidth("health", portion);
    }

    public void setPlayer_ResourceBar_Mana_Width(float portion) {
        setPlayer_ResourceBarWidth("mana", portion);
    }

    private void setPlayer_ResourceBarWidth(String resourceName, float portion) {
        Element resourceBar = getElementByID("player_resource_bar_" + resourceName);
        resourceBar.setConstraintWidth(new SizeValue((portion * 100) + "%"));
        resourceBar.getParent().layoutElements();
    }

    public void setPlayer_ResourceBar_Health_Text(String text) {
        setPlayer_ResourceBarText("health", text);
    }

    public void setPlayer_ResourceBar_Mana_Text(String text) {
        setPlayer_ResourceBarText("mana", text);
    }

    private void setPlayer_ResourceBarText(String resourceName, String text) {
        getTextRenderer("player_resource_bar_" + resourceName + "_text").setText(text);
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

    public void setPlayer_SpellSufficientMana(int index, boolean sufficientMana) {
        setSufficientMana("player_spell", index, sufficientMana);
    }

    public void generatePlayerInventory(EntityWorld entityWorlds, int[] itemEntities) {
        playerInventoryPanelGenerator.setData(entityWorlds, itemEntities);
        playerInventoryPanelGenerator.update(nifty, "player_inventory_items_layer");
    }

    public void generateInspectionInventory(EntityWorld entityWorlds, int[] itemEntities) {
        inspectionPlayerInventoryPanelGenerator.setData(entityWorlds, itemEntities);
        inspectionPlayerInventoryPanelGenerator.update(nifty, "inspection_inventory_items_wrapper");
    }

    public void showPlayer_ItemCooldown(int index, float remainingTime){
        showCooldown(playerInventoryPanelGenerator.getId() + "_item", index, remainingTime);
    }
    
    public void hidePlayer_ItemCooldown(int index){
        hideCooldown(playerInventoryPanelGenerator.getId() + "_item", index);
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

    public void setPlayer_MapSpellSufficientMana(int index, boolean sufficientMana) {
        setSufficientMana("player_map_spell", index, sufficientMana);
    }

    private void showCooldown(String prefix, int index, float remainingTime){
        getElementByID(prefix + "_cooldown_" + index).show();
        getTextRenderer(prefix + "_cooldown_time_" + index).setText("" + Util.round(remainingTime, 1));
    }
    
    private void hideCooldown(String prefix, int index){
        getElementByID(prefix + "_cooldown_" + index).hide();
    }

    private void setSufficientMana(String prefix, int index, boolean sufficientMana){
        getElementByID(prefix + "_insufficient_mana_" + index).setVisible(!sufficientMana);
    }

    public void setPlayer_Gold(float gold){
        getTextRenderer(playerInventoryPanelGenerator.getId() + "_player_gold").setText("" + ((int) gold));
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
        setDeathRecapVisible(!getElementByID("death_recap_container").isVisible());
    }
    
    public void setDeathRecapVisible(boolean isVisible){
        getElementByID("death_recap_container").setVisible(isVisible);
    }

    public void setDeathRecapText(String text){
        deathRecapGenerator.setData(text);
        deathRecapGenerator.update(nifty, "death_recap_container");
    }
    
    public void toggleShopVisible(){
        NiftyAppState niftyAppState = mainApplication.getStateManager().getState(NiftyAppState.class);
        niftyAppState.getScreenController(ScreenController_Shop.class).toggleShopVisible();
    }

    public void setPlayer_SpellInformations_Passives(SpellInformation[] spellInformations_Passives) {
        this.spellInformations_Passives = spellInformations_Passives;
    }

    public void setPlayer_SpellInformations_LearnableSpells(SpellInformation[] spellInformations_LearnableSpells) {
        this.spellInformations_LearnableSpells = spellInformations_LearnableSpells;
    }

    public void setPlayer_SpellInformations_Spells(SpellInformation[] spellInformations_Spells) {
        this.spellInformations_Spells = spellInformations_Spells;
    }

    public void setPlayer_SpellInformations_UpgradedSpells(SpellInformation[] spellInformations_UpgradedSpells) {
        this.spellInformations_UpgradedSpells = spellInformations_UpgradedSpells;
    }

    public void setPlayer_SpellInformations_MapSpells(SpellInformation[] spellInformations_MapSpells) {
        this.spellInformations_MapSpells = spellInformations_MapSpells;
    }

    public void showPlayer_SpellInformation_Passive(String indexText) {
        int index = Integer.parseInt(indexText);
        tryShowSpellInformation(spellInformations_Passives, index);
    }

    public void showPlayer_SpellInformation_LearnableSpell(String indexText) {
        int index = Integer.parseInt(indexText);
        if (!hasSpellInformation(spellInformations_Spells, index)) {
            tryShowSpellInformation(spellInformations_LearnableSpells, index);
        }
    }

    public void showPlayer_SpellInformation_Spell(String indexText) {
        int index = Integer.parseInt(indexText);
        tryShowSpellInformation(spellInformations_Spells, index);
    }

    public void showPlayer_SpellInformation_UpgradedSpell(String indexText) {
        int index = Integer.parseInt(indexText);
        tryShowSpellInformation(spellInformations_UpgradedSpells, index);
    }

    public void showPlayer_SpellInformation_MapSpell(String indexText) {
        int index = Integer.parseInt(indexText);
        tryShowSpellInformation(spellInformations_MapSpells, index);
    }

    private void tryShowSpellInformation(SpellInformation[] spellInformations, int index) {
        if (hasSpellInformation(spellInformations, index)) {
            action_ShowSpellInformation = spellInformations[index];
        }
    }

    private boolean hasSpellInformation(SpellInformation[] spellInformations, int index) {
        return ((index < spellInformations.length) && (spellInformations[index] != null));
    }

    public void hidePlayer_SpellInformation(){
        action_HideSpellInformation = true;
    }

    public void checkAction_SpellInformation() {
        if (action_ShowSpellInformation != null) {
            showPlayer_SpellInformation(action_ShowSpellInformation);
            action_ShowSpellInformation = null;
            action_HideSpellInformation = false;
        } else if (action_HideSpellInformation) {
            getElementByID("player_spell_information_layer").setVisible(false);
            PlayerAppState playerAppState = mainApplication.getStateManager().getState(PlayerAppState.class);
            playerAppState.getSpellIndicatorSystem().hideIndicator();
            action_HideSpellInformation = false;
        }
    }

    private void showPlayer_SpellInformation(SpellInformation spellInformation) {
        getElementByID("player_spell_information_layer").setVisible(true);
        getTextRenderer("player_spell_information_name").setText(spellInformation.getName());
        getTextRenderer("player_spell_information_description").setText(spellInformation.getDescription());
        // Cooldown
        boolean hasCooldown = (spellInformation.getCooldown() != -1);
        Element cooldownIcon = getElementByID("player_spell_information_cooldown_icon");
        cooldownIcon.setVisible(hasCooldown);
        cooldownIcon.setConstraintWidth(new SizeValue((hasCooldown ? 16 : 0) + "px"));
        Element cooldownSeparator = getElementByID("player_spell_information_cooldown_separator");
        cooldownSeparator.setVisible(hasCooldown);
        cooldownSeparator.setConstraintWidth(new SizeValue((hasCooldown ? 5 : 0) + "px"));
        Element cooldownText = getElementByID("player_spell_information_cooldown_text");
        int cooldownTextWidth = 0;
        if (hasCooldown) {
            TextRenderer cooldownTextRenderer = getTextRenderer(cooldownText.getId());
            cooldownTextRenderer.setText(GUIUtil.getValueText(spellInformation.getCooldown()) + "s");
            cooldownTextWidth = cooldownTextRenderer.getTextWidth();
        }
        cooldownText.setVisible(hasCooldown);
        cooldownText.setConstraintWidth(new SizeValue(cooldownTextWidth + "px"));
        // ManaCost
        boolean hasManaCost = (spellInformation.getManaCost() != null);
        Element manaCostIcon = getElementByID("player_spell_information_mana_cost_icon");
        manaCostIcon.setVisible(hasManaCost);
        manaCostIcon.setConstraintWidth(new SizeValue((hasManaCost ? 16 : 0) + "px"));
        Element manaCostSeparator = getElementByID("player_spell_information_mana_cost_separator");
        manaCostSeparator.setVisible(hasManaCost);
        manaCostSeparator.setConstraintWidth(new SizeValue((hasManaCost ? 5 : 0) + "px"));
        Element manaCostText = getElementByID("player_spell_information_mana_cost_text");
        int manaCostTextWidth = 0;
        if (hasManaCost) {
            TextRenderer manaCostTextRenderer = getTextRenderer(manaCostText.getId());
            manaCostTextRenderer.setText(GUIUtil.getValueText(spellInformation.getManaCost()));
            manaCostTextWidth = manaCostTextRenderer.getTextWidth();
        }
        manaCostText.setVisible(hasManaCost);
        manaCostText.setConstraintWidth(new SizeValue(manaCostTextWidth + "px"));
        boolean displayManaCostAndCooldownSeparator = (hasManaCost && hasCooldown);
        Element manaCostAndCooldownSeparator = getElementByID("player_spell_information_separator_mana_cost_and_cooldown");
        manaCostAndCooldownSeparator.setVisible(displayManaCostAndCooldownSeparator);
        manaCostAndCooldownSeparator.setConstraintWidth(new SizeValue((displayManaCostAndCooldownSeparator ? 10 : 0) + "px"));
        cooldownText.getParent().layoutElements();
        PlayerAppState playerAppState = mainApplication.getStateManager().getState(PlayerAppState.class);
        playerAppState.getSpellIndicatorSystem().showIndicator(spellInformation.getEntity());
    }

    public void setPlayer_UpgradeSpellsContainerVisible(boolean isVisible) {
        Element upgradeSpellsContainer = getElementByID("upgrade_spells_container");
        upgradeSpellsContainer.setVisible(isVisible);
        // Set height to 0 and relayout the according vertical-layout parent to adjust the buffs container correctly
        upgradeSpellsContainer.setConstraintHeight(new SizeValue((isVisible ? 45 : 0) + "px"));
        upgradeSpellsContainer.getParent().getParent().layoutElements();
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

    public void upgradeSpell(String upgradeIndexString) {
        int upgradeIndex = Integer.parseInt(upgradeIndexString);
        SendPlayerCommandsAppState sendPlayerCommandsAppState = mainApplication.getStateManager().getState(SendPlayerCommandsAppState.class);
        sendPlayerCommandsAppState.upgradeSpell(currentUpgradeSpellIndex, upgradeIndex);
        hidePlayer_UpgradeSpell();
        hidePlayer_SpellInformation();
    }

    private void hidePlayer_UpgradeSpell(){
        getElementByID("upgrade_spell_layer_container").setVisible(false);
        getElementByID("upgrade_spell_layer_images").setVisible(false);
    }

    public void setPlayerBuffs(EntityWorld entityWorld, int[] buffStatusEntities) {
        playerBuffsPanelGenerator.setData(entityWorld, buffStatusEntities);
        playerBuffsPanelGenerator.update(nifty, "player_buffs_container");
    }

    public void setInspectionBuffs(EntityWorld entityWorld, int[] buffStatusEntities) {
        inspectionBuffsPanelGenerator.setData(entityWorld, buffStatusEntities);
        inspectionBuffsPanelGenerator.update(nifty, "inspection_buffs_container");
    }

    public void setPlayerBuffStacks(int buffStatusEntity, int stacks) {
        playerBuffsPanelGenerator.setBuffStacks(nifty, buffStatusEntity, stacks);
    }

    public void setInspectionBuffStacks(int buffStatusEntity, int stacks) {
        inspectionBuffsPanelGenerator.setBuffStacks(nifty, buffStatusEntity, stacks);
    }

    public void setPlayerBuffStacksVisible(int buffStatusEntity, boolean visible) {
        playerBuffsPanelGenerator.setBuffStacksVisible(nifty, buffStatusEntity, visible);
    }

    public void setInspectionBuffStacksVisible(int buffStatusEntity, boolean visible) {
        inspectionBuffsPanelGenerator.setBuffStacksVisible(nifty, buffStatusEntity, visible);
    }

    public void setPlayerBagItems(EntityWorld entityWorld, int[] bagItemEntities) {
        bagPanelGenerator.setData(entityWorld, bagItemEntities);
        bagPanelGenerator.update(nifty, "player_bag_items_container");
    }

    public void togglePlayerBagVisible(){
        setPlayerBagVisible(!getElementByID("player_bag_window").isVisible());
    }

    public void setPlayerBagVisible(boolean isVisible){
        getElementByID("player_bag_window").setVisible(isVisible);
    }

    public void generateScoreboard(GameSelectionPlayer[][] teams) {
        scoreboardGenerator.setData(teams);
        scoreboardGenerator.update(nifty, "scoreboard_container");
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

    public void setScoreboard_KDA(int playerIndex, int kills, int deaths, int assists) {
        setScoreboard_Score(playerIndex, "kda", (kills + "/" + deaths + "/" + assists));
    }

    public void setScoreboard_CreepScore(int playerIndex, int kills) {
        setScoreboard_Score(playerIndex, "creepscore", "" + kills);
    }

    private void setScoreboard_Score(int playerIndex, String suffix, String text) {
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

    public void setGameTime(String formattedTime) {
        getTextRenderer("game_time").setText(formattedTime);
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
        // TODO: Look in given direction when in 3rd person camera
        mainApplication.getStateManager().getState(IngameCameraAppState.class).lookAt(tmpMapLocation);
    }

    @NiftyEventSubscriber(pattern=".*")
    public void onDrop(String id, DroppableDroppedEvent event) {
        ItemIndex itemIndex1 = getItemIndex(event.getSource());
        ItemIndex itemIndex2 = getItemIndex(event.getTarget());
        SendPlayerCommandsAppState sendPlayerCommandsAppState = mainApplication.getStateManager().getState(SendPlayerCommandsAppState.class);
        sendPlayerCommandsAppState.sendCommand(new SwapItemsCommand(itemIndex1, itemIndex2));
    }

    private ItemIndex getItemIndex(Droppable droppable) {
        String id = droppable.getId();
        ItemIndex.ItemSet itemSet = null;
        if (id.startsWith(playerInventoryPanelGenerator.getId())) {
            itemSet = ItemIndex.ItemSet.INVENTORY;
        } else if (id.startsWith(bagPanelGenerator.getId())) {
            itemSet = ItemIndex.ItemSet.BAG;
        }
        String[] parts = droppable.getId().split("_");
        int index = Integer.parseInt(parts[parts.length - 1]);
        return new ItemIndex(itemSet, index);
    }
}
