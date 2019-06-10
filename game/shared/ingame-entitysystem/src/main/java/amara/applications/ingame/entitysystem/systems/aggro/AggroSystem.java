/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.aggro;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.applications.ingame.entitysystem.systems.units.TeamVisionSystem;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Philipp
 */
public class AggroSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int entity : entityWorld.getEntitiesWithAll(AutoAggroComponent.class, AutoAttackComponent.class, PositionComponent.class)) {
            if (!entityWorld.hasComponent(entity, AggroTargetComponent.class)) {
                Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                float autoAggroRange = entityWorld.getComponent(entity, AutoAggroComponent.class).getRange();
                int autoAttackEntity = entityWorld.getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
                int targetRulesEntity = entityWorld.getComponent(autoAttackEntity, SpellTargetRulesComponent.class).getTargetRulesEntity();
                // Search and prioritize target
                int targetEntity = -1;
                int targetAggroPriority = Integer.MIN_VALUE;
                float targetDistanceSquared = Float.MAX_VALUE;
                for (int otherEntity : entityWorld.getEntitiesWithAny(PositionComponent.class)) {
                    // Target visible
                    if(TeamVisionSystem.hasTeamSight(entityWorld, entity, otherEntity)) {
                        Vector2f otherPosition = entityWorld.getComponent(otherEntity, PositionComponent.class).getPosition();
                        float distanceSquared = position.distanceSquared(otherPosition);
                        // Target in range
                        if (((autoAggroRange == -1) || (distanceSquared <= (autoAggroRange * autoAggroRange)))) {
                            AggroPriorityComponent aggroPriorityComponent = entityWorld.getComponent(otherEntity, AggroPriorityComponent.class);
                            int aggroPriority = ((aggroPriorityComponent != null) ? aggroPriorityComponent.getPriority() : 0);
                            // Target valid
                            if (TargetUtil.isValidTarget(entityWorld, entity, otherEntity, targetRulesEntity)) {
                                // Target prioritization
                                if ((targetEntity == -1)
                                        || (aggroPriority > targetAggroPriority)
                                        || ((aggroPriority == targetAggroPriority) && (distanceSquared < targetDistanceSquared))) {
                                    targetEntity = otherEntity;
                                    targetAggroPriority = aggroPriority;
                                    targetDistanceSquared = distanceSquared;
                                }
                            }
                        }
                    }
                }
                if (targetEntity != -1) {
                    AggroUtil.drawAggro(entityWorld, entity, targetEntity);
                }
            }
        }
    }
}
