/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.aggro;

import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.PositionComponent;
import amara.game.entitysystem.components.units.*;
import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class AggroSystem implements EntitySystem
{

    public void update(EntityWorld entityWorld, float deltaSeconds)
    {
        for (int entity : entityWorld.getCurrent().getEntitiesWithAll(AutoAggroComponent.class, PositionComponent.class))
        {
            ArrayList<Integer> targets = new ArrayList<Integer>();
            for (int target : entityWorld.getCurrent().getEntitiesWithAll(PositionComponent.class))
            {
                if(isLegalTarget(entityWorld, entity, target))
                {
                    targets.add(target);
                }
            }
            entityWorld.getCurrent().setComponent(entity, new TargetsInAggroRangeComponent(listToArray(targets)));
        }
        for (int entity : entityWorld.getRemoved().getEntitiesWithAll(AutoAggroComponent.class))
        {
            entityWorld.getCurrent().removeComponent(entity, TargetsInAggroRangeComponent.class);
        }
    }
    
    private static int[] listToArray(ArrayList<Integer> list)
    {
        int[] out = new int[list.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = list.get(i);
        }
        return out;
    }
    
    private boolean isLegalTarget(EntityWorld world, int agressor, int target)
    {
        if(world.getCurrent().getComponent(agressor, PositionComponent.class).getPosition().distanceSquared(world.getCurrent().getComponent(target, PositionComponent.class).getPosition()) <= world.getCurrent().getComponent(agressor, AutoAggroComponent.class).getRange())
        {
            return true;
        }
        return false;
    }
}
