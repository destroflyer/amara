package amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class MovementTriggerMovedDistanceComponent {

    public MovementTriggerMovedDistanceComponent() {

    }

    public MovementTriggerMovedDistanceComponent(float distance) {
        this.distance = distance;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float distance;

    public float getDistance() {
        return distance;
    }
}
