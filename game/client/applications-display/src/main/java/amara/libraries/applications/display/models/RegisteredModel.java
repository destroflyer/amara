/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.applications.display.models;

import amara.core.settings.Settings;
import amara.libraries.applications.display.JMonkeyUtil;
import com.jme3.animation.*;
import com.jme3.scene.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carl
 */
public class RegisteredModel {

    public RegisteredModel(Node node, List<ModelSkeleton> skeletons) {
        this.node = node;
        this.skeletons = skeletons;
    }
    private Node node;
    private List<ModelSkeleton> skeletons;
    private HashMap<String, Node> boneAttachmentsNodes = new HashMap<>();

    public void initialize(ModelObject modelObject) {
        // Animation
        for (ModelSkeleton skeleton : skeletons) {
            AnimControl skeletonAnimationControl = skeleton.getAnimationControl();
            // Clone animations since they are shared otherwise, which e.g. restarts all when starting one
            HashMap<String, Animation> clonedAnimations = new HashMap<>();
            for (String animationName : skeletonAnimationControl.getAnimationNames()) {
                Animation animation = skeletonAnimationControl.getAnim(animationName);
                clonedAnimations.put(animationName, animation.clone());
            }
            skeletonAnimationControl.setAnimations(clonedAnimations);
            skeletonAnimationControl.createChannel();
        }
        if (skeletons.size() > 0) {
            boolean isPlayingAnimation = false;
            // Copy the animation of the original model for the others
            if (this != modelObject.getOriginalRegisteredModel()) {
                AnimChannel activeOriginalAnimationChannel = modelObject.getOriginalRegisteredModel().getActiveAnimationChannel();
                if (activeOriginalAnimationChannel.getAnimationName() != null) {
                    setAnimationName(activeOriginalAnimationChannel.getAnimationName());
                    JMonkeyUtil.copyAnimation(activeOriginalAnimationChannel, getActiveAnimationChannel());
                    isPlayingAnimation = true;
                }
            }
            // Default the first skeleton (for controls, attachments etc.) if no animation is immediately played
            if (!isPlayingAnimation) {
                setSkeleton(skeletons.get(0));
            }
        }
        // HardwareSkinning
        boolean isHardwareSkinningPreferred = Settings.getBoolean("hardware_skinning");
        String rigType = modelObject.getSkin().getRigType();
        if (rigType != null) {
            String forceHardwareSkinningValue = Settings.get("hardware_skinning_rig_type_" + rigType);
            if (forceHardwareSkinningValue != null) {
                isHardwareSkinningPreferred = Settings.toBoolean(forceHardwareSkinningValue);
            }
        }
        JMonkeyUtil.setHardwareSkinningPreferred(node, isHardwareSkinningPreferred);
    }

    public void setAnimationName(String animationName) {
        for (ModelSkeleton skeleton : skeletons) {
            AnimControl animationControl = skeleton.getAnimationControl();
            if (animationControl.getAnimationNames().contains(animationName)) {
                if (animationControl != node.getControl(AnimControl.class)) {
                    setSkeleton(skeleton);
                }
                AnimChannel animationChannel = animationControl.getChannel(0);
                try {
                    animationChannel.setAnim(animationName);
                } catch(IllegalArgumentException ex) {
                    stopAndRewindAnimation();
                }
                break;
            }
        }
    }

    private void setSkeleton(ModelSkeleton skeleton) {
        node.removeControl(SkeletonControl.class);
        node.removeControl(AnimControl.class);
        node.addControl(skeleton.getSkeletonControl());
        node.addControl(skeleton.getAnimationControl());
        updateBoneAttachmentNodes();
    }

    public Node requestBoneAttachmentsNode(String boneName) {
        Node node = boneAttachmentsNodes.computeIfAbsent(boneName, bn -> new Node());
        updateBoneAttachmentNodes();
        return node;
    }

    private void updateBoneAttachmentNodes() {
        SkeletonControl skeletonControl = node.getControl(SkeletonControl.class);
        for (Map.Entry<String, Node> entry : boneAttachmentsNodes.entrySet()) {
            String boneName = entry.getKey();
            Node attachmentNode = entry.getValue();
            skeletonControl.getAttachmentsNode(boneName).attachChild(attachmentNode);
        }
    }

    public void setAnimationProperties(float loopDuration, boolean isLoop) {
        AnimChannel animationChannel = getActiveAnimationChannel();
        if (animationChannel.getAnimationName() != null) {
            animationChannel.setSpeed(animationChannel.getAnimMaxTime() / loopDuration);
            animationChannel.setLoopMode(isLoop ? LoopMode.Loop : LoopMode.DontLoop);
        }
    }

    public void stopAndRewindAnimation() {
        AnimChannel animationChannel = getActiveAnimationChannel();
        if (animationChannel != null) {
            animationChannel.reset(true);
        }
    }

    public AnimChannel getActiveAnimationChannel() {
        AnimControl animationControl = node.getControl(AnimControl.class);
        return ((animationControl != null) ? animationControl.getChannel(0) : null);
    }

    public Node getNode() {
        return node;
    }
}
