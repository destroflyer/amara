package amara.applications.ingame.entitysystem.components.game;

import com.jme3.network.serializing.Serializable;

@Serializable
public class NextEffectActionIndexComponent {

    public NextEffectActionIndexComponent() {

    }

    public NextEffectActionIndexComponent(int index) {
        this.index = index;
    }
    private int index;

    public int getIndex() {
        return index;
    }
}
