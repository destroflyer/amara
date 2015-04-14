/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui;

import java.util.LinkedList;
import com.jme3.math.FastMath;
import amara.Util;
import amara.engine.applications.ingame.client.appstates.SendPlayerCommandsAppState;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.gui.GameScreenController;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.items.*;
import de.lessvoid.nifty.NiftyEvent;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;
import de.lessvoid.nifty.effects.Effect;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.effects.impl.Hint;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.events.NiftyMousePrimaryClickedEvent;

/**
 *
 * @author Carl
 */
public class ScreenController_HUD extends GameScreenController{

    public ScreenController_HUD(){
        super("start");
    }
    private final static String SHOP_ITEM_FILTER_CHECKBOX_ID_PREFIX = "shop_item_filter_checkbox_";
    private final static int SHOP_ITEMS_PER_ROW = 5;
    private EntityWorld shopEntityWorld = new EntityWorld();
    private String[] shopItemTemplateNames = new String[]{
        "boots","dorans_blade","dorans_ring","dagger","needlessly_large_rod","zhonyas_hourglass","warmogs_armor","youmuus_ghostblade","tiamat"
    };
    private EntityWrapper[] shopItems;
    private EntityWrapper[] shopItems_Special;
    private String shopItemFilterText = "";
    private boolean[] shopItemFilters = new boolean[5];
    private boolean isUpdatingShopItemFilters;
    private LinkedList<EntityWrapper> shopFilteredItems = new LinkedList<EntityWrapper>();
    private SendPlayerCommandsAppState sendPlayerCommandsAppState;
    
    @Override
    public void onStartup(){
        super.onStartup();
        for(int i=0;i<4;i++){
            hideSpellCooldown(i);
        }
        for(int i=0;i<6;i++){
            hideItemCooldown(i);
        }
        onShopItemFilter(0, true);
    }

    @Override
    protected void initialize(){
        super.initialize();
        shopItemFilters[0] = true;
        initializeShopEntities();
    }
    
