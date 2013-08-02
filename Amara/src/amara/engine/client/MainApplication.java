package amara.engine.client;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import amara.engine.client.appstates.*;

/**
 * @author Carl
 */
public class MainApplication extends SimpleApplication{

    public static void main(String[] args){
        Logger.getLogger("").setLevel(Level.SEVERE);
        MainApplication app = new MainApplication();
        app.start();
    }

    public MainApplication(){
        settings = new AppSettings(true);
        settings.setTitle("Amara");
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setFrameRate(60);
        setPauseOnLostFocus(false);
    }

    @Override
    public void simpleInitApp(){
        MaterialFactory.setAssetManager(assetManager);
        viewPort.getQueue().setGeometryComparator(RenderQueue.Bucket.Opaque, new LayerGeometryComparator());
        stateManager.attach(new EventManagerAppState());
        stateManager.attach(new NiftyAppState());
        stateManager.attach(new LightAppState());
        stateManager.attach(new PostFilterAppState());
        stateManager.attach(new SendPlayerCommandsAppState());
        stateManager.attach(new EntitySystemAppState());
        stateManager.attach(new MapAppState("testmap"));
        stateManager.attach(new IngameCameraAppState());
        //Debug Camera
        cam.setLocation(new Vector3f(30, 52, -18));
        cam.lookAtDirection(new Vector3f(0, -1.3f, 1), Vector3f.UNIT_Y);
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame){
        
    }
    
    public CollisionResults getRayCastingResults_Cursor(Node node){
        Vector3f cursorRayOrigin = getCursorLocation(0);
        Vector3f cursorRayDirection = getCursorLocation(1).subtractLocal(cursorRayOrigin).normalizeLocal();
        return getRayCastingResults(node, new Ray(cursorRayOrigin, cursorRayDirection));
    }
    
    private Vector3f getCursorLocation(float z){
        return cam.getWorldCoordinates(inputManager.getCursorPosition(), z);
    }
    
    public CollisionResults getRayCastingResults_Camera(Node node){
        Vector3f origin = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();
        return getRayCastingResults(node, new Ray(origin, direction));
    }
    
    private  CollisionResults getRayCastingResults(Node node, Ray ray){
        Vector3f origin = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();
        CollisionResults results = new CollisionResults();
        node.collideWith(ray, results);
        return results;
    }
    
    public void enqueueTask(final Runnable runnable){
        enqueue(new Callable<Object>(){

            public Object call(){
                runnable.run();
                return null;
            }
        });
    }
}
