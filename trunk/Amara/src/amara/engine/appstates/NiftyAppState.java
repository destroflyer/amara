/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine.appstates;


import java.util.ArrayList;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.ScreenController;
import amara.engine.gui.GameScreenController;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author Carl
 */
public class NiftyAppState extends BaseDisplayAppState{

    public NiftyAppState(){
        
    }
    private ViewPort viewPort;
    private ArrayList<Nifty> runningNifties = new ArrayList<Nifty>();

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        viewPort = mainApplication.getRenderManager().createPostView("niftygui", mainApplication.getGuiViewPort().getCamera().clone());
        viewPort.setClearFlags(false, false, false);
    }
        
    public Nifty createNifty(String filePath){
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(mainApplication.getAssetManager(), mainApplication.getInputManager(), mainApplication.getAudioRenderer(), mainApplication.getGuiViewPort());
        viewPort.addProcessor(niftyDisplay);
        Nifty nifty = niftyDisplay.getNifty();
        nifty.addXml(filePath);
        goToScreen(nifty, "start");
        runningNifties.add(nifty);
        return nifty;
    }
    
    public boolean isReadyForUpdate(){
        for(int i=0;i<runningNifties.size();i++){
            Nifty nifty = runningNifties.get(i);
            if(!nifty.getCurrentScreen().isRunning()){
                return false;
            }
        }
        return true;
    }

    public <T extends ScreenController> void goToScreen(Class<T> screenControllerClass, String screenID){
        for(int i=0;i<runningNifties.size();i++){
            Nifty nifty = runningNifties.get(i);
            if(screenControllerClass.isInstance(nifty.getCurrentScreen().getScreenController())){
                goToScreen(nifty, screenID);
                break;
            }
        }
    }

    private void goToScreen(Nifty nifty, String screenID){
        nifty.gotoScreen(screenID);
        ScreenController screenController = nifty.getScreen(screenID).getScreenController();
        if(screenController instanceof GameScreenController){
            GameScreenController gameScreenController = (GameScreenController) screenController;
            gameScreenController.setMainApplication(mainApplication);
            gameScreenController.setNifty(nifty);
            gameScreenController.onStartup();
        }
    }
    
    public <T extends ScreenController> T getScreenController(Class<T> screenControllerClass){
        for(Nifty nifty : runningNifties){
            for(String screenName : nifty.getAllScreensName()){
                ScreenController screenController = nifty.getScreen(screenName).getScreenController();
                if(screenControllerClass.isInstance(screenController)){
                    return (T) screenController;
                }
            }
        }
        return null;
    }
}
