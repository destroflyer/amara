/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.ai.actions;

import amara.ingame.ai.Action;
import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class WalkAction extends Action{

    public WalkAction(Vector2f position){
        this.position = position;
    }
    private Vector2f position;

    public Vector2f getPosition(){
        return position;
    }
}
