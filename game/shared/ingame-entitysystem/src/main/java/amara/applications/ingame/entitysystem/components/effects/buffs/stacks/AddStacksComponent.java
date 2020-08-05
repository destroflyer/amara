package amara.applications.ingame.entitysystem.components.effects.buffs.stacks;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class AddStacksComponent {

    public AddStacksComponent() {

    }

    public AddStacksComponent(int buffEntity, int stacks) {
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
