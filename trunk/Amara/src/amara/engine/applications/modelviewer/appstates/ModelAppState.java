/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.modelviewer.appstates;

import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.MouseInput;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.animation.AnimChannel;
import amara.engine.JMonkeyUtil;
import amara.engine.applications.ingame.client.models.ModelObject;
import amara.engine.appstates.BaseDisplayAppState;

/**
 *
 * @author Carl
 */
public class ModelAppState extends BaseDisplayAppState implements ActionListener{
    
    private final String[] modelSkinPaths = new String[]{
        "/Models/daydream/skin_default.xml",
        "/Models/daydream/skin_fire.xml",
        "/Models/daydream/skin_nightmare.xml",
        "/Models/daydream/skin_envy.xml",
        "/Models/daydream/skin_inuyasha.xml",
        "/Models/oz/skin_default.xml",
        "/Models/oz/skin_oz_junior.xml",
        "/Models/minion/skin_default.xml",
        "/Models/minion/skin_gentleman.xml",
        "/Models/wizard/skin_default.xml",
        "/Models/robot/skin_default.xml",
        "/Models/robot/skin_definitely_not.xml",
        "/Models/jaime/skin_default.xml",
        "/Models/soldier/skin_default.xml",
        "/Models/steve/skin_default.xml",
    };
    private ModelObject[] modelObjects;
    private String[][] animationNames;
    private int modelIndex = -1;
    private int animationIndex = -1;
    private float rotation;
    private boolean backgroundWhiteOrBlack;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        modelObjects = new ModelObject[modelSkinPaths.length];
        animationNames = new String[modelSkinPaths.length][];
        for(int i=0;i<modelObjects.length;i++){
            modelObjects[i] = new ModelObject(mainApplication, modelSkinPaths[i]);
            AnimControl animationControl = modelObjects[i].getModelSpatial().getControl(AnimControl.class);
            if(animationControl != null){
                animationNames[i] = animationControl.getAnimationNames().toArray(new String[0]);
            }
            else{
                animationNames[i] = new String[0];
            }
        }
        mainApplication.getInputManager().addMapping("mouse_click_left", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        mainApplication.getInputManager().addMapping("refresh", new KeyTrigger(KeyInput.KEY_F5));
        mainApplication.getInputManager().addListener(this, new String[]{
            "mouse_click_left","refresh"
        });
        showModel(0);
    }
    
    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        rotation += (30 * lastTimePerFrame);
        while(rotation >= 360){
            rotation -= 360;
        }
        modelObjects[modelIndex].setLocalRotation(JMonkeyUtil.getQuaternion_Y(rotation));
    }
    
    private void showModel(int index){
        if(modelIndex != -1){
            String[] pathParts1 = modelSkinPaths[modelIndex].split("/");
            String[] pathParts2 = modelSkinPaths[index].split("/");
            if(pathParts1[2].equals(pathParts2[2])){
                AnimChannel animationChannel1 = modelObjects[modelIndex].getModelSpatial().getControl(AnimControl.class).getChannel(0);
                AnimChannel animationChannel2 = modelObjects[index].getModelSpatial().getControl(AnimControl.class).getChannel(0);
                JMonkeyUtil.copyAnimation(animationChannel1, animationChannel2);
            }
            else{
                animationIndex = -1;
            }
            mainApplication.getRootNode().detachChild(modelObjects[modelIndex]);
            modelObjects[modelIndex].stopAndRewindAnimation();
        }
        modelIndex = index;
        mainApplication.getRootNode().attachChild(modelObjects[modelIndex]);
    }

    @Override
    public void onAction(String name, boolean value, float lastTimePerFrame){
        if(name.equals("mouse_click_left") && value){
            Vector2f cursorPosition = mainApplication.getInputManager().getCursorPosition();
            boolean cursorLeftOrRight = (cursorPosition.getX() < (mainApplication.getContext().getSettings().getWidth() / 2));
            boolean cursorTopOrBottom = (cursorPosition.getY() > (mainApplication.getContext().getSettings().getHeight() / 2));
            if(cursorTopOrBottom){
                if(cursorLeftOrRight){
                    animationIndex++;
                    if(animationIndex < animationNames[modelIndex].length){
                        modelObjects[modelIndex].playAnimation(animationNames[modelIndex][animationIndex], 2);
                    }
                    else{
                        animationIndex = -1;
                        modelObjects[modelIndex].stopAndRewindAnimation();
                    }
                }
                else{
                    backgroundWhiteOrBlack = (!backgroundWhiteOrBlack);
                    mainApplication.getViewPort().setBackgroundColor(backgroundWhiteOrBlack?ColorRGBA.White:ColorRGBA.Black);
                }
            }
            else{
                int newModelIndex = (modelIndex + (cursorLeftOrRight?-1:1));
                if(newModelIndex < 0){
                    newModelIndex = (modelSkinPaths.length - 1);
                }
                else if(newModelIndex >= modelSkinPaths.length){
                    newModelIndex = 0;
                }
                showModel(newModelIndex);
            }
        }
        else if(name.equals("refresh") && value){
            mainApplication.getRootNode().detachChild(modelObjects[modelIndex]);
            modelObjects[modelIndex] = new ModelObject(mainApplication, modelSkinPaths[modelIndex]);
            mainApplication.getRootNode().attachChild(modelObjects[modelIndex]);
        }
    }
}
