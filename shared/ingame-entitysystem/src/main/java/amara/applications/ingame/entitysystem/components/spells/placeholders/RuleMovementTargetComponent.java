package amara.applications.ingame.entitysystem.components.spells.placeholders;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class RuleMovementTargetComponent {

    public RuleMovementTargetComponent() {

    }

    public RuleMovementTargetComponent(int targetRulesEntity) {
        this.targetRulesEntity = targetRulesEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetRulesEntity;

    public int getTargetRulesEntity() {
        return targetRulesEntity;
    }
}
