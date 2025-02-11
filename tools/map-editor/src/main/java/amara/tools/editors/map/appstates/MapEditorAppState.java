package amara.tools.editors.map.appstates;

import java.util.ArrayList;

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
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.applications.ingame.shared.maps.*;
import amara.applications.ingame.shared.maps.obstacles.*;
import amara.applications.ingame.shared.maps.visuals.ModelVisual;
import amara.core.Util;
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.appstates.*;
import amara.libraries.applications.display.ingame.maps.*;
import amara.libraries.applications.display.materials.PaintableImage;
import amara.libraries.applications.display.meshes.ConnectedPointsMesh;
import amara.libraries.applications.display.models.ModelObject;
import amara.libraries.physics.shapes.*;
import amara.tools.editors.map.MapEditorApplication;
import amara.tools.editors.map.gui.ScreenController_MapEditor;

public class MapEditorAppState extends BaseDisplayAppState<MapEditorApplication> implements ActionListener{

    public enum Action{
        NONE,
        VIEW,
        PLACE_HITBOX_CIRCLE,
        PLACE_HITBOX_CUSTOM,
        PLACE_VISUAL,
        REMOVE,
        PAINT_TERRAIN_ALPHAMAP,
    }
    private Action currentAction = Action.NONE;
    private Action actionBeforeRemove;
    private Vector2f currentHoveredLocation = new Vector2f();
    private MapObstacle obstacleToPlace;
    private Spatial toolCursor;
    private double circleRadius = 3;
    private double circleRadiusStep = 0.2f;
    private ArrayList<Vector2D> customShapePoints = new ArrayList<>();
    private String[] visualsModelSkinPaths = new String[]{
        "Models/3dsa_fantasy_forest_animal_bones/skin.xml",
        "Models/3dsa_fantasy_forest_cave_entrance/skin.xml",
        "Models/3dsa_fantasy_forest_dead_tree/skin.xml",
        "Models/3dsa_fantasy_forest_fence_1/skin.xml",
        "Models/3dsa_fantasy_forest_fence_2/skin.xml",
        "Models/3dsa_fantasy_forest_grass_1/skin.xml",
        "Models/3dsa_fantasy_forest_grass_2/skin.xml",
        "Models/3dsa_fantasy_forest_grass_3/skin.xml",
        "Models/3dsa_fantasy_forest_grass_4/skin.xml",
        "Models/3dsa_fantasy_forest_grass_5/skin.xml",
        "Models/3dsa_fantasy_forest_ladder/skin.xml",
        "Models/3dsa_fantasy_forest_mushroom_1/skin.xml",
        "Models/3dsa_fantasy_forest_mushroom_2/skin.xml",
        "Models/3dsa_fantasy_forest_mushroom_3/skin.xml",
        "Models/3dsa_fantasy_forest_mushroom_4/skin.xml",
        "Models/3dsa_fantasy_forest_pillar/skin.xml",
        "Models/3dsa_fantasy_forest_pillar_curved/skin.xml",
        "Models/3dsa_fantasy_forest_pillar_door/skin.xml",
        "Models/3dsa_fantasy_forest_pillar_door_broken/skin.xml",
        "Models/3dsa_fantasy_forest_pillar_script/skin.xml",
        "Models/3dsa_fantasy_forest_push_cart/skin.xml",
        "Models/3dsa_fantasy_forest_rock_1/skin.xml",
        "Models/3dsa_fantasy_forest_rock_2/skin.xml",
        "Models/3dsa_fantasy_forest_rock_3/skin.xml",
        "Models/3dsa_fantasy_forest_rock_4/skin.xml",
        "Models/3dsa_fantasy_forest_rock_5/skin.xml",
        "Models/3dsa_fantasy_forest_rock_6/skin.xml",
        "Models/3dsa_fantasy_forest_rock_7/skin.xml",
        "Models/3dsa_fantasy_forest_rock_8/skin.xml",
        "Models/3dsa_fantasy_forest_sign_board/skin_left.xml",
        "Models/3dsa_fantasy_forest_sign_board/skin_right.xml",
        "Models/3dsa_fantasy_forest_tree_1/skin.xml",
        "Models/3dsa_fantasy_forest_tree_2/skin.xml",
        "Models/3dsa_fantasy_forest_tree_log_1/skin.xml",
        "Models/3dsa_fantasy_forest_tree_log_2/skin.xml",
        "Models/3dsa_fantasy_forest_tree_log_3/skin.xml",
        "Models/3dsa_fantasy_forest_waypoint_base/skin.xml",
        "Models/3dsa_fantasy_forest_waypoint_crystal/skin.xml",
        "Models/3dsa_fantasy_forest_well/skin.xml"
    };
    private int visualModelSkinPathIndex = 0;
    private boolean changeVisualDirectionOrScale = true;
    private Vector3f visualDirection = new Vector3f(0, 0, -1);
    private float visualDirectionStep = 5;
    private float visualScale = 1;
    private float visualScaleStep = 0.025f;
    private boolean sideOrTopDownView;
    private int terrainTextureIndex = 0;
    private int terrainTextureSize = 10;
    private int terrainTextureSizeStep = 1;
    private int terrainTexturePaintStrength = 255;
    private int terrainTexturePaintStrengthStep = 10;
    private boolean changeTerrainTextureSizeOrStrength = true;
    private boolean isPaintingTerrainAlphamap;
    
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
        mainApplication.getInputManager().addMapping("editor_5", new KeyTrigger(KeyInput.KEY_5));
        mainApplication.getInputManager().addMapping("editor_6", new KeyTrigger(KeyInput.KEY_6));
        mainApplication.getInputManager().addMapping("editor_7", new KeyTrigger(KeyInput.KEY_7));
        mainApplication.getInputManager().addMapping("editor_8", new KeyTrigger(KeyInput.KEY_8));
        mainApplication.getInputManager().addMapping("editor_x", new KeyTrigger(KeyInput.KEY_X));
        mainApplication.getInputManager().addMapping("editor_q", new KeyTrigger(KeyInput.KEY_Q));
        mainApplication.getInputManager().addMapping("editor_w", new KeyTrigger(KeyInput.KEY_W));
        mainApplication.getInputManager().addMapping("editor_enter", new KeyTrigger(KeyInput.KEY_RETURN));
        mainApplication.getInputManager().addMapping("editor_left_shift", new KeyTrigger(KeyInput.KEY_LSHIFT));
        mainApplication.getInputManager().addListener(this, new String[]{
            "editor_mouse_click_left","editor_mouse_click_right","editor_mouse_wheel_up","editor_mouse_wheel_down",
            "editor_1","editor_2","editor_3","editor_4","editor_5","editor_6","editor_7","editor_8",
            "editor_x","editor_q","editor_w","editor_enter","editor_left_shift"
        });
        getAppState(MapObstaclesAppState.class).setDisplayObstacles(true);
        setAction(Action.VIEW);
        changeCameraAngle();
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getInputManager().removeListener(this);
        getAppState(MapObstaclesAppState.class).setDisplayObstacles(false);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        Vector2f groundLocation = getAppState(MapAppState.class).getGroundLocation_Cursor();
        if((groundLocation != null) && (!groundLocation.equals(currentHoveredLocation))){
            currentHoveredLocation.set(groundLocation);
            updateToolCursorLocation();
            switch(currentAction){
                case PAINT_TERRAIN_ALPHAMAP:
                    if(isPaintingTerrainAlphamap){
                        paintAlphamap();
                    }
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
        if(actionName.equals("editor_1") && value){
            setAction(Action.VIEW);
        }
        else if(actionName.equals("editor_2") && value){
            setAction(Action.PLACE_HITBOX_CIRCLE);
        }
        else if(actionName.equals("editor_3") && value){
            setAction(Action.PLACE_HITBOX_CUSTOM);
        }
        else if(actionName.equals("editor_4") && value){
            setAction(Action.PLACE_VISUAL);
        }
        else if(actionName.equals("editor_5") && value){
            setAction(Action.PAINT_TERRAIN_ALPHAMAP);
        }
        else if(actionName.equals("editor_6") && value){
            changeCameraAngle();
        }
        else if(actionName.equals("editor_7") && value){
            ScreenController_MapEditor screenController_MapEditor = getAppState(NiftyAppState.class).getScreenController(ScreenController_MapEditor.class);
            screenController_MapEditor.toggleHoveredCoordinates();
        }
        else if(actionName.equals("editor_8") && value){
            mirrorObstacles();
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
                        addCurrentObstacle();
                        generateNewToolCursor();
                    }
                    else if(actionName.equals("editor_mouse_wheel_up")){
                        circleRadius += circleRadiusStep;
                        generateNewToolCursor();
                    }
                    else if(actionName.equals("editor_mouse_wheel_down")){
                        circleRadius = Math.max(circleRadiusStep, circleRadius - circleRadiusStep);
                        generateNewToolCursor();
                    }
                    break;
                
                case PLACE_HITBOX_CUSTOM:
                    if(actionName.equals("editor_mouse_click_left") && value){
                        customShapePoints.add(new Vector2D(currentHoveredLocation.getX(), currentHoveredLocation.getY()));
                        if(customShapePoints.size() > 1){
                            generateNewToolCursor();
                        }
                    }
                    else if(actionName.equals("editor_mouse_click_right") && value){
                        cancelCurrentCustomShape();
                    }
                    else if(actionName.equals("editor_enter") && value){
                        obstacleToPlace = new MapObstacle_Polygon(customShapePoints);
                        addCurrentObstacle();
                    }
                    break;
                
                case PLACE_VISUAL:
                    if(actionName.equals("editor_mouse_click_left") && value){
                        Vector3f position = new Vector3f(currentHoveredLocation.getX(), 0, currentHoveredLocation.getY());
                        ModelVisual modelVisual = new ModelVisual(visualsModelSkinPaths[visualModelSkinPathIndex], position, visualDirection, visualScale);
                        map.addVisual(modelVisual);
                        mapAppState.updateVisuals();
                        generateNewToolCursor();
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
                                ArrayList<MapObstacle> obstacles = map.getPhysicsInformation().getObstacles();
                                Vector2D currentHoveredLocation2D = new Vector2D(currentHoveredLocation.getX(), currentHoveredLocation.getY());
                                for(int i=0;i<obstacles.size();i++){
                                    if(obstacles.get(i).getConvexedOutline().contains(currentHoveredLocation2D)){
                                        obstacles.remove(i);
                                        getAppState(MapObstaclesAppState.class).update();
                                        break;
                                    }
                                }
                                break;
                            
                            case PLACE_VISUAL:
                                Node visualsNode = getAppState(MapAppState.class).getVisualsNode();
                                CollisionResult collisionResult = mainApplication.getRayCastingResults_Cursor(visualsNode).getClosestCollision();
                                if (collisionResult != null) {
                                    Spatial parent = collisionResult.getGeometry().getParent();
                                    if ((parent != null) && (parent.getParent() instanceof ModelObject modelObject)) {
                                        MapVisual mapVisual = mapAppState.getMapVisual(modelObject);
                                        if (mapVisual != null) {
                                            map.removeVisual(mapVisual);
                                            mapAppState.updateVisuals();
                                        }
                                    }
                                }
                                break;
                        }
                    }
                    break;
                
                case PAINT_TERRAIN_ALPHAMAP:
                    if(actionName.equals("editor_mouse_click_left")){
                        isPaintingTerrainAlphamap = value;
                        if(isPaintingTerrainAlphamap){
                            paintAlphamap();
                        }
                    }
                    else if(actionName.equals("editor_mouse_wheel_up")){
                        if(changeTerrainTextureSizeOrStrength){
                            terrainTextureSize += terrainTextureSizeStep;
                            generateNewToolCursor();
                        }
                        else{
                            terrainTexturePaintStrength = Math.min(terrainTexturePaintStrength + terrainTexturePaintStrengthStep, 255);
                            updateToolInformation();
                        }
                    }
                    else if(actionName.equals("editor_mouse_wheel_down")){
                        if(changeTerrainTextureSizeOrStrength){
                            terrainTextureSize = Math.max(terrainTextureSizeStep, terrainTextureSize - terrainTextureSizeStep);
                            generateNewToolCursor();
                        }
                        else{
                            terrainTexturePaintStrength = Math.max(0, terrainTexturePaintStrength - terrainTexturePaintStrengthStep);
                            updateToolInformation();
                        }
                    }
                    else if(actionName.equals("editor_q") && value){
                        changeTerrainTextureIndex(false);
                    }
                    else if(actionName.equals("editor_w") && value){
                        changeTerrainTextureIndex(true);
                    }
                    else if(actionName.equals("editor_left_shift")){
                        changeTerrainTextureSizeOrStrength = (!value);
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
                    removeToolPreviewShape();
                    break;
                
                case PLACE_HITBOX_CUSTOM:
                    cancelCurrentCustomShape();
                    break;
                
                case PLACE_VISUAL:
                    removeToolPreviewShape();
                    break;
                
                case PAINT_TERRAIN_ALPHAMAP:
                    removeToolPreviewShape();
                    break;
            }
            currentAction = action;
            switch(currentAction){
                case VIEW:
                    getAppState(IngameCameraAppState.class).setZoomEnabled(true);
                    break;
                
                case PLACE_HITBOX_CIRCLE:
                    generateNewToolCursor();
                    break;
                
                case PLACE_HITBOX_CUSTOM:
                    break;
                
                case PLACE_VISUAL:
                    generateNewToolCursor();
                    break;
                
                case PAINT_TERRAIN_ALPHAMAP:
                    generateNewToolCursor();
                    break;
            }
            updateToolInformation();
        }
    }
    
    private void generateNewToolCursor(){
        removeToolPreviewShape();
        Node parentNode = null;
        switch(currentAction){
            case PLACE_HITBOX_CIRCLE:
                obstacleToPlace = new MapObstacle_Circle(new Vector2D(), circleRadius);
                toolCursor = MapObstaclesAppState.generateGeometry(mainApplication.getAssetManager(), obstacleToPlace.getConvexedOutline().getConvexShapes().get(0));
                parentNode = getAppState(MapObstaclesAppState.class).getObstaclesNode();
                break;
            
            case PLACE_HITBOX_CUSTOM:
                obstacleToPlace = null;
                Vector2D[] basePoints = customShapePoints.toArray(Vector2D[]::new);
                ConnectedPointsMesh connectedPointsMesh = new ConnectedPointsMesh(basePoints);
                toolCursor = MapObstaclesAppState.generateGeometry(mainApplication.getAssetManager(), connectedPointsMesh, ColorRGBA.Blue);
                parentNode = getAppState(MapObstaclesAppState.class).getObstaclesNode();
                break;
            
            case PLACE_VISUAL:
                toolCursor = new ModelObject(mainApplication.getAssetManager(), "/" + visualsModelSkinPaths[visualModelSkinPathIndex]);
                toolCursor.setShadowMode(RenderQueue.ShadowMode.Cast);
                parentNode = getAppState(MapAppState.class).getVisualsNode();
                updateVisualToPlaceTransformation();
                break;
            
            case PAINT_TERRAIN_ALPHAMAP:
                MapAppState mapAppState = getAppState(MapAppState.class);
                TerrainAlphamap alphamap = mapAppState.getMapTerrain().getAlphamaps()[terrainTextureIndex / 3];
                float rectangleSizeX = ((((float) terrainTextureSize) / alphamap.getPaintableImage().getWidth()) * mapAppState.getMap().getPhysicsInformation().getWidth());
                float rectangleSizeY = ((((float) terrainTextureSize) / alphamap.getPaintableImage().getHeight()) * mapAppState.getMap().getPhysicsInformation().getHeight());
                toolCursor = MapObstaclesAppState.generateGeometry(mainApplication.getAssetManager(), new Rectangle(rectangleSizeX, rectangleSizeY), ColorRGBA.White);
                parentNode = getAppState(MapAppState.class).getVisualsNode();
                break;
        }
        parentNode.attachChild(toolCursor);
        updateToolCursorLocation();
    }
    
    private void removeToolPreviewShape(){
        if(toolCursor != null){
            if(toolCursor.getParent() != null){
                toolCursor.getParent().detachChild(toolCursor);
            }
            toolCursor = null;
        }
    }
    
    private void updateToolCursorLocation(){
        if((toolCursor != null) && (currentAction != Action.PLACE_HITBOX_CUSTOM)){
            toolCursor.setLocalTranslation(currentHoveredLocation.getX(), 0, currentHoveredLocation.getY());
        }
        switch(currentAction){
            case PLACE_HITBOX_CIRCLE:
                obstacleToPlace.getConvexedOutline().getConvexShapes().get(0).setTransform(new Transform2D(1, 0, currentHoveredLocation.getX(), currentHoveredLocation.getY()));
                break;

            case PLACE_VISUAL:
                float y1 = getAppState(MapAppState.class).getMapHeightmap().getHeight(currentHoveredLocation);
                toolCursor.setLocalTranslation(toolCursor.getLocalTranslation().getX(), y1, toolCursor.getLocalTranslation().getZ());
                break;

            case PAINT_TERRAIN_ALPHAMAP:
                float y2 = getAppState(MapAppState.class).getMapTerrain().getHeight(currentHoveredLocation);
                toolCursor.setLocalTranslation(toolCursor.getLocalTranslation().getX(), y2, toolCursor.getLocalTranslation().getZ());
                break;
        }
    }
    
    private void addCurrentObstacle(){
        ArrayList<MapObstacle> obstacles = getAppState(MapAppState.class).getMap().getPhysicsInformation().getObstacles();
        obstacles.add(obstacleToPlace);
        getAppState(MapObstaclesAppState.class).update();
        cancelCurrentCustomShape();
    }
    
    private void cancelCurrentCustomShape(){
        customShapePoints.clear();
        removeToolPreviewShape();
    }
    
    private void changeVisualIndex(boolean increaseOrDecrease){
        visualModelSkinPathIndex += (increaseOrDecrease?1:-1);
        if(visualModelSkinPathIndex < 0){
            visualModelSkinPathIndex = (visualsModelSkinPaths.length - 1);
        }
        else if(visualModelSkinPathIndex >= visualsModelSkinPaths.length){
            visualModelSkinPathIndex = 0;
        }
        generateNewToolCursor();
        updateToolInformation();
    }
    
    private void changeVisualDirectionOrScale(boolean mouseWheelUpOrDown){
        float factor = (mouseWheelUpOrDown?1:-1);
        if(changeVisualDirectionOrScale){
            visualDirection = JMonkeyUtil.getQuaternion_Y(factor * visualDirectionStep).mult(visualDirection);
        }
        else{
            visualScale += (factor * visualScaleStep);
        }
        updateVisualToPlaceTransformation();
        updateToolInformation();
    }
    
    private void updateVisualToPlaceTransformation(){
        JMonkeyUtil.setLocalRotation(toolCursor, visualDirection);
        toolCursor.setLocalScale(visualScale);
    }

    private void paintAlphamap() {
        MapAppState mapAppState = getAppState(MapAppState.class);
        MapTerrain mapTerrain = mapAppState.getMapTerrain();
        int alphamapIndex = (terrainTextureIndex / 3);
        int channelIndex = (terrainTextureIndex % 3);
        PaintableImage paintableImage = mapTerrain.getAlphamaps()[alphamapIndex].getPaintableImage();
        float startX_Portion = (currentHoveredLocation.getX() / mapAppState.getMap().getPhysicsInformation().getWidth());
        float startY_Portion = (1 - (currentHoveredLocation.getY() / mapAppState.getMap().getPhysicsInformation().getHeight()));
        int startX = Math.max(0, Math.round((startX_Portion * paintableImage.getWidth()) - (terrainTextureSize / 2f)));
        int startY = Math.max(0, Math.round((startY_Portion * paintableImage.getHeight()) - (terrainTextureSize / 2f)));
        int endX = Math.min((startX + terrainTextureSize), paintableImage.getWidth() - 1);
        int endY = Math.min((startY + terrainTextureSize), paintableImage.getHeight() - 1);
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                int index = paintableImage.getIndex(x, y, channelIndex);
                paintableImage.setPixel(index, terrainTexturePaintStrength);
            }
        }
        mapTerrain.updateAlphamap(alphamapIndex);
    }

    private void changeTerrainTextureIndex(boolean increaseOrDecrease){
        terrainTextureIndex += (increaseOrDecrease?1:-1);
        int texturesCount = getAppState(MapAppState.class).getMap().getTerrainSkin().getTextures().length;
        if(terrainTextureIndex < 0){
            terrainTextureIndex = (texturesCount - 1);
        }
        else if(terrainTextureIndex >= texturesCount){
            terrainTextureIndex = 0;
        }
        updateToolInformation();
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

    public void mirrorObstacles() {
        MapPhysicsInformation mapPhysicsInformation = getAppState(MapAppState.class).getMap().getPhysicsInformation();
        ArrayList<MapObstacle> obstacles = mapPhysicsInformation.getObstacles();
        for (MapObstacle obstacle : new ArrayList<>(obstacles)) {
            MapObstacle mirroredObstacle = null;
            if (obstacle instanceof MapObstacle_Polygon) {
                ArrayList<Vector2D> mirroredOutline = new ArrayList<>();
                for (Vector2D outlinePoint : obstacle.getConvexedOutline().getCcwOutline()) {
                    mirroredOutline.add(mirrorPositionX(outlinePoint, mapPhysicsInformation.getWidth()));
                }
                mirroredObstacle = new MapObstacle_Polygon(mirroredOutline);
            } else if (obstacle instanceof MapObstacle_Circle) {
                MapObstacle_Circle obstacle_Circle = (MapObstacle_Circle) obstacle;
                Circle circle = (Circle) obstacle_Circle.getConvexedOutline().getConvexShapes().get(0);
                Vector2D mirroredPosition = mirrorPositionX(circle.getGlobalPosition(), mapPhysicsInformation.getWidth());
                mirroredObstacle = new MapObstacle_Circle(mirroredPosition, circle.getLocalRadius());
            }
            obstacles.add(mirroredObstacle);
        }
        getAppState(MapObstaclesAppState.class).update();
    }

    private Vector2D mirrorPositionX(Vector2D position, float mapWidth) {
        double mirroredX = ((1 - (position.getX() / mapWidth)) * mapWidth);
        return new Vector2D(mirroredX, position.getY());
    }

    private void updateToolInformation(){
        ScreenController_MapEditor screenController_MapEditor = getAppState(NiftyAppState.class).getScreenController(ScreenController_MapEditor.class);
        String text = "Tool: " + currentAction.name();
        String imagePath = null;
        switch(currentAction){
            case PLACE_VISUAL:
                text += " [" + visualsModelSkinPaths[visualModelSkinPathIndex] + "]";
                break;
            
            case PAINT_TERRAIN_ALPHAMAP:
                int alphamapIndex = (terrainTextureIndex / 3);
                int channelIndex = (terrainTextureIndex % 3);
                text += " [AlphaMap #" + (alphamapIndex + 1) +", Texture #" + (channelIndex + 1) + ", Paint Strength " + (int) (terrainTexturePaintStrength / 2.55f) + "%]";
                imagePath = getAppState(MapAppState.class).getMap().getTerrainSkin().getTextures()[terrainTextureIndex].getFilePath();
                break;
        }
        screenController_MapEditor.setToolInformation(text);
        if(imagePath != null){
            screenController_MapEditor.showToolInformationImage(imagePath);
        }
        else{
            screenController_MapEditor.hideToolInformationImage();
        }
    }
}
