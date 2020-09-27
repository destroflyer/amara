package amara.applications.ingame.entitysystem.components.attributes;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class BonusFlatManaRegenerationComponent {

    public BonusFlatManaRegenerationComponent() {

    }

    public BonusFlatManaRegenerationComponent(float value) {
        this.value = value;
    }
    @ComponentField(type=ComponentField.Type.ATTRIBUTE)
    private float value;

    public float getValue() {
        return value;
    }
}
