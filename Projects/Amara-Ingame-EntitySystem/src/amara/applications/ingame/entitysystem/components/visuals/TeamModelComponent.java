/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.visuals;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class TeamModelComponent{

    public TeamModelComponent(){
        
    }
    
    public TeamModelComponent(String modelSkinPath){
        this.modelSkinPath = modelSkinPath;
    }
    @ComponentField(type=ComponentField.Type.FILEPATH)
    private String modelSkinPath;

    public String getModelSkinPath(){
        return modelSkinPath;
    }
}
