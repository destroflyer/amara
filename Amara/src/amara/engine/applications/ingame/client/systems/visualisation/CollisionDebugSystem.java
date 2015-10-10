/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import amara.engine.appstates.MapObstaclesAppState;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;

/**
 *
 * @author Carl
 */
public class CollisionDebugSystem implements EntitySystem{
    
    public CollisionDebugSystem(Node node){
        this.node = node;
    }
    private Node node;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.requestObserver(this, HitboxComponent.class, HitboxActiveComponent.class, PositionComponent.class);
        for(Integer entity : observer.getNew().getEntitiesWithAll(HitboxComponent.class)){
            updateGeometry(entityWorld, entity);
        }
        for(Integer entity : observer.getChanged().getEntitiesWithAll(HitboxComponent.class)){
            updateGeometry(entityWorld, entity);
        }
        for(Integer entity : observer.getRemoved().getEntitiesWithAll(HitboxComponent.class)){
            removeGeometry(entity);
        }
        for(Integer entity : observer.getNew().getEntitiesWithAll(HitboxActiveComponent.class)){
            updateGeometry(entityWorld, entity);
        }
        for(Integer entity : observer.getRemoved().getEntitiesWithAll(HitboxActiveComponent.class)){
            updateGeometry(entityWorld, entity);
        }
        for(Integer entity : observer.getNew().getEntitiesWithAll(PositionComponent.class)){
            updateGeometryLocation(entityWorld, entity);
        }
        for(Integer entity : observer.getChanged().getEntitiesWithAll(PositionComponent.class)){
            updateGeometryLocation(entityWorld, entity);
        }
    }
    
    private void updateGeometry(EntityWorld entityWorld, Integer entity){
        removeGeometry(entity);
        HitboxComponent hitboxComponent = entityWorld.getComponent(entity, HitboxComponent.class);
        if(hitboxComponent != null){
            Geometry collisionMeshGeometry = MapObstaclesAppState.generateGeometry(hitboxComponent.getShape(), entityWorld.hasComponent(entity, HitboxActiveComponent.class));
            collisionMeshGeometry.setName(getGeometryName(entity));
            node.attachChild(collisionMeshGeometry);
            updateGeometryLocation(entityWorld, entity);
        }
    }
    
    private void removeGeometry(Integer entity){
        node.detachChildNamed(getGeometryName(entity));
    }
    
    private void updateGeometryLocation(EntityWorld entityWorld, Integer entity){
        Spatial geometry = node.getChild(getGeometryName(entity));
        if(geometry != null){
            PositionComponent positionComponent = entityWorld.getComponent(entity, PositionComponent.class);
            if(positionComponent != null){
                Vector2f location = positionComponent.getPosition();
                geometry.setLocalTranslation(location.getX(), 0, location.getY());
            }
            else{
                removeGeometry(entity);
            }
        }
    }
    
    private String getGeometryName(Integer entity){
        return ("collisionMesh_" + entity);
    }
}
