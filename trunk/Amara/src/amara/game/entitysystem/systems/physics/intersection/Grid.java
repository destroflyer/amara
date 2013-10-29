/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.physics.intersection;

import amara.game.entitysystem.systems.physics.shapes.*;
import java.awt.Graphics;
import java.util.*;

/**
 *
 * @author Philipp
 */
public class Grid<Hitbox extends HasShape> {

    public Grid(int cols, int rows, double cellSize) {
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

    public void add(Hitbox hitable) {
        ArrayList<ArrayList<Hitbox>> buckets = getCells(hitable);
        for(int i = 0; i < buckets.size(); i++) {
            buckets.get(i).add(hitable);
        }
    }
    public void addPrecise(Hitbox hitable) {
        ArrayList<ArrayList<Hitbox>> buckets = getPreciseCells(hitable.getShape());
        for(int i = 0; i < buckets.size(); i++) {
            buckets.get(i).add(hitable);
        }
    }
    public void remove(Hitbox hitable) {
        ArrayList<ArrayList<Hitbox>> buckets = getCells(hitable);
        for(int i = 0; i < buckets.size(); i++) {
            buckets.get(i).remove(hitable);
        }
    }
    public void removePrecise(Hitbox hitable) {
        for(int x = 0; x < cols; x++) {
            for(int y = 0; y < rows; y++) {
                cells.get(x).get(y).remove(hitable);
            }
        }
    }
    
    protected ArrayList<ArrayList<Hitbox>> getCells(Hitbox hitable) {
        ArrayList<ArrayList<Hitbox>> out = new ArrayList<ArrayList<Hitbox>>();
        int minX = (int)((hitable.getShape().getMinX()) / cellSize);
        if(minX < 0) minX = 0;
        int maxX = (int)((hitable.getShape().getMaxX()) / cellSize);
        if(maxX >= cols) maxX = cols - 1;
        int minY = (int)((hitable.getShape().getMinY()) / cellSize);
        if(minY < 0) minY = 0;
        int maxY = (int)((hitable.getShape().getMaxY()) / cellSize);
        if(maxY >= rows) maxY = rows - 1;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                out.add(cells.get(x).get(y));
            }
        }
        return out;
    }
    protected ArrayList<ArrayList<Hitbox>> getPreciseCells(Shape shape) {
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
    
    public boolean isIntersected(Hitbox hitable) {
        return getFirstIntersectionPartner(hitable) != null;
    }
    public Hitbox getFirstIntersectionPartner(Hitbox hitable) {
        ArrayList<ArrayList<Hitbox>> areaCells = getCells(hitable);
        for(int i = 0; i < areaCells.size(); i++) {
            for(int j = 0; j < areaCells.get(i).size(); j++) {
                if(hitable == (areaCells.get(i).get(j))) continue;
                if(hitable.getShape().intersects(areaCells.get(i).get(j).getShape())) {
                    return areaCells.get(i).get(j);
                }
            }
        }
        return null;
    }
    public ArrayList<Hitbox> getAllIntersectionPartners(Hitbox hitable) {
        ArrayList<Hitbox> partners = new ArrayList<Hitbox>(getNeighbors(hitable));//new ArrayList<Hitbox>();
        for(int i = 0; i < partners.size();) {
            if(!hitable.getShape().intersects(partners.get(i).getShape())) {
                partners.remove(i);
            } else i++;
        }
        partners.remove(hitable);
//        ArrayList<ArrayList<Hitbox>> areaCells = getCells(hitable);
//        for(int i = 0; i < areaCells.size(); i++) {
//            for(int j = 0; j < areaCells.get(i).size(); j++) {
//                if(hitable == (areaCells.get(i).get(j))) continue;
//                if(Shape.intersect(hitable.getShape(), areaCells.get(i).get(j).getShape())) {
//                    if(!partners.contains(areaCells.get(i).get(j))) {
//                        partners.add(areaCells.get(i).get(j));
//                    }
//                }
//            }
//        }
        return partners;
    }
//    public ArrayList<Hitbox> getNeighbors(Hitbox hitable) {
//        ArrayList<ArrayList<Hitbox>> areaCells = getCells(hitable);
//        ArrayList<Hitbox> neighbors = new ArrayList<Hitbox>();
//        for(int i = 0; i < areaCells.size(); i++) {
//            for(int j = 0; j < areaCells.get(i).size(); j++) {
//                neighbors.add(areaCells.get(i).get(j));
//            }
//        }
//        Collections.sort(neighbors, new Comparator() {
//
//            @Override
//            public int compare(Object o1, Object o2) {
//                return o2.hashCode() - o1.hashCode();
//            }
//            
//        });
//        for(int i = 1; i < neighbors.size();) {
//            if(neighbors.get(i) == neighbors.get(i - 1)) {
//                neighbors.remove(i);
//            } else i++;
//        }
//        return neighbors;
//    }
//    public ArrayList<Hitbox> getNeighbors(Hitbox hitable) {
//        ArrayList<ArrayList<Hitbox>> areaCells = getCells(hitable);
//        HashSet<Hitbox> neighbors = new HashSet<Hitbox>();
//        for(int i = 0; i < areaCells.size(); i++) {
//            for(int j = 0; j < areaCells.get(i).size(); j++) {
//                neighbors.add(areaCells.get(i).get(j));
//            }
//        }
//        return new ArrayList<Hitbox>(neighbors);
//    }
    public HashSet<Hitbox> getNeighbors(Hitbox hitable) {
        ArrayList<ArrayList<Hitbox>> areaCells = getCells(hitable);
        HashSet<Hitbox> neighbors = new HashSet<Hitbox>();
        for(int i = 0; i < areaCells.size(); i++) {
            for(int j = 0; j < areaCells.get(i).size(); j++) {
                neighbors.add(areaCells.get(i).get(j));
            }
        }
        return neighbors;
    }
    
    public void debugDraw(Graphics graphics) {
        for(int x = 0; x < cols; x++) {
            for(int y = 0; y < rows; y++) {
                graphics.drawRect((int)(x * cellSize), (int)(y * cellSize), (int)cellSize, (int)cellSize);
            }
        }
    }
    
    public void reset() {
        for(int x = 0; x < cols; x++) {
            for(int y = 0; y < rows; y++) {
                cells.get(x).get(y).clear();
            }
        }
    }

    public double getCellSize() {
        return cellSize;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
    
    protected void setGridChecker(int x, int y) {
        gridChecker.setX(((double) x + 0.5) * cellSize);
        gridChecker.setY(((double) y + 0.5) * cellSize);
    }
    
    protected ArrayList<ArrayList<ArrayList<Hitbox>>> cells;
    protected double cellSize;
    protected int cols, rows;
    protected Rectangle gridChecker;
}