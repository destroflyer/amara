/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation.effects.crodwcontrol;

import java.util.HashMap;
import java.util.LinkedList;

import com.jme3.scene.Node;
import amara.applications.ingame.client.systems.visualisation.*;
import amara.applications.ingame.entitysystem.components.units.crowdcontrol.*;
import amara.applications.ingame.entitysystem.components.effects.crowdcontrol.knockup.*;
import amara.applications.ingame.entitysystem.components.game.*;
import amara.applications.ingame.shared.maps.Map;
import amara.libraries.entitysystem.*;

/**
 *
 * @author Carl
 */
public class KnockupVisualisationSystem implements EntitySystem{
    
    public KnockupVisualisationSystem(EntitySceneMap entitySceneMap, PositionSystem positionSystem){
        this.entitySceneMap = entitySceneMap;
        this.positionSystem = positionSystem;
    }
    private EntitySceneMap entitySceneMap;
    private PositionSystem positionSystem;
    private HashMap<Integer, KnockupCurve> knockupCurves = new HashMap<>();
    private LinkedList<Integer> initializedPositionEntities = new LinkedList<>();
    private LinkedList<Integer> knockupCurvesToRemove = new LinkedList<>();

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        updateCurves(entityWorld, deltaSeconds);
        ComponentMapObserver observer = entityWorld.requestObserver(this, IsKnockupedComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAny(IsKnockupedComponent.class)){
            addCurve(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(IsKnockupedComponent.class)){
            addCurve(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(IsKnockupedComponent.class)){
            knockupCurves.remove(entity);
        }
        updatePositions(entityWorld);
    }
    
    private void updateCurves(EntityWorld entityWorld, float deltaSeconds){
        GameSpeedComponent gameSpeedComponent = entityWorld.getComponent(Map.GAME_ENTITY, GameSpeedComponent.class);
        float gameSpeed = ((gameSpeedComponent != null)?gameSpeedComponent.getSpeed():1);
        float passedTime = (deltaSeconds * gameSpeed);
        for(KnockupCurve knockupCurve : knockupCurves.values()){
            knockupCurve.onTimePassed(passedTime);
        }
    }
    
    private void addCurve(EntityWorld entityWorld, int entity){
        IsKnockupedComponent isKnockupedComponent = entityWorld.getComponent(entity, IsKnockupedComponent.class);
        KnockupCurve knockupCurve = knockupCurves.get(entity);
        if((knockupCurve == null) || (isKnockupedComponent.getRemainingDuration() > knockupCurve.getIsKnockupedComponent().getRemainingDuration())){
            float knockupHeight = entityWorld.getComponent(isKnockupedComponent.getKnockupEntity(), KnockupHeightComponent.class).getHeight();
            float knockupDuration = entityWorld.getComponent(isKnockupedComponent.getKnockupEntity(), KnockupDurationComponent.class).getDuration();
            float offset = ((knockupCurve != null)?knockupCurve.getCurrentHeight():0);
            knockupCurve = new KnockupCurve(entity, knockupDuration, knockupHeight, offset);
            knockupCurves.put(entity, knockupCurve);
        }
        knockupCurve.setIsKnockupedComponent(isKnockupedComponent);
    }
    
    private void updatePositions(EntityWorld entityWorld){
        initializedPositionEntities.clear();
        knockupCurvesToRemove.clear();
        for(KnockupCurve knockupCurve : knockupCurves.values()){
            if(!initializedPositionEntities.contains(knockupCurve.getEntity())){
                positionSystem.updatePosition(entityWorld, knockupCurve.getEntity());
                initializedPositionEntities.add(knockupCurve.getEntity());
            }
            if(knockupCurve.isFinished()){
                knockupCurvesToRemove.add(knockupCurve.getEntity());
            }
            else{
                Node node = entitySceneMap.requestNode(knockupCurve.getEntity());
                node.move(0, knockupCurve.getCurrentHeight(), 0);
            }
        }
        for(int entity : knockupCurvesToRemove){
            knockupCurves.remove(entity);
        }
    }
}
