/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.gui;

import java.util.HashMap;
import java.util.LinkedList;

import com.jme3.math.FastMath;
import amara.applications.ingame.client.appstates.SendPlayerCommandsAppState;
import amara.applications.ingame.client.gui.objects.ItemRecipe;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.items.*;
import amara.applications.ingame.network.messages.objects.commands.*;
import amara.libraries.applications.display.gui.GameScreenController;
import amara.libraries.entitysystem.*;
import amara.libraries.entitysystem.templates.StaticEntityWorld;
import de.lessvoid.nifty.NiftyEvent;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;
import de.lessvoid.nifty.elements.Element;

/**
 *
 * @author Carl
 */
public class ScreenController_Shop extends GameScreenController{

    public ScreenController_Shop(){
        super("start");
    }
    private final static String ITEM_FILTER_CHECKBOX_ID_PREFIX = "shop_item_filter_checkbox_";
    private final static int ITEMS_PER_ROW = 10;
    private final static int ITEMS_ROW_HEIGHT = 70;
    private static final int INGREDIENT_WIDTH = 45;
    private static final int INGREDIENT_HEIGHT = 60;
    private String[] shopItemNames_Special = new String[] {
        "golden_eagle","youmuus_ghostblade","lifebinder"
    };
    private EntityWrapper[] shopItems = new EntityWrapper[0];
    private EntityWrapper[] shopItems_Special = new EntityWrapper[0];
    private HashMap<String, ItemRecipe> itemsRecipes = new HashMap<>();
    private LinkedList<ItemRecipe> tmpInventoryItemsRecipes = new LinkedList<>();
    private int shopPageID;
    private String shopItemFilterText = "";
    private boolean[] shopItemFilters = new boolean[11];
    private boolean isUpdatingShopItemFilters;
    private LinkedList<EntityWrapper> shopFilteredItems = new LinkedList<>();
    
    @Override
    public void onStartup(){
        super.onStartup();
        onShopItemFilter(0, true);
    }

    @Override
    protected void initialize(){
        super.initialize();
        shopItems_Special = new EntityWrapper[shopItemNames_Special.length];
        for(int i=0;i<shopItems_Special.length;i++){
            shopItems_Special[i] = StaticEntityWorld.loadTemplateWrapped("items/" + shopItemNames_Special[i]);
        }
        // Preload recipes
        for (EntityWrapper shopItem_Special : shopItems_Special) {
            String itemID = shopItem_Special.getComponent(ItemIDComponent.class).getID();
            getItemRecipe(itemID);
        }
        shopItemFilters[0] = true;
    }

    public void setShopItems(String[] itemTemplateNames) {
        // Load new item entities
        shopItems = new EntityWrapper[itemTemplateNames.length];
        for(int i=0;i<shopItems.length;i++){
            shopItems[i] = StaticEntityWorld.loadTemplateWrapped(itemTemplateNames[i]);
        }
        // Preload recipes
        for (EntityWrapper shopItem : shopItems) {
            String itemID = shopItem.getComponent(ItemIDComponent.class).getID();
            getItemRecipe(itemID);
        }
    }

    private ItemRecipe getItemRecipe(String itemID){
        ItemRecipe itemRecipe = itemsRecipes.get(itemID);
        if(itemRecipe == null){
            int itemEntity = StaticEntityWorld.loadTemplate("items/" + itemID);
            ItemRecipeComponent itemRecipeComponent = StaticEntityWorld.getEntityWorld().getComponent(itemEntity, ItemRecipeComponent.class);
            ItemRecipe[] ingredientsRecipes = new ItemRecipe[itemRecipeComponent.getItemIDs().length];
            int previousDepth = -1;
            for(int i=0;i<ingredientsRecipes.length;i++){
                ItemRecipe ingredientRecipe = getItemRecipe(itemRecipeComponent.getItemIDs()[i]);
                if(ingredientRecipe.getDepth() > previousDepth){
                    previousDepth = ingredientRecipe.getDepth();
                }
                ingredientsRecipes[i] = ingredientRecipe;
            }
            itemRecipe = new ItemRecipe(itemEntity, itemRecipeComponent.getGold(), ingredientsRecipes, (previousDepth + 1));
            itemsRecipes.put(itemID, itemRecipe);
        }
        return itemRecipe;
    }
    
