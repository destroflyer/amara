package amara.libraries.applications.display.models;

import com.jme3.animation.AnimControl;
import com.jme3.animation.SkeletonControl;

public class ModelSkeleton {

    public ModelSkeleton(SkeletonControl skeletonControl, AnimControl animationControl) {
        this.skeletonControl = skeletonControl;
        this.animationControl = animationControl;
    }
    private SkeletonControl skeletonControl;
    private AnimControl animationControl;

    public ModelSkeleton clone() {
        SkeletonControl clonedSkeletonControl = (SkeletonControl) skeletonControl.jmeClone();
        AnimControl clonedAnimationControl = (AnimControl) animationControl.jmeClone();
        return new ModelSkeleton(clonedSkeletonControl, clonedAnimationControl);
    }

    public SkeletonControl getSkeletonControl() {
        return skeletonControl;
    }

    public AnimControl getAnimationControl() {
        return animationControl;
    }
}
