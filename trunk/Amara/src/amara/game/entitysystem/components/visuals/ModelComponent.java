/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.visuals;

import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class ModelComponent{

    public ModelComponent(){
        
    }
    
    public ModelComponent(String modelSkinPath){
        this.modelSkinPath = modelSkinPath;
    }
    private String modelSkinPath;

    public String getModelSkinPath(){
        return modelSkinPath;
    }
}
