package amara.engine;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class Main extends SimpleApplication{

    public static void main(String[] args){
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp(){
        Geometry box = new Geometry("Box", new Box(1, 1, 1));
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Blue);
        box.setMaterial(material);
        rootNode.attachChild(box);
    }

    @Override
    public void simpleUpdate(float lastTimePerFrame){
        
    }
}
