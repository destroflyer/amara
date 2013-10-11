/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems.visualisation;

import java.util.Iterator;
import java.util.List;
import com.jme3.scene.Node;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import amara.engine.client.MaterialFactory;
import amara.engine.client.systems.debug.*;
import amara.game.entitysystem.*;
import amara.game.entitysystem.components.physics.*;
import shapes.*;

/**
 *
 * @author Carl
 */
public class CollisionDebugSystem implements EntitySystem{
    
    public CollisionDebugSystem(Node node, List<Shape> mapObstacles){
        this.node = node;
        Iterator<Shape> shapesIterator = mapObstacles.iterator();
        while(shapesIterator.hasNext()){
            Shape shape = shapesIterator.next();
            Geometry collisionMeshGeometry = generateGeometry(shape);
            collisionMeshGeometry.setLocalTranslation((float) shape.getX(), 0, (float) shape.getY());
            node.attachChild(collisionMeshGeometry);
        }
    }
    private Node node;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        ComponentMapObserver observer = entityWorld.getOrCreateObserver(this, HitboxComponent.class, PositionComponent.class);
        for(int entity : observer.getNew().getEntitiesWithAll(HitboxComponent.class))
        {
            updateGeometry(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(HitboxComponent.class))
        {
            updateGeometry(entityWorld, entity);
        }
        for(int entity : observer.getRemoved().getEntitiesWithAll(HitboxComponent.class))
        {
            removeGeometry(entity);
        }
        for(int entity : observer.getNew().getEntitiesWithAll(PositionComponent.class))
        {
            updateGeometryLocation(entityWorld, entity);
        }
        for(int entity : observer.getChanged().getEntitiesWithAll(PositionComponent.class))
        {
            updateGeometryLocation(entityWorld, entity);
        }
        observer.reset();
    }
    
    private void updateGeometry(EntityWorld entityWorld, int entity){
        removeGeometry(entity);
        HitboxComponent hitboxComponent = entityWorld.getComponent(entity, HitboxComponent.class);
        Geometry collisionMeshGeometry = generateGeometry(hitboxComponent.getShape());
        collisionMeshGeometry.setName(getGeometryName(entity));
        node.attachChild(collisionMeshGeometry);
    }
    
    private void removeGeometry(int entity){
        node.detachChildNamed(getGeometryName(entity));
    }
    
    private Geometry generateGeometry(Shape shape){
        Mesh collisionMesh;
        ColorRGBA meshColor = ColorRGBA.Blue;
        if(shape instanceof Circle){
            Circle circle = (Circle) shape;
            collisionMesh = new CircleMesh((float) circle.getBoundRadius(), 64);
        }
        else if(shape instanceof SimpleConvex){
            SimpleConvex simpleConvex = (SimpleConvex) shape;
            collisionMesh = new ShapeMesh(simpleConvex);
        }
        else{
            collisionMesh = new CircleMesh((float) shape.getBoundRadius(), 64);
            meshColor = ColorRGBA.Red;
        }
        Geometry collisionMeshGeometry = new Geometry("", collisionMesh);
        collisionMeshGeometry.setMaterial(MaterialFactory.generateUnshadedMaterial(meshColor));
        collisionMeshGeometry.getMaterial().getAdditionalRenderState().setDepthTest(false);
        collisionMeshGeometry.setUserData("layer", 999);
        return collisionMeshGeometry;
    }
    
    private void updateGeometryLocation(EntityWorld entityWorld, int entity){
        Spatial geometry = node.getChild(getGeometryName(entity));
        if(geometry != null){
            PositionComponent positionComponent = entityWorld.getComponent(entity, PositionComponent.class);
            Vector2f location = positionComponent.getPosition();
            geometry.setLocalTranslation(location.getX(), 0, location.getY());
        }
    }
    
    private String getGeometryName(int entity){
        return ("collisionMesh_" + entity);
    }
}
