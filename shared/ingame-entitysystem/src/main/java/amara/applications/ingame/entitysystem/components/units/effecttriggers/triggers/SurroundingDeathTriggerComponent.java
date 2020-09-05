package amara.applications.ingame.entitysystem.components.units.effecttriggers.triggers;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class SurroundingDeathTriggerComponent {

    public SurroundingDeathTriggerComponent() {

    }

    public SurroundingDeathTriggerComponent(float maximumDistance) {
        this.maximumDistance = maximumDistance;
    }
    @ComponentField(type=ComponentField.Type.DISTANCE)
    private float maximumDistance;

    public float getMaximumDistance() {
        return maximumDistance;
    }
}
