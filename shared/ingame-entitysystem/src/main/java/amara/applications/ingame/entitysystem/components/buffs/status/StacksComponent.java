package amara.applications.ingame.entitysystem.components.buffs.status;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class StacksComponent {

    public StacksComponent() {

    }

    public StacksComponent(int stacks) {
        this.stacks = stacks;
    }
    @ComponentField(type=ComponentField.Type.STACKS)
    private int stacks;

    public int getStacks() {
        return stacks;
    }
}
