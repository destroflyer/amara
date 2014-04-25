/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import amara.game.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public abstract class Map{
    
    private String name;
    protected MapCamera camera;
    protected MapPhysicsInformation physicsInformation;
    protected MapVisuals visuals = new MapVisuals();
    protected int entity;
    
    public abstract void load(EntityWorld entityWorld);
    
    public abstract void spawn(EntityWorld entityWorld, int playerEntity);

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCamera(MapCamera camera){
        this.camera = camera;
    }

    public MapCamera getCamera(){
        return camera;
    }

    public void setPhysicsInformation(MapPhysicsInformation physicsInformation){
        this.physicsInformation = physicsInformation;
    }

    public MapPhysicsInformation getPhysicsInformation(){
        return physicsInformation;
    }

    public MapVisuals getVisuals(){
        return visuals;
    }

    public void setEntity(int entity){
        this.entity = entity;
    }

    public int getEntity(){
        return entity;
    }
}
