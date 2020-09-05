package amara.applications.ingame.entitysystem.components.effects.units;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class SetAutoAggroComponent {

    public SetAutoAggroComponent() {

    }

    public SetAutoAggroComponent(float range){
        this.range = range;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float range;

    public float getRange() {
        return range;
    }
}
