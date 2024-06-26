package amara.applications.ingame.entitysystem.components.buffs.status;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class ActiveBuffComponent {

    public ActiveBuffComponent() {
        
    }

    public ActiveBuffComponent(int targetEntity, int buffEntity) {
        this.targetEntity = targetEntity;
        this.buffEntity = buffEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetEntity;
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int buffEntity;

    public int getTargetEntity() {
        return targetEntity;
    }

    public int getBuffEntity() {
        return buffEntity;
    }
}
