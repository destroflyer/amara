/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.popups;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class PopupTextComponent{

    public PopupTextComponent(){
        
    }

    public PopupTextComponent(String text){
        this.text = text;
    }
    private String text;

    public String getText(){
        return text;
    }
}
