package amara.engine.applications.ingame.editor;

import java.io.File;
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
        stateManager.attach(new PostFilterAppState());
        stateManager.attach(new IngameCameraAppState());
        stateManager.attach(new MapAppState(MapFileHandler.loadFile(new File("./assets/Maps/empty.xml"))));
        stateManager.attach(new MapObstaclesAppState());
        stateManager.attach(new MapEditorAppState());
        //Debug Camera
        cam.setLocation(new Vector3f(22, 34, -10));
        cam.lookAtDirection(new Vector3f(0, -1.3f, 1), Vector3f.UNIT_Y);
        
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame){
        
    }
}
