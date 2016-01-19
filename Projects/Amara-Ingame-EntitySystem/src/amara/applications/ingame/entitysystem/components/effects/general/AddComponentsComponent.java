/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.general;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class AddComponentsComponent{

    public AddComponentsComponent(){
        
    }

    public AddComponentsComponent(Object... components){
        this.components = components;
    }
    private Object[] components;

    public Object[] getComponents(){
        return components;
    }
}
