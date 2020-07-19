package amara.applications.ingame.entitysystem.components.effects;

import com.jme3.network.serializing.Serializable;

@Serializable
public class EffectSourceActionIndexComponent {

    public EffectSourceActionIndexComponent() {

    }

    public EffectSourceActionIndexComponent(int index) {
        this.index = index;
    }
    private int index;

    public int getIndex() {
        return index;
    }
}
