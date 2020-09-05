package amara.applications.ingame.entitysystem.components.effects.buffs;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class AddBuffComponent {

    public AddBuffComponent() {

    }

    public AddBuffComponent(int[] buffEntities, float duration) {
        this.buffEntities = buffEntities;
        this.duration = duration;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] buffEntities;
    @ComponentField(type=ComponentField.Type.TIMER)
    private float duration;

    public int[] getBuffEntities() {
        return buffEntities;
    }

    public float getDuration() {
        return duration;
    }
}
