package amara.applications.ingame.entitysystem.components.buffs.areas;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class AreaBuffComponent {

    public AreaBuffComponent() {

    }

    public AreaBuffComponent(int buffEntity) {
        this.buffEntity = buffEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int buffEntity;

    public int getBuffEntity() {
        return buffEntity;
    }
}