    public void updateRecipeCosts(EntityWorld entityWorld, int[] inventoryItemEntities){
        tmpInventoryItemsRecipes.clear();
        for (int inventoryItemEntity : inventoryItemEntities) {
            if (inventoryItemEntity != -1) {
                ItemIDComponent itemIDComponent = entityWorld.getComponent(inventoryItemEntity, ItemIDComponent.class);
                if (itemIDComponent != null) {
                    tmpInventoryItemsRecipes.add(getItemRecipe(itemIDComponent.getID()));
                }
            }
        }
        for(ItemRecipe itemRecipe : itemsRecipes.values()){
            itemRecipe.updateResolvedGold(tmpInventoryItemsRecipes);
        }
        updateShopAvailableItems();
    }
    
    @NiftyEventSubscriber(pattern = ".*")
    public void onNiftyEvent(final String elementID, final NiftyEvent receivedEvent){
        if(receivedEvent instanceof CheckBoxStateChangedEvent){
            CheckBoxStateChangedEvent event = (CheckBoxStateChangedEvent) receivedEvent;
            if(elementID.startsWith(ITEM_FILTER_CHECKBOX_ID_PREFIX)){
                if(!isUpdatingShopItemFilters){
                    int filterIndex = Integer.parseInt(elementID.substring(ITEM_FILTER_CHECKBOX_ID_PREFIX.length()));
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
    }
    
    public void toggleShopVisible(){
        setShopVisible(!getElementByID("shop_window").isVisible());
    }
    
    public void setShopVisible(boolean isVisible){
        Element shopWindow = getElementByID("shop_window");
        shopWindow.setVisible(isVisible);
        hideShopItemInformation();
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
        Element itemsScrollPanel = getElementByID("shop_available_items_" + shopPageID);
        if(itemsScrollPanel != null){
            itemsScrollPanel.markForRemoval();
        }
        shopPageID++;
        shopFilteredItems.clear();
        String itemFilterTextLowerCase = shopItemFilterText.toLowerCase();
        for (EntityWrapper item : shopItems) {
            boolean isFiltered = true;
            String itemName = item.getComponent(NameComponent.class).getName();
            if (!itemName.toLowerCase().contains(itemFilterTextLowerCase)) {
                isFiltered = false;
            } else if ((shopItemFilters[1] && (!item.hasComponent(BonusFlatMaximumHealthComponent.class)))
                    || (shopItemFilters[2] && (!item.hasComponent(BonusFlatAttackDamageComponent.class)))
                    || (shopItemFilters[3] && (!item.hasComponent(BonusPercentageAttackSpeedComponent.class)))
                    || (shopItemFilters[4] && (!item.hasComponent(BonusPercentageCriticalChanceComponent.class)))
                    || (shopItemFilters[5] && (!item.hasComponent(BonusPercentageLifestealComponent.class)))
                    || (shopItemFilters[6] && (!item.hasComponent(BonusFlatAbilityPowerComponent.class)))
                    || (shopItemFilters[7] && (!item.hasComponent(BonusPercentageCooldownSpeedComponent.class)))
                    || (shopItemFilters[8] && (!item.hasComponent(BonusFlatArmorComponent.class)))
                    || (shopItemFilters[9] && (!item.hasComponent(BonusFlatMagicResistanceComponent.class)))
                    || (shopItemFilters[10] && (!item.hasComponent(BonusFlatWalkSpeedComponent.class)))) {
                isFiltered = false;
            }
            if (isFiltered) {
                shopFilteredItems.add(item);
            }
        }
        if(itemFilterTextLowerCase.equals("etherblood")){
            for(EntityWrapper shopItem_Special : shopItems_Special){
                shopFilteredItems.add(shopItem_Special);
            }
        }
        new ScrollPanelBuilder("shop_available_items_" + shopPageID){{
            set("height", "100%");
            set("horizontal", "false");
            set("style", "nifty-scrollpanel");
            
            final int rows = (int) FastMath.ceil(((float) shopFilteredItems.size()) / ITEMS_PER_ROW);
            panel(new PanelBuilder(){{
                childLayoutVertical();
                height(((rows * ITEMS_ROW_HEIGHT) + 10) + "px");
                padding("10px");
                backgroundColor("#000C");
                
                for(int i=0;i<rows;i++){
                    final int rowIndex = i;
                    panel(new PanelBuilder(){{
                        childLayoutHorizontal();
                        height(ITEMS_ROW_HEIGHT + "px");
                        
                        for(int r=0;r<ITEMS_PER_ROW;r++){
                            final int itemIndex = ((rowIndex * ITEMS_PER_ROW) + r);
                            if(itemIndex >= shopFilteredItems.size()){
                                break;
                            }
                            final EntityWrapper item = shopFilteredItems.get(itemIndex);
                            final String itemID = item.getComponent(ItemIDComponent.class).getID();
                            final ItemRecipe itemRecipe = getItemRecipe(itemID);
                            panel(new PanelBuilder(){{
                                childLayoutVertical();
                                width("55px");

                                image(new ImageBuilder("shop_item_" + shopPageID + "_" + itemIndex){{
                                    String imageFilePath = GUIItems.getImageFilePath(StaticEntityWorld.getEntityWorld(), item.getId());
                                    filename(imageFilePath);
                                    width("45px");
                                    height("45px");

                                    interactOnClick("buyItem(" + itemID + ")");
                                    setHoverEffect(this, "showShopItemInformation(" + itemID + ")", "hideShopItemInformation()");
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
                                        text(getRecipeGoldText(itemRecipe));
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
        hideShopItemInformation();
    }
    
    public void showShopItemInformation(String itemID){
        ItemRecipe itemRecipe = getItemRecipe(itemID);
        NameComponent nameComponent = StaticEntityWorld.getEntityWorld().getComponent(itemRecipe.getEntity(), NameComponent.class);
        getTextRenderer("shop_item_information_name").setText((nameComponent != null)?nameComponent.getName():"<Unnamed>");
        Element recipeContainer = getElementByID("shop_item_information_recipe");
        for(Element childElement : recipeContainer.getChildren()){
            childElement.markForRemoval();
        }
        createRecipeContainer(recipeContainer, itemRecipe, (recipeContainer.getWidth() / 2), recipeContainer.getWidth(), itemRecipe.getDepth(), itemRecipe.getDepth());
        getTextRenderer("shop_item_information_description").setText(itemRecipe.getDescription());
        getElementByID("shop_item_information").setVisible(true);
    }
    
    private void createRecipeContainer(final Element container, final ItemRecipe itemRecipe, final int x, int width, final int depth, final int maximumDepth){
        final int recipeContainerPaddingY = ((maximumDepth > 1)?20:50);
        new PanelBuilder(){{
            childLayoutVertical();
            x((x - (INGREDIENT_WIDTH / 2)) + "px");
            y(((int) ((recipeContainerPaddingY + (((maximumDepth - depth) + 0.5f) * ((container.getHeight() - (2 * recipeContainerPaddingY))) / (maximumDepth + 1))) - (INGREDIENT_HEIGHT / 2))) + "px");
            width(INGREDIENT_WIDTH + "px");
            height(INGREDIENT_HEIGHT + "px");
            
            image(new ImageBuilder(){{
                String imageFilePath = GUIItems.getImageFilePath(StaticEntityWorld.getEntityWorld(), itemRecipe.getEntity());
                filename(imageFilePath);
                width("45px");
                height("45px");
            }});
            panel(new PanelBuilder(){{
                childLayoutHorizontal();
                width(INGREDIENT_WIDTH + "px");
                height("15px");
                
                image(new ImageBuilder(){{
                    filename("Interface/hud/gold.png");
                    width("15px");
                    height("15px");
                }});
                text(new TextBuilder(){{
                    width("30px");
                    height("100%");
                    text(getRecipeGoldText(itemRecipe));
                    font("Interface/fonts/Verdana_12.fnt");
                    textHAlignCenter();
                }});
            }});
        }}.build(nifty, nifty.getCurrentScreen(), container);
        if(itemRecipe.getIngredients().length > 0){
            int subWidth = (width / itemRecipe.getIngredients().length);
            for(int i=0;i<itemRecipe.getIngredients().length;i++){
                int ingredientX = (int) ((x - (width / 2)) + ((i + 0.5f) * subWidth));
                createRecipeContainer(container, itemRecipe.getIngredients()[i], ingredientX, subWidth, (depth - 1), maximumDepth);
            }
        }
    }
    
    private static String getRecipeGoldText(ItemRecipe itemRecipe){
        String text = "";
        if(itemRecipe.getResolvedGold() != itemRecipe.getTotalGold()){
            text += "\\#00CE00#";
        }
        text += GUIUtil.getValueText(itemRecipe.getResolvedGold());
        return text;
    }
    
    public void hideShopItemInformation(){
        getElementByID("shop_item_information").setVisible(false);
    }
    
    public void buyItem(String itemID){
        SendPlayerCommandsAppState sendPlayerCommandsAppState = mainApplication.getStateManager().getState(SendPlayerCommandsAppState.class);
        sendPlayerCommandsAppState.sendCommand(new BuyItemCommand(itemID));
    }
    
    public void sellItem(String inventoryIndexString){
        int inventoryIndex = Integer.parseInt(inventoryIndexString);
        SendPlayerCommandsAppState sendPlayerCommandsAppState = mainApplication.getStateManager().getState(SendPlayerCommandsAppState.class);
        sendPlayerCommandsAppState.sendCommand(new SellItemCommand(inventoryIndex));
    }
}
