/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes;

import amara.libraries.physics.shapes.PolygonMath.Polygon;
import amara.libraries.physics.shapes.PolygonMath.PolygonBuilder;
import java.util.ArrayList;

/**
 *
 * @author Philipp
 */
public class IntersectorsPopulator {
    public void populate(Intersectors intersectors) {
        intersectors.add(circle_circle);
        intersectors.add(circle_convex);
        intersectors.add(circle_poly);
        
        intersectors.add(convex_circle);
        intersectors.add(convex_convex);
        intersectors.add(convex_poly);
        
        intersectors.add(poly_circle);
        intersectors.add(poly_convex);
        intersectors.add(poly_poly);
        
        intersectors.add(point_shape);
        intersectors.add(shape_point);
    }
    
    private AbstractIntersector<Circle, Circle> circle_circle = new AbstractIntersector<Circle, Circle>(Circle.class, Circle.class) {
            @Override
            public Vector2D resolveVector(Circle a, Circle b) {
                Vector2D delta = b.getGlobalPosition().sub(a.getGlobalPosition());
                return intersectionResolverHelper(delta, a.getGlobalRadius() + b.getGlobalRadius());
            }
            private Vector2D intersectionResolverHelper(Vector2D delta, double distance) {
                assert delta.length() <= distance: delta.length() + " / " + distance;
                double len = delta.length();
                if (len > 0) {
                    return delta.mult((len - distance) / len);
                } else {
                    return new Vector2D(distance, 0);
                }
            }

            @Override
            public boolean intersect(Circle a, Circle b) {
                return a.getGlobalPosition().squaredDistance(b.getGlobalPosition()) < Util.squared(a.getGlobalRadius() + b.getGlobalRadius());
            }
        };
    
    private AbstractIntersector<Circle, SimpleConvexPolygon> circle_convex = new AbstractIntersector<Circle, SimpleConvexPolygon>(Circle.class, SimpleConvexPolygon.class) {
            @Override
            public Vector2D resolveVector(Circle a, SimpleConvexPolygon b) {
                Vector2D center = a.getGlobalPosition();
                double radius = a.getGlobalRadius();

                Vector2D[] points = b.getGlobalPoints();

                Vector2D resolver = Vector2D.Zero;
                double length = Double.POSITIVE_INFINITY;

                for (int i = 0; i < points.length; i++)
                {
                    int j = (i + 1) % points.length;

                    Vector2D dir = points[j].sub(points[i]);

                    double c = points[i].dot(dir);
                    double d = points[j].dot(dir);
                    double e = center.dot(dir);
                    assert c < d;
                    if(e < d)
                    {
                        if(c < e)
                        {
                            dir = dir.unit().rightHand();
                            assert Util.withinEpsilon(dir.dot(points[i]) - dir.dot(points[j]));
                            double distance = center.dot(dir) - points[i].dot(dir);
                            if(radius < distance) continue;
                            if(0 <= distance) return dir.mult(radius - distance);
                            if(radius - distance < length)
                            {
                                length = radius - distance;
                                resolver = dir.mult(length);
                            }
                        }
                    }
                    else
                    {
                        int k = (i + 2) % points.length;

                        dir = points[k].sub(points[j]);

                        c = points[j].dot(dir);
                        e = center.dot(dir);
                        if(e <= c)
                        {
                            dir = center.sub(points[j]).unit();
                            double distance = center.dot(dir) - points[j].dot(dir);
                            return dir.mult(radius - distance);
                        }
                    }
                }
                assert !resolver.withinEpsilon();
                return resolver;
            }
            
            @Override
            public boolean intersect(Circle a, SimpleConvexPolygon b) {
                if(circle_circle.intersect(a, b.getBoundCircle()))
                {
                    if(outlineCircleIntersect(a.getGlobalPosition(), Util.squared(a.getGlobalRadius()), b.getGlobalPoints()))
                    {
                        return true;
                    }
                    if(b.contains(a.getGlobalPosition()))
                    {
                        return true;
                    }
                }
                return false;
            }
    
        };
    
    private AbstractIntersector<Circle, PolygonShape> circle_poly = new AbstractIntersector<Circle, PolygonShape>(Circle.class, PolygonShape.class) {
        @Override
        public Vector2D resolveVector(Circle a, PolygonShape b) {
            throw new RuntimeException("resolveVector is not implemented for circle<->poly");
        }
        
        @Override
        public boolean intersect(Circle a, PolygonShape b) {
            Polygon poly = b.getGlobalPolygon();
            Vector2D center = a.getGlobalPosition();

            if(poly.contains(center)) return true;
            double radiusSquared = Util.squared(a.getGlobalRadius());

            for (ArrayList<Vector2D> points : poly.outlines())
            {
                if(outlineCircleIntersect(center, radiusSquared, points.toArray(Vector2D[]::new))) return true;
            }
            return false;
        }
    };
    
    private boolean outlineCircleIntersect(Vector2D center, double radiusSquared, Vector2D[] poly)
    {
        for (int i = 0; i < poly.length; i++)
        {
            int j = (i + 1) % poly.length;

            if(Vector2DUtil.fromLineSegmentToPoint(poly[i], poly[j], center).squaredLength() < radiusSquared) return true;
        }
        return false;
    }
    
    
    private IntersectorMirrorWrapper<SimpleConvexPolygon, Circle> convex_circle = new IntersectorMirrorWrapper<SimpleConvexPolygon, Circle>(circle_convex);
    
