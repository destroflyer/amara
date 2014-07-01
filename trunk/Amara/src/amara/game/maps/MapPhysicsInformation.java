/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import java.util.ArrayList;
import amara.game.entitysystem.systems.physics.PolyHelper;
import amara.game.entitysystem.systems.physics.intersectionHelper.PolyMapManager;
import amara.game.entitysystem.systems.physics.shapes.Shape;

/**
 *
 * @author Carl
 */
public class MapPhysicsInformation{

    public MapPhysicsInformation(int width, int height, float heightmapScale, ArrayList<Shape> obstacles){
        this.width = width;
        this.height = height;
        this.heightmapScale = heightmapScale;
        this.obstacles = obstacles;
        polyMapManager = new PolyMapManager(PolyHelper.fromShapes(obstacles), width, height);
    }
    private int width;
    private int height;
    private float heightmapScale;
    private ArrayList<Shape> obstacles;
    private PolyMapManager polyMapManager;

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public float getHeightmapScale(){
        return heightmapScale;
    }

    public ArrayList<Shape> getObstacles(){
        return obstacles;
    }

    public PolyMapManager getPolyMapManager(){
        return polyMapManager;
    }
}
