/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spawns;

import com.jme3.network.serializing.Serializable;
import amara.game.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class SpawnTemplateComponent{

    public SpawnTemplateComponent(){
        
    }
    
    public SpawnTemplateComponent(String... templateNames){
        this.templateNames = templateNames;
    }
    @ComponentField(type=ComponentField.Type.TEMPLATE)
    private String[] templateNames;

    public String[] getTemplateNames(){
        return templateNames;
    }
}