package amara.engine.applications.ingame.editor;

import com.jme3.math.Vector3f;
import amara.core.Launcher_Core;
import amara.engine.applications.DisplayApplication;
import amara.engine.applications.ingame.editor.appstates.*;
import amara.engine.appstates.*;
import amara.game.maps.*;

/**
 * @author Carl
 */
public class MapEditorApplication extends DisplayApplication{
    
    public static void main(String[] args){
        Launcher_Core.initialize();
        MapEditorApplication application = new MapEditorApplication();
        application.start();
    }

    @Override
    public void simpleInitApp(){
        super.simpleInitApp();
        stateManager.attach(new NiftyAppState());
        stateManager.attach(new NiftyAppState_MapEditor());
        stateManager.attach(new AudioAppState());
        stateManager.attach(new LightAppState());
        stateManager.attach(new WaterAppState());
        stateManager.attach(new PostFilterAppState());
        stateManager.attach(new WireframeAppState());
        stateManager.attach(new IngameCameraAppState(false));
        stateManager.attach(new MapAppState(MapFileHandler.load("empty")));
        stateManager.attach(new MapObstaclesAppState());
        stateManager.attach(new MapEditorAppState());
        //Debug Camera
        cam.setLocation(new Vector3f(22, 34, -10));
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame){
        
    }
}
