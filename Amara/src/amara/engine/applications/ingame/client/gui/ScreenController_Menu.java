/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui;

import java.util.HashMap;
import amara.engine.gui.GameScreenController;
import amara.engine.input.Keys;
import amara.engine.input.events.MouseClickEvent;
import amara.engine.settings.*;
import amara.engine.settings.types.*;
import de.lessvoid.nifty.NiftyEvent;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import de.lessvoid.nifty.controls.dropdown.builder.DropDownBuilder;
import de.lessvoid.nifty.controls.scrollpanel.builder.ScrollPanelBuilder;
import de.lessvoid.nifty.controls.slider.builder.SliderBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;

/**
 *
 * @author Carl
 */
public class ScreenController_Menu extends GameScreenController{

    public ScreenController_Menu(){
        super("start");
    }
    private static final String SUFFIX_SLIDER_VALUE = "_value";
    private IngameSettings ingameSettings = new IngameSettings();
    private int pageID;
    private int currentCategoryIndex = -1;
    private HashMap<String, String[]> builtDropDownSettings = new HashMap<String, String[]>();
    private HashMap<String, String> editedSettings = new HashMap<String, String>();
    private String currentEditingSettingKey;
    private boolean isReadingKeyInput;

    @Override
    public void onStartup(){
        super.onStartup();
        generateMenuNavigation();
        selectCategory(0);
    }
    
    private void generateMenuNavigation(){
        Element menuContainer = getElementByID("menu_container");
        new PanelBuilder(){{
            childLayoutVertical();
            set("width", "200px");
            set("height", "100%");
            set("padding", "10px");
            set("backgroundColor", "#000D");

            for(int i=0;i<ingameSettings.getSubCategories().length;i++){
                final int categoryIndex = i;
                final SettingsCategory settingsCategory = ingameSettings.getSubCategories()[i];
                panel(new PanelBuilder(){{
                    childLayoutHorizontal();
                    set("height", "32px");
                    
                    control(new ControlBuilder("button"){{
                        set("width", "100%");
                        set("height", "30px");
                        set("label", settingsCategory.getTitle());
                        
                        interactOnClick("selectCategory_Nifty(" + categoryIndex + ")");
                    }});
                }});
            }
            panel(new PanelBuilder());
            panel(new PanelBuilder(){{
                childLayoutHorizontal();
                set("height", "32px");
                
                control(new ControlBuilder("button"){{
                    set("width", "50%");
                    set("height", "30px");
                    set("label", "Apply");

                    interactOnClick("applyChanges()");
                }});
                control(new ControlBuilder("button"){{
                    set("width", "50%");
                    set("height", "30px");
                    set("label", "Reset");

                    interactOnClick("reset()");
                }});
            }});
            panel(new PanelBuilder(){{
                childLayoutHorizontal();
                set("height", "32px");
                
                control(new ControlBuilder("button"){{
                    set("width", "100%");
                    set("height", "30px");
                    set("label", "Exit Game");

                    interactOnClick("exitGame()");
                }});
            }});
        }}.build(nifty, nifty.getCurrentScreen(), menuContainer);
    }
    
    public void toggleMenuVisible(){
        setMenuVisible(!getElementByID("menu_window").isVisible());
    }
    
    public void setMenuVisible(boolean isVisible){
        Element menuWindow = getElementByID("menu_window");
        menuWindow.setVisible(isVisible);
    }
    
    public void selectCategory_Nifty(String indexString){
        int index = Integer.parseInt(indexString);
        if(index != currentCategoryIndex){
            selectCategory(index);
        }
    }
    
    public void selectCategory(int index){
        if(currentCategoryIndex != -1){
            getElementByID("menu_content_" + currentCategoryIndex).markForRemoval();
        }
        pageID++;
        currentCategoryIndex = index;
        Element menuContainer = getElementByID("menu_container");
        builtDropDownSettings.clear();
        generateMenuContentBuilder(currentCategoryIndex).build(nifty, nifty.getCurrentScreen(), menuContainer);
        for(String settingKey : builtDropDownSettings.keySet()){
            String[] items = builtDropDownSettings.get(settingKey);
            //Retrieve this before adding the items since the event listener updates the editedSettings hashmap
            int mouseButtonIndex = Settings.toInteger(getSettingValue(settingKey));
            DropDown dropDown = getDropDown(pageID + "_" + settingKey);
            for(String item : items){
                dropDown.addItem(item);
            }
            dropDown.selectItemByIndex(mouseButtonIndex);
        }
    }
    
