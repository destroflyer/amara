/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.general;

/**
 *
 * @author Carl
 */
public class NameComponent{

    public NameComponent(String name){
        this.name = name;
    }
    private String name;

    public String getName(){
        return name;
    }
}
