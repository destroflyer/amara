/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class IngameCameraAppState extends BaseDisplayAppState implements ActionListener{

    public IngameCameraAppState(){
        
    }
    private boolean[] moveDirections = new boolean[4];
    private float movementSpeed = 20;
    private Vector2f limitMinimum;
    private Vector2f limitMaximum;
    private Spatial limitSurfaceSpatial;
    private Vector2f leftTopCornerScreenLocation;
    private Vector2f rightBottomCornerScreenLocation;
    private boolean isMovementEnabled = true;
    private boolean isZoomEnabled = true;
    private int currentZoomLevel;
    private int maximumZoomLevel = -1;
    private float zoomInterval = 2;
    
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
            Vector3f movedDistance = new Vector3f();
            if(moveDirections[0]){
                movedDistance.addLocal(0, 0, 1);
            }
            if(moveDirections[1]){
                movedDistance.addLocal(-1, 0, 0);
            }
            if(moveDirections[2]){
                movedDistance.addLocal(0, 0, -1);
            }
            if(moveDirections[3]){
                movedDistance.addLocal(1, 0, 0);
            }
            movedDistance.multLocal(movementSpeed * lastTimePerFrame);
            Vector3f oldLocation = mainApplication.getCamera().getLocation().clone();
            mainApplication.getCamera().setLocation(oldLocation.add(movedDistance));
            if(limitMinimum != null){
                Vector3f minimumCornerLocation = mainApplication.getRayCastingResults_Screen(limitSurfaceSpatial, rightBottomCornerScreenLocation).getClosestCollision().getContactPoint();
                Vector3f maximumCornerLocation = mainApplication.getRayCastingResults_Screen(limitSurfaceSpatial, leftTopCornerScreenLocation).getClosestCollision().getContactPoint();
                if(((movedDistance.getX() < 0) && (minimumCornerLocation.getX() < limitMinimum.getX()))
                || ((movedDistance.getX() > 0) && (maximumCornerLocation.getX() > limitMaximum.getX()))){
                    movedDistance.setX(0);
                }
                if(((movedDistance.getZ() < 0) && (minimumCornerLocation.getZ() < limitMinimum.getY()))
                || ((movedDistance.getZ() > 0) && (maximumCornerLocation.getZ() > limitMaximum.getY()))){
                    movedDistance.setZ(0);
                }
                mainApplication.getCamera().setLocation(oldLocation.add(movedDistance));
            }
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
            moveDirections[0] = value;
        }
        else if(actionName.equals("camera_right")){
            moveDirections[1] = value;
        }
        else if(actionName.equals("camera_down")){
            moveDirections[2] = value;
        }
        else if(actionName.equals("camera_left")){
            moveDirections[3] = value;
        }
        else if(actionName.equals("camera_zoom_out")){
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

    public void setMovementEnabled(boolean isMovementEnabled){
        this.isMovementEnabled = isMovementEnabled;
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
}
