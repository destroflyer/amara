package amara.applications.ingame.entitysystem.components.units.shields;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class ActiveShieldComponent {

    public ActiveShieldComponent() {

    }

    public ActiveShieldComponent(int targetEntity, int shieldEntity) {
        this.targetEntity = targetEntity;
        this.shieldEntity = shieldEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetEntity;
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int shieldEntity;

    public int getTargetEntity() {
        return targetEntity;
    }

    public int getShieldEntity() {
        return shieldEntity;
    }
}
