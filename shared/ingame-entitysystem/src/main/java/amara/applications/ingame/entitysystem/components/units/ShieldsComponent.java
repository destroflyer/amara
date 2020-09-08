package amara.applications.ingame.entitysystem.components.units;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class ShieldsComponent {

    public ShieldsComponent() {

    }

    public ShieldsComponent(int... shieldStatusEntities) {
        this.shieldStatusEntities = shieldStatusEntities;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int[] shieldStatusEntities;

    public int[] getShieldStatusEntities() {
        return shieldStatusEntities;
    }
}