    private ScrollPanelBuilder generateMenuContentBuilder(int categoryIndex){
        final SettingsCategory settingsCategory = ingameSettings.getSubCategories()[categoryIndex];
        return new ScrollPanelBuilder("menu_content_" + categoryIndex){{
            set("width", "433px");
            set("height", "100%");
            set("horizontal", "false");
            set("style", "nifty-listbox");

            panel(new PanelBuilder(){{
                childLayoutVertical();
                set("width", "100%");
                set("padding", "10px");
                set("backgroundColor", "#111F");

                for(int r=0;r<settingsCategory.getSubCategories().length;r++){
                    final SettingsCategory settingsSubCategory = settingsCategory.getSubCategories()[r];
                    text(new TextBuilder(){{
                        set("text", settingsSubCategory.getTitle());
                        set("font", "Interface/fonts/Verdana_14.fnt");
                        set("height", "30px");
                    }});
                    for(int z=0;z<settingsSubCategory.getSettings().length;z++){
                        final Setting setting = settingsSubCategory.getSettings()[z];
                        final String settingKey = setting.getKey();
                        panel(new PanelBuilder(){{
                            childLayoutAbsolute();
                            set("width", "100%");
                            set("height", "32px");

                            text(new TextBuilder(){{
                                set("x", "0px");
                                set("y", "0px");
                                set("width", "160px");
                                set("height", "30px");
                                set("text", setting.getTitle());
                                set("font", "Interface/fonts/Verdana_14.fnt");
                                set("textHAlign", "left");
                            }});
                            SettingType settingType = setting.getType();
                            String elementID = (pageID + "_" + settingKey);
                            if(settingType instanceof KeyType){
                                control(new ControlBuilder(elementID, "button"){{
                                    set("x", "165px");
                                    set("y", "0px");
                                    set("width", "30px");
                                    set("height", "30px");
                                    set("label", getKeyTitle(Settings.toInteger(getSettingValue(settingKey))));
                                    
                                    interactOnClick("readKeyInput(" + settingKey + ")");
                                }});
                            }
                            else if(settingType instanceof SliderType){
                                final SliderType sliderType = (SliderType) settingType;
                                control(new SliderBuilder(elementID, false){{
                                    set("x", "165px");
                                    set("y", "3px");
                                    set("width", "160px");
                                    min(sliderType.getMinimum());
                                    max(sliderType.getMaximum());
                                    stepSize(sliderType.getStepSize());
                                    buttonStepSize(sliderType.getButtonStepSize());
                                    initial(Settings.toFloat(getSettingValue(settingKey)));
                                }});
                                text(new TextBuilder(elementID + SUFFIX_SLIDER_VALUE){{
                                    set("x", "330px");
                                    set("y", "0px");
                                    set("width", "62px");
                                    set("height", "30px");
                                    set("text", "???");
                                    set("font", "Interface/fonts/Verdana_14.fnt");
                                }});
                            }
                            else if(settingType instanceof BooleanType){
                                control(new CheckboxBuilder(elementID){{
                                    set("x", "165px");
                                    set("y", "3px");
                                    checked(Settings.toBoolean(getSettingValue(settingKey)));
                                }});
                            }
                            else if(settingType instanceof MouseButtonType){
                                control(new DropDownBuilder(elementID){{
                                    set("x", "165px");
                                    set("y", "3px");
                                    set("width", "100px");
                                }});
                                String[] items = new String[MouseClickEvent.Button.values().length];
                                for(int i=0;i<items.length;i++){
                                    items[i] = getMouseButtonTitle(MouseClickEvent.Button.values()[i]);
                                }
                                builtDropDownSettings.put(settingKey, items);
                            }
                        }});
                    }
                }
            }});
        }};
    }
    
    @NiftyEventSubscriber(pattern = ".*")
    public void onNiftyEvent(String elementID, NiftyEvent receivedEvent){
        if(receivedEvent instanceof SliderChangedEvent){
            SliderChangedEvent event = (SliderChangedEvent) receivedEvent;
            String settingKey = getSettingKey(elementID);
            String settingValue = Settings.toString(event.getValue());
            TextRenderer textRenderer = getTextRenderer(elementID + SUFFIX_SLIDER_VALUE);
            if(textRenderer != null){
                textRenderer.setText(settingValue);
            }
            editedSettings.put(settingKey, settingValue);
        }
        else if(receivedEvent instanceof CheckBoxStateChangedEvent){
            CheckBoxStateChangedEvent event = (CheckBoxStateChangedEvent) receivedEvent;
            editedSettings.put(getSettingKey(elementID), Settings.toString(event.isChecked()));
        }
        else if(receivedEvent instanceof DropDownSelectionChangedEvent){
            DropDownSelectionChangedEvent event = (DropDownSelectionChangedEvent) receivedEvent;
            editedSettings.put(getSettingKey(elementID), Settings.toString(event.getSelectionItemIndex()));
        }
    }
    
    private String getSettingKey(String elementID){
        return elementID.substring(("" + pageID).length() + 1);
    }
    
    private String getSettingValue(String key){
        String value = editedSettings.get(key);
        return ((value != null)?value:Settings.get(key));
    }
    
    public void applyChanges(){
        for(String key : editedSettings.keySet()){
            String value = editedSettings.get(key);
            Settings.set(key, value);
        }
        Settings.saveFile();
        editedSettings.clear();
    }
    
    public void reset(){
        editedSettings.clear();
        selectCategory(currentCategoryIndex);
    }
    
    public void exitGame(){
        System.exit(0);
    }

    public void readKeyInput(String settingKey){
        if(!isReadingKeyInput){
            getButton(pageID + "_" + settingKey).setText("");
            currentEditingSettingKey = settingKey;
            isReadingKeyInput = true;
        }
    }
    
    public void readKey(int keyCode){
        editedSettings.put(currentEditingSettingKey, Settings.toString(keyCode));
        getButton(pageID + "_" + currentEditingSettingKey).setText(getKeyTitle(keyCode));
        currentEditingSettingKey = null;
        isReadingKeyInput = false;
    }

    public boolean isReadingKeyInput(){
        return isReadingKeyInput;
    }
    
    private static String getKeyTitle(int keyCode){
        return Keys.getTitle(keyCode);
    }
    
    private static String getMouseButtonTitle(MouseClickEvent.Button mouseButton){
        return mouseButton.name();
    }
}
