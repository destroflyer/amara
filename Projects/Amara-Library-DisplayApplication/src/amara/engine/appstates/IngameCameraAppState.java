/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import java.awt.MouseInfo;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import amara.core.settings.Settings;
import amara.engine.applications.DisplayApplication;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Carl
 */
public class IngameCameraAppState extends BaseDisplayAppState<DisplayApplication> implements ActionListener{

    public IngameCameraAppState(boolean shouldBeLimited){
        this.shouldBeLimited = shouldBeLimited;
    }
    private boolean[] moveDirections_Keys = new boolean[4];
    private boolean[] moveDirections_Cursor = new boolean[4];
    private boolean isCursorPositionInitialized;
    private Vector2f limitMinimum;
    private Vector2f limitMaximum;
    private Spatial limitSurfaceSpatial;
    private Vector2f leftTopCornerScreenLocation;
    private Vector2f rightBottomCornerScreenLocation;
    private boolean shouldBeLimited;
    private boolean isMovementEnabled = true;
    private boolean isZoomEnabled = true;
    private int currentZoomLevel;
    private int maximumZoomLevel = -1;
    private float zoomInterval = 2;
    private Vector3f tmpCameraLocation = new Vector3f();
    private Quaternion tmpCameraRotation = new Quaternion();
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        leftTopCornerScreenLocation = new Vector2f(0, (mainApplication.getContext().getSettings().getHeight() - 1));
        rightBottomCornerScreenLocation = new Vector2f((mainApplication.getContext().getSettings().getWidth() - 1), 0);
        mainApplication.getFlyByCamera().setEnabled(false);
        mainApplication.getInputManager().addMapping("camera_up", new KeyTrigger(KeyInput.KEY_UP));
        mainApplication.getInputManager().addMapping("camera_right", new KeyTrigger(KeyInput.KEY_RIGHT));
        mainApplication.getInputManager().addMapping("camera_down", new KeyTrigger(KeyInput.KEY_DOWN));
        mainApplication.getInputManager().addMapping("camera_left", new KeyTrigger(KeyInput.KEY_LEFT));
        mainApplication.getInputManager().addMapping("camera_zoom_in", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        mainApplication.getInputManager().addMapping("camera_zoom_out", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        mainApplication.getInputManager().addListener(this, new String[]{
            "camera_up","camera_right","camera_down","camera_left","camera_zoom_in","camera_zoom_out"
        });
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        if(isMovementEnabled){
            checkCursorCameraMovement();
            Vector3f movedDistance = new Vector3f();
            if(moveDirections_Keys[0] || moveDirections_Cursor[0]){
                movedDistance.addLocal(0, 0, 1);
            }
            if(moveDirections_Keys[1] || moveDirections_Cursor[1]){
                movedDistance.addLocal(-1, 0, 0);
            }
            if(moveDirections_Keys[2] || moveDirections_Cursor[2]){
                movedDistance.addLocal(0, 0, -1);
            }
            if(moveDirections_Keys[3] || moveDirections_Cursor[3]){
                movedDistance.addLocal(1, 0, 0);
            }
            movedDistance.multLocal(Settings.getFloat("camera_movement_speed") * lastTimePerFrame);
            Vector3f oldLocation = mainApplication.getCamera().getLocation().clone();
            mainApplication.getCamera().setLocation(oldLocation.add(movedDistance));
            if(limitMinimum != null){
                CollisionResult minimumCornerCollisionResult = mainApplication.getRayCastingResults_Screen(limitSurfaceSpatial, rightBottomCornerScreenLocation).getClosestCollision();
                CollisionResult maximumCornerCollisionResult = mainApplication.getRayCastingResults_Screen(limitSurfaceSpatial, leftTopCornerScreenLocation).getClosestCollision();
                if((minimumCornerCollisionResult != null) && (maximumCornerCollisionResult != null)){
                    if(((movedDistance.getX() < 0) && (minimumCornerCollisionResult.getContactPoint().getX() < limitMinimum.getX()))
                    || ((movedDistance.getX() > 0) && (maximumCornerCollisionResult.getContactPoint().getX() > limitMaximum.getX()))){
                        movedDistance.setX(0);
                    }
                    if(((movedDistance.getZ() < 0) && (minimumCornerCollisionResult.getContactPoint().getZ() < limitMinimum.getY()))
                    || ((movedDistance.getZ() > 0) && (maximumCornerCollisionResult.getContactPoint().getZ() > limitMaximum.getY()))){
                        movedDistance.setZ(0);
                    }
                    mainApplication.getCamera().setLocation(oldLocation.add(movedDistance));
                }
            }
        }
    }
    
    private void checkCursorCameraMovement(){
        for(int i=0;i<moveDirections_Cursor.length;i++){
            moveDirections_Cursor[i] = false;
        }
        Vector2f cursorPosition = mainApplication.getInputManager().getCursorPosition();
        if(isCursorPositionInitialized){
            if(isMouseInWindow()){
                float width = mainApplication.getContext().getSettings().getWidth();
                float height = mainApplication.getContext().getSettings().getHeight();
                float cursorMovementBorderSize = Settings.getFloat("camera_movement_cursor_border");
                moveDirections_Cursor[0] = ((cursorPosition.getY() >= (height - cursorMovementBorderSize)) && (cursorPosition.getY() < height));
                moveDirections_Cursor[1] = ((cursorPosition.getX() >= (width - cursorMovementBorderSize)) && (cursorPosition.getX() < width));
                moveDirections_Cursor[2] = ((cursorPosition.getY() >= 0) && (cursorPosition.getY() < cursorMovementBorderSize));
                moveDirections_Cursor[3] = ((cursorPosition.getX() >= 0) && (cursorPosition.getX() < cursorMovementBorderSize));
            }
        }
        else{
            //[0|0] is the uninitialized state of the cursor position
            isCursorPositionInitialized = ((cursorPosition.getX() != 0) || (cursorPosition.getY() != 0));
        }
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getInputManager().removeListener(this);
        mainApplication.getFlyByCamera().setEnabled(true);
    }

    @Override
    public void onAction(String actionName, boolean value, float lastTimePerFrame){
        if(actionName.equals("camera_up")){
            moveDirections_Keys[0] = value;
        }
        else if(actionName.equals("camera_right")){
            moveDirections_Keys[1] = value;
        }
        else if(actionName.equals("camera_down")){
            moveDirections_Keys[2] = value;
        }
        else if(actionName.equals("camera_left")){
            moveDirections_Keys[3] = value;
        }
        else if(isEnabled()){
            if(actionName.equals("camera_zoom_out")){
                if(isZoomEnabled){
                    zoom(-1);
                }
            }
            else if(actionName.equals("camera_zoom_in")){
                if(isZoomEnabled){
                    zoom(1);
                }
            }
        }
    }
    
    public void setLimit(Vector2f minimum, Vector2f maximum, Spatial surfaceSpatial){
        limitMinimum = minimum;
        limitMaximum = maximum;
        limitSurfaceSpatial = surfaceSpatial;
    }

    public void zoom(int zoomLevelChange){
        int newZoomLevel = (currentZoomLevel + zoomLevelChange);
        if((maximumZoomLevel == -1) || ((newZoomLevel >= 0) && (newZoomLevel <= maximumZoomLevel))){
            Vector3f distance = mainApplication.getCamera().getDirection().mult(zoomInterval * zoomLevelChange);
            mainApplication.getCamera().setLocation(mainApplication.getCamera().getLocation().add(distance));
            currentZoomLevel = newZoomLevel;
        }
    }
    
    public void lookAt(Vector2f mapLocation){
        float mapHeight = getAppState(MapAppState.class).getMapHeightmap().getHeight(mapLocation);
        mainApplication.getCamera().setLocation(new Vector3f(mapLocation.getX(), mapHeight, mapLocation.getY()));
        Vector3f distance = mainApplication.getCamera().getDirection().mult(-40);
        mainApplication.getCamera().setLocation(mainApplication.getCamera().getLocation().add(distance));
    }
    
    public void saveState(){
        tmpCameraLocation.set(mainApplication.getCamera().getLocation());
        tmpCameraRotation.set(mainApplication.getCamera().getRotation());
    }
    
    public void restoreState(){
        mainApplication.getCamera().setLocation(tmpCameraLocation);
        mainApplication.getCamera().setRotation(tmpCameraRotation);
    }

    public void setMovementEnabled(boolean isMovementEnabled){
        this.isMovementEnabled = isMovementEnabled;
    }

    public boolean shouldBeLimited(){
        return shouldBeLimited;
    }

    public boolean isMovementEnabled(){
        return isMovementEnabled;
    }

    public void setZoomEnabled(boolean isZoomEnabled){
        this.isZoomEnabled = isZoomEnabled;
    }

    public boolean isZoomEnabled(){
        return isZoomEnabled;
    }

    public void setMaximumZoomLevel(int maximumZoomLevel){
        this.maximumZoomLevel = maximumZoomLevel;
    }

    public void setZoomInterval(float zoomInterval){
        this.zoomInterval = zoomInterval;
    }
    
    private static boolean isMouseInWindow(){
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;
        int minX = Display.getX();
        int minY = Display.getY();
        if(!Settings.getBoolean("fullscreen")){
            minX += 2;
            minY += 26;
        }
        int maxX = (minX + Display.getWidth());
        int maxY = (minY + Display.getHeight());
        return !((mouseX < minX) || (mouseX > maxX) || (mouseY < minY) || (mouseY > maxY));
    }
}
