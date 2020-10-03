package amara.applications.ingame.entitysystem.components.conditions;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class InRangeConditionComponent {

    public InRangeConditionComponent() {

    }

    public InRangeConditionComponent(int targetEntity, float distance) {
        this.targetEntity = targetEntity;
        this.distance = distance;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetEntity;
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float distance;

    public int getTargetEntity() {
        return targetEntity;
    }

    public float getDistance() {
        return distance;
    }
}
