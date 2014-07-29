/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.applications.modelviewer.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import amara.engine.appstates.*;

/**
 *
 * @author Carl
 */
public class InitializeAppState extends BaseDisplayAppState{
    
    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        getAppState(LightAppState.class).addLight(new AmbientLight(){{
            setColor(ColorRGBA.White.mult(0.4f));
        }});
        getAppState(LightAppState.class).addLight(new DirectionalLight(){{
            setColor(ColorRGBA.White.mult(1));
            setDirection(new Vector3f(-1, -1, -1));
        }});
    }
}
