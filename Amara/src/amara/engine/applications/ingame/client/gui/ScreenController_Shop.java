/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui;

import java.util.HashMap;
import java.util.LinkedList;
import com.jme3.math.FastMath;
import amara.engine.applications.ingame.client.appstates.SendPlayerCommandsAppState;
import amara.engine.applications.ingame.client.commands.*;
import amara.engine.applications.ingame.client.gui.objects.ItemRecipe;
import amara.engine.gui.GameScreenController;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.attributes.*;
import amara.game.entitysystem.components.general.*;
import amara.game.entitysystem.components.items.*;
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
    private final static int ITEMS_PER_ROW = 5;
    private final static int ITEMS_ROW_HEIGHT = 70;
    private static final int INGREDIENT_WIDTH = 45;
    private static final int INGREDIENT_HEIGHT = 60;
    private EntityWorld shopEntityWorld = new EntityWorld();
    private String[] shopItemTemplateNames = new String[]{
        "egos_sword","egos_shield","egos_ring",
        "boots","boots_of_haste","boots_of_ferocity","boots_of_sorcery","boots_of_intellect","boots_of_iron","boots_of_silence",
        "sword","reinforced_sword","greatsword","dagger","bow",
        "leather_armor","heavy_leather_armor","cotton_armor","iron_armor","arcane_vesture","enchanted_vesture",
        "book_of_vampirism","doomblade","hells_scream",
        "book_of_precision","book_of_extreme_precision","blinkstrike","new_dawn",
        "swift_dagger","swift_bow",
        "scepter","rod","the_untamed",
        "book_of_wisdom","moonlight","nightkiss","requiem",
        "book_of_vitality","ethers_armor","iron_shield","natures_protector",
        "reaper","soulblade","misty_arcaneblade"
    };
    private String[] shopItemTemplateNames_Special = new String[]{
        "zhonyas_hourglass","youmuus_ghostblade"
    };
    private EntityWrapper[] shopItems;
    private EntityWrapper[] shopItems_Special;
    private HashMap<String, ItemRecipe> itemsRecipes = new HashMap<String, ItemRecipe>();
    private String shopItemFilterText = "";
    private boolean[] shopItemFilters = new boolean[7];
    private boolean isUpdatingShopItemFilters;
    private LinkedList<EntityWrapper> shopFilteredItems = new LinkedList<EntityWrapper>();
    
    @Override
    public void onStartup(){
        super.onStartup();
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
            shopItems[i] = EntityTemplate.createFromTemplate(shopEntityWorld, "items/" + shopItemTemplateNames[i]);
        }
        shopItems_Special = new EntityWrapper[shopItemTemplateNames_Special.length];
        for(int i=0;i<shopItems_Special.length;i++){
            shopItems_Special[i] = EntityTemplate.createFromTemplate(shopEntityWorld, "items/" + shopItemTemplateNames_Special[i]);
        }
        //Preload recipes
        for(int i=0;i<shopItems.length;i++){
            getItemRecipe(shopItemTemplateNames[i]);
        }
        for(int i=0;i<shopItems_Special.length;i++){
            getItemRecipe(shopItemTemplateNames_Special[i]);
        }
    }
    
    private ItemRecipe getItemRecipe(String itemID){
        ItemRecipe itemRecipe = itemsRecipes.get(itemID);
        if(itemRecipe == null){
            EntityWrapper item = EntityTemplate.createFromTemplate(shopEntityWorld, "items/" + itemID);
            ItemRecipeComponent itemRecipeComponent = item.getComponent(ItemRecipeComponent.class);
            ItemRecipe[] ingredientsRecipes = new ItemRecipe[itemRecipeComponent.getItemIDs().length];
            int previousDepth = -1;
            for(int i=0;i<ingredientsRecipes.length;i++){
                ItemRecipe ingredientRecipe = getItemRecipe(itemRecipeComponent.getItemIDs()[i]);
                if(ingredientRecipe.getDepth() > previousDepth){
                    previousDepth = ingredientRecipe.getDepth();
                }
                ingredientsRecipes[i] = ingredientRecipe;
            }
            itemRecipe = new ItemRecipe(item, itemRecipeComponent.getGold(), ingredientsRecipes, (previousDepth + 1));
            itemsRecipes.put(itemID, itemRecipe);
        }
        return itemRecipe;
    }
    
    public void updateRecipeCosts(EntityWorld entityWorld, int[] inventoryItemEntities){
        ItemRecipe[] inventoryItemsRecipes = new ItemRecipe[inventoryItemEntities.length];
        for(int i=0;i<inventoryItemsRecipes.length;i++){
            String itemID = entityWorld.getComponent(inventoryItemEntities[i], ItemIDComponent.class).getID();
            inventoryItemsRecipes[i] = getItemRecipe(itemID);
        }
        for(ItemRecipe itemRecipe : itemsRecipes.values()){
            itemRecipe.updateResolvedGold(inventoryItemsRecipes);
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
        Element itemsScrollPanel = getElementByID("shop_available_items");
        if(itemsScrollPanel != null){
            itemsScrollPanel.markForRemoval();
        }
        shopFilteredItems.clear();
        String itemFilterTextLowerCase = shopItemFilterText.toLowerCase();
        for(int i=0;i<shopItems.length;i++){
            EntityWrapper item = shopItems[i];
            boolean isFiltered = true;
            String itemName = item.getComponent(NameComponent.class).getName();
            if(!itemName.toLowerCase().contains(itemFilterTextLowerCase)){
                isFiltered = false;
            }
            else if((shopItemFilters[1] && (!item.hasComponent(BonusFlatMaximumHealthComponent.class)))
            || (shopItemFilters[2] && (!item.hasComponent(BonusFlatAttackDamageComponent.class)))
            || (shopItemFilters[3] && (!item.hasComponent(BonusFlatAbilityPowerComponent.class)))
            || (shopItemFilters[4] && (!item.hasComponent(BonusFlatArmorComponent.class)))
            || (shopItemFilters[5] && (!item.hasComponent(BonusFlatMagicResistanceComponent.class)))
            || (shopItemFilters[6] && (!item.hasComponent(BonusFlatWalkSpeedComponent.class)))){
                isFiltered = false;
            }
            if(isFiltered){
                shopFilteredItems.add(item);
            }
        }
        if(itemFilterTextLowerCase.equals("etherblood")){
            shopFilteredItems.add(shopItems_Special[0]);
            shopFilteredItems.add(shopItems_Special[1]);
        }
        new ScrollPanelBuilder("shop_available_items"){{
            set("height", "100%");
            set("horizontal", "false");
            set("style", "nifty-listbox");
            
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

                                image(new ImageBuilder("shop_item_" + itemIndex){{
                                    filename("Interface/hud/items/" + itemID + ".png");
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
                                        text("" + getRecipeGoldText(itemRecipe));
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
        NameComponent nameComponent = itemRecipe.getItem().getComponent(NameComponent.class);
        getTextRenderer("shop_item_information_name").setText((nameComponent != null)?nameComponent.getName():"<Unnamed>");
        Element recipeContainer = getElementByID("shop_item_information_recipe");
        for(Element childElement : recipeContainer.getElements()){
            childElement.markForRemoval();
        }
        createRecipeContainer(recipeContainer, itemRecipe, (recipeContainer.getWidth() / 2), recipeContainer.getWidth(), itemRecipe.getDepth(), itemRecipe.getDepth());
        getTextRenderer("shop_item_information_description").setText(itemRecipe.getDescription());
        getElementByID("shop_item_information").setVisible(true);
    }
    
    private void createRecipeContainer(final Element container, final ItemRecipe itemRecipe, final int x, int width, final int depth, final int maximumDepth){
        final String itemID = itemRecipe.getItem().getComponent(ItemIDComponent.class).getID();
        final int recipeContainerPaddingY = ((maximumDepth > 1)?20:50);
        new PanelBuilder(){{
            childLayoutVertical();
            x((x - (INGREDIENT_WIDTH / 2)) + "px");
            y(((int) ((recipeContainerPaddingY + (((maximumDepth - depth) + 0.5f) * ((container.getHeight() - (2 * recipeContainerPaddingY))) / (maximumDepth + 1))) - (INGREDIENT_HEIGHT / 2))) + "px");
            width(INGREDIENT_WIDTH + "px");
            height(INGREDIENT_HEIGHT + "px");
            
            image(new ImageBuilder(){{
                filename("Interface/hud/items/" + itemID + ".png");
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
        text += itemRecipe.getResolvedGold();
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
