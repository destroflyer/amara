/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.effects.aggro;

import com.jme3.math.Vector2f;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.effects.*;
import amara.game.entitysystem.components.effects.aggro.*;
import amara.game.entitysystem.components.effects.casts.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.aggro.AggroUtil;
import amara.game.entitysystem.systems.units.UnitUtil;

/**
 *
 * @author Carl
 */
public class ApplyDrawTeamAggroSystem implements EntitySystem{
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(EntityWrapper effectImpact : entityWorld.getWrapped(entityWorld.getEntitiesWithAll(ApplyEffectImpactComponent.class, DrawTeamAggroComponent.class)))
        {
            int targetEntity = effectImpact.getComponent(ApplyEffectImpactComponent.class).getTargetID();
            EffectCastSourceComponent effectCastSourceComponent = effectImpact.getComponent(EffectCastSourceComponent.class);
            if((effectCastSourceComponent != null) && UnitUtil.isPlayerUnit(entityWorld, targetEntity)){
                int targetTeamEntity = entityWorld.getComponent(targetEntity, TeamComponent.class).getTeamEntity();
                Vector2f aggroCenter = entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition();
                for(int entity : entityWorld.getEntitiesWithAll(TeamComponent.class, PositionComponent.class)){
                    if(entity != targetEntity){
                        int teamEntity = entityWorld.getComponent(entity, TeamComponent.class).getTeamEntity();
                        Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                        float range = effectImpact.getComponent(DrawTeamAggroComponent.class).getRange();
                        if((teamEntity == targetTeamEntity) && (position.distanceSquared(aggroCenter) <= (range * range))){
                            AggroUtil.drawAggro(entityWorld, entity, effectCastSourceComponent.getSourceEntity());
                        }
                    }
                }
            }
        }
    }
}
