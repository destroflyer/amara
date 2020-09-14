package amara.applications.ingame.entitysystem.components.effects.spells;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class ReduceRemainingCooldownAbsoluteComponent {

    public ReduceRemainingCooldownAbsoluteComponent(float duration) {
        this.duration = duration;
    }
    @ComponentField(type=ComponentField.Type.TIMER)
    private float duration;

    public float getDuration() {
        return duration;
    }
}
