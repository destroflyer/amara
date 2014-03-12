/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import amara.engine.applications.ingame.client.systems.debug.*;
import amara.engine.materials.MaterialFactory;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.maps.Map;

/**
 *
 * @author Carl
 */
public class MapObstaclesAppState extends BaseDisplayAppState{

    private Node node = new Node();
    private Node obstaclesNode = new Node();
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        node.setLocalTranslation(0, 1, 0);
        node.attachChild(obstaclesNode);
        mainApplication.getRootNode().attachChild(node);
        update();
    }
    
    public void update(){
        obstaclesNode.detachAllChildren();
        Map map = getAppState(MapAppState.class).getMap();
        for(Shape shape : map.getPhysicsInformation().getObstacles()){
            Geometry collisionMeshGeometry = generateGeometry(shape);
            collisionMeshGeometry.setLocalTranslation((float) shape.getX(), 0, (float) shape.getY());
            obstaclesNode.attachChild(collisionMeshGeometry);
        }
    }

    public Node getNode(){
        return node;
    }
    
    public static Geometry generateGeometry(Shape shape){
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
        return generateGeometry(collisionMesh, meshColor);
    }
    
    public static Geometry generateGeometry(Mesh mesh, ColorRGBA color){
        Geometry collisionMeshGeometry = new Geometry("", mesh);
        collisionMeshGeometry.setMaterial(MaterialFactory.generateUnshadedMaterial(color));
        collisionMeshGeometry.getMaterial().getAdditionalRenderState().setDepthTest(false);
        collisionMeshGeometry.setUserData("layer", 999);
        return collisionMeshGeometry;
    }
}
