package amara.applications.ingame.entitysystem.components.effects.buffs.stacks;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class CopyStacksComponent {

    public CopyStacksComponent() {

    }

    public CopyStacksComponent(int sourceBuffEntity, int targetBuffEntity) {
        this.sourceBuffEntity = sourceBuffEntity;
        this.targetBuffEntity = targetBuffEntity;
    }
    @ComponentField(type=ComponentField.Type.STACKS)
    private int sourceBuffEntity;
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetBuffEntity;

    public int getSourceBuffEntity() {
        return sourceBuffEntity;
    }

    public int getTargetBuffEntity() {
        return targetBuffEntity;
    }
}
