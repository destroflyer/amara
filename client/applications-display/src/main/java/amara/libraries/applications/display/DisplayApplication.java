package amara.libraries.applications.display;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import amara.core.GameInfo;
import amara.core.files.FileAssets;
import amara.libraries.applications.display.comparators.LayerGeometryComparator_Opaque;

public class DisplayApplication extends SimpleApplication {

    public DisplayApplication() {
        JMonkeyUtil.disableLogger();
        settings = new AppSettings(true);
        settings.setTitle(GameInfo.NAME);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setVSync(true);
        setPauseOnLostFocus(false);
    }

    @Override
    public void simpleInitApp() {
        assetManager.registerLocator(FileAssets.ROOT, FileLocator.class);
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);
        viewPort.getQueue().setGeometryComparator(RenderQueue.Bucket.Opaque, new LayerGeometryComparator_Opaque());
        setDisplayStatView(false);
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

    public void enqueueTask(final Runnable runnable) {
        enqueue(() -> {
            runnable.run();
            return null;
        });
    }
}
