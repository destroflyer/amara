package amara.applications.ingame.entitysystem.systems.targets;

import amara.applications.ingame.entitysystem.components.targets.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.systems.buffs.BuffUtil;
import amara.applications.ingame.entitysystem.systems.physics.PositionUtil;
import amara.libraries.entitysystem.*;

import java.util.List;
import java.util.stream.Collectors;

public class TargetUtil {

    public static List<Integer> getValidTargets(EntityWorld entityWorld, int sourceEntity, int targetRulesEntity) {
        return entityWorld.getEntitiesWithAll().stream()
                .filter(entity -> isValidTarget(entityWorld, sourceEntity, entity, targetRulesEntity))
                .collect(Collectors.toList());
    }

    public static boolean hasValidTarget(EntityWorld entityWorld, int sourceEntity, int targetRulesEntity) {
        return entityWorld.getEntitiesWithAll().stream()
                .anyMatch(entity -> isValidTarget(entityWorld, sourceEntity, entity, targetRulesEntity));
    }

    public static boolean isValidTarget(EntityWorld entityWorld, int sourceEntity, int targetEntity, int targetRulesEntity) {
        if (entityWorld.hasComponent(targetRulesEntity, RequireProjectileComponent.class)) {
            if (!entityWorld.hasComponent(targetEntity, IsProjectileComponent.class)) {
                return false;
            }
        } else if ((!entityWorld.hasComponent(targetRulesEntity, AcceptUntargetableComponent.class)) && (!entityWorld.hasComponent(targetEntity, IsTargetableComponent.class))) {
            return false;
        }
        if (entityWorld.hasComponent(targetRulesEntity, RequireCharacterComponent.class) && (!entityWorld.hasComponent(targetEntity, IsCharacterComponent.class))) {
            return false;
        }
        if (entityWorld.hasComponent(targetRulesEntity, RequireMinionComponent.class) && (!entityWorld.hasComponent(targetEntity, IsMinionComponent.class))) {
            return false;
        }
        TeamComponent sourceTeamComponent = entityWorld.getComponent(sourceEntity, TeamComponent.class);
        TeamComponent targetTeamComponent = entityWorld.getComponent(targetEntity, TeamComponent.class);
        if (entityWorld.hasComponent(targetRulesEntity, RequiresAllyComponent.class)) {
            if ((sourceTeamComponent == null) || (targetTeamComponent == null) || (sourceTeamComponent.getTeamEntity() != targetTeamComponent.getTeamEntity())) {
                return false;
            }
        }
        if (entityWorld.hasComponent(targetRulesEntity, RequiresEnemyComponent.class)) {
            if ((sourceTeamComponent != null) && (targetTeamComponent != null) && (sourceTeamComponent.getTeamEntity() == targetTeamComponent.getTeamEntity())) {
                return false;
            }
        }
        RequireAllBuffsComponent requireAllBuffsComponent = entityWorld.getComponent(targetRulesEntity, RequireAllBuffsComponent.class);
        if ((requireAllBuffsComponent != null) && (!BuffUtil.hasAllBuffs(entityWorld, targetEntity, requireAllBuffsComponent.getBuffEntities()))) {
            return false;
        }
        RequireAnyBuffsComponent requireAnyBuffsComponent = entityWorld.getComponent(targetRulesEntity, RequireAnyBuffsComponent.class);
        if ((requireAnyBuffsComponent != null) && (!BuffUtil.hasAnyBuffs(entityWorld, targetEntity, requireAnyBuffsComponent.getBuffEntities()))) {
            return false;
        }
        RequireNoBuffsComponent requireNoBuffsComponent = entityWorld.getComponent(targetRulesEntity, RequireNoBuffsComponent.class);
        if ((requireNoBuffsComponent != null) && BuffUtil.hasAnyBuffs(entityWorld, targetEntity, requireNoBuffsComponent.getBuffEntities())) {
            return false;
        }
        RequireEntityComponent requireEntityComponent = entityWorld.getComponent(targetRulesEntity, RequireEntityComponent.class);
        if ((requireEntityComponent != null) && (targetEntity != requireEntityComponent.getEntity())) {
            return false;
        }
        RequireMaximumDistanceComponent requireMaximumDistanceComponent = entityWorld.getComponent(targetRulesEntity, RequireMaximumDistanceComponent.class);
        if ((requireMaximumDistanceComponent != null) && (!PositionUtil.isInRange(entityWorld, sourceEntity, targetEntity, requireMaximumDistanceComponent.getDistance()))) {
            return false;
        }
        return true;
    }
}
