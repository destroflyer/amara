package amara.applications.ingame.entitysystem.components.buffs.status;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class RemainingBuffDurationComponent {

    public RemainingBuffDurationComponent() {

    }
    
    public RemainingBuffDurationComponent(float remainingDuration) {
        this.remainingDuration = remainingDuration;
    }
    @ComponentField(type=ComponentField.Type.TIMER)
    private float remainingDuration;

    public float getRemainingDuration() {
        return remainingDuration;
    }
}
