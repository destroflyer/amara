package amara.applications.ingame.entitysystem.components.conditions;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class HasBuffConditionComponent {

    public HasBuffConditionComponent() {

    }

    public HasBuffConditionComponent(int buffEntity, int requiredStacks) {
        this.buffEntity = buffEntity;
        this.requiredStacks = requiredStacks;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int buffEntity;
    @ComponentField(type=ComponentField.Type.STACKS)
    private int requiredStacks;

    public int getBuffEntity() {
        return buffEntity;
    }

    public int getRequiredStacks() {
        return requiredStacks;
    }
}
