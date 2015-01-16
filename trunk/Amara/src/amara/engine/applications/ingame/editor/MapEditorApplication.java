package amara.engine.applications.ingame.editor;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.math.Vector3f;
import amara.engine.applications.DisplayApplication;
import amara.engine.applications.ingame.editor.appstates.*;
import amara.engine.appstates.*;
import amara.game.maps.*;

/**
 * @author Carl
 */
public class MapEditorApplication extends DisplayApplication{
    
    public static void main(String[] args){
        Logger.getLogger("").setLevel(Level.SEVERE);
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
        stateManager.attach(new IngameCameraAppState());
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
