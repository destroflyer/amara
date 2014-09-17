/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.effects.general;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class RemoveComponentsComponent{

    public RemoveComponentsComponent(){
        
    }

    public RemoveComponentsComponent(Class... componentsClasses){
        componentsClassesNames = new String[componentsClasses.length];
        for(int i=0;i<componentsClassesNames.length;i++){
            componentsClassesNames[i] = componentsClasses[i].getName();
        }
    }
    private String[] componentsClassesNames;

    public String[] getComponentsClassesNames(){
        return componentsClassesNames;
    }
}
