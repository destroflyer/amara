package amara.applications.ingame.entitysystem.components.units;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class BuffsComponent {

    public BuffsComponent() {

    }

    public BuffsComponent(int... buffStatusEntities) {
        this.buffStatusEntities = buffStatusEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] buffStatusEntities;

    public int[] getBuffStatusEntities() {
        return buffStatusEntities;
    }
}
