/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.shapes.PolygonMath;

/**
 *
 * @author Philipp
 */
public class HolePolygonUtil
{

    public static boolean haveTouchingEdge(HolePolygon a, HolePolygon b)
    {
        for (int k = 0; k < a.numSimplePolys(); k++)
        {
            SimplePolygon simple = a.getSimplePoly(k);
            for (int i = 0; i < simple.numPoints(); i++)
            {
                int j = (i + 1) % simple.numPoints();
                if (b.hasEdge(simple.getPoint(j), simple.getPoint(i))) return true;
            }
        }
        return false;
    }
    public static boolean haveCommonPoint(HolePolygon a, HolePolygon b)
    {
        for (int k = 0; k < a.numSimplePolys(); k++)
        {
            SimplePolygon simple = a.getSimplePoly(k);
            for (int i = 0; i < simple.numPoints(); i++)
            {
                if (b.hasPoint(simple.getPoint(i))) return true;
            }
        }
        return false;
    }
    public static boolean areTouching(HolePolygon a, HolePolygon b)
    {
        HolePolygon tmpA = new HolePolygon(a);
        HolePolygon tmpB = new HolePolygon(b);
        insertCommonOutlinePoints(tmpA, tmpB);
        return haveCommonPoint(tmpA, tmpB);
    }
    public static void insertCommonOutlinePoints(HolePolygon a, HolePolygon b)
    {
        assert(!a.hasLooseLines());
        assert(!b.hasLooseLines());

        for (int i = 0; i < a.numSimplePolys(); i++)
        {
            SimplePolygon A = a.getSimplePoly(i);
            for (int j = 0; j < b.numSimplePolys(); j++)
            {
                SimplePolygon B = b.getSimplePoly(j);
                SimplePolygonUtil.insertCommonOutlinePoints(A, B);
            }
        }
        assert(!a.hasLooseLines());
        assert(!b.hasLooseLines());
    }

    public static boolean haveOverlappingAreas(HolePolygon a, HolePolygon b)
    {
        if(a.isInfinite() && b.isInfinite()) return true;
        if (!haveMainPolysOverlappingAreas(a.mainPoly(), b.mainPoly())) return false;
        if (mainContainsOutline(a.mainPoly(), b.mainPoly()))
        {
            for (int i = 0; i < a.numHolePolys(); i++)
            {
                if (!a.getHolePoly(i).areaContainsOutline(b.mainPoly())) return false;
            }
        }
        else if (b.mainPoly().areaContainsOutline(a.mainPoly()))
        {
            for (int i = 0; i < b.numHolePolys(); i++)
            {
                if (!b.getHolePoly(i).areaContainsOutline(a.mainPoly())) return false;
            }
        }
        return true;
    }
    private static boolean haveMainPolysOverlappingAreas(SimplePolygon a, SimplePolygon b)
    {
        if(a == null || b == null) return true;
        return SimplePolygonUtil.haveOverlappingAreas(a, b);
    }
    private static boolean mainContainsOutline(SimplePolygon a, SimplePolygon b)
    {
        if(a == null) return true;
        if(b == null) return false;
        return a.areaContainsOutline(b);
    }

    public static boolean outlinesIntersect(HolePolygon a, HolePolygon b)
    {
        for (int i = 0; i < a.numSimplePolys(); i++)
        {
            for (int j = 0; j < b.numSimplePolys(); j++)
            {
                if (SimplePolygonUtil.outlinesIntersect(a.getSimplePoly(i), b.getSimplePoly(j))) return true;
            }
        }
        return false;
    }

    public static void write(ByteBuffer buffer, HolePolygon poly)
    {
        SimplePolygonUtil.write(buffer, poly.mainPoly());
        buffer.writeInt(poly.numHolePolys());
        for (int i = 0; i < poly.numHolePolys(); i++)
        {
            SimplePolygonUtil.write(buffer, poly.getHolePoly(i));
        }
    }

    public static HolePolygon read(ByteBuffer buffer)
    {
        HolePolygon hole = new HolePolygon(SimplePolygonUtil.read(buffer));
        int num = buffer.readInt();
        for (int i = 0; i < num; i++)
        {
            hole.add(SimplePolygonUtil.read(buffer));
        }
        return hole;
    }
    
}
