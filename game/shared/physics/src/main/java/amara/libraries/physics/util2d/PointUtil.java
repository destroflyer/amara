/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.util2d;

import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;

/**
 *
 * @author Carl
 */
public class PointUtil{
    
    public static Vector2f[] getCirclePoints(float radius, int count){
        Vector2f[] points = new Vector2f[count];
        for(int i=0;i<count;i++){
            float progress = (i * (FastMath.TWO_PI / count));
            float x = (FastMath.sin(progress) * radius);
            float y = (FastMath.cos(progress) * radius);
            points[i] = new Vector2f(x, y);
        }
        return points;
    }
}
