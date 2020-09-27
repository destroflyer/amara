package amara.applications.ingame.entitysystem.components.costs;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class ManaCostComponent {

    public ManaCostComponent() {

    }

    public ManaCostComponent(float mana) {
        this.mana = mana;
    }
    @ComponentField(type=ComponentField.Type.ATTRIBUTE)
    private float mana;

    public float getMana() {
        return mana;
    }
}
