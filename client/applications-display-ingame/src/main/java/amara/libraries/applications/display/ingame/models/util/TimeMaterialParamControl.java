package amara.libraries.applications.display.ingame.models.util;

import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;
import com.jme3.shader.VarType;

public class TimeMaterialParamControl extends AbstractControl {

    public TimeMaterialParamControl(String parameterName) {
        this.parameterName = parameterName;
    }
    private String parameterName;
    private float passedTime;

    @Override
    protected void controlUpdate(float lastTimePerFrame) {
        passedTime += lastTimePerFrame;
        Geometry geometry = (Geometry) spatial;
        Material material = geometry.getMaterial();
        material.setParam(parameterName, VarType.Float, passedTime);
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
