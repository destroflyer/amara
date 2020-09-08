package amara.applications.ingame.entitysystem.components.effects.units;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class AddShieldComponent {

    public AddShieldComponent() {

    }

    public AddShieldComponent(int shieldEntity, float duration) {
        this.shieldEntity = shieldEntity;
        this.duration = duration;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int shieldEntity;
    @ComponentField(type=ComponentField.Type.TIMER)
    private float duration;

    public int getShieldEntity() {
        return shieldEntity;
    }

    public float getDuration() {
        return duration;
    }
}
