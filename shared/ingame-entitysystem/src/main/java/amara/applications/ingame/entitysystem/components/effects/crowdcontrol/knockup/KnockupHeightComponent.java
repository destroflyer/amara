/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

/**
 *
 * @author Carl
 */
@Serializable
public class KnockupHeightComponent {

    public KnockupHeightComponent() {
        
    }

    public KnockupHeightComponent(float height) {
        this.height = height;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float height;

    public float getHeight() {
        return height;
    }
}
