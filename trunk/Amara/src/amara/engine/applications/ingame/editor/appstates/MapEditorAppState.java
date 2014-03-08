/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.ingame.editor.appstates;

import java.util.ArrayList;
import java.util.LinkedList;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import amara.Util;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.maps.MapTerrain;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.appstates.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import amara.game.maps.Map;
import amara.game.maps.MapVisual;

/**
 *
 * @author Carl
 */
public class MapEditorAppState extends BaseDisplayAppState implements ActionListener{

    public enum Action{
        NONE,
        PLACE_HITBOX_CIRCLE,
        PLACE_HITBOX_CUSTOM,
        PLACE_VISUAL
    }
    private Action currentAction = Action.NONE;
    private Vector2f currentHoveredLocation = new Vector2f();
    private boolean isRemoveActivated;
    private Shape shapeToPlace;
    private Geometry shapeToPlaceGeometry;
    private double circleRadius = 3;
    private double circleRadiusStep = 0.2f;
    private LinkedList<Vector2D> customShapePoints = new LinkedList<Vector2D>();
    private String[] visualsModelSkinPaths = new String[]{
        "Models/tree/skin.xml",
        "Models/tree_2/skin.xml"
    };
    private int visualModelSkinPathIndex = 0;
    private ModelObject visualToPlaceModelObject;
    private boolean changeVisualDirectionOrScale = true;
    private Vector3f visualDirection = new Vector3f(0, 0, -1);
    private float visualDirectionStep = 5;
    private float visualScale = 1;
    private float visualScaleStep = 0.025f;
    
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
        mainApplication.getInputManager().addMapping("editor_x", new KeyTrigger(KeyInput.KEY_X));
        mainApplication.getInputManager().addMapping("editor_enter", new KeyTrigger(KeyInput.KEY_RETURN));
        mainApplication.getInputManager().addMapping("editor_left_shift", new KeyTrigger(KeyInput.KEY_LSHIFT));
        mainApplication.getInputManager().addListener(this, new String[]{
            "editor_mouse_click_left","editor_mouse_click_right","editor_mouse_wheel_up","editor_mouse_wheel_down","editor_1","editor_2","editor_3","editor_x","editor_enter","editor_left_shift"
        });
        getAppState(IngameCameraAppState.class).setZoomEnabled(false);
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getInputManager().removeListener(this);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        if(currentAction != Action.NONE){
            Vector2f groundLocation = getAppState(MapAppState.class).getCursorHoveredGroundLocation();
            if(groundLocation != null){
                currentHoveredLocation.set(groundLocation);
                switch(currentAction){
                    case PLACE_HITBOX_CIRCLE:
                        shapeToPlace.getTransform().setX(groundLocation.getX());
                        shapeToPlace.getTransform().setY(groundLocation.getY());
                        shapeToPlaceGeometry.setLocalTranslation(groundLocation.getX(), 0, groundLocation.getY());
                        break;
                    
                    case PLACE_VISUAL:
                        MapTerrain mapTerrain = getAppState(MapAppState.class).getMapTerrain();
                        visualToPlaceModelObject.setLocalTranslation(currentHoveredLocation.getX(), mapTerrain.getHeight(currentHoveredLocation), currentHoveredLocation.getY());
                        break;
                }
            }
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
        else if(actionName.equals("editor_x")){
            isRemoveActivated = value;
        }
        else if(isRemoveActivated && actionName.equals("editor_mouse_click_left") && value){
            Vector2f groundLocation = mapAppState.getCursorHoveredGroundLocation();
            for(int i=0;i<obstacles.size();i++){
                Shape shape = obstacles.get(i);
                if(shape.contains(new Vector2D(groundLocation.getX(), groundLocation.getY()))){
                    obstacles.remove(i);
                    getAppState(MapObstaclesAppState.class).update();
                    break;
                }
            }
        }
        else{
            switch(currentAction){
                case PLACE_HITBOX_CIRCLE:
                    if(actionName.equals("editor_mouse_click_left") && value){
                        addCurrentShape();
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
                        addCurrentShape();
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
                    else if(actionName.equals("editor_mouse_click_right") && value){
                        visualModelSkinPathIndex++;
                        if(visualModelSkinPathIndex >= visualsModelSkinPaths.length){
                            visualModelSkinPathIndex = 0;
                        }
                        generateNewModelObject();
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
            }
        }
    }
    
    public void setAction(Action action){
        if(action != currentAction){
            currentAction = action;
            switch(currentAction){
                case PLACE_HITBOX_CIRCLE:
                    cancelCurrentCustomShape();
                    removeVisualToPlaceModelObject();
                    generateNewShape();
                    break;
                
                case PLACE_HITBOX_CUSTOM:
                    removeShapeToPlaceGeometry();
                    removeVisualToPlaceModelObject();
                    break;
                
                case PLACE_VISUAL:
                    cancelCurrentCustomShape();
                    generateNewModelObject();
                    break;
            }
        }
    }
    
    private void generateNewShape(){
        removeShapeToPlaceGeometry();
        switch(currentAction){
            case PLACE_HITBOX_CIRCLE:
                shapeToPlace = new Circle(circleRadius);
                break;
            
            case PLACE_HITBOX_CUSTOM:
                Vector2D[] basePoints = Util.toArray(customShapePoints, Vector2D.class);
                shapeToPlace = new SimpleConvex(basePoints);
                break;
        }
        shapeToPlaceGeometry = MapObstaclesAppState.generateGeometry(shapeToPlace);
        Node obstaclesNode = getAppState(MapObstaclesAppState.class).getNode();
        obstaclesNode.attachChild(shapeToPlaceGeometry);
    }
    
    private void addCurrentShape(){
        ArrayList<Shape> obstacles = getAppState(MapAppState.class).getMap().getPhysicsInformation().getObstacles();
        obstacles.add(shapeToPlace);
        getAppState(MapObstaclesAppState.class).update();
        cancelCurrentCustomShape();
    }
    
    private void cancelCurrentCustomShape(){
        customShapePoints.clear();
        removeShapeToPlaceGeometry();
    }
    
    private void removeShapeToPlaceGeometry(){
        if(shapeToPlaceGeometry != null){
            Node obstaclesNode = getAppState(MapObstaclesAppState.class).getNode();
            obstaclesNode.detachChild(shapeToPlaceGeometry);
        }
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
}
