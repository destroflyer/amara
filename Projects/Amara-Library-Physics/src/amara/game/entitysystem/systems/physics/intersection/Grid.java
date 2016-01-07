/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersection;

import java.util.*;

/**
 *
 * @author Philipp
 */
public class Grid<Hitbox extends BoundAabb>
{
    private ArrayList<ArrayList<Hitbox>> cells;
    private double cellSize;
    private int cols, rows;
    
    public Grid(int cols, int rows, double cellSize)
    {
        this.cellSize = cellSize;
        this.cols = cols;
        this.rows = rows;
        int size = cols * rows;
        cells = new ArrayList<ArrayList<Hitbox>>();
        for (int i = 0; i < size; i++)
        {
            cells.add(new ArrayList<Hitbox>());
        }
    }
    
    public void add(Hitbox hitbox)
    {
        for (ArrayList<Hitbox> bucket : getCells(hitbox))
        {
            bucket.add(hitbox);
        }
    }
    
    public void remove(Hitbox hitbox)
    {
        for (ArrayList<Hitbox> bucket : getCells(hitbox))
        {
            bucket.remove(hitbox);
        }
    }
    
    public Set<Hitbox> getAllNeighbors(Hitbox hitbox)
    {
        HashSet<Hitbox> neighbors = new HashSet<Hitbox>();
        for(ArrayList<Hitbox> cell: getCells(hitbox))
        {
            neighbors.addAll(cell);
        }
        return neighbors;
    }
    
//    public List<Hitbox> getAllIntersectionPartners(Hitbox hitbox, Filter<BoundAabb> filter)
//    {
//        ArrayList<ArrayList<Hitbox>> cellList = getCells(hitbox);
//        HashSet<Hitbox> neighbors = new HashSet<Hitbox>();
//        for(ArrayList<Hitbox> cell: cellList)
//        {
//            neighbors.addAll(cell);
//        }
//        ArrayList<Hitbox> partners = new ArrayList<Hitbox>();
//        for(Hitbox hitable: neighbors)
//        {
//            if(filter.pass(hitbox, hitable))
//            {
//                partners.add(hitable);
//            }
//        }
//        return partners;
//    }
    
    private ArrayList<ArrayList<Hitbox>> getCells(Hitbox hitbox)
    {
        ArrayList<ArrayList<Hitbox>> out = new ArrayList<ArrayList<Hitbox>>();
        int minX = (int)((hitbox.getMinX()) / cellSize);
        if(minX < 0) minX = 0;
        int minY = (int)((hitbox.getMinY()) / cellSize);
        if(minY < 0) minY = 0;
        
        int maxX = (int)((hitbox.getMaxX()) / cellSize);
        if(maxX >= cols) maxX = cols - 1;
        int maxY = (int)((hitbox.getMaxY()) / cellSize);
        if(maxY >= rows) maxY = rows - 1;
        
        for (int x = minX; x <= maxX; x++)
        {
            for (int y = minY; y <= maxY; y++)
            {
                out.add(getCell(x, y));
            }
        }
        return out;
    }
    private ArrayList<Hitbox> getCell(int x, int y)
    {
        return cells.get(x + y * cols);
    }
    
    public void clear()
    {
        for (ArrayList<Hitbox> cell : cells)
        {
            cell.clear();
        }
    }
    
}
