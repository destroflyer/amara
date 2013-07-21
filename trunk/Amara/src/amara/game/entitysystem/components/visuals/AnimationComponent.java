/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.components.visuals;

/**
 *
 * @author Carl
 */
public class AnimationComponent{

    public AnimationComponent(String name, float speed){
        this.name = name;
        this.speed = speed;
    }
    private String name;
    private float speed;

    public String getName(){
        return name;
    }

    public float getSpeed(){
        return speed;
    }
}
