package amara.tools.modelviewer;

import com.jme3.math.Vector3f;
import amara.core.Launcher_Core;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.appstates.*;
import amara.tools.modelviewer.appstates.*;

public class ModelViewerApplication extends DisplayApplication {
    
    public static void main(String[] args) {
        Launcher_Core.initialize();
        new ModelViewerApplication().start();
    }

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        setDisplayStatView(false);
        stateManager.attach(new LightAppState());
        stateManager.attach(new PostFilterAppState());
        stateManager.attach(new WireframeAppState());
        stateManager.attach(new ModelAppState());
        stateManager.attach(new InitializeAppState());
        cam.setLocation(new Vector3f(0, 3, 10));
        flyCam.setEnabled(false);
    }
}
