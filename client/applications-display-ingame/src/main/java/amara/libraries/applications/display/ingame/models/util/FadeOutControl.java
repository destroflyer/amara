package amara.libraries.applications.display.ingame.models.util;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;

public class FadeOutControl extends AbstractControl {

    public FadeOutControl(float duration) {
        this.duration = duration;
    }
    private float duration;
    private float alpha = 1;

    @Override
    protected void controlUpdate(float tpf) {
        alpha -= (tpf / duration);
        Geometry geometry = (Geometry) getSpatial();
        Material material = geometry.getMaterial();
        if (alpha > 0) {
            material.setColor("Color", new ColorRGBA(1, 1, 1, alpha));
        } else {
            geometry.getParent().detachChild(geometry);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
