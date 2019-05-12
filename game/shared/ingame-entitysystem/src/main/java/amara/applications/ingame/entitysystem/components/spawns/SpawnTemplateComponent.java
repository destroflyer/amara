/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.spawns;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

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
