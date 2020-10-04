package amara.applications.ingame.entitysystem.components.targets;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class RequireMaximumDistanceComponent {

    public RequireMaximumDistanceComponent() {

    }

    public RequireMaximumDistanceComponent(float distance){
        this.distance = distance;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float distance;

    public float getDistance() {
        return distance;
    }
}
