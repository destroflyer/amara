/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui;

import java.util.HashMap;
import amara.engine.gui.GameScreenController;
import amara.engine.settings.*;
import amara.engine.settings.types.*;
import de.lessvoid.nifty.NiftyEvent;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import de.lessvoid.nifty.controls.scrollpanel.builder.*;
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
    private int currentCategoryIndex = -1;
    private HashMap<String, String> editedSettings = new HashMap<String, String>();

    @Override
    public void onStartup(){
        super.onStartup();
        generateMenuNavigation();
        selectCategory("0");
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
                        
                        interactOnClick("selectCategory(" + categoryIndex + ")");
                    }});
                }});
            }
            panel(new PanelBuilder());
            panel(new PanelBuilder(){{
                childLayoutHorizontal();
                set("height", "32px");
                
                control(new ControlBuilder("button"){{
                    set("width", "100%");
                    set("height", "30px");
                    set("label", "Apply");

                    interactOnClick("applyChanges()");
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
    
    public void selectCategory(String indexString){
        int index = Integer.parseInt(indexString);
        if(index != currentCategoryIndex){
            if(currentCategoryIndex != -1){
                cacheEditedSettings(ingameSettings.getSubCategories()[currentCategoryIndex]);
                getElementByID("menu_content_" + currentCategoryIndex).markForRemoval();
            }
            currentCategoryIndex = index;
            Element menuContainer = getElementByID("menu_container");
            generateMenuContentBuilder(currentCategoryIndex).build(nifty, nifty.getCurrentScreen(), menuContainer);
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
                                set("text", setting.getTitle());
                                set("font", "Interface/fonts/Verdana_14.fnt");
                                set("width", "160px");
                                set("height", "30px");
                                set("textHAlign", "left");
                            }});
                            SettingType settingType = setting.getType();
                            if(settingType instanceof KeyType){
                                control(new ControlBuilder("button"){{
                                    set("x", "165px");
                                    set("y", "0px");
                                    set("width", "30px");
                                    set("height", "30px");
                                    set("label", "?");
                                }});
                            }
                            else if(settingType instanceof SliderType){
                                final SliderType sliderType = (SliderType) settingType;
                                control(new SliderBuilder(settingKey, false){{
                                    set("x", "165px");
                                    set("y", "3px");
                                    set("width", "160px");
                                    min(sliderType.getMinimum());
                                    max(sliderType.getMaximum());
                                    stepSize(sliderType.getStepSize());
                                    buttonStepSize(sliderType.getButtonStepSize());
                                    initial(Settings.toFloat(getSettingsValue(settingKey)));
                                }});
                                text(new TextBuilder(settingKey + SUFFIX_SLIDER_VALUE){{
                                    set("x", "330px");
                                    set("y", "0px");
                                    set("width", "62px");
                                    set("height", "30px");
                                    set("text", "???");
                                    set("font", "Interface/fonts/Verdana_14.fnt");
                                }});
                            }
                            else if(settingType instanceof BooleanType){
                                control(new CheckboxBuilder(settingKey){{
                                    set("x", "165px");
                                    set("y", "0px");
                                    checked(Settings.toBoolean(getSettingsValue(settingKey)));
                                }});
                            }
                            else if(settingType instanceof MouseButtonType){
                                control(new ControlBuilder("button"){{
                                    set("x", "165px");
                                    set("y", "0px");
                                    set("width", "100px");
                                    set("height", "30px");
                                    set("label", "???");
                                }});
                            }
                        }});
                    }
                }
            }});
        }};
    }
    
    private String getSettingsValue(String key){
        String value = editedSettings.get(key);
        return ((value != null)?value:Settings.get(key));
    }
    
    @NiftyEventSubscriber(pattern = ".*")
    public void onNiftyEvent(String elementID, NiftyEvent receivedEvent){
        if(receivedEvent instanceof SliderChangedEvent){
            SliderChangedEvent event = (SliderChangedEvent) receivedEvent;
            String settinsValue = Settings.toString(event.getValue());
            TextRenderer textRenderer = getTextRenderer(elementID + SUFFIX_SLIDER_VALUE);
            if(textRenderer != null){
                textRenderer.setText(settinsValue);
            }
            editedSettings.put(elementID, settinsValue);
        }
        else if(receivedEvent instanceof CheckBoxStateChangedEvent){
            CheckBoxStateChangedEvent event = (CheckBoxStateChangedEvent) receivedEvent;
            editedSettings.put(elementID, Settings.toString(event.isChecked()));
        }
    }
    
    private void cacheEditedSettings(SettingsCategory settingsCategory){
        if(settingsCategory.getSubCategories() != null){
            for(SettingsCategory settingsSubCategory : settingsCategory.getSubCategories()){
                cacheEditedSettings(settingsSubCategory);
            }
        }
        else{
            for(Setting setting : settingsCategory.getSettings()){
                String settingKey = setting.getKey();
                SettingType settingType = setting.getType();
                String settingValue = null;
                if(settingType instanceof KeyType){
                    
                }
                else if(settingType instanceof SliderType){
                    Slider slider = getSlider(settingKey);
                    settingValue = Settings.toString(slider.getValue());
                }
                else if(settingType instanceof BooleanType){
                    CheckBox checkBox = getCheckBox(settingKey);
                    settingValue = Settings.toString(checkBox.isChecked());
                }
                else if(settingType instanceof MouseButtonType){
                    
                }
                if(settingValue != null){
                    editedSettings.put(settingKey, settingValue);
                }
            }
        }
    }
    
    public void applyChanges(){
        for(String key : editedSettings.keySet()){
            String value = editedSettings.get(key);
            Settings.set(key, value);
        }
        Settings.saveFile();
        editedSettings.clear();
    }
    
    public void exitGame(){
        System.exit(0);
    }
}
