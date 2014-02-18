/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import java.util.ArrayList;
import amara.game.entitysystem.systems.physics.shapes.Shape;

/**
 *
 * @author Carl
 */
public class MapPhysicsInformation{

    public MapPhysicsInformation(String terrainName, int width, int height, ArrayList<Shape> obstacles){
        this.terrainName = terrainName;
        this.width = width;
        this.height = height;
        this.obstacles = obstacles;
    }
    private String terrainName;
    private int width;
    private int height;
    private ArrayList<Shape> obstacles;

    public String getTerrainName(){
        return terrainName;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public ArrayList<Shape> getObstacles(){
        return obstacles;
    }
}
