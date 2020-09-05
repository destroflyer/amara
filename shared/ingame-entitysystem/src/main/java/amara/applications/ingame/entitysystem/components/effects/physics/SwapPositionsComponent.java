package amara.applications.ingame.entitysystem.components.effects.physics;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class SwapPositionsComponent {

    public SwapPositionsComponent() {

    }

    public SwapPositionsComponent(int targetEntity) {
        this.targetEntity = targetEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetEntity;

    public int getTargetEntity() {
        return targetEntity;
    }
}
