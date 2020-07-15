package amara.applications.ingame.entitysystem.components.buffs;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class BuffStacksComponent {

    public BuffStacksComponent() {

    }

    public BuffStacksComponent(int stacksEntity) {
        this.stacksEntity = stacksEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int stacksEntity;

    public int getStacksEntity() {
        return stacksEntity;
    }
}
