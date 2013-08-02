/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.client.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class IngameCameraAppState extends BaseAppState implements ActionListener{

    public IngameCameraAppState(){
        
    }
    private boolean[] moveDirections = new boolean[4];
    private float movementSpeed = 20;
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication.getFlyByCamera().setEnabled(false);
        mainApplication.getInputManager().addMapping("camera_up", new KeyTrigger(KeyInput.KEY_UP), new KeyTrigger(KeyInput.KEY_W));
        mainApplication.getInputManager().addMapping("camera_right", new KeyTrigger(KeyInput.KEY_RIGHT), new KeyTrigger(KeyInput.KEY_D));
        mainApplication.getInputManager().addMapping("camera_down", new KeyTrigger(KeyInput.KEY_DOWN), new KeyTrigger(KeyInput.KEY_S));
        mainApplication.getInputManager().addMapping("camera_left", new KeyTrigger(KeyInput.KEY_LEFT), new KeyTrigger(KeyInput.KEY_A));
        mainApplication.getInputManager().addListener(this, new String[]{
            "camera_up","camera_right","camera_down","camera_left"
        });
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
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
        mainApplication.getCamera().setLocation(mainApplication.getCamera().getLocation().add(movedDistance));
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
    }
}