    private AbstractIntersector<SimpleConvexPolygon, SimpleConvexPolygon> convex_convex = new AbstractIntersector<SimpleConvexPolygon, SimpleConvexPolygon>(SimpleConvexPolygon.class, SimpleConvexPolygon.class) {
        @Override
        public Vector2D resolveVector(SimpleConvexPolygon a, SimpleConvexPolygon b) {
            return pusher(a.getGlobalPoints(), b.getGlobalPoints());
        }
        public Vector2D pusher(Vector2D[] convex1, Vector2D[] convex2)
        {
            Vector2D v1 = minPenetration(convex1, convex2);
            Vector2D v2 = minPenetration(convex2, convex1);
            if(v2.squaredLength() < v1.squaredLength()) return v2.inverse();
            return v1;
        }
        private Vector2D minPenetration(Vector2D[] c1, Vector2D[] c2)
        {
            int b;
            Vector2D tmp;
            Vector2D outer = new Vector2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            Vector2D inner;
            for(int a = 0; a < c1.length; a++)
            {
                b = (a + 1) % c1.length;
                inner = Vector2D.Zero;
                for(int i = 0; i < c2.length; i++)
                {
                    if(Vector2DUtil.lineSide(c2[i], c1[a], c1[b]) < 0)
                    {
                        tmp = Vector2DUtil.fromLineSegmentToPoint(c1[a], c1[b], c2[i]);
                        if(inner.squaredLength() < tmp.squaredLength()) inner = tmp;
                    }
                }
                if(outer.squaredLength() > inner.squaredLength()) outer = inner;
            }
            return outer;
        }
        
        @Override
        public boolean intersect(SimpleConvexPolygon a, SimpleConvexPolygon b) {
            if(circle_circle.intersect(a.getBoundCircle(), b.getBoundCircle()))
            {
                return !(seperated(a.getGlobalPoints(), b.getGlobalPoints()) || seperated(b.getGlobalPoints(), a.getGlobalPoints()));
            }
            return false;
        }
    };
    
    private AbstractIntersector<SimpleConvexPolygon, PolygonShape> convex_poly = new AbstractIntersector<SimpleConvexPolygon, PolygonShape>(SimpleConvexPolygon.class, PolygonShape.class) {
        @Override
        public Vector2D resolveVector(SimpleConvexPolygon a, PolygonShape b) {
            throw new RuntimeException("resolveVector is not implemented for convex<->poly");
        }

        @Override
        public boolean intersect(SimpleConvexPolygon a, PolygonShape b) {
            if(seperated(a.getGlobalPoints(), b.getGlobalPolygon().points().toArray(Vector2D[]::new))) return false;

            Vector2D[] points = a.getGlobalPoints();
            PolygonBuilder builder = new PolygonBuilder();
            builder.nextOutline(false);
            for (int i = 0; i < points.length; i++)
            {
                builder.add(points[i]);
            }
            Polygon c = builder.build(false);
            return b.getGlobalPolygon().intersects(c);
        }
    };
    
    private boolean seperated(Vector2D[] c1, Vector2D[] c2)
    {
        int b;
        boolean seperated;
        for(int a = 0; a < c1.length; a++)
        {
            seperated = true;
            b = (a + 1) % c1.length;
            for(int i = 0; seperated && i < c2.length; i++)
            {
                if(Vector2DUtil.lineSide(c2[i], c1[a], c1[b]) < 0)
                {
                    seperated = false;
                    break;
                }
            }
            if(seperated) return true;
        }
        return false;
    }
        
    
    private AbstractIntersector<PointShape, Shape> point_shape = new AbstractIntersector<PointShape, Shape>(PointShape.class, Shape.class) {
        @Override
        public Vector2D resolveVector(PointShape a, Shape b) {
            throw new RuntimeException("resolveVector is not implemented for point<->shape");
        }
        
        @Override
        public boolean intersect(PointShape a, Shape b) {
            return b.contains(a.getGlobalPoint());
        }
    };
    
    private IntersectorMirrorWrapper<Shape, PointShape> shape_point = new IntersectorMirrorWrapper<Shape, PointShape>(point_shape);
    
    
    private IntersectorMirrorWrapper<PolygonShape, Circle> poly_circle = new IntersectorMirrorWrapper<PolygonShape, Circle>(circle_poly);
    
    private IntersectorMirrorWrapper<PolygonShape, SimpleConvexPolygon> poly_convex = new IntersectorMirrorWrapper<PolygonShape, SimpleConvexPolygon>(convex_poly);
    
    private AbstractIntersector<PolygonShape, PolygonShape> poly_poly = new AbstractIntersector<PolygonShape, PolygonShape>(PolygonShape.class, PolygonShape.class) {
        @Override
        public Vector2D resolveVector(PolygonShape a, PolygonShape b) {
            throw new RuntimeException("resolveVector is not implemented for poly<->poly");
        }
        
        @Override
        public boolean intersect(PolygonShape a, PolygonShape b) {
            return a.getGlobalPolygon().intersects(b.getGlobalPolygon());
        }
    };
    
}