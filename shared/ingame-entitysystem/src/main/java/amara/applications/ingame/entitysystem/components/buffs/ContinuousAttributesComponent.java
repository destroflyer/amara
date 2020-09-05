package amara.applications.ingame.entitysystem.components.buffs;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class ContinuousAttributesComponent {

    public ContinuousAttributesComponent() {

    }

    public ContinuousAttributesComponent(int bonusAttributesEntity) {
        this.bonusAttributesEntity = bonusAttributesEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int bonusAttributesEntity;

    public int getBonusAttributesEntity() {
        return bonusAttributesEntity;
    }
}
