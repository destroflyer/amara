/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.ai.goals;

import java.util.LinkedList;
import amara.applications.ingame.entitysystem.ai.actions.*;
import amara.applications.ingame.entitysystem.components.attributes.*;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.spells.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.applications.ingame.entitysystem.components.units.types.*;
import amara.ingame.ai.*;
import amara.libraries.entitysystem.EntityWorld;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class LastHitGoal extends Goal{
    
    private LinkedList<Integer> enemyMinionEntities = new LinkedList<>();
    private float attackDamage;
    private float autoAttackRange;
    private int nearestAlliedStructureEntity;

    @Override
    public boolean isEnabled(EntityWorld entityWorld, int entity){
        return entityWorld.hasComponent(entity, IsAliveComponent.class);
    }
    
    @Override
    public void initialize(EntityWorld entityWorld, int entity){
        Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
        attackDamage = entityWorld.getComponent(entity, AttackDamageComponent.class).getValue();
        int autoAttackEntity = entityWorld.getComponent(entity, AutoAttackComponent.class).getAutoAttackEntity();
        autoAttackRange = entityWorld.getComponent(autoAttackEntity, RangeComponent.class).getDistance();
        int ownTeamEntity = entityWorld.getComponent(entity, TeamComponent.class).getTeamEntity();
        enemyMinionEntities.clear();
        for(int minionEntity : entityWorld.getEntitiesWithAny(IsMinionComponent.class)){
            int minionTeamEntity = entityWorld.getComponent(minionEntity, TeamComponent.class).getTeamEntity();
            if (minionTeamEntity != ownTeamEntity) {
                Vector2f minionPosition = entityWorld.getComponent(minionEntity, PositionComponent.class).getPosition();
                if (minionPosition.distance(position) < 40) {
                    enemyMinionEntities.add(minionEntity);
                }
            }
        }
        float minimumStructureDistance = Float.MAX_VALUE;
        for(int structureEntity : entityWorld.getEntitiesWithAny(IsStructureComponent.class)){
            int structureTeamEntity = entityWorld.getComponent(structureEntity, TeamComponent.class).getTeamEntity();
            if (structureTeamEntity == ownTeamEntity) {
                Vector2f structurePosition = entityWorld.getComponent(structureEntity, PositionComponent.class).getPosition();
                float structureDistance = structurePosition.distance(position);
                if (structureDistance < minimumStructureDistance) {
                    nearestAlliedStructureEntity = structureEntity;
                    minimumStructureDistance = structureDistance;
                }
            }
        }
    }

    @Override
    public double getValue(EntityWorld entityWorld, int entity) {
        return enemyMinionEntities.size();
    }

    @Override
    public void addBestActions(EntityWorld entityWorld, int entity, LinkedList<Action> actions){
        if(enemyMinionEntities.size() > 0){
            int lasthittableMinionEntity = getLasthittableMinionEntity(entityWorld);
            if(lasthittableMinionEntity != -1){
                actions.add(new AutoAttackAction(lasthittableMinionEntity));
            }
            else{
                Vector2f bestPosition = getBestPosition(entityWorld);
                if(bestPosition != null){
                    actions.add(new WalkAction(bestPosition));
                }
            }
        }
    }
    
    private int getLasthittableMinionEntity(EntityWorld entityWorld){
        for(int minionEntity : enemyMinionEntities){
            float health = entityWorld.getComponent(minionEntity, HealthComponent.class).getValue();
            if(health < (attackDamage + (2 * autoAttackRange))){
                return minionEntity;
            }
        }
        return -1;
    }
    
    private Vector2f getBestPosition(EntityWorld entityWorld){
        Vector2f alliedStructurePosition = entityWorld.getComponent(nearestAlliedStructureEntity, PositionComponent.class).getPosition();
        float totalMinionWeightings = 0;
        Vector2f result = null;
        for(int minionEntity : enemyMinionEntities){
            Vector2f minionPosition = entityWorld.getComponent(minionEntity, PositionComponent.class).getPosition();
            Vector2f directionToAlliedStructure = alliedStructurePosition.subtract(minionPosition).normalizeLocal();
            Vector2f lasthittablePosition = minionPosition.add(directionToAlliedStructure.mult(autoAttackRange + 5));

            float maximumHealth = entityWorld.getComponent(minionEntity, MaximumHealthComponent.class).getValue();
            float health = entityWorld.getComponent(minionEntity, HealthComponent.class).getValue();
            float weighting = FastMath.pow((maximumHealth / health), 2);

            if(result == null){
                result = new Vector2f();
            }
            result.addLocal(lasthittablePosition.mult(weighting));
            totalMinionWeightings += weighting;
        }
        if(result != null){
            result.divideLocal(totalMinionWeightings);
        }
        return result;
    }
}
