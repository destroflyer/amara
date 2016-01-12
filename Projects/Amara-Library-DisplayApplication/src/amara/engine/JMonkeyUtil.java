/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.engine;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.SkeletonControl;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;


/**
 *
 * @author Carl
 */
public class JMonkeyUtil{
    
    public static void disableLogger(){
        Logger.getLogger("").setLevel(Level.SEVERE);
        Logger.getLogger(SkeletonControl.class.getName()).setLevel(Level.SEVERE);
    }
    
    public static Vector3f getSpatialDimension(Spatial spatial){
        if(spatial.getWorldBound() instanceof BoundingBox){
            BoundingBox boundingBox = (BoundingBox) spatial.getWorldBound();
            return new Vector3f(2 * boundingBox.getXExtent(), 2 * boundingBox.getYExtent(), 2 * boundingBox.getZExtent());
        }
        return new Vector3f(0, 0, 0);
    }
    
    public static Spatial getChild(Spatial spatial, int... index){
        for(int i=0;i<index.length;i++){
            if(spatial instanceof Node){
                Node node = (Node) spatial;
                spatial = node.getChild(index[i]);
            }
            else{
                break;
            }
        }
        return spatial;
    }
    
    public static LinkedList<Geometry> getAllGeometryChilds(Spatial spatial){
        LinkedList<Geometry> geometryChilds = new LinkedList<Geometry>();
        if(spatial instanceof Node){
            Node node = (Node) spatial;
            for(int i=0;i<node.getChildren().size();i++){
                Spatial child = node.getChild(i);
                if(child instanceof Geometry){
                    Geometry geometry = (Geometry) child;
                    geometryChilds.add(geometry);
                }
                else{
                    geometryChilds.addAll(getAllGeometryChilds(child));
                }
            }
        }
        return geometryChilds;
    }
    
    public static void copyAnimation(AnimChannel sourceAnimationChannel, AnimChannel targetAnimationChannel){
        if(sourceAnimationChannel.getAnimationName() != null){
            targetAnimationChannel.setAnim(sourceAnimationChannel.getAnimationName());
            targetAnimationChannel.setSpeed(sourceAnimationChannel.getSpeed());
            targetAnimationChannel.setTime(sourceAnimationChannel.getTime());
            targetAnimationChannel.setLoopMode(sourceAnimationChannel.getLoopMode());
        }
    }
    
    public static void setLocalRotation(Spatial spatial, Vector3f rotation){
        Vector3f lookAtLocation = spatial.getWorldTranslation().add(rotation);
        spatial.lookAt(lookAtLocation, Vector3f.UNIT_Y);
    }
    
    public static Quaternion getQuaternion_X(float degrees){
        return getQuaternion(degrees, Vector3f.UNIT_X);
    }
    
    public static Quaternion getQuaternion_Y(float degrees){
        return getQuaternion(degrees, Vector3f.UNIT_Y);
    }
    
    public static Quaternion getQuaternion_Z(float degrees){
        return getQuaternion(degrees, Vector3f.UNIT_Z);
    }
    
    public static Quaternion getQuaternion(float degrees, Vector3f axis){
        return new Quaternion().fromAngleAxis(((degrees / 360) * (2 * FastMath.PI)), axis);
    }
}
