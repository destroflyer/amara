package amara.applications.ingame.entitysystem.components.conditions;

import amara.libraries.entitysystem.synchronizing.ComponentField;
import com.jme3.network.serializing.Serializable;

@Serializable
public class HasRuleTargetConditionComponent {

    public HasRuleTargetConditionComponent() {

    }

    public HasRuleTargetConditionComponent(int targetRulesEntity) {
        this.targetRulesEntity = targetRulesEntity;
    }
    @ComponentField(type=ComponentField.Type.ENTITY)
    private int targetRulesEntity;

    public int getTargetRulesEntity() {
        return targetRulesEntity;
    }
}
