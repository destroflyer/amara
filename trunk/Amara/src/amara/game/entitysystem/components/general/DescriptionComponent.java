/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.general;

/**
 *
 * @author Carl
 */
public class DescriptionComponent{

    public DescriptionComponent(String description){
        this.description = description;
    }
    private String description;

    public String getDescription(){
        return description;
    }
}
