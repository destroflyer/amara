/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.visuals;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class TitleComponent{

    public TitleComponent(){
        
    }

    public TitleComponent(String title){
        this.title = title;
    }
    private String title;

    public String getTitle(){
        return title;
    }
}
