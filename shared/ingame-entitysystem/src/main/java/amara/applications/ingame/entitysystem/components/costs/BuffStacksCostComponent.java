package amara.applications.ingame.entitysystem.components.costs;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class BuffStacksCostComponent {

    public BuffStacksCostComponent() {

    }

    public BuffStacksCostComponent(int buffEntity, int stacks) {
        this.buffEntity = buffEntity;
        this.stacks = stacks;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int buffEntity;
    @ComponentField(type=ComponentField.Type.STACKS)
    private int stacks;

    public int getBuffEntity() {
        return buffEntity;
    }

    public int getStacks() {
        return stacks;
    }
}
