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
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, AutoAggroComponent.class);
        for (int entity : entityWorld.getEntitiesWithAll(AutoAggroComponent.class, PositionComponent.class))
        {
            ArrayList<Integer> targets = new ArrayList<Integer>();
            for (int target : entityWorld.getEntitiesWithAll(PositionComponent.class))
            {
                if(isLegalTarget(entityWorld, entity, target))
                {
                    targets.add(target);
                }
            }
            entityWorld.setComponent(entity, new TargetsInAggroRangeComponent(listToArray(targets)));
        }
        for (int entity : observer.getRemoved().getEntitiesWithAll(AutoAggroComponent.class))
        {
            entityWorld.removeComponent(entity, TargetsInAggroRangeComponent.class);
        }
        observer.reset();
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
        float range = world.getComponent(agressor, AutoAggroComponent.class).getRange();
        if(world.getComponent(agressor, PositionComponent.class).getPosition().distanceSquared(world.getComponent(target, PositionComponent.class).getPosition()) <= range * range)
        {
            TeamComponent teamA = world.getComponent(agressor, TeamComponent.class);
            if(teamA == null) return true;
            TeamComponent teamB = world.getComponent(target, TeamComponent.class);
            if(teamB == null) return true;
            return teamA.getTeamEntityID() != teamB.getTeamEntityID();
        }
        return false;
    }
}
