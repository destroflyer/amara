/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.spawns;

/**
 *
 * @author Carl
 */
public class SpawnTemplateComponent{

    public SpawnTemplateComponent(String templateName){
        this.templateName = templateName;
    }
    private String templateName;

    public String getTemplateName(){
        return templateName;
    }
}
