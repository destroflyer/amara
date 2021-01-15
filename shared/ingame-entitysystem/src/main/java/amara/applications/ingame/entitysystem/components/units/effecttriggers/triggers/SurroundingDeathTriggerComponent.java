package amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class SurroundingDeathTriggerComponent {

    public SurroundingDeathTriggerComponent() {

    }

    public SurroundingDeathTriggerComponent(float maximumDistance, int targetRulesEntity) {
        this.maximumDistance = maximumDistance;
        this.targetRulesEntity = targetRulesEntity;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float maximumDistance;
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetRulesEntity;

    public float getMaximumDistance() {
        return maximumDistance;
    }

    public int getTargetRulesEntity() {
        return targetRulesEntity;
    }
}
