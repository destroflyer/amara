/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersectionHelper;

import amara.game.entitysystem.systems.physics.intersection.*;
import amara.game.entitysystem.systems.physics.shapes.*;
import java.util.*;

/**
 *
 * @author Philipp
 */
public class MapGrid<Hitbox extends HasShape>
{
    private ArrayList<ArrayList<ArrayList<Hitbox>>> cells;
    private double cellSize;
    private int cols, rows;
    private Rectangle gridChecker;
    
    public MapGrid(int cols, int rows, double cellSize)
    {
        this.cellSize = cellSize;
        this.cols = cols;
        this.rows = rows;
        cells = new ArrayList<ArrayList<ArrayList<Hitbox>>>();
        for(int x = 0; x < cols; x++) {
            cells.add(new ArrayList<ArrayList<Hitbox>>());
            for(int y = 0; y < rows; y++) {
                cells.get(x).add(new ArrayList<Hitbox>());
            }
        }
        gridChecker = new Rectangle(cellSize + 1, cellSize + 1);
    }
    
    public void addObstacle(Hitbox hitable)
    {
        ArrayList<ArrayList<Hitbox>> buckets = getPreciseCells(hitable.getShape());
        for(int i = 0; i < buckets.size(); i++) {
            buckets.get(i).add(hitable);
        }
    }
    
    public void removeObstacle(Hitbox hitable)
    {
        ArrayList<ArrayList<Hitbox>> buckets = getCells(hitable.getShape());
        for(int i = 0; i < buckets.size(); i++) {
            buckets.get(i).remove(hitable);
        }
    }
    
    public List<Hitbox> getAllIntersectionPartners(Shape shape)
    {
        ArrayList<ArrayList<Hitbox>> cellList = getCells(shape);
        HashSet<Hitbox> neighbors = new HashSet<Hitbox>();
        for(ArrayList<Hitbox> cell: cellList)
        {
            neighbors.addAll(cell);
        }
        ArrayList<Hitbox> partners = new ArrayList<Hitbox>();
        for(Hitbox hitable: neighbors)
        {
            if(shape.intersects(hitable.getShape()))
            {
                partners.add(hitable);
            }
        }
        return partners;
    }
    
    private ArrayList<ArrayList<Hitbox>> getCells(Shape shape)
    {
        ArrayList<ArrayList<Hitbox>> out = new ArrayList<ArrayList<Hitbox>>();
        int minX = (int)((shape.getMinX()) / cellSize);
        if(minX < 0) minX = 0;
        int maxX = (int)((shape.getMaxX()) / cellSize);
        if(maxX >= cols) maxX = cols - 1;
        int minY = (int)((shape.getMinY()) / cellSize);
        if(minY < 0) minY = 0;
        int maxY = (int)((shape.getMaxY()) / cellSize);
        if(maxY >= rows) maxY = rows - 1;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                out.add(cells.get(x).get(y));
            }
        }
        return out;
    }
    
    private ArrayList<ArrayList<Hitbox>> getPreciseCells(Shape shape)
    {
        ArrayList<ArrayList<Hitbox>> out = new ArrayList<ArrayList<Hitbox>>();
        int minX = (int)((shape.getMinX()) / cellSize);
        if(minX < 0) minX = 0;
        int maxX = (int)((shape.getMaxX()) / cellSize);
        if(maxX >= cols) maxX = cols - 1;
        int minY = (int)((shape.getMinY()) / cellSize);
        if(minY < 0) minY = 0;
        int maxY = (int)((shape.getMaxY()) / cellSize);
        if(maxY >= rows) maxY = rows - 1;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                setGridChecker(x, y);
                if(gridChecker.intersects(shape)) {
                    out.add(cells.get(x).get(y));
                }
            }
        }
        return out;
    }
    
    public double getCellSize()
    {
        return cellSize;
    }

    public int getCols()
    {
        return cols;
    }

    public int getRows()
    {
        return rows;
    }
    
    private void setGridChecker(int x, int y)
    {
        gridChecker.setTransform(new Transform2D(1, 0, ((double) x + 0.5) * cellSize, ((double) y + 0.5) * cellSize));
    }
    
    public void reset()
    {
        for(int x = 0; x < cols; x++) {
            for(int y = 0; y < rows; y++) {
                cells.get(x).get(y).clear();
            }
        }
    }
    
}
