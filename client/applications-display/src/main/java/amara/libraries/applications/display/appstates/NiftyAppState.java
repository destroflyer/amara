package amara.libraries.applications.display.appstates;

import java.util.ArrayList;

import amara.libraries.applications.display.DisplayApplication;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import amara.libraries.applications.display.gui.GameScreenController;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.render.batch.BatchRenderConfiguration;
import de.lessvoid.nifty.screen.ScreenController;

public class NiftyAppState extends BaseDisplayAppState<DisplayApplication> {

    private ViewPort viewPort;
    private ArrayList<Nifty> runningNifties = new ArrayList<>();

    @Override
    public void initialize(AppStateManager stateManager, Application application) {
        super.initialize(stateManager, application);
        viewPort = mainApplication.getRenderManager().createPostView("niftygui", mainApplication.getGuiViewPort().getCamera().clone());
        viewPort.setClearFlags(false, false, false);
    }

    public void createNifty(String filePath) {
        createNifty(filePath, false);
    }

    public void createNifty(String filePath, boolean useBatchedRenderer) {
        NiftyJmeDisplay niftyDisplay;
        if (useBatchedRenderer) {
            // There are multiple parametrizations available here, but the default values fit our needs (and useHighQualityTextures doesn't seem to be implemented yet)
            BatchRenderConfiguration batchRenderConfiguration = new BatchRenderConfiguration();
            niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(mainApplication.getAssetManager(), mainApplication.getInputManager(), mainApplication.getAudioRenderer(), mainApplication.getGuiViewPort(), batchRenderConfiguration);
        } else {
            niftyDisplay = new NiftyJmeDisplay(mainApplication.getAssetManager(), mainApplication.getInputManager(), mainApplication.getAudioRenderer(), mainApplication.getGuiViewPort());
        }
        viewPort.addProcessor(niftyDisplay);
        Nifty nifty = niftyDisplay.getNifty();
        nifty.addXml(filePath);
        goToScreen(nifty, "start");
        runningNifties.add(nifty);
    }

    public <T extends ScreenController> void goToScreen(Class<T> screenControllerClass, String screenID) {
        for (int i=0;i<runningNifties.size();i++) {
            Nifty nifty = runningNifties.get(i);
            if (screenControllerClass.isInstance(nifty.getCurrentScreen().getScreenController())) {
                goToScreen(nifty, screenID);
            }
        }
    }

    private void goToScreen(Nifty nifty, String screenID) {
        nifty.gotoScreen(screenID);
        ScreenController screenController = nifty.getScreen(screenID).getScreenController();
        if (screenController instanceof GameScreenController) {
            GameScreenController gameScreenController = (GameScreenController) screenController;
            gameScreenController.setMainApplication(mainApplication);
            gameScreenController.setNifty(nifty);
            gameScreenController.onStartup();
        }
    }

    public <T extends ScreenController> T getScreenController(Class<T> screenControllerClass) {
        for (Nifty nifty : runningNifties) {
            for (String screenName : nifty.getAllScreensName()) {
                ScreenController screenController = nifty.getScreen(screenName).getScreenController();
                if (screenControllerClass.isInstance(screenController)) {
                    return (T) screenController;
                }
            }
        }
        return null;
    }

    @Override
    public void cleanup() {
        super.cleanup();
        // Unsubscribe leftover element styleListeners to prevent memory leak (https://github.com/nifty-gui/nifty-gui/issues/461)
        for (Nifty nifty : runningNifties) {
            nifty.getEventService().clearAllSubscribers();
        }
    }
}
