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

    public SpawnTemplateComponent(String... templateNames){
        this.templateNames = templateNames;
    }
    private String[] templateNames;

    public String[] getTemplateNames(){
        return templateNames;
    }
}
