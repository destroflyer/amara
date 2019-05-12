/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.libraries.physics.shapes.PolygonMath;

import amara.libraries.physics.shapes.Vector2D;

/**
 *
 * @author Philipp
 */
public class AbstractTriangleNode
{
    private Vector2D[] points;
    private AbstractTriangleNode[] neighbors = new AbstractTriangleNode[3];
    private int level = -1;
    private int connected = -1;
    private AbstractTriangleNode[] adjacent = new AbstractTriangleNode[3];
    private double[] channelAngle = new double[3];
    private double[] chokeWidth = new double[3];
    private double[] edgeWidth = new double[3];

    public AbstractTriangleNode(Vector2D... points)
    {
        this.points = points;
    }
    
    public Vector2D getPoint(int i)
    {
        return points[i];
    }
    public AbstractTriangleNode getNeighbor(int i)
    {
        return neighbors[i];
    }
    
    public boolean hasNeighbor(AbstractTriangleNode n)
    {
        for (int i = 0; i < 3; i++)
        {
            if(neighbors[i] == n) return true;
        }
        return false;
    }
    
    public void setNeighbor(int i, AbstractTriangleNode n)
    {
        if(n.indexOf(points[i]) == -1) throw new Error("tris are not neighbors");
        int j = n.indexOf(points[(i + 1) % 3]);
        if(j == -1) throw new Error("tris are not neighbors");
        assert neighbors[i] == null;
        assert n.neighbors[j] == null;
        neighbors[i] = n;
        n.neighbors[j] = this;
    }
    public boolean tryNeighbor(AbstractTriangleNode tri)
    {
        for (int i = 0; i < 3; i++)
        {
            int j = (i + 1) % 3;
            int k = tri.indexOf(points[j]);
            if(k == -1 || tri.indexOf(points[i]) == -1) continue;
            assert neighbors[i] == null;
            assert tri.neighbors[k] == null;
            neighbors[i] = tri;
            tri.neighbors[k] = this;
            return true;
        }
        return false;
    }
    
    public int indexOf(Vector2D p)
    {
        for (int i = 0; i < 3; i++)
        {
            if(p.equals(points[i])) return i;
        }
        return -1;
    }
    
    public int numNeighbors()
    {
        int num = 0;
        for (int i = 0; i < 3; i++)
        {
            if(neighbors[i] != null) num++;
        }
        return num;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getConnected() {
        return connected;
    }

    public void setConnected(int connected) {
        this.connected = connected;
    }

    public AbstractTriangleNode getAdjacent(int i) {
        return adjacent[i];
    }

    public void setAdjacent(int i, AbstractTriangleNode adjacent) {
        this.adjacent[i] = adjacent;
    }

    public double getAngle(int i) {
        return channelAngle[i];
    }

    public void setAngle(int i, double channelAngle) {
        this.channelAngle[i] = channelAngle;
    }

    public double getChoke(int i) {
        return chokeWidth[i];
    }

    public void setChoke(int i, double chokeWidth) {
        this.chokeWidth[i] = chokeWidth;
    }

    public double getEdgeWidth(int i) {
        return edgeWidth[i];
    }

    public void setEdgeWidth(int i, double edgeWidth) {
        this.edgeWidth[i] = edgeWidth;
    }
    
    
}
