/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

import com.jme3.network.serializing.Serializable;
import java.awt.Graphics;

/**
 *
 * @author Philipp
 */
@Serializable
public class SimpleConvex extends Shape {

    public SimpleConvex(){
        
    }
    
    public SimpleConvex(double centerX, double centerY, Vector2D... base) {
        this(base);
        transform.translate(centerX, centerY);
    }
    
    public SimpleConvex(Vector2D... base) {
        this.base = base;
        correctBase();
//        calcBoundRadius();
        points = new Vector2D[base.length];
        for(int i = 0; i < base.length; i++) {
            points[i] = base[i].clone();
        }
    }

    private void correctBase() {
        if(base.length < 2) throw new Error("Convex needs a minimum of 2 points.");
        for(int i = 0; i < base.length; i++) {
            int j = (i + 1) % base.length;
            if(base[i].equals(base[j])) throw new Error("Point must not be connected to itself.");
        }
        if(!isConvex(base)) throw new Error("Outline is not convex.");
        if(isClockwise(base)) {
            revert();
        }
        calcBoundRadius();
    }
    
    private void revert() {
        Util.revertArray(base);
    }
//    @Override
//    public double getUnscaledBoundRadius() {
//        double baseBoundRadius = 0;
//        for(int i = 0; i < base.length; i++) {
//            if(base[i].length() > baseBoundRadius) baseBoundRadius = base[i].length();
//        }
//        return baseBoundRadius;
//    }
    private void calcBoundRadius() {
        for(int i = 0; i < base.length; i++) {
            if(base[i].length() > baseBoundRadius) baseBoundRadius = base[i].length();
        }
    }
    private boolean isConvex(Vector2D[] points) {
        double side = 0;
        int i, j ,k;
        for(i = 0; side == 0 && i < points.length; i++) {
            j = (i + 1) % points.length;
            k = (j + 1) % points.length;
            side = Util.getLineSide(points[i], points[j], points[k]);
        }
        if(side == 0) return true;
        for(i = 0; i < points.length; i++) {
            j = (i + 1) % points.length;
            k = (j + 1) % points.length;
            if(side * Util.getLineSide(points[i], points[j], points[k]) < 0) return false;
        }
        return true;
    }
    private static boolean isClockwise(Vector2D[] points) {
        return getArea(points) < 0;
    }
    private static double getArea(Vector2D[] points) {
        double doubledArea = 0;
        int j;
        for(int i = 0; i < points.length; i++) {
            j = (i + 1) % points.length;
            doubledArea += (points[i].getX() - points[j].getX()) * (points[i].getY() + points[j].getY());
        }
        return doubledArea / 2;
    }

    @Override
    public boolean contains(Vector2D point) {
        updateShape();
        for(int i = 0; i < points.length; i++) {
            int j = (i + 1) % points.length;
            if(!Util.isSideLeft(Util.getLineSide(points[i], points[j], point))) return false;
        }
        return true;
    }

    @Override
    public boolean intersects(Shape s) {
        return s.intersects(this);
    }
    @Override
    public boolean intersects(SimpleConvex c) {
        return !(seperated(getPoints(), c.getPoints()) || seperated(c.getPoints(), getPoints()));
    }

    @Override
    public boolean intersects(Circle c) {
        updateShape();
        Vector2D circleCenter = new Vector2D(c.getX(), c.getY());
        boolean inside = true;
        for(int i = 0; i < points.length; i++) {
            int j = (i + 1) % points.length;
            if(Util.isSideRight(Util.getLineSide(points[i], points[j], circleCenter))) {
                inside = false;
                if(Util.vectorDistancePointLineSegment(points[i], points[j], circleCenter).squareLength() < c.getBoundRadius() * c.getBoundRadius()) return true;
            }
        }
        return inside;
    }

//    private boolean projectionsIntersect(Vector2D a, Vector2D b) {
//        return a.getX() < b.getY() && b.getX() < a.getY();
//    }
//    private double projectionIntersectionFactor(Bounds a, Bounds b) {
//        double x = b.getMax() - a.getMin();
//        double y = b.getMin() - a.getMax();
//        if(Math.abs(x) < Math.abs(y)) return x;
//        return y;
//    }
    
