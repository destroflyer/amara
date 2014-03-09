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

    public MapPhysicsInformation(int width, int height, ArrayList<Shape> obstacles){
        this.width = width;
        this.height = height;
        this.obstacles = obstacles;
    }
    private int width;
    private int height;
    private ArrayList<Shape> obstacles;

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
