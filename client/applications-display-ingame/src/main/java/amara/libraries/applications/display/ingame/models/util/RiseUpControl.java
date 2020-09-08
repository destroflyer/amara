package amara.libraries.applications.display.ingame.models.util;

import amara.libraries.applications.display.JMonkeyUtil;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class RiseUpControl extends AbstractControl {

    public RiseUpControl(float duration) {
        this.duration = duration;
    }
    private float duration;
    private float height;
    private float passedTime;

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        if (spatial != null) {
            height = JMonkeyUtil.getSpatialDimension(spatial).getY();
        }
    }

    @Override
    protected void controlUpdate(float lastTimePerFrame) {
        passedTime += lastTimePerFrame;
        if (passedTime < duration) {
            getSpatial().setLocalTranslation(0, ((1 - (passedTime / duration)) * (-1 * height)), 0);
        } else {
            getSpatial().setLocalTranslation(0, 0, 0);
            getSpatial().removeControl(this);
        }
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
