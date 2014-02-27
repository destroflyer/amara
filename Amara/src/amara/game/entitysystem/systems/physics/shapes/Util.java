/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes;

/**
 *
 * @author Philipp
 */
class Util {
    public static <T> void revertArray(T[] array) {
        for(int i = 0; i < (array.length / 2); i++) {
            int reversedIndex = (array.length - 1 - i);
            T tempElement = array[i];
            array[i] = array[reversedIndex];
            array[reversedIndex] = tempElement;
        }
    }
    public static void revertArray(boolean[] array) {
        for(int i = 0; i < (array.length / 2); i++) {
            int reversedIndex = (array.length - 1 - i);
            boolean tempElement = array[i];
            array[i] = array[reversedIndex];
            array[reversedIndex] = tempElement;
        }
    }
    
    public static double getLineSide(double ax, double ay, double bx, double by, double px, double py) {
        return (by - ay) * (px - ax) - (bx - ax) * (py - ay);
    }
    // left < 0 < right
    public static double getLineSide(Vector2D a, Vector2D b, Vector2D v) {
        return getLineSide(a.getX(), a.getY(), b. getX(), b.getY(), v.getX(), v.getY());
    }
    public static boolean isSideRight(double side) {
        return 0 < side;
    }
    public static boolean isSideLeft(double side) {
        return side < 0;
    }
    public static boolean isSideOnLine(double side) {
        return 0 == side;
    }
    
    public static Vector2D pusher(Vector2D[] convex1, Vector2D[] convex2) {
        Vector2D v1 = minPenetration(convex1, convex2);
        Vector2D v2 = minPenetration(convex2, convex1);
        if(v2.length() < v1.length()) return v2;
        v1.invert();
        return v1;
    }
    private static Vector2D minPenetration(Vector2D[] c1, Vector2D[] c2) {
        int b;
        Vector2D tmp;
        Vector2D outer = new Vector2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        Vector2D inner = new Vector2D();
        for(int a = 0; a < c1.length; a++) {
            b = (a + 1) % c1.length;
            inner.set(0, 0);
            for(int i = 0; i < c2.length; i++) {
                if(Util.isSideLeft(Util.getLineSide(c1[a], c1[b], c2[i]))) {
                    tmp = Util.vectorDistancePointLine(c1[a], c1[b], c2[i]);
                    if(inner.length() < tmp.length()) inner.set(tmp);
                }
            }
            if(outer.length() > inner.length()) outer.set(inner);
        }
        return outer;
    }
    
//    public static double distancePointLine(Vector2D a, Vector2D b, Vector2D p) {
//        return distancePointLine(a.getX(), a.getY(), b.getX(), b.getY(), p.getX(), p.getY());
//    }
//    public static double distancePointLine(double ax, double ay, double bx, double by, double px, double py) {
//        bx -= ax;
//        by -= ay;
//        px -= ax;
//        py -= ay;
//        ay = (px * bx + py * by) / (bx * bx + by * by);
//        ax = ay * bx - px;
//        ay = ay * by - py;
//        return Math.sqrt(ax * ax + ay * ay);
//    }
    public static double signedDistancePointLine(double ax, double ay, double bx, double by, double px, double py) {
        bx -= ax;
        by -= ay;
        px -= ax;
        py -= ay;
        ay = (px * bx + py * by) / (bx * bx + by * by);
        ax = ay * bx - px;
        ay = ay * by - py;
        return Math.sqrt(ax * ax + ay * ay);
    }
    public static Vector2D vectorDistancePointLine(double ax, double ay, double bx, double by, double px, double py) {
        bx -= ax;
        by -= ay;
        px -= ax;
        py -= ay;
        ay = (px * bx + py * by) / (bx * bx + by * by);
        ax = ay * bx - px;
        ay = ay * by - py;
        return new Vector2D(-ax, -ay);
    }
    public static Vector2D vectorDistancePointLine(Vector2D a, Vector2D b, Vector2D p) {
        return vectorDistancePointLine(a.getX(), a.getY(), b.getX(), b.getY(), p.getX(), p.getY());
    }
    public static Vector2D vectorDistancePointLineSegment(Vector2D a, Vector2D b, Vector2D p) {
        return vectorDistancePointLineSegment(a.getX(), a.getY(), b.getX(), b.getY(), p.getX(), p.getY());
    }
    public static Vector2D vectorDistancePointLineSegment(double ax, double ay, double bx, double by, double px, double py) {
        bx -= ax;
        by -= ay;
        px -= ax;
        py -= ay;
        ay = (px * bx + py * by);
        if(ay <= 0) return new Vector2D(px, py);
        ax = (bx * bx + by * by);
        if(ax <= ay) return new Vector2D(px - bx, py - by);
        ay /= ax;
        ax = -(ay * bx - px);
        ay = -(ay * by - py);
        return new Vector2D(ax, ay);
    }
//    public static Vector2D closestPointOnLineToPoint(double ax, double ay, double bx, double by, double px, double py) {
//        bx -= ax;
//        by -= ay;
//        px -= ax;
//        py -= ay;
//        px = (px * bx + py * by) / (bx * bx + by * by);
//        ax += px * bx;
//        ay += px * by;
//        return new Vector2D(ax, ay);
//    }
    
//    private static long[] calcSingle()
//    {
//        long[] calc = new long[64];
//        calc[0] = 1l;
//        for (int i = 1; i < 64; i++)
//        {
//            calc[i] = calc[i - 1] << 1l;
//        }
//        return calc;
//    }
    
