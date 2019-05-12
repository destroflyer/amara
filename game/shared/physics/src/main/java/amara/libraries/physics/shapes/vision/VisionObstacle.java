/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.vision;

import java.util.LinkedList;
import amara.libraries.physics.shapes.ConvexShape;

/**
 *
 * @author Carl
 */
public class VisionObstacle{

    public VisionObstacle(ConvexShape shape, boolean isBlockingInsideOrOutside){
        this.shape = shape;
        this.isBlockingInsideOrOutside = isBlockingInsideOrOutside;
    }
    private ConvexShape shape;
    private boolean isBlockingInsideOrOutside;

    public ConvexShape getShape(){
        return shape;
    }

    public boolean isBlockingInsideOrOutside(){
        return isBlockingInsideOrOutside;
    }
    
    public static LinkedList<VisionObstacle> generateDefaultObstacles(Iterable<ConvexShape> shapse){
        LinkedList<VisionObstacle> visionObstacles = new LinkedList<VisionObstacle>();
        for(ConvexShape shape : shapse){
            visionObstacles.add(new VisionObstacle(shape, true));
        }
        return visionObstacles;
    }
}
