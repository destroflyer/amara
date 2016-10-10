/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.vision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import amara.libraries.physics.shapes.*;

/**
 *
 * @author Carl
 */
public class MergedVision{

    public MergedVision(List<ConvexShape> obstacles){
        this(new Vector2D[0], obstacles);
    }

    public MergedVision(Vector2D[] borderPoints, List<ConvexShape> obstacles){
        this.borderPoints = borderPoints;
        this.obstacles = obstacles;
    }
    private static final int VISION_CIRCLE_EDGES = 10;
    private static final Transform2D circleNextPointTransform = new Transform2D(1, ((Math.PI * -2) / VISION_CIRCLE_EDGES), 0, 0);
    private Vector2D[] borderPoints;
    private List<ConvexShape> obstacles;
    private Vision vision = new Vision();
    private ArrayList<Vector2D> visionEdges = new ArrayList<Vector2D>();
    private HashMap<Double, Circle> visionCircleShapes = new HashMap<Double, Circle>();
    private HashMap<Integer, SightResult> sightResults = new HashMap<Integer, SightResult>();
    
    public boolean isVisible(Vector2D position){
        for(SightResult sightResult : sightResults.values()){
            if(sightResult.contains(position)){
                return true;
            }
        }
        return false;
    }
    
    public void setVision(int id, Vector2D position, double sightRange){
        ArrayList<Vector2D> sightOutline = getSightOutline(position, sightRange);
        sightResults.put(id, new SightResult(position, sightOutline));
    }
    
    public SightResult getVision(int id){
        return sightResults.get(id);
    }
    
    public void removeVision(int id){
        sightResults.remove(id);
    }
    
    public ArrayList<Vector2D> getSightOutline(Vector2D position, double sightRange){
        visionEdges.clear();
        addPointEdges(visionEdges, borderPoints, false);
        addCircleEdges(visionEdges, position, sightRange);
        Circle visionCircleShape = getVisionCircleShape(sightRange);
        visionCircleShape.setTransform(new Transform2D(1, 0, position.getX(), position.getY()));
        for(ConvexShape obstacle : obstacles){
            if(obstacle.intersects(visionCircleShape)){
                if(obstacle instanceof SimpleConvexPolygon){
                    SimpleConvexPolygon simpleConvexPolygon = (SimpleConvexPolygon) obstacle;
                    addPointEdges(visionEdges, simpleConvexPolygon.getGlobalPoints(), true);
                }
                else if(obstacle instanceof Circle){
                    Circle circle = (Circle) obstacle;
                    addCircleEdges(visionEdges, circle.getGlobalPosition(), circle.getGlobalRadius());
                }
            }
        }
        return vision.sightPolyOutline(position, visionEdges);
    }
    
    private static void addPointEdges(List<Vector2D> edges, Vector2D[] points, boolean reversed){
        if(points.length > 0){
            if(reversed){
                Vector2D lastPoint = points[0];
                for(int i=(points.length - 1);i>=0;i--){
                    edges.add(lastPoint);
                    edges.add(points[i]);
                    lastPoint = points[i];
                }
            }
            else{
                Vector2D lastPoint = points[points.length - 1];
                for(Vector2D point : points){
                    edges.add(lastPoint);
                    edges.add(point);
                    lastPoint = point;
                }
            }
        }
    }
    
    private static void addCircleEdges(List<Vector2D> edges, Vector2D position, double radius){
        Vector2D relativePosition = new Vector2D(0, radius);
        Vector2D firstPoint = null;
        for(int i=0;i<VISION_CIRCLE_EDGES;i++){
            relativePosition = circleNextPointTransform.transform(relativePosition);
            Vector2D point = position.add(relativePosition);
            if(firstPoint == null){
                firstPoint = point;
            }
            else{
                edges.add(point);
            }
            edges.add(point);
        }
        edges.add(firstPoint);
    }
    
    private Circle getVisionCircleShape(double radius){
        Circle circle = visionCircleShapes.get(radius);
        if(circle == null){
            circle = new Circle(radius);
            visionCircleShapes.put(radius, circle);
        }
        return circle;
    }
}
