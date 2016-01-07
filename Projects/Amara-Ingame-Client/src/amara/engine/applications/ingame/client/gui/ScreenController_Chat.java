/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.gui;

import amara.engine.applications.ingame.client.appstates.ClientChatAppState;
import amara.engine.gui.GameScreenController;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;

/**
 *
 * @author Carl
 */
public class ScreenController_Chat extends GameScreenController{

    public ScreenController_Chat(){
        super("start");
    }

    @Override
    public void onStartup(){
        super.onStartup();
        setChatVisible_Input(false);
        setChatVisible_Output(false);
    }
    
    @Override
    protected void initialize(){
        super.initialize();
        getElementByID("chat_input").addInputHandler(new KeyInputHandler(){
            
            @Override
            public boolean keyEvent(NiftyInputEvent inputEvent){
                if(inputEvent != null){
                    switch(inputEvent){
                        case SubmitText:
                            sendMessage();
                            return true;
                    }
                }
                return false;
            }
        });
    }

    public void setChatVisible_Input(boolean isVisible){
        getElementByID("chat_input").setVisible(isVisible);
        if(isVisible){
            getTextField("chat_input_text").setFocus();
        }
    }

    public void setChatVisible_Output(boolean isVisible){
        getElementByID("chat_output").setVisible(isVisible);
    }
    
    public boolean isOnlyChatOutputVisible(){
        return (getElementByID("chat_output").isVisible() && (!getElementByID("chat_input").isVisible()));
    }
    
    public void sendMessage(){
        TextField textField = getTextField("chat_input_text");
        String text = textField.getText();
        if(!text.isEmpty()){
            ClientChatAppState clientChatAppState = mainApplication.getStateManager().getState(ClientChatAppState.class);
            clientChatAppState.sendMessage(text);
            textField.setText("");
        }
        setChatVisible_Input(false);
    }
    
    public void addChatLine(String text){
        for(int i=5;i>0;i--){
            TextRenderer previouseLine = getTextRenderer("chat_line_" + (i - 1));
            TextRenderer currentLine = getTextRenderer("chat_line_" + i);
            currentLine.setText(previouseLine.getOriginalText());
        }
        getTextRenderer("chat_line_0").setText(text);
    }
}
