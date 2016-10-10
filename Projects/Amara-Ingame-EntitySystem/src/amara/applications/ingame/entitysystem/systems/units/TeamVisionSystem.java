
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.units;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import com.jme3.math.Vector2f;
import amara.applications.ingame.entitysystem.components.physics.PositionComponent;
import amara.applications.ingame.entitysystem.components.units.*;
import amara.libraries.entitysystem.*;
import amara.libraries.physics.shapes.*;
import amara.libraries.physics.shapes.vision.MergedVision;

/**
 *
 * @author Carl
 */
public class TeamVisionSystem implements EntitySystem{

    public TeamVisionSystem(int teamsCount, List<ConvexShape> obstacles){
        this.teamsCount = teamsCount;
        this.obstacles = obstacles;
        teamVisions = new MergedVision[teamsCount];
    }
    private int teamsCount;
    private List<ConvexShape> obstacles;
    private MergedVision[] teamVisions;
    private HashMap<Integer, Integer> currentTeams = new HashMap<Integer, Integer>();
    private LinkedList<Integer> changedVisionTeams = new LinkedList<Integer>();
    private LinkedList<Integer> changedVisionEntites = new LinkedList<Integer>();
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        changedVisionTeams.clear();
        changedVisionEntites.clear();
        ComponentMapObserver observer = entityWorld.requestObserver(this, PositionComponent.class, SightRangeComponent.class, TeamComponent.class, IsAlwaysVisibleComponent.class);
        //New
        for(int entity : observer.getNew().getEntitiesWithAny(PositionComponent.class, SightRangeComponent.class)){
            updateTeamVision(entityWorld, entity);
            changedVisionEntites.add(entity);
        }
        for(int entity : observer.getNew().getEntitiesWithAny(TeamComponent.class)){
            int teamEntity = entityWorld.getComponent(entity, TeamComponent.class).getTeamEntity();
            currentTeams.put(entity, teamEntity);
            updateTeamVision(entityWorld, entity);
            changedVisionEntites.add(entity);
        }
        for(int entity : observer.getNew().getEntitiesWithAny(IsAlwaysVisibleComponent.class)){
            changedVisionEntites.add(entity);
        }
        //Changed
        for(int entity : observer.getChanged().getEntitiesWithAny(PositionComponent.class, SightRangeComponent.class)){
            updateTeamVision(entityWorld, entity);
            changedVisionEntites.add(entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(TeamComponent.class)){
            removeOldTeamVision(entity);
            int teamEntity = entityWorld.getComponent(entity, TeamComponent.class).getTeamEntity();
            currentTeams.put(entity, teamEntity);
            updateTeamVision(entityWorld, entity);
            changedVisionEntites.add(entity);
        }
        //Removed
        for(int entity : observer.getRemoved().getEntitiesWithAll(PositionComponent.class, SightRangeComponent.class, TeamComponent.class)){
            removeOldTeamVision(entity);
            entityWorld.removeComponent(entity, IsVisibleForTeamsComponent.class);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(IsAlwaysVisibleComponent.class)){
            changedVisionEntites.add(entity);
        }
        //Update IsVisibleForTeamsComponent
        for(int entity : entityWorld.getEntitiesWithAll(PositionComponent.class)){
            updateIsVisibleForTeams(entityWorld, entity);
        }
    }
    
    private void updateTeamVision(EntityWorld entityWorld, int entity){
        TeamComponent teamComponent = entityWorld.getComponent(entity, TeamComponent.class);
        if(teamComponent != null){
            SightRangeComponent sightRangeComponent = entityWorld.getComponent(entity, SightRangeComponent.class);
            if(sightRangeComponent != null){
                Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
                MergedVision teamVision = requestTeamVision(teamComponent.getTeamEntity());
                teamVision.setVision(entity, new Vector2D(position.getX(), position.getY()), sightRangeComponent.getRange());
                changedVisionTeams.add(teamComponent.getTeamEntity());
            }
        }
    }
    
    private void removeOldTeamVision(int entity){
        int oldTeamEntity = currentTeams.get(entity);
        MergedVision oldTeamVision = requestTeamVision(oldTeamEntity);
        oldTeamVision.removeVision(entity);
        changedVisionTeams.add(oldTeamEntity);
    }
    
    private void updateIsVisibleForTeams(EntityWorld entityWorld, int entity){
        boolean[] isVisibleForTeams = new boolean[teamsCount];
        if(entityWorld.hasComponent(entity, IsAlwaysVisibleComponent.class)){
            for(int i=0;i<isVisibleForTeams.length;i++){
                isVisibleForTeams[i] = true;
            }
        }
        else{
            Vector2f position = entityWorld.getComponent(entity, PositionComponent.class).getPosition();
            TeamComponent teamComponent = entityWorld.getComponent(entity, TeamComponent.class);
            if(changedVisionEntites.contains(entity)){
                for(int i=0;i<isVisibleForTeams.length;i++){
                    int teamEntity = i;
                    isVisibleForTeams[i] = isVisibleForTeam(teamEntity, teamComponent, position);
                }
            }
            else{
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
        entityWorld.setComponent(entity, new IsVisibleForTeamsComponent(isVisibleForTeams));
    }
    
    private boolean isVisibleForTeam(int teamEntity, TeamComponent teamComponent, Vector2f position){
        if((teamComponent != null) && (teamComponent.getTeamEntity() == teamEntity)){
            return true;
        }
        else{
            MergedVision teamVision = requestTeamVision(teamEntity);
            return teamVision.isVisible(new Vector2D(position.getX(), position.getY()));
        }
    }
    
    private MergedVision requestTeamVision(int teamEntity){
        int teamIndex = teamEntity;
        MergedVision teamVision = teamVisions[teamIndex];
        if(teamVision == null){
            teamVision = new MergedVision(obstacles);
            teamVisions[teamIndex] = teamVision;
        }
        return teamVision;
    }
}
