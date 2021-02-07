package amara.tools.editors.map;

import amara.applications.ingame.shared.maps.MapFileHandler;
import amara.core.Launcher_Core;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.appstates.*;
import amara.tools.editors.map.appstates.*;

public class MapEditorApplication extends DisplayApplication {

    public static void main(String[] args) {
        Launcher_Core.initialize();
        new MapEditorApplication().start();
    }

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        stateManager.attach(new SettingsAppState());
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
    }
}
