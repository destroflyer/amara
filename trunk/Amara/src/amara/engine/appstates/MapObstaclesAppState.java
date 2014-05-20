/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.scene.Spatial;
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
public class MapObstaclesAppState extends BaseDisplayAppState implements ActionListener{

    private Node node = new Node();
    private Node obstaclesNode = new Node();
    private boolean displayObstacles = true;
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getInputManager().addMapping("toggle_hitboxes", new KeyTrigger(KeyInput.KEY_H));
        mainApplication.getInputManager().addListener(this, new String[]{
            "toggle_hitboxes"
        });
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

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame){
        if(name.equals("toggle_hitboxes") && isPressed){
            displayObstacles = (!displayObstacles);
            obstaclesNode.setCullHint(displayObstacles?Spatial.CullHint.Inherit: Spatial.CullHint.Always);
        }
    }

    public Node getObstaclesNode(){
        return obstaclesNode;
    }
    
    public static Geometry generateGeometry(Shape shape){
        return generateGeometry(shape, true);
    }
    
    public static Geometry generateGeometry(Shape shape, boolean isActive){
        Mesh collisionMesh;
        ColorRGBA meshColor = (isActive?ColorRGBA.Blue:ColorRGBA.LightGray);
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
