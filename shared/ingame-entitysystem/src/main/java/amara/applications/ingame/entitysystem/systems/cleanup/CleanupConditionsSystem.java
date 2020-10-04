package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.conditions.HasRuleTargetConditionComponent;
import amara.applications.ingame.entitysystem.components.conditions.OrConditionsComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntitySystem;
import amara.libraries.entitysystem.EntityWorld;

public class CleanupConditionsSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        ComponentMapObserver observer = entityWorld.requestObserver(
            this,
            OrConditionsComponent.class,
            HasRuleTargetConditionComponent.class
        );
        for (int conditionEntity : observer.getRemoved().getEntitiesWithAny(OrConditionsComponent.class)) {
            int[] conditionEntities = observer.getRemoved().getComponent(conditionEntity, OrConditionsComponent.class).getConditionEntities();
            CleanupUtil.tryCleanupEntities(entityWorld, conditionEntities);
        }
        for (int conditionEntity : observer.getRemoved().getEntitiesWithAny(HasRuleTargetConditionComponent.class)) {
            int targetRulesEntity = observer.getRemoved().getComponent(conditionEntity, HasRuleTargetConditionComponent.class).getTargetRulesEntity();
            CleanupUtil.tryCleanupEntity(entityWorld, targetRulesEntity);
        }
    }
}
