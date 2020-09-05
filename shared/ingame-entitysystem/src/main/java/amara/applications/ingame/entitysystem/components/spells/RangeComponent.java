package amara.applications.ingame.entitysystem.components.spells;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class RangeComponent {

    public RangeComponent() {

    }

    public RangeComponent(RangeType type, float distance) {
        this.type = type;
        this.distance = distance;
    }
    public enum RangeType {
        CENTER_TO_CENTER,
        EDGE_TO_EDGE
    }
    private RangeType type;
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float distance;

    public RangeType getType() {
        return type;
    }

    public float getDistance() {
        return distance;
    }
}
