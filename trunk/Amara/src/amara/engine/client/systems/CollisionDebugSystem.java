/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.systems;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
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
    
    public CollisionDebugSystem(EntitySceneMap entitySceneMap){
        this.entitySceneMap = entitySceneMap;
    }
    public static final String NODE_NAME_COLLISION_MESH = "collisionMesh";
    private EntitySceneMap entitySceneMap;

    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        for(int entity : entityWorld.getNew().getEntitiesWithAll(HitboxComponent.class))
        {
            updateCollisionMeshGeometry(entityWorld, entity);
        }
        for(int entity : entityWorld.getChanged().getEntitiesWithAll(HitboxComponent.class))
        {
            updateCollisionMeshGeometry(entityWorld, entity);
        }
        for(int entity : entityWorld.getRemoved().getEntitiesWithAll(HitboxComponent.class))
        {
            removeCollisionMeshGeometry(entity);
        }
    }
    
    private void updateCollisionMeshGeometry(EntityWorld entityWorld, int entity){
        removeCollisionMeshGeometry(entity);
        Node node = entitySceneMap.requestNode(entity);
        HitboxComponent hitboxComponent = entityWorld.getCurrent().getComponent(entity, HitboxComponent.class);
        Shape shape = hitboxComponent.getShape();
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
        Geometry collisionMeshGeometry = new Geometry(NODE_NAME_COLLISION_MESH, collisionMesh);
        collisionMeshGeometry.setMaterial(MaterialFactory.generateUnshadedMaterial(meshColor));
        collisionMeshGeometry.getMaterial().getAdditionalRenderState().setWireframe(true);
        node.attachChild(collisionMeshGeometry);
    }
    
    private void removeCollisionMeshGeometry(int entity){
        Node node = entitySceneMap.requestNode(entity);
        node.detachChildNamed(NODE_NAME_COLLISION_MESH);
    }
}
