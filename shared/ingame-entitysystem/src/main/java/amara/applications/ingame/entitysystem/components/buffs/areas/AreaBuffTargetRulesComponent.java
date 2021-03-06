package amara.applications.ingame.entitysystem.components.buffs.areas;

import com.jme3.network.serializing.Serializable;
import amara.libraries.entitysystem.synchronizing.ComponentField;

@Serializable
public class AreaBuffTargetRulesComponent {

    public AreaBuffTargetRulesComponent() {
        
    }

    public AreaBuffTargetRulesComponent(int targetRulesEntity) {
        this.targetRulesEntity = targetRulesEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetRulesEntity;

    public int getTargetRulesEntity() {
        return targetRulesEntity;
    }
}
