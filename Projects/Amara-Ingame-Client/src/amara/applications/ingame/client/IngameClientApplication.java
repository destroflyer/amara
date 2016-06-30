package amara.applications.ingame.client;

import com.jme3.system.AppSettings;
import amara.applications.ingame.client.appstates.*;
import amara.applications.ingame.client.interfaces.MasterserverClientInterface;
import amara.core.GameInfo;
import amara.core.settings.Settings;
import amara.libraries.applications.display.DisplayApplication;
import amara.libraries.applications.display.appstates.*;
import amara.libraries.applications.display.ingame.appstates.*;

/**
 * @author Carl
 */
public class IngameClientApplication extends DisplayApplication{

    public IngameClientApplication(MasterserverClientInterface masterserverClient, int authentificationKey){
        this.masterserverClient = masterserverClient;
        this.authentificationKey = authentificationKey;
        loadSettings();
    }
    private MasterserverClientInterface masterserverClient;
    private int authentificationKey;
    
    private void loadSettings(){
        settings = new AppSettings(true);
        if(Settings.getBoolean("fullscreen")){
            settings.setWidth(-1);
            settings.setHeight(-1);
            settings.setFullscreen(true);
        }
        else{
            settings.setWidth(Settings.getInteger("resolution_width"));
            settings.setHeight(Settings.getInteger("resolution_height"));
        }
        settings.setTitle(GameInfo.getClientTitle());
        settings.setIcons(GameInfo.ICONS);
        settings.setSamples(Settings.getInteger("antialiasing"));
        settings.setVSync(Settings.getBoolean("vsync"));
        setShowSettings(false);
    }

    @Override
    public void simpleInitApp(){
        super.simpleInitApp();
        stateManager.attach(new NiftyAppState());
        stateManager.attach(new NiftyAppState_IngameClient());
        stateManager.attach(new MouseCursorAppState());
        stateManager.attach(new IngameMouseCursorAppState());
        stateManager.attach(new PingAppState());
        stateManager.attach(new EventManagerAppState());
        stateManager.attach(new AudioAppState());
        stateManager.attach(new LightAppState());
        stateManager.attach(new WaterAppState());
        stateManager.attach(new PostFilterAppState());
        stateManager.attach(new WireframeAppState());
        stateManager.attach(new PreloadAssetsAppState());
        stateManager.attach(new IngameCinematicAppState());
        stateManager.attach(new IngameCameraAppState(true));
        stateManager.attach(new IngameFeedbackAppState());
        stateManager.attach(new LoadingScreenAppState());
        stateManager.attach(new PlayerAuthentificationAppState(authentificationKey));
    }

    public MasterserverClientInterface getMasterserverClient(){
        return masterserverClient;
    }
}
