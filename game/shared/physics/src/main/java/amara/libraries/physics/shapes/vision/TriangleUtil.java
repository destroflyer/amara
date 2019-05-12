/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.vision;

import amara.libraries.physics.shapes.Vector2D;

/**
 *
 * @author Carl
 */
public class TriangleUtil{
    
    //http://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle#2049593
    public static boolean contains(Vector2D trianglePoint1, Vector2D trianglePoint2, Vector2D trianglePoint3, Vector2D point){
        boolean b1 = (sign(point, trianglePoint1, trianglePoint2) < 0);
        boolean b2 = (sign(point, trianglePoint2, trianglePoint3) < 0);
        boolean b3 = (sign(point, trianglePoint3, trianglePoint1) < 0);
        return ((b1 == b2) && (b2 == b3));
    }
    
    public static double sign(Vector2D point, Vector2D linePoint1, Vector2D lintPoint2){
        return (((point.getX() - lintPoint2.getX()) * (linePoint1.getY() - lintPoint2.getY()))
              - ((linePoint1.getX() - lintPoint2.getX()) * (point.getY() - lintPoint2.getY())));
    }
}