    // a + s * av = b + t * bv
    //returns s
    public static double lineIntersection(double ax, double ay, double avx, double avy, double bx, double by, double bvx, double bvy) {
        return (bvx * (ay - by) - bvy * (ax - bx)) / (bvy * avx - bvx * avy);
    }
    public static double lineIntersection(Vector2D a, Vector2D b, Vector2D e, Vector2D f) {
        return lineIntersection(a.getX(), a.getY(), b.getX(), b.getY(), e.getX(), e.getY(), f.getX(), f.getY());
//        return (f.getX() * (a.getY() - e.getY()) - f.getY() * (a.getX() - e.getX())) / (f.getY() * b.getX() - f.getX() * b.getY());
    }
    public static double lineIntersection(Vector2D[] a, Vector2D[] b, Vector2D speed, Vector2D refWallDirection) {
        double s = 0;
        for(int i = 0; i < a.length; i++) {
            int j = (i + 1) % a.length;
            for(int k = 0; k < b.length; k++) {
                if(isSideLeft(getLineSide(b[k].getX(), b[k].getY(), b[k].getX() - speed.getX(), b[k].getY() - speed.getY(), a[i].getX(), a[i].getY()))) {
                    if(isSideRight(getLineSide(b[k].getX(), b[k].getY(), b[k].getX() - speed.getX(), b[k].getY() - speed.getY(), a[j].getX(), a[j].getY()))) {
                        double tmp = lineIntersection(b[k].getX(), b[k].getY(), -speed.getX(), -speed.getY(), a[i].getX(), a[i].getY(), a[j].getX() - a[i].getX(), a[j].getY() - a[i].getY());
                        if(tmp < s) {
                            s = tmp;
                            refWallDirection.set(a[j].getX() - a[i].getX(), a[j].getY() - a[i].getY());
                        }
                    }
                }
            }
        }
        for(int i = 0; i < b.length; i++) {
            int j = (i + 1) % b.length;
            for(int k = 0; k < a.length; k++) {
                if(isSideLeft(getLineSide(a[k].getX(), a[k].getY(), a[k].getX() + speed.getX(), a[k].getY() + speed.getY(), b[i].getX(), b[i].getY()))) {
                    if(isSideRight(getLineSide(a[k].getX(), a[k].getY(), a[k].getX() + speed.getX(), a[k].getY() + speed.getY(), b[j].getX(), b[j].getY()))) {
                        double tmp = lineIntersection(a[k].getX(), a[k].getY(), speed.getX(), speed.getY(), b[i].getX(), b[i].getY(), b[j].getX() - b[i].getX(), b[j].getY() - b[i].getY());
                        if(tmp < s) {
                            s = tmp;
                            refWallDirection.set(b[j].getX() - b[i].getX(), b[j].getY() - b[i].getY());
                        }
                    }
                }
            }
        }
        refWallDirection.setLength(1);
        return s;
    }
//    public static double lineIntersection(ArrayList<Vector2D> cornersA, ArrayList<Edge> edgesB, Vector2D speed, Vector2D refWall, double min) {
//        for(Edge e: edgesB) {
//            for(Vector2D c: cornersA) {
//                if(isSideLeft(getLineSide(c.getX(), c.getY(), c.getX() + speed.getX(), c.getY() + speed.getY(), e.getA().getX(), e.getA().getY()))) {
//                    if(isSideLeft(getLineSide(c.getX(), c.getY(), c.getX() + speed.getX(), c.getY() + speed.getY(), e.getB().getX(), e.getB().getY()))) {
//                        double tmp = lineIntersection(c.getX(), c.getY(), speed.getX(), speed.getY(), e.getA().getX(), e.getA().getY(), e.getB().getX() - e.getA().getX(), e.getB().getY() - e.getA().getY());
//                        if(tmp < min) {
//                            min = tmp;
//                            refWall.set(e.getB().getX() - e.getA().getX(), e.getB().getY() - e.getA().getY());
//                        }
//                    }
//                }
//            }
//        }
//        return min;
//    }
//    public static double lineIntersection2(Vector2D[] a, Vector2D[] b, Vector2D speed, Vector2D refWallDirection) {
//        return Math.min(lineIntersectionLoop(a, b, speed.getX(), speed.getY(), refWallDirection), lineIntersectionLoop(b, a, -speed.getX(), -speed.getY(), refWallDirection));
//    }
//    private static double lineIntersectionLoop(Vector2D[] a, Vector2D[] b, double speedX, double speedY, Vector2D refWallDirection) {
//        double s = 0;
//        for(int i = 0; i < a.length; i++) {
//            int j = (i + 1) % a.length;
//            for(int k = 0; k < b.length; k++) {
//                if(isSideLeft(getLineSide(b[k].getX(), b[k].getY(), b[k].getX() - speedX, b[k].getY() - speedY, a[i].getX(), a[i].getY()))) {
//                    if(isSideRight(getLineSide(b[k].getX(), b[k].getY(), b[k].getX() - speedX, b[k].getY() - speedY, a[j].getX(), a[j].getY()))) {
//                        double tmp = lineIntersection(b[k].getX(), b[k].getY(), speedX, speedY, a[i].getX(), a[i].getY(), a[j].getX() - a[i].getX(), a[j].getY() - a[i].getY());
//                        if(tmp < s) s = tmp;
//                        refWallDirection.set(a[j].getX() - a[i].getX(), a[j].getY() - a[i].getY());
//                    }
//                }
//            }
//        }
//        return s;
//    }
    
    public static int firstBit(long l)
    {
        return Long.numberOfTrailingZeros(l);
    }
    public static int lastBit(long l)
    {
        return 64 - Long.numberOfLeadingZeros(l);
    }
    public static int countBits(long l)
    {
        return Long.bitCount(l);
    }
    public static long singleBit(int i) {
        return 1 << i;
    }
    
//    public final static long[] singleBit = calcSingle();
}