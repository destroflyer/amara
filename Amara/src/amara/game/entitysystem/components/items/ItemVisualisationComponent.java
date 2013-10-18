/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.items;

/**
 *
 * @author Carl
 */
public class ItemVisualisationComponent{

    public ItemVisualisationComponent(String name){
        this.name = name;
    }
    private String name;

    public String getName(){
        return name;
    }
}
