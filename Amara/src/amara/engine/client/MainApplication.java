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
        stateManager.attach(new NiftyAppState());
        stateManager.attach(new LightAppState());
        stateManager.attach(new PostFilterAppState());
        stateManager.attach(new EntitySystemAppState());
        //Debug Camera
        flyCam.setMoveSpeed(12);
        cam.setLocation(new Vector3f(10, 27, -3));
        cam.lookAtDirection(new Vector3f(0.33f, -0.23f, 0.91f), Vector3f.UNIT_Y);
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame){
        
    }
    
    public CollisionResults getRayCastingResults(Node node){
        Vector3f origin = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();
        Ray ray = new Ray(origin, direction);
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
