/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.aggro;

import java.util.LinkedList;
import com.jme3.math.Vector2f;
import amara.Util;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import amara.game.entitysystem.components.spells.*;
import amara.game.entitysystem.components.units.*;
import amara.game.entitysystem.systems.targets.TargetUtil;

/**
 *
 * @author Philipp
 */
public class AggroSystem implements EntitySystem
{

    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for(Integer entity : entityWorld.getEntitiesWithAll(AutoAggroComponent.class, AutoAttackComponent.class, PositionComponent.class)){
            Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
            float autoAggroRange = entityWorld.getComponent(entity, AutoAggroComponent.class).getRange();
            int autoAttackEntity = entityWorld.getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            int targetRulesEntity = entityWorld.getComponent(autoAttackEntity, SpellTargetRulesComponent.class).getTargetRulesEntity();
            LinkedList<Integer> targets = new LinkedList<Integer>();
            for(int targetEntity : entityWorld.getEntitiesWithAll(PositionComponent.class)){
                float distanceSquared = position.distanceSquared(entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition());
                if((distanceSquared <= (autoAggroRange * autoAggroRange)) && TargetUtil.isValidTarget(entityWorld, entity, targetEntity, targetRulesEntity)){
                    targets.add(targetEntity);
                }
            }
            entityWorld.setComponent(entity, new TargetsInAggroRangeComponent(Util.convertToArray(targets)));
        }
        ComponentMapObserver observer = entityWorld.requestObserver(this, AutoAggroComponent.class);
        for(Integer entity : observer.getRemoved().getEntitiesWithAll(AutoAggroComponent.class)){
            entityWorld.removeComponent(entity, TargetsInAggroRangeComponent.class);
        }
    }
}
