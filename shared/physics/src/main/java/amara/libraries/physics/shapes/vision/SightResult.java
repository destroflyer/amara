/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.vision;

import java.util.List;
import amara.libraries.physics.shapes.Vector2D;

/**
 *
 * @author Carl
 */
public class SightResult{

    public SightResult(Vector2D center, List<Vector2D> outline){
        this.center = center;
        this.outline = outline;
    }
    private Vector2D center;
    private List<Vector2D> outline;
    
    public boolean contains(Vector2D position){
        Vector2D lastPoint = outline.get(outline.size() - 1);
        for(Vector2D point : outline){
            if(TriangleUtil.contains(lastPoint, point, center, position)){
                return true;
            }
            lastPoint = point;
        }
        return false;
    }

    public Vector2D getCenter(){
        return center;
    }

    public List<Vector2D> getOutline(){
        return outline;
    }
}