    @Override
    public Vector2D getResolveVector(Circle c) {
        Vector2D overlap = new Vector2D();
        double length = Double.POSITIVE_INFINITY;
        Vector2D circleCenter = new Vector2D(c.getX(), c.getY());
        updateShape();
        
        for(int i = 0; i < points.length; i++) {
            int j = (i + 1) % points.length;
            Vector2D axis = Vector2D.difference(points[j], points[i]);
            axis.toLeftHand();
            axis.toUnit();
//            if(projectionsIntersect(scalarProjectOntoBounds(orth), c.scalarProjectOntoBounds(orth))) {
                double tmp = getPenetration(c, axis);//getScalarProjectOnto(axis).getOverlap(c.getScalarProjectOnto(axis));
//                System.out.println(tmp);
                if(Math.abs(tmp) < length) {
                    axis.scale(tmp);
                    overlap = axis;
                    length = overlap.length();
                }
//            }
            
            
            axis = Vector2D.difference(circleCenter, points[i]);
            axis.toUnit();
//            if(projectionsIntersect(scalarProjectOntoBounds(orth), c.scalarProjectOntoBounds(orth))) {
                tmp = getPenetration(c, axis);//getScalarProjectOnto(axis).getOverlap(c.getScalarProjectOnto(axis));
                if(Math.abs(tmp) < length) {
                    axis.scale(tmp);
                    overlap = axis;
                    length = overlap.length();
                }
//            }
        }
        return overlap;
        
//        boolean inside = true;
//        for(int i = 0; i < points.length; i++) {
//            int j = (i + 1) % points.length;
//            if(Util.isSideRight(Util.getLineSide(points[i], points[j], circleCenter))) inside = false;
//        }
//        if(inside) {
//            for(int i = 0; i < points.length; i++) {
//                int j = (i + 1) % points.length;
//                Vector2D tmp = Util.vectorDistancePointLine(points[i], points[j], circleCenter);
//                double tmpLength = tmp.length();
//                if(tmpLength < length) {
//                    overlap = tmp;
//                    length = tmpLength;
//                }
//            }
//            if(!overlap.isZero()) overlap.addLength(c.getBoundRadius());
//            return overlap;
//        } else {
//            for(int i = 0; i < points.length; i++) {
//                int j = (i + 1) % points.length;
//                Vector2D tmp = Util.vectorDistancePointLineSegment(points[i], points[j], circleCenter);
//                double tmpLength = tmp.length();
//                if(tmpLength < length) {
//                    overlap = tmp;
//                    length = tmpLength;
//                }
//            }
//            overlap.addLength(-c.getBoundRadius());
//            return overlap;
//        }
    }
    @Override
    public Vector2D getResolveVector(Shape s) {
        Vector2D overlap = s.getResolveVector(this);
        overlap.invert();
        return overlap;
    }
    @Override
    public Vector2D getResolveVector(SimpleConvex c) {
        return Util.pusher(c.getPoints(), getPoints());
    }

//    @Override
//    public double getIntersectionTime(Shape s, Vector2D speed, Vector2D refWall) {
//        speed.invert();
//        return s.getIntersectionTime(this, speed, refWall);
//    }
//
//    @Override
//    public double getIntersectionTime(Convex c, Vector2D speed, Vector2D refWall) {
//        return  Util.lineIntersection(getPoints(), c.getPoints(), speed, refWall);
////        return Util.lineIntersection(getSolidCorners(), c.getSolidEdges(), speed, refWall, Util.lineIntersection(c.getSolidCorners(), getSolidEdges(), new Vector2D(-speed.getX(), -speed.getY()), refWall, 0));
//    }
//
//    @Override
//    public double getIntersectionTime(Circle c, Vector2D speed, Vector2D refWall) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
    
//    static public boolean intersect(Convex convex1, Convex convex2) {
//        return circlesIntersect(convex1.getX(), convex1.getY(), convex1.getBoundRadius(), convex2.getX(), convex2.getY(), convex2.getBoundRadius()) && intersect(convex1.getPoints(), convex2.getPoints());
//    }
//    
//    static boolean intersect(Vector2D[] convex1, Vector2D[] convex2) {
//        return !(seperated(convex1, convex2) || seperated(convex2, convex1));
//    }
    static private boolean seperated(Vector2D[] c1, Vector2D[] c2) {
        int b;
        boolean seperated;
        for(int a = 0; a < c1.length; a++) {
            seperated = true;
            b = (a + 1) % c1.length;
            for(int i = 0; seperated && i < c2.length; i++) {
                if(Util.isSideLeft(Util.getLineSide(c1[a], c1[b], c2[i]))) {
                    seperated = false;
                }
            }
            if(seperated) return true;
        }
        return false;
    }
    
    public final double getArea() {
        updateShape();
        return getArea(points);
    }
    
    @Override
    public void updateTransform() {
        for(int i = 0; i < base.length; i++) {
            points[i].set(base[i]);
            transform.apply(points[i]);
        }
    }

    @Override
    public Bounds getScalarProjectOnto(Vector2D vec) {
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for(Vector2D point: getPoints()) {
            double tmp = point.getScalarProjectOnto(vec);
            if(tmp < min) min = tmp;
            if(tmp > max) max = tmp;
        }
        return new Bounds(min, max);
    }
    
    public final Vector2D[] getPoints() {
        updateShape();
        return points;
    }
//    
//    public ArrayList<Edge> getSolidEdges() {
//        updateShape();
//        ArrayList<Edge> edges = new ArrayList<>();
//        for(int i = 0; i < base.length; i++) {
//            edges.add(new Edge(points[i], points[(i + 1) % base.length]));
//        }
//        return edges;
//    }
//    public ArrayList<Vector2D> getSolidCorners() {
//        updateShape();
//        ArrayList<Vector2D> corners = new ArrayList<>();
//        for(int i = 0; i < base.length; i++) {
//            int j = (i + 1) % base.length;
//            corners.add(points[j]);
//        }
//        return corners;
//    }

    protected final int[][] getAsIntArray() {
        updateShape();
        int[][] iPoints = new int[2][points.length];
        for(int i = 0; i < points.length; i++) {
            iPoints[0][i] = (int)Math.round(points[i].getX());
            iPoints[1][i] = (int)Math.round(points[i].getY());
        }
        return iPoints;
    }
    
    @Override
    public void draw(Graphics graphics) {
        int[][] arr = getAsIntArray();
        graphics.drawPolygon(arr[0], arr[1], arr[0].length);
    }

    @Override
    public void fill(Graphics graphics) {
        int[][] arr = getAsIntArray();
        graphics.fillPolygon(arr[0], arr[1], arr[0].length);
    }
    
    @Override
    public SimpleConvex clone() {
        SimpleConvex clone = new SimpleConvex(base);
        clone.transform = transform.clone();
        return clone;
    }
    
    protected Vector2D[] base, points;
}
