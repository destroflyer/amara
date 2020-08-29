package amara.applications.ingame.entitysystem.components.units;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class AttributesPerLevelComponent {

    public AttributesPerLevelComponent() {

    }

    public AttributesPerLevelComponent(int bonusAttributesEntity, float exponentialBase) {
        this.bonusAttributesEntity = bonusAttributesEntity;
        this.exponentialBase = exponentialBase;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int bonusAttributesEntity;
    private float exponentialBase;

    public int getBonusAttributesEntity() {
        return bonusAttributesEntity;
    }

    public float getExponentialBase() {
        return exponentialBase;
    }
}
