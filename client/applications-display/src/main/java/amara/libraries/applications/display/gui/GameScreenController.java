package amara.libraries.applications.display.gui;

import java.util.List;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.appstates.NiftyAppState;
import com.jme3.niftygui.RenderImageJme;
import com.jme3.texture.Texture2D;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.effects.Effect;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.effects.impl.Hint;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.*;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class GameScreenController implements ScreenController {

    public GameScreenController(String screenID) {
        this.screenID = screenID;
    }
    private String screenID;
    protected DisplayApplication mainApplication;
    protected Nifty nifty;
    private boolean isInitialized = false;
    
    public void onStartup(){
        if(!isInitialized){
            initialize();
            isInitialized = true;
        }
    }
    
    protected void initialize(){
        
    }

    public DisplayApplication getMainApplication(){
        return mainApplication;
    }

    public void setMainApplication(DisplayApplication mainApplication){
        this.mainApplication = mainApplication;
    }

    public Nifty getNifty(){
        return nifty;
    }

    public void setNifty(Nifty nifty){
        this.nifty = nifty;
    }
    
    public boolean isVisible(){
        return nifty.getCurrentScreen().getScreenId().equals(screenID);
    }

    // Helper methods

    protected PanelRenderer getPanelRenderer(String id){
        return getElementByID(id).getRenderer(PanelRenderer.class);
    }
    
    protected TextRenderer getTextRenderer(String id){
        return getElementByID(id).getRenderer(TextRenderer.class);
    }
    
    protected ImageRenderer getImageRenderer(String id){
        return getElementByID(id).getRenderer(ImageRenderer.class);
    }
    
    protected Button getButton(String id){
        return nifty.getScreen(screenID).findNiftyControl(id, Button.class);
    }
    
    protected TextField getTextField(String id){
        return nifty.getScreen(screenID).findNiftyControl(id, TextField.class);
    }
    
    protected CheckBox getCheckBox(String id){
        return nifty.getScreen(screenID).findNiftyControl(id, CheckBox.class);
    }
    
    protected RadioButton getRadioButton(String id){
        return nifty.getScreen(screenID).findNiftyControl(id, RadioButton.class);
    }
    
    protected Slider getSlider(String id){
        return nifty.getScreen(screenID).findNiftyControl(id, Slider.class);
    }
    
    protected DropDown getDropDown(String id){
        return nifty.getScreen(screenID).findNiftyControl(id, DropDown.class);
    }
    
    protected ScrollPanel getScrollPanel(String id){
        return nifty.getScreen(screenID).findNiftyControl(id, ScrollPanel.class);
    }
    
    protected Window getWindow(String id){
        return nifty.getScreen(screenID).findNiftyControl(id, Window.class);
    }
    
    protected Element getElementByID(String id){
        return nifty.getScreen(screenID).findElementById(id);
    }
    
    public void goToScreen(String screenID){
        mainApplication.getStateManager().getState(NiftyAppState.class).goToScreen(getClass(), screenID);
    }
    
    protected void removeAllChildElements(Element element){
        List<Element> children = element.getChildren();
        for(int i=0;i<children.size();i++){
            Element child = children.get(i);
            child.markForRemoval();
        }
    }
    
    protected NiftyImage createImage(String filePath){
        return nifty.createImage(filePath, false);
    }
    
    protected NiftyImage createImage(Texture2D texture2D){
        return new NiftyImage(nifty.getRenderEngine(), new RenderImageJme(texture2D));
    }
    
    protected void setHoverEffect(ElementBuilder elementBuilder, String onStartHover, String onEndHover){
        HoverEffectBuilder startHoverEffect = new HoverEffectBuilder("nop");
        startHoverEffect.effectParameter("onStartEffect", onStartHover);
        elementBuilder.onStartHoverEffect(startHoverEffect);
        HoverEffectBuilder endHoverEffect = new HoverEffectBuilder("nop");
        endHoverEffect.effectParameter("onStartEffect", onEndHover);
        elementBuilder.onEndHoverEffect(endHoverEffect);
    }
    
    protected void showHintText(String elementID, String description){
        Effect hoverEffect = getHintEffect(elementID);
        hoverEffect.enableHover(null);
        hoverEffect.getParameters().setProperty("hintText", description);
    }
    
    protected void hideHintText(String elementID){
        Effect hoverEffect = getHintEffect(elementID);
        hoverEffect.disableHover();
    }
    
    protected Effect getHintEffect(String elementID){
        return getElementByID(elementID).getEffects(EffectEventId.onHover, Hint.class).get(0);
    }

    protected void loseFocusWithoutAssigningNext(Element element) {
        FocusHandler focusHandler = nifty.getCurrentScreen().getFocusHandler();
        focusHandler.lostKeyboardFocus(element);
        focusHandler.lostMouseFocus(element);
    }

    // ScreenController interface

    @Override
    public void bind(Nifty nifty, Screen screen) {

    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }
}
