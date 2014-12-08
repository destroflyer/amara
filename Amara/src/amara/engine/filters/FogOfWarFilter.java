package amara.engine.filters;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.post.Filter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Texture;

/**
 *
 * @author Carl
 */
public class FogOfWarFilter extends Filter{

    public FogOfWarFilter(){
        super("FogOfWar");
    }
    private Camera camera;
                
    @Override
    protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort viewPort, int width, int height){
        camera = viewPort.getCamera();
        material = new Material(manager, "Shaders/filters/fog_of_war/matdefs/fog_of_war.j3md");
    }
    
    public void setMapSize(float width, float height){
        material.setVector2("MapSize", new Vector2f(width, height));
    }
    
    public void setFog(Texture texture){
        material.setTexture("Fog", texture);
    }

    @Override
    protected void preFrame(float lastTimePerFrame){
        super.preFrame(lastTimePerFrame);
        material.setMatrix4("ViewProjectionMatrixInverse", camera.getViewProjectionMatrix().invert());
    }

    @Override
    protected Material getMaterial(){
        return material;
    }

    @Override
    protected boolean isRequiresDepthTexture(){
        return true;
    }
}
