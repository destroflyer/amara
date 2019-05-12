/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.ai.goals;

import java.util.LinkedList;
import amara.applications.ingame.entitysystem.ai.actions.WalkAction;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.ingame.ai.Action;
import amara.ingame.ai.Goal;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class WalkToEnemyNexusGoal extends Goal{
    
    private int enemyNexusEntity;

    @Override
    public boolean isEnabled(EntityWorld entityWorld, int entity){
        return entityWorld.hasComponent(entity, IsAliveComponent.class);
    }
    
    @Override
    public void initialize(EntityWorld entityWorld, int entity){
        int ownTeamEntity = entityWorld.getComponent(entity, TeamComponent.class).getTeamEntity();
        for(int nexusEntity : entityWorld.getEntitiesWithAll(NameComponent.class)){
            String name = entityWorld.getComponent(nexusEntity, NameComponent.class).getName();
            if("Nexus".equals(name)){
                int nexusTeamEntity = entityWorld.getComponent(nexusEntity, TeamComponent.class).getTeamEntity();
                if (nexusTeamEntity != ownTeamEntity) {
                    enemyNexusEntity = nexusEntity;
                    break;
                }
            }
        }
    }

    @Override
    public double getValue(EntityWorld entityWorld, int entity) {
        return 0;
    }

    @Override
    public void addBestActions(EntityWorld entityWorld, int entity, LinkedList<Action> actions){
        Vector2f nexusPosition = entityWorld.getComponent(enemyNexusEntity, PositionComponent.class).getPosition();
        actions.add(new WalkAction(nexusPosition));
    }
}
