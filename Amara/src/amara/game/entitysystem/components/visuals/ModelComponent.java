/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.visuals;

/**
 *
 * @author Carl
 */
public class ModelComponent{

    public ModelComponent(String modelSkinPath){
        this.modelSkinPath = modelSkinPath;
    }
    private String modelSkinPath;

    public String getModelSkinPath(){
        return modelSkinPath;
    }
}
