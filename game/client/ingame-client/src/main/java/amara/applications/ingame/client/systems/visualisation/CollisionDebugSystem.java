/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.visualisation;

import com.jme3.scene.Node;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import amara.applications.ingame.entitysystem.components.physics.*;
import amara.libraries.applications.display.ingame.appstates.MapObstaclesAppState;
import amara.libraries.entitysystem.*;

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
        for(int entity : observer.getNew().getEntitiesWithAny(HitboxComponent.class)){
            updateGeometry(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(HitboxComponent.class)){
            updateGeometry(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(HitboxComponent.class)){
            removeGeometry(entity);
        }
        for(int entity : observer.getNew().getEntitiesWithAny(HitboxActiveComponent.class)){
            updateGeometry(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAny(HitboxActiveComponent.class)){
            updateGeometry(entityWorld, entity);
        }
        for(int entity : observer.getNew().getEntitiesWithAny(PositionComponent.class)){
            updateGeometryLocation(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAny(PositionComponent.class)){
            updateGeometryLocation(entityWorld, entity);
        }
    }
    
    private void updateGeometry(EntityWorld entityWorld, int entity){
        removeGeometry(entity);
        HitboxComponent hitboxComponent = entityWorld.getComponent(entity, HitboxComponent.class);
        if(hitboxComponent != null){
            Geometry collisionMeshGeometry = MapObstaclesAppState.generateGeometry(hitboxComponent.getShape(), entityWorld.hasComponent(entity, HitboxActiveComponent.class));
            collisionMeshGeometry.setName(getGeometryName(entity));
            node.attachChild(collisionMeshGeometry);
            updateGeometryLocation(entityWorld, entity);
        }
    }
    
    private void removeGeometry(int entity){
        node.detachChildNamed(getGeometryName(entity));
    }
    
    private void updateGeometryLocation(EntityWorld entityWorld, int entity){
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
    
    private String getGeometryName(int entity){
        return ("collisionMesh_" + entity);
    }
}
