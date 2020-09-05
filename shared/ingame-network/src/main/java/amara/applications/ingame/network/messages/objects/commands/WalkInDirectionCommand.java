/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.network.messages.objects.commands;

import com.jme3.math.Vector2f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Carl
 */
@Serializable
public class WalkInDirectionCommand extends Command {

    public WalkInDirectionCommand() {

    }

    public WalkInDirectionCommand(Vector2f direction) {
        this.direction = direction;
    }
    private Vector2f direction;

    public Vector2f getDirection() {
        return direction;
    }
}
