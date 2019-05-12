/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.aggro;

import java.util.LinkedList;
import com.jme3.math.Vector2f;
import amara.core.Util;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.systems.targets.TargetUtil;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Philipp
 */
public class AggroSystem implements EntitySystem{
    
    private LinkedList<Integer> tmpTargets = new LinkedList<Integer>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getEntitiesWithAll(AutoAggroComponent.class, AutoAttackComponent.class, PositionComponent.class)){
            Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
            float autoAggroRange = entityWorld.getComponent(entity, AutoAggroComponent.class).getRange();
            int autoAttackEntity = entityWorld.getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
            int targetRulesEntity = entityWorld.getComponent(autoAttackEntity, SpellTargetRulesComponent.class).getTargetRulesEntity();
            for(int targetEntity : entityWorld.getEntitiesWithAll(PositionComponent.class)){
                float distanceSquared = position.distanceSquared(entityWorld.getComponent(targetEntity, PositionComponent.class).getPosition());
                if((distanceSquared <= (autoAggroRange * autoAggroRange)) && TargetUtil.isValidTarget(entityWorld, entity, targetEntity, targetRulesEntity)){
                    tmpTargets.add(targetEntity);
                }
            }
            entityWorld.setComponent(entity, new TargetsInAggroRangeComponent(Util.convertToArray_Integer(tmpTargets)));
            tmpTargets.clear();
        }
        ComponentMapObserver observer = entityWorld.requestObserver(this, AutoAggroComponent.class);
        for(int entity : observer.getRemoved().getEntitiesWithAll(AutoAggroComponent.class)){
            entityWorld.removeComponent(entity, TargetsInAggroRangeComponent.class);
        }
    }
}
