package amara.applications.ingame.entitysystem.components.effects.mana;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class ResultingAddManaComponent {

    public ResultingAddManaComponent() {

    }

    public ResultingAddManaComponent(float value) {
        this.value = value;
    }
    @ComponentField(type=ComponentField.Type.ATTRIBUTE)
    private float value;

    public float getValue() {
        return value;
    }
}
