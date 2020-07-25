package amara.applications.ingame.entitysystem.components.spawns;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class SpawnMovementDistanceComponent {

    public SpawnMovementDistanceComponent() {

    }

    public SpawnMovementDistanceComponent(float distance) {
        this.distance = distance;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float distance;

    public float getDistance() {
        return distance;
    }
}