/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.effects.aggro;

import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.effects.*;
import amara.applications.ingame.entitysystem.components.effects.aggro.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.applications.ingame.entitysystem.systems.aggro.AggroUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class ApplyDrawTeamAggroSystem implements EntitySystem {

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds) {
        for (int effectImpactEntity : entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, DrawTeamAggroComponent.class)) {
            int targetEntity = entityWorld.getComponent(effectImpactEntity, ApplyEffectImpactComponent.class).getTargetEntity();
            if (entityWorld.hasComponent(targetEntity, IsCharacterComponent.class)) {
                EffectSourceComponent effectSourceComponent = entityWorld.getComponent(effectImpactEntity, EffectSourceComponent.class);
                if (effectSourceComponent != null) {
                    int targetTeamEntity = entityWorld.getComponent(targetEntity, TeamComponent.class).getTeamEntity();
                    Vector2f aggroCenter = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
                    for (int entity : entityWorld.getEntitiesWithAll(TeamComponent.class, PositionComponent.class)) {
                        if (entity != targetEntity) {
                            int teamEntity = entityWorld.getComponent(entity, TeamComponent.class).getTeamEntity();
                            if (teamEntity == targetTeamEntity) {
                                Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                                float range = entityWorld.getComponent(effectImpactEntity, DrawTeamAggroComponent.class).getRange();
                                if (position.distanceSquared(aggroCenter) <= (range * range)) {
                                    AggroUtil.drawAggro(entityWorld, entity, effectSourceComponent.getSourceEntity());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
