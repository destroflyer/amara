/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.editor.appstates;

import java.util.ArrayList;
import java.util.LinkedList;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.Util;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.maps.MapTerrain;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.applications.ingame.client.systems.debug.ConnectedPointsMesh;
import amara.engine.applications.ingame.editor.gui.ScreenController_MapEditor;
import amara.engine.appstates.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.maps.*;

/**
 *
 * @author Carl
 */
public class MapEditorAppState extends BaseDisplayAppState implements ActionListener{

    public enum Action{
        NONE,
        VIEW,
        PLACE_HITBOX_CIRCLE,
        PLACE_HITBOX_CUSTOM,
        PLACE_VISUAL,
        REMOVE
    }
    private Action currentAction = Action.NONE;
    private Action actionBeforeRemove;
    private Vector2f currentHoveredLocation = new Vector2f();
    private LinkedList<Shape> shapesToPlace = new LinkedList<Shape>();
    private Geometry shapeToPlacePreviewGeometry;
    private double circleRadius = 3;
    private double circleRadiusStep = 0.2f;
    private LinkedList<Vector2D> customShapePoints = new LinkedList<Vector2D>();
    private String[] visualsModelSkinPaths = new String[]{
        "Models/tree/skin.xml",
        "Models/tree_2/skin.xml",
        "Models/japanese_bridge/skin.xml",
        "Models/cartoon_forest_grass/skin.xml",
        "Models/cartoon_forest_tree_1/skin.xml",
        "Models/cartoon_forest_tree_2/skin.xml",
        "Models/cartoon_forest_tree_3/skin.xml",
        "Models/cartoon_forest_tree_4/skin.xml",
        "Models/cartoon_forest_tree_5/skin.xml",
        "Models/cartoon_forest_stone_1/skin.xml",
        "Models/cartoon_forest_stone_2/skin.xml",
        "Models/cartoon_forest_stone_3/skin.xml",
        "Models/cartoon_forest_stone_4/skin.xml",
        "Models/cartoon_forest_stone_5/skin.xml",
        "Models/cartoon_forest_stone_6/skin.xml",
        "Models/thousand_sunny/skin.xml"
    };
    private int visualModelSkinPathIndex = 0;
    private ModelObject visualToPlaceModelObject;
    private boolean changeVisualDirectionOrScale = true;
    private Vector3f visualDirection = new Vector3f(0, 0, -1);
    private float visualDirectionStep = 5;
    private float visualScale = 1;
    private float visualScaleStep = 0.025f;
    private boolean sideOrTopDownView;
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getInputManager().addMapping("editor_mouse_click_left", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        mainApplication.getInputManager().addMapping("editor_mouse_click_right", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        mainApplication.getInputManager().addMapping("editor_mouse_wheel_up", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        mainApplication.getInputManager().addMapping("editor_mouse_wheel_down", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        mainApplication.getInputManager().addMapping("editor_1", new KeyTrigger(KeyInput.KEY_1));
        mainApplication.getInputManager().addMapping("editor_2", new KeyTrigger(KeyInput.KEY_2));
        mainApplication.getInputManager().addMapping("editor_3", new KeyTrigger(KeyInput.KEY_3));
        mainApplication.getInputManager().addMapping("editor_4", new KeyTrigger(KeyInput.KEY_4));
        mainApplication.getInputManager().addMapping("editor_x", new KeyTrigger(KeyInput.KEY_X));
        mainApplication.getInputManager().addMapping("editor_q", new KeyTrigger(KeyInput.KEY_Q));
        mainApplication.getInputManager().addMapping("editor_w", new KeyTrigger(KeyInput.KEY_W));
        mainApplication.getInputManager().addMapping("editor_enter", new KeyTrigger(KeyInput.KEY_RETURN));
        mainApplication.getInputManager().addMapping("editor_left_shift", new KeyTrigger(KeyInput.KEY_LSHIFT));
        mainApplication.getInputManager().addListener(this, new String[]{
            "editor_mouse_click_left","editor_mouse_click_right","editor_mouse_wheel_up","editor_mouse_wheel_down","editor_1","editor_2","editor_3","editor_4","editor_x","editor_q","editor_w","editor_enter","editor_left_shift"
        });
        setAction(Action.VIEW);
        changeCameraAngle();
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getInputManager().removeListener(this);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        Vector2f groundLocation = getAppState(MapAppState.class).getCursorHoveredGroundLocation();
        if(groundLocation != null){
            currentHoveredLocation.set(groundLocation);
            switch(currentAction){
                case PLACE_HITBOX_CIRCLE:
                    shapesToPlace.get(0).getTransform().setX(groundLocation.getX());
                    shapesToPlace.get(0).getTransform().setY(groundLocation.getY());
                    shapeToPlacePreviewGeometry.setLocalTranslation(groundLocation.getX(), 0, groundLocation.getY());
                    break;

                case PLACE_VISUAL:
                    MapTerrain mapTerrain = getAppState(MapAppState.class).getMapTerrain();
                    visualToPlaceModelObject.setLocalTranslation(currentHoveredLocation.getX(), mapTerrain.getHeight(currentHoveredLocation), currentHoveredLocation.getY());
                    break;
            }
            ScreenController_MapEditor screenController_MapEditor = getAppState(NiftyAppState.class).getScreenController(ScreenController_MapEditor.class);
            screenController_MapEditor.setHoveredLocation(currentHoveredLocation);
        }
    }

    @Override
    public void onAction(String actionName, boolean value, float lastTimePerFrame){
        MapAppState mapAppState = getAppState(MapAppState.class);
        Map map = mapAppState.getMap();
        ArrayList<Shape> obstacles = map.getPhysicsInformation().getObstacles();
        if(actionName.equals("editor_1") && value){
            setAction(Action.PLACE_HITBOX_CIRCLE);
        }
        else if(actionName.equals("editor_2") && value){
            setAction(Action.PLACE_HITBOX_CUSTOM);
        }
        else if(actionName.equals("editor_3") && value){
            setAction(Action.PLACE_VISUAL);
        }
        else if(actionName.equals("editor_4") && value){
            changeCameraAngle();
        }
        else if(actionName.equals("editor_x")){
            if(value){
                setAction(Action.REMOVE);
            }
            else{
                setAction(actionBeforeRemove);
            }
        }
        else{
            switch(currentAction){
                case PLACE_HITBOX_CIRCLE:
                    if(actionName.equals("editor_mouse_click_left") && value){
                        addCurrentShapes();
                        generateNewShape();
                    }
                    else if(actionName.equals("editor_mouse_wheel_up")){
                        circleRadius += circleRadiusStep;
                        generateNewShape();
                    }
                    else if(actionName.equals("editor_mouse_wheel_down")){
                        circleRadius -= circleRadiusStep;
                        if(circleRadius <= 0){
                            circleRadius = circleRadiusStep;
                        }
                        generateNewShape();
                    }
                    break;
                
                case PLACE_HITBOX_CUSTOM:
                    if(actionName.equals("editor_mouse_click_left") && value){
                        customShapePoints.add(new Vector2D(currentHoveredLocation.getX(), currentHoveredLocation.getY()));
                        if(customShapePoints.size() > 1){
                            generateNewShape();
                        }
                    }
                    else if(actionName.equals("editor_mouse_click_right") && value){
                        cancelCurrentCustomShape();
                    }
                    else if(actionName.equals("editor_enter") && value){
                        if(generateCustomShapes()){
                            addCurrentShapes();
                        }
                        else{
                            cancelCurrentCustomShape();
                        }
                    }
                    break;
                
                case PLACE_VISUAL:
                    if(actionName.equals("editor_mouse_click_left") && value){
                        Vector3f position = new Vector3f(currentHoveredLocation.getX(), 0, currentHoveredLocation.getY());
                        MapVisual mapVisual = new MapVisual(visualsModelSkinPaths[visualModelSkinPathIndex], position, visualDirection, visualScale);
                        map.getVisuals().addVisual(mapVisual);
                        mapAppState.updateVisuals();
                        generateNewModelObject();
                    }
                    else if(actionName.equals("editor_q") && value){
                        changeVisualIndex(false);
                    }
                    else if(actionName.equals("editor_w") && value){
                        changeVisualIndex(true);
                    }
                    else if(actionName.equals("editor_left_shift")){
                        changeVisualDirectionOrScale = (!value);
                    }
                    else if(actionName.equals("editor_mouse_wheel_up")){
                        changeVisualDirectionOrScale(true);
                    }
                    else if(actionName.equals("editor_mouse_wheel_down")){
                        changeVisualDirectionOrScale(false);
                    }
                    break;
                
                case REMOVE:
                    if(actionName.equals("editor_mouse_click_left") && value){
                        switch(actionBeforeRemove){
                            case PLACE_HITBOX_CIRCLE:
                            case PLACE_HITBOX_CUSTOM:
                                for(int i=0;i<obstacles.size();i++){
                                    Shape shape = obstacles.get(i);
                                    if(shape.contains(new Vector2D(currentHoveredLocation.getX(), currentHoveredLocation.getY()))){
                                        obstacles.remove(i);
                                        getAppState(MapObstaclesAppState.class).update();
                                        break;
                                    }
                                }
                                break;
                            
                            case PLACE_VISUAL:
                                Node visualsNode = getAppState(MapAppState.class).getVisualsNode();
                                CollisionResult collisionResult = mainApplication.getRayCastingResults_Cursor(visualsNode).getClosestCollision();
                                if(collisionResult != null){
                                    Spatial parent = collisionResult.getGeometry().getParent();
                                    if((parent != null) && (parent.getParent() instanceof ModelObject)){
                                        ModelObject modelObject = (ModelObject) parent.getParent();
                                        MapVisual mapVisual = mapAppState.getMapVisual(modelObject);
                                        if(mapVisual != null){
                                            map.getVisuals().removeVisual(mapVisual);
                                            mapAppState.updateVisuals();
                                        }
                                    }
                                }
                                break;
                        }
                    }
                    break;
            }
        }
    }
    
    public void setAction(Action action){
        if(action != currentAction){
            if(action == Action.REMOVE){
                actionBeforeRemove = currentAction;
            }
            switch(currentAction){
                case VIEW:
                    getAppState(IngameCameraAppState.class).setZoomEnabled(false);
                    break;
                
                case PLACE_HITBOX_CIRCLE:
                    removeShapeToPlaceGeometry();
                    break;
                
                case PLACE_HITBOX_CUSTOM:
                    cancelCurrentCustomShape();
                    break;
                
                case PLACE_VISUAL:
                    removeVisualToPlaceModelObject();
                    break;
            }
            currentAction = action;
            switch(currentAction){
                case VIEW:
                    getAppState(IngameCameraAppState.class).setZoomEnabled(true);
                    break;
                
                case PLACE_HITBOX_CIRCLE:
                    generateNewShape();
                    break;
                
                case PLACE_HITBOX_CUSTOM:
                    break;
                
                case PLACE_VISUAL:
                    generateNewModelObject();
                    break;
            }
        }
    }
    
    private void generateNewShape(){
        shapesToPlace.clear();
        removeShapeToPlaceGeometry();
        switch(currentAction){
            case PLACE_HITBOX_CIRCLE:
                Circle circle = new Circle(circleRadius);
                shapesToPlace.add(circle);
                shapeToPlacePreviewGeometry = MapObstaclesAppState.generateGeometry(circle);
                break;
            
            case PLACE_HITBOX_CUSTOM:
                Vector2D[] basePoints = Util.toArray(customShapePoints, Vector2D.class);
                ConnectedPointsMesh connectedPointsMesh = new ConnectedPointsMesh(basePoints);
                shapeToPlacePreviewGeometry = MapObstaclesAppState.generateGeometry(connectedPointsMesh, ColorRGBA.Blue);
                break;
        }
        Node obstaclesNode = getAppState(MapObstaclesAppState.class).getNode();
        obstaclesNode.attachChild(shapeToPlacePreviewGeometry);
    }
    
    private boolean generateCustomShapes(){
        Triangulator triangulator = new Triangulator();
        if((!triangulator.isConvex(customShapePoints)) && triangulator.canTriangulate(customShapePoints)){
            shapesToPlace.addAll(triangulator.createDelaunayTrisFromPoly(customShapePoints));
        }
        else{
            try{
                Vector2D[] basePoints = Util.toArray(customShapePoints, Vector2D.class);
                shapesToPlace.add(new SimpleConvex(basePoints));
            }catch(Error error){
                return false;
            }
        }
        return true;
    }
    
    private void addCurrentShapes(){
        ArrayList<Shape> obstacles = getAppState(MapAppState.class).getMap().getPhysicsInformation().getObstacles();
        obstacles.addAll(shapesToPlace);
        getAppState(MapObstaclesAppState.class).update();
        cancelCurrentCustomShape();
    }
    
    private void cancelCurrentCustomShape(){
        customShapePoints.clear();
        removeShapeToPlaceGeometry();
    }
    
    private void removeShapeToPlaceGeometry(){
        if(shapeToPlacePreviewGeometry != null){
            Node obstaclesNode = getAppState(MapObstaclesAppState.class).getNode();
            obstaclesNode.detachChild(shapeToPlacePreviewGeometry);
            shapeToPlacePreviewGeometry = null;
        }
    }
    
    private void changeVisualIndex(boolean increaseOrDecrease){
        visualModelSkinPathIndex += (increaseOrDecrease?1:-1);
        if(visualModelSkinPathIndex < 0){
            visualModelSkinPathIndex = (visualsModelSkinPaths.length - 1);
        }
        else if(visualModelSkinPathIndex >= visualsModelSkinPaths.length){
            visualModelSkinPathIndex = 0;
        }
        generateNewModelObject();
    }
    
    private void generateNewModelObject(){
        removeVisualToPlaceModelObject();
        visualToPlaceModelObject = new ModelObject(mainApplication, "/" + visualsModelSkinPaths[visualModelSkinPathIndex]);
        Node visualsNode = getAppState(MapAppState.class).getVisualsNode();
        visualsNode.attachChild(visualToPlaceModelObject);
        updateVisualToPlaceModelObject();
    }
    
    private void removeVisualToPlaceModelObject(){
        if(visualToPlaceModelObject != null){
            Node visualsNode = getAppState(MapAppState.class).getVisualsNode();
            visualsNode.detachChild(visualToPlaceModelObject);
        }
    }
    
    private void changeVisualDirectionOrScale(boolean mouseWheelUpOrDown){
        float factor = (mouseWheelUpOrDown?1:-1);
        if(changeVisualDirectionOrScale){
            visualDirection = JMonkeyUtil.getQuaternion_Y(factor * visualDirectionStep).mult(visualDirection);
        }
        else{
            visualScale += (factor * visualScaleStep);
        }
        updateVisualToPlaceModelObject();
    }
    
    private void updateVisualToPlaceModelObject(){
        JMonkeyUtil.setLocalRotation(visualToPlaceModelObject, visualDirection);
        visualToPlaceModelObject.setLocalScale(visualScale);
    }
    
    public void changeCameraAngle(){
        sideOrTopDownView = (!sideOrTopDownView);
        Camera camera = mainApplication.getCamera();
        if(sideOrTopDownView){
            camera.lookAtDirection(new Vector3f(0, -1.3f, 1), Vector3f.UNIT_Y);
        }
        else{
            camera.lookAtDirection(new Vector3f(0, -1, 0), Vector3f.UNIT_Z);
        }
    }
}
