
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.shapes.*;
import amara.libraries.physics.shapes.vision.*;

/**
 *
 * @author Carl
 */
public class TeamVisionSystem implements EntitySystem{

    public TeamVisionSystem(int teamsCount, List<ConvexShape> mapObstacles){
        teamVisions = new MergedVision[teamsCount];
        LinkedList<VisionObstacle> visionObstacles = VisionObstacle.generateDefaultObstacles(mapObstacles);
        for(int i=0;i<teamVisions.length;i++){
            MergedVision teamVision = new MergedVision(visionObstacles);
            teamVision.setEnableSightInSolidObstacles(true);
            teamVisions[i] = teamVision;
        }
    }
    private MergedVision[] teamVisions;
    private HashMap<Integer, Integer> currentTeams = new HashMap<>();
    private LinkedList<Integer> changedVisionEntities = new LinkedList<>();
    private LinkedList<Integer> changedVisionTeams = new LinkedList<>();
    private boolean haveHiddenAreasChanged;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        changedVisionEntities.clear();
        changedVisionTeams.clear();
        haveHiddenAreasChanged = false;
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsHiddenAreaComponent.class, PositionComponent.class, SightRangeComponent.class, TeamComponent.class, IsAlwaysVisibleComponent.class, IsStealthedComponent.class);
        //Update hidden areas
        for(int entity : observer.getNew().getEntitiesWithAll(IsHiddenAreaComponent.class)){
            updateHiddenAreaObstacle(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(IsHiddenAreaComponent.class)){
            for(MergedVision teamVision : teamVisions){
                teamVision.removeObstacle(entity);
            }
            haveHiddenAreasChanged = true;
        }        
        //New
        for(int entity : observer.getNew().getEntitiesWithAny(PositionComponent.class, SightRangeComponent.class)){
            updateTeamVision(entityWorld, entity);
        }
        for(int entity : observer.getNew().getEntitiesWithAny(TeamComponent.class)){
            int teamEntity = entityWorld.getComponent(entity, TeamComponent.class).getTeamEntity();
            currentTeams.put(entity, teamEntity);
            updateTeamVision(entityWorld, entity);
        }
        for(int entity : observer.getNew().getEntitiesWithAny(IsAlwaysVisibleComponent.class, IsStealthedComponent.class)){
            changedVisionEntities.add(entity);
        }
        //Changed
        for(int entity : observer.getChanged().getEntitiesWithAny(PositionComponent.class, SightRangeComponent.class)){
            updateTeamVision(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(TeamComponent.class)){
            removeOldTeamVision(entity);
            int teamEntity = entityWorld.getComponent(entity, TeamComponent.class).getTeamEntity();
            currentTeams.put(entity, teamEntity);
            updateTeamVision(entityWorld, entity);
        }
        //Removed
        for(int entity : observer.getRemoved().getEntitiesWithAny(PositionComponent.class, SightRangeComponent.class, TeamComponent.class)){
            removeOldTeamVision(entity);
            entityWorld.removeComponent(entity, IsVisibleForTeamsComponent.class);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(IsAlwaysVisibleComponent.class, IsStealthedComponent.class)){
            changedVisionEntities.add(entity);
        }
        //Update IsVisibleForTeamsComponent
        if((changedVisionEntities.size() > 0) || haveHiddenAreasChanged){
            for(int entity : entityWorld.getEntitiesWithAll(PositionComponent.class)){
                updateIsVisibleForTeams(entityWorld, entity);
            }
        }
    }
    
    private void updateTeamVision(EntityWorld entityWorld, int entity){
        TeamComponent teamComponent = entityWorld.getComponent(entity, TeamComponent.class);
        if(teamComponent != null){
            SightRangeComponent sightRangeComponent = entityWorld.getComponent(entity, SightRangeComponent.class);
            if(sightRangeComponent != null){
                Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                MergedVision teamVision = getTeamVision(teamComponent.getTeamEntity());
                teamVision.setVision(entity, new Vector2D(position.getX(), position.getY()), sightRangeComponent.getRange());
                changedVisionTeams.add(teamComponent.getTeamEntity());
            }
        }
        changedVisionEntities.add(entity);
    }
    
    private void removeOldTeamVision(int entity){
        Integer oldTeamEntity = currentTeams.get(entity);
        if(oldTeamEntity != null){
            MergedVision oldTeamVision = getTeamVision(oldTeamEntity);
            oldTeamVision.removeVision(entity);
            changedVisionTeams.add(oldTeamEntity);
        }
    }
    
    private void updateHiddenAreaObstacle(EntityWorld entityWorld, int entity){
        HitboxComponent hitboxComponent = entityWorld.getComponent(entity, HitboxComponent.class);
        if(hitboxComponent != null){
            for(MergedVision teamVision : teamVisions){
                teamVision.setObstacle(entity, new VisionObstacle((ConvexShape) hitboxComponent.getShape(), false));
            }
            haveHiddenAreasChanged = true;
        }
    }
    
    private void updateIsVisibleForTeams(EntityWorld entityWorld, int entity){
        boolean[] isVisibleForTeams = new boolean[teamVisions.length];
        if(entityWorld.hasComponent(entity, IsAlwaysVisibleComponent.class)){
            for(int i=0;i<isVisibleForTeams.length;i++){
                isVisibleForTeams[i] = true;
            }
        }
        else{
            TeamComponent teamComponent = entityWorld.getComponent(entity, TeamComponent.class);
            if(entityWorld.hasComponent(entity, IsStealthedComponent.class)){
                for(int i=0;i<isVisibleForTeams.length;i++){
                    int teamEntity = i;
                    isVisibleForTeams[i] = ((teamComponent != null) && (teamEntity == teamComponent.getTeamEntity()));
                }
            }
            else{
                Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                if(changedVisionEntities.contains(entity)){
                    for(int i=0;i<isVisibleForTeams.length;i++){
                        int teamEntity = i;
                        isVisibleForTeams[i] = isVisibleForTeam(teamEntity, teamComponent, position);
                    }
                }
                else{
                    if(changedVisionTeams.isEmpty()){
                        return;
                    }
                    IsVisibleForTeamsComponent isVisibleForTeamsComponent = entityWorld.getComponent(entity, IsVisibleForTeamsComponent.class);
                    if(isVisibleForTeamsComponent != null){
                        for(int i=0;i<isVisibleForTeamsComponent.isVisibleForTeams().length;i++){
                            isVisibleForTeams[i] = isVisibleForTeamsComponent.isVisibleForTeams()[i];
                        }
                    }
                    for(int teamEntity : changedVisionTeams){
                        int teamIndex = teamEntity;
                        isVisibleForTeams[teamIndex] = isVisibleForTeam(teamEntity, teamComponent, position);
                    }
                }
            }
        }
        entityWorld.setComponent(entity, new IsVisibleForTeamsComponent(isVisibleForTeams));
    }
    
    private boolean isVisibleForTeam(int teamEntity, TeamComponent teamComponent, Vector2f position){
        if((teamComponent != null) && (teamComponent.getTeamEntity() == teamEntity)){
            return true;
        }
        else{
            MergedVision teamVision = getTeamVision(teamEntity);
            return teamVision.isVisible(new Vector2D(position.getX(), position.getY()));
        }
    }
    
    private MergedVision getTeamVision(int teamEntity){
        int teamIndex = teamEntity;
        return teamVisions[teamIndex];
    }
    
    public static boolean hasTeamSight(EntityWorld entityWorld, int entity, int targetEntity){
        TeamComponent teamComponent = entityWorld.getComponent(entity, TeamComponent.class);
        if(teamComponent != null){
            IsVisibleForTeamsComponent isVisibleForTeamsComponent = entityWorld.getComponent(targetEntity, IsVisibleForTeamsComponent.class);
            if(isVisibleForTeamsComponent != null){
                int teamIndex = teamComponent.getTeamEntity();
                return isVisibleForTeamsComponent.isVisibleForTeams()[teamIndex];
            }
        }
        return false;
    }
}
