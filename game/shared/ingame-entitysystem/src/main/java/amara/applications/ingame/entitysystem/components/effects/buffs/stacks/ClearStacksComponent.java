package amara.applications.ingame.entitysystem.components.effects.buffs.stacks;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class ClearStacksComponent {

    public ClearStacksComponent() {

    }

    public ClearStacksComponent(int buffEntity) {
        this.buffEntity = buffEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int buffEntity;

    public int getBuffEntity() {
        return buffEntity;
    }
}
