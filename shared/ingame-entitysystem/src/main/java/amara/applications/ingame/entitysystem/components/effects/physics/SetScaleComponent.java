package amara.applications.ingame.entitysystem.components.effects.physics;

import com.jme3.network.serializing.Serializable;

@Serializable
public class SetScaleComponent {

    public SetScaleComponent() {

    }

    public SetScaleComponent(float scale) {
        this.scale = scale;
    }
    private float scale;

    public float getScale() {
        return scale;
    }
}
