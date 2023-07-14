package amara.libraries.applications.display;

import amara.core.GameInfo;
import amara.core.files.FileAssets;
import amara.core.settings.Settings;
import amara.libraries.applications.display.comparators.LayerGeometryComparator_Opaque;
import com.destroflyer.jme3.effekseer.nativ.Effekseer;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

public class DisplayApplication extends SimpleApplication {

    public DisplayApplication() {
        JMonkeyUtil.disableLogger();
        loadSettings();
    }

    private void loadSettings() {
        settings = new AppSettings(true);
        settings.setTitle(GameInfo.getClientTitle());
        settings.setIcons(GameInfo.ICONS);
        settings.setFullscreen(Settings.getBoolean("fullscreen"));
        settings.setWidth(Settings.getInteger("resolution_width"));
        settings.setHeight(Settings.getInteger("resolution_height"));
        settings.setSamples(Settings.getInteger("antialiasing"));
        settings.setVSync(Settings.getBoolean("vsync"));
        settings.setGammaCorrection(false);
        setShowSettings(false);
    }

    @Override
    public void simpleInitApp() {
        assetManager.registerLocator(FileAssets.ROOT, FileLocator.class);
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);
        viewPort.getQueue().setGeometryComparator(RenderQueue.Bucket.Opaque, new LayerGeometryComparator_Opaque());
        Effekseer.initialize(stateManager, viewPort, assetManager, context.getSettings().isGammaCorrection(), false, false);
        setDisplayStatView(false);
        setPauseOnLostFocus(false);
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame) {

    }

    public CollisionResults getRayCastingResults_Cursor(Spatial spatial) {
        return getRayCastingResults_Screen(spatial, inputManager.getCursorPosition());
    }

    public CollisionResults getRayCastingResults_Screen(Spatial spatial, Vector2f screenLocation) {
        Vector3f cursorRayOrigin = cam.getWorldCoordinates(screenLocation, 0);
        Vector3f cursorRayDirection = cam.getWorldCoordinates(screenLocation, 1).subtractLocal(cursorRayOrigin).normalizeLocal();
        return getRayCastingResults(spatial, new Ray(cursorRayOrigin, cursorRayDirection));
    }

    public CollisionResults getRayCastingResults_ScreenCenter(Spatial spatial) {
        Vector3f origin = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2f), (settings.getHeight() / 2f)), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2f), (settings.getHeight() / 2f)), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();
        return getRayCastingResults(spatial, new Ray(origin, direction));
    }

    private CollisionResults getRayCastingResults(Spatial spatial, Ray ray) {
        CollisionResults results = new CollisionResults();
        spatial.collideWith(ray, results);
        return results;
    }
}
