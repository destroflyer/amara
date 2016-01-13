package amara.engine.applications.modelviewer;

import com.jme3.math.Vector3f;
import amara.Launcher_Core;
import amara.engine.appstates.*;
import amara.engine.applications.DisplayApplication;
import amara.engine.applications.modelviewer.appstates.*;
import amara.engine.materials.MaterialFactory;

/**
 * @author Carl
 */
public class ModelViewerApplication extends DisplayApplication{
    
    public static void main(String[] args){
        Launcher_Core.initialize();
        new ModelViewerApplication().start();
    }

    public ModelViewerApplication(){
        setShowSettings(false);
    }

    @Override
    public void simpleInitApp(){
        super.simpleInitApp();
        MaterialFactory.setAssetManager(assetManager);
        setDisplayStatView(false);
        stateManager.attach(new LightAppState());
        stateManager.attach(new PostFilterAppState());
        stateManager.attach(new WireframeAppState());
        stateManager.attach(new ModelAppState());
        stateManager.attach(new InitializeAppState());
        cam.setLocation(new Vector3f(0, 3, 10));
        flyCam.setEnabled(false);
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame){
        
    }
}
