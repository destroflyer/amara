/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.models;

import java.util.*;
import java.util.stream.Collectors;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import amara.libraries.applications.display.DisplayApplication;

/**
 *
 * @author Carl
 */
public class ModelObject extends Node {

    public ModelObject(DisplayApplication mainApplication, String skinPath) {
        this.mainApplication = mainApplication;
        skin = ModelSkin.get(skinPath);
        initialize();
    }
    private DisplayApplication mainApplication;
    private ModelSkin skin;
    private LinkedList<ModelSkeleton> skeletons;
    private ArrayList<RegisteredModel> registeredModels = new ArrayList<>();

    private void initialize() {
        skeletons = skin.getModelSkeletons();
        loadAndRegisterModel();
    }

    public RegisteredModel loadAndRegisterModel() {
        Node node = skin.load();
        RegisteredModel registeredModel = new RegisteredModel(node, cloneSkeletons());
        registeredModels.add(registeredModel);
        registeredModel.initialize(this);
        attachChild(node);
        for (ModelModifier modelModifier : skin.getModelModifiers()) {
            modelModifier.modify(registeredModel);
        }
        return registeredModel;
    }

    private List<ModelSkeleton> cloneSkeletons() {
        return skeletons.stream()
                .map(ModelSkeleton::clone)
                .collect(Collectors.toList());
    }

    public void unregisterModel(Spatial spatial) {
        for (int i = 0; i < registeredModels.size(); i++) {
            RegisteredModel registeredModel = registeredModels.get(i);
            if (registeredModel.getNode() == spatial) {
                registeredModels.remove(i);
                detachChild(spatial);
                break;
            }
        }
    }

    public void playAnimation(String animationName, float loopDuration) {
        playAnimation(animationName, loopDuration, true);
    }

    public void playAnimation(String animationName, float loopDuration, boolean isLoop) {
        setAnimationName(animationName);
        setAnimationProperties(loopDuration, isLoop);
    }

    public void setAnimationName(String animationName) {
        registeredModels.forEach(registeredModel -> registeredModel.setAnimationName(animationName));
    }

    public void setAnimationProperties(float loopDuration, boolean isLoop) {
        registeredModels.forEach(registeredModel -> registeredModel.setAnimationProperties(loopDuration, isLoop));
    }

    public void stopAndRewindAnimation() {
        mainApplication.enqueueTask(() -> registeredModels.forEach(RegisteredModel::stopAndRewindAnimation));
    }

    public ModelSkin getSkin() {
        return skin;
    }

    public LinkedList<ModelSkeleton> getSkeletons() {
        return skeletons;
    }

    public RegisteredModel getOriginalRegisteredModel() {
        return registeredModels.get(0);
    }

    public Spatial getModelNode() {
        return getOriginalRegisteredModel().getNode();
    }
}
