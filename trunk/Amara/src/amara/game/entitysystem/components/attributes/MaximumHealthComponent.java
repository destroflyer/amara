/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.attributes;

/**
 *
 * @author Carl
 */
public class MaximumHealthComponent{

    public MaximumHealthComponent(float value){
        this.value = value;
    }
    private float value;

    public float getValue(){
        return value;
    }
}