    private void initializeShopEntities(){
        shopItems = new EntityWrapper[shopItemTemplateNames.length];
        for(int i=0;i<shopItems.length;i++){
            shopItems[i] = EntityTemplate.createFromTemplate(shopEntityWorld, shopItemTemplateNames[i]);
        }
        shopItems_Special = new EntityWrapper[]{
            EntityTemplate.createFromTemplate(shopEntityWorld, "rod_of_ages")
        };
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
    
    @NiftyEventSubscriber(pattern = ".*")
    public void onNiftyEvent(final String elementID, final NiftyEvent receivedEvent){
        if(receivedEvent instanceof CheckBoxStateChangedEvent){
            CheckBoxStateChangedEvent event = (CheckBoxStateChangedEvent) receivedEvent;
            if(elementID.startsWith(SHOP_ITEM_FILTER_CHECKBOX_ID_PREFIX)){
                if(!isUpdatingShopItemFilters){
                    int filterIndex = Integer.parseInt(elementID.substring(SHOP_ITEM_FILTER_CHECKBOX_ID_PREFIX.length()));
                    onShopItemFilter(filterIndex, event.isChecked());
                }
            }
        }
        else if(receivedEvent instanceof TextFieldChangedEvent){
            TextFieldChangedEvent event = (TextFieldChangedEvent) receivedEvent;
            if(elementID.equals("shop_item_filter_text")){
                shopItemFilterText = event.getText();
                updateShopAvailableItems();
            }
        }
        else if(receivedEvent instanceof NiftyMousePrimaryClickedEvent){
            NiftyMousePrimaryClickedEvent event = (NiftyMousePrimaryClickedEvent) receivedEvent;
            if(elementID.startsWith("shop_item_")){
                int itemIndex = Integer.parseInt(elementID.substring(10));
                EntityWrapper item = shopFilteredItems.get(itemIndex);
                String itemID = item.getComponent(ItemIDComponent.class).getID();
                sendPlayerCommandsAppState.sendCommand(new BuyItemCommand(itemID));
            }
        }
    }
    
    public void toggleShopVisible(){
        Element shopWindow = getElementByID("shop_window");
        shopWindow.setVisible(!shopWindow.isVisible());
    }
    
    public void onShopItemFilter(int filterIndex, boolean isSelected){
        shopItemFilters[filterIndex] = isSelected;
        boolean enableAllItems = true;
        if(filterIndex != 0){
            for(int i=1;i<shopItemFilters.length;i++){
                if(shopItemFilters[i]){
                    enableAllItems = false;
                    break;
                }
            }
        }
        isUpdatingShopItemFilters = true;
        setShopItemFilter(0, enableAllItems);
        if(enableAllItems){
            for(int i=1;i<shopItemFilters.length;i++){
                setShopItemFilter(i, false);
            }
        }
        isUpdatingShopItemFilters = false;
        updateShopAvailableItems();
    }
    
    private void setShopItemFilter(int index, boolean isSelected){
        shopItemFilters[index] = isSelected;
        getCheckBox("shop_item_filter_checkbox_" + index).setChecked(isSelected);
    }
    
    private void updateShopAvailableItems(){
        Element shopContainer = getElementByID("shop_container");
        Element itemsScrollPanel = getElementByID("shop_available_items");
        if(itemsScrollPanel != null){
            itemsScrollPanel.markForRemoval();
        }
        shopFilteredItems.clear();
        String itemFilterTextLowerCase = shopItemFilterText.toLowerCase();
        for(int i=0;i<shopItems.length;i++){
            EntityWrapper item = shopItems[i];
            boolean isFiltered = true;
            String itemID = item.getComponent(ItemIDComponent.class).getID();
            if(!itemID.toLowerCase().contains(itemFilterTextLowerCase)){
                isFiltered = false;
            }
            else if((shopItemFilters[1] && (!item.hasComponent(BonusFlatAttackDamageComponent.class)))
            || (shopItemFilters[2] && (!item.hasComponent(BonusFlatAbilityPowerComponent.class)))
            || (shopItemFilters[3] && (!item.hasComponent(BonusFlatWalkSpeedComponent.class)))
            || shopItemFilters[4]){
                isFiltered = false;
            }
            if(isFiltered){
                shopFilteredItems.add(item);
            }
        }
        if(itemFilterTextLowerCase.equals("etherblood")){
            shopFilteredItems.add(shopItems_Special[0]);
        }
        new ScrollPanelBuilder("shop_available_items"){{
            set("height", "100%");
            set("horizontal", "false");
            set("style", "nifty-listbox");
            
            panel(new PanelBuilder(){{
                childLayoutVertical();
                padding("10px");
                backgroundColor("#000C");
                
                int rows = (int) FastMath.ceil(((float) shopFilteredItems.size()) / SHOP_ITEMS_PER_ROW);
                for(int i=0;i<rows;i++){
                    final int rowIndex = i;
                    panel(new PanelBuilder(){{
                        childLayoutHorizontal();
                        height("70px");
                        
                        for(int r=0;r<SHOP_ITEMS_PER_ROW;r++){
                            final int itemIndex = ((rowIndex * SHOP_ITEMS_PER_ROW) + r);
                            if(itemIndex >= shopFilteredItems.size()){
                                break;
                            }
                            final EntityWrapper item = shopFilteredItems.get(itemIndex);
                            panel(new PanelBuilder(){{
                                childLayoutVertical();
                                width("55px");

                                image(new ImageBuilder("shop_item_" + itemIndex){{
                                    filename("Interface/hud/items/" + item.getComponent(ItemIDComponent.class).getID() + ".png");
                                    width("45px");
                                    height("45px");
                                    visibleToMouse(true);
                                }});
                                panel(new PanelBuilder(){{
                                    height("2px");
                                }});
                                panel(new PanelBuilder(){{
                                    childLayoutHorizontal();
                                    height("32px");

                                    panel(new PanelBuilder(){{
                                        width("2px");
                                    }});
                                    image(new ImageBuilder(){{
                                        filename("Interface/hud/gold.png");
                                        width("15px");
                                        height("15px");
                                    }});
                                    panel(new PanelBuilder(){{
                                        width("1px");
                                    }});
                                    text(new TextBuilder(){{
                                        text("" + item.getComponent(ItemRecipeComponent.class).getGold());
                                        font("Interface/fonts/Verdana_12.fnt");
                                    }});
                                    panel(new PanelBuilder());
                                }});
                            }});
                        }
                    }});
                }
                panel(new PanelBuilder());
            }});
        }}.build(nifty, nifty.getCurrentScreen(), shopContainer);
    }
    
    public void sellItem(String inventoryIndexString){
        int inventoryIndex = Integer.parseInt(inventoryIndexString);
        sendPlayerCommandsAppState.sendCommand(new SellItemCommand(inventoryIndex));
    }

    public void setAppStates(SendPlayerCommandsAppState sendPlayerCommandsAppState){
        this.sendPlayerCommandsAppState = sendPlayerCommandsAppState;
    }
}
