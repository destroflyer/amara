package amara.engine.applications.ingame.client;

import com.jme3.system.AppSettings;
import amara.GameInfo;
import amara.engine.applications.DisplayApplication;
import amara.engine.applications.ingame.client.appstates.*;
import amara.engine.applications.ingame.client.network.backends.*;
import amara.engine.appstates.*;
import amara.engine.network.*;
import amara.engine.network.exceptions.*;
import amara.engine.settings.Settings;

/**
 * @author Carl
 */
public class IngameClientApplication extends DisplayApplication{

    public IngameClientApplication(HostInformation hostInformation, int authentificationKey){
        this.hostInformation = hostInformation;
        this.authentificationKey = authentificationKey;
        loadSettings();
    }
    private HostInformation hostInformation;
    private int authentificationKey;
    
    private void loadSettings(){
        settings = new AppSettings(true);
        if(Settings.getBoolean("fullscreen")){
            settings.setWidth(-1);
            settings.setHeight(-1);
            settings.setFullscreen(true);
        }
        else{
            settings.setWidth(Settings.getInt("resolution_width"));
            settings.setHeight(Settings.getInt("resolution_height"));
        }
        settings.setTitle(GameInfo.getClientTitle());
        settings.setIcons(GameInfo.ICONS);
        settings.setFrameRate(Settings.getInt("frame_rate"));
        settings.setSamples(Settings.getInt("antialiasing"));
        settings.setVSync(Settings.getBoolean("vsync"));
        setShowSettings(false);
        setPauseOnLostFocus(false);
        setDisplayStatView(false);
    }

    @Override
    public void simpleInitApp(){
        super.simpleInitApp();
        try{
            stateManager.attach(new NetworkClientAppState(hostInformation.getHost(), hostInformation.getPort()));
            stateManager.getState(NetworkClientAppState.class).getNetworkClient().addMessageBackend(new GameInfoBackend(this));
            stateManager.attach(new PlayerAuthentificationAppState(authentificationKey));
            stateManager.attach(new NiftyAppState());
            stateManager.attach(new NiftyAppState_IngameClient());
            stateManager.attach(new PingAppState());
            stateManager.attach(new EventManagerAppState());
            stateManager.attach(new AudioAppState());
            stateManager.attach(new LightAppState());
            stateManager.attach(new WaterAppState());
            stateManager.attach(new PostFilterAppState());
            stateManager.attach(new WireframeAppState());
            stateManager.attach(new CinematicAppState());
            stateManager.attach(new IngameCameraAppState());
            stateManager.attach(new IngameFeedbackAppState());
            stateManager.attach(new LoadingScreenAppState());
        }catch(ServerConnectionException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }catch(ServerConnectionTimeoutException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame){
        
    }
}
