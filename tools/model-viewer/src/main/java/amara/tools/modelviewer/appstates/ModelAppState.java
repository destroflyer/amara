/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.tools.modelviewer.appstates;

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
import amara.libraries.applications.display.JMonkeyUtil;
import amara.libraries.applications.display.appstates.BaseDisplayAppState;
import amara.libraries.applications.display.models.ModelObject;
import amara.tools.modelviewer.ModelViewerApplication;

/**
 *
 * @author Carl
 */
public class ModelAppState extends BaseDisplayAppState<ModelViewerApplication> implements ActionListener{
    
    private final String[] modelSkinPaths = new String[]{
        "Models/tristan/skin_default.xml",
        "Models/elven_archer/skin_default.xml",
        "Models/alice/skin_default.xml",
        "Models/aland/skin_default.xml",
        "Models/dosaz/skin_default.xml",
        "Models/ghost/skin.xml",
        "Models/garmon/skin_default.xml",
        "Models/dwarf_warrior/skin_default.xml",
        "Models/scarlet/skin_default.xml",
        "Models/kachujin/skin_default.xml",
        "Models/maw/skin_default.xml",
        "Models/maria/skin_default.xml",
        "Models/erika/skin_default.xml",
        "Models/ganfaul/skin_default.xml",
        "Models/daydream/skin_default.xml",
        "Models/daydream/skin_fire.xml",
        "Models/daydream/skin_envy.xml",
        "Models/daydream/skin_inuyasha.xml",
        "Models/daydream/skin_amazoness.xml",
        "Models/oz/skin_oz_junior.xml",
        "Models/minion/skin_gentleman.xml",
        "Models/wizard/skin_default.xml",
        "Models/robot/skin_definitely_not.xml",
        "Models/jaime/skin_default.xml",
        "Models/soldier/skin_default.xml",
        "Models/steve/skin_default.xml",
        "Models/nathalya/skin_default.xml",
        "Models/forest_monster/skin.xml",
        "Models/3dsa_medieval_knight/skin_team_enemy.xml",
        "Models/3dsa_archer/skin_team_enemy.xml",
        "Models/3dsa_fire_dragon/skin.xml"
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
            AnimControl animationControl = modelObjects[i].getModelNode().getControl(AnimControl.class);
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
            if(pathParts1[1].equals(pathParts2[1])){
                AnimControl animationControl1 = modelObjects[modelIndex].getModelNode().getControl(AnimControl.class);
                AnimControl animationControl2 = modelObjects[index].getModelNode().getControl(AnimControl.class);
                if((animationControl1 != null) && (animationControl2 != null)){
                    AnimChannel animationChannel1 = modelObjects[modelIndex].getModelNode().getControl(AnimControl.class).getChannel(0);
                    AnimChannel animationChannel2 = modelObjects[index].getModelNode().getControl(AnimControl.class).getChannel(0);
                    JMonkeyUtil.copyAnimation(animationChannel1, animationChannel2);
                }
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
