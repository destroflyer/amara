package amara.applications.ingame.entitysystem.components.buffs.status;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class TimeSinceLastRepeatingEffectComponent {

    public TimeSinceLastRepeatingEffectComponent() {

    }

    public TimeSinceLastRepeatingEffectComponent(float duration) {
        this.duration = duration;
    }
    @ComponentField(type=ComponentField.Type.TIMER)
    private float duration;

    public float getDuration() {
        return duration;
    }
}
