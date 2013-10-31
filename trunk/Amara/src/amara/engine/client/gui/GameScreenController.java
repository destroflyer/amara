/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.gui;

import java.util.List;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.RadioButton;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.*;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import amara.engine.client.ClientApplication;
import amara.engine.client.appstates.NiftyAppState;

/**
 *
 * @author Carl
 */
public class GameScreenController implements ScreenController{

    public GameScreenController(){
        
    }
    protected ClientApplication clientApplication;
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

    public ClientApplication getClientApplication(){
        return clientApplication;
    }

    public void setMainApplication(ClientApplication clientApplication){
        this.clientApplication = clientApplication;
    }

    public Nifty getNifty(){
        return nifty;
    }

    public void setNifty(Nifty nifty){
        this.nifty = nifty;
    }
    
    public String getCurrentScreenID(){
        return nifty.getCurrentScreen().getScreenId();
    }
    
    //Helper methods
    
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
        return nifty.getCurrentScreen().findNiftyControl(id, Button.class);
    }
    
    protected TextField getTextField(String id){
        return nifty.getCurrentScreen().findNiftyControl(id, TextField.class);
    }
    
    protected RadioButton getRadioButton(String id){
        return nifty.getCurrentScreen().findNiftyControl(id, RadioButton.class);
    }
    
    protected Element getElementByID(String id){
        return nifty.getCurrentScreen().findElementByName(id);
    }
    
    public void goToScreen(String screenID){
        clientApplication.getStateManager().getState(NiftyAppState.class).goToScreen(getClass(), screenID);
    }
    
    protected void removeAllChildElements(Element element){
        List<Element> children = element.getElements();
        for(int i=0;i<children.size();i++){
            Element child = children.get(i);
            child.markForRemoval();
        }
    }
    
    protected NiftyImage createImage(String filePath){
        return nifty.createImage(filePath, false);
    }
    
    protected void setHoverEffect(ElementBuilder elementBuilder, String onStartHover, String onEndHover){
        HoverEffectBuilder startHoverEffect = new HoverEffectBuilder("nop");
        startHoverEffect.effectParameter("onStartEffect", onStartHover);
        elementBuilder.onStartHoverEffect(startHoverEffect);
        HoverEffectBuilder endHoverEffect = new HoverEffectBuilder("nop");
        endHoverEffect.effectParameter("onStartEffect", onEndHover);
        elementBuilder.onEndHoverEffect(endHoverEffect);
    }
    
    //This method can be called to avoid the user interface to receive the event
    public void doNothing(){
        
    }
    
    //ScreenControllerInterface

    public void bind(Nifty nifty, Screen screen){
        
    }

    public void onStartScreen(){
        
    }

    public void onEndScreen(){
        
    }
}
