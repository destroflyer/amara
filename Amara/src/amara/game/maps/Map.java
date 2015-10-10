/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.maps;

import amara.engine.applications.ingame.client.maps.TerrainSkin;
import amara.game.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public abstract class Map{
    
    private String name;
    protected MapCamera camera;
    protected MapLights lights = new MapLights();
    protected TerrainSkin terrainSkin;
    protected MapPhysicsInformation physicsInformation;
    protected MapVisuals visuals = new MapVisuals();
    protected Integer entity;
    
    public abstract void load(EntityWorld entityWorld);
    
    public void initializePlayer(EntityWorld entityWorld, int playerEntity){
        
    }
    
    public abstract void spawnPlayer(EntityWorld entityWorld, int playerEntity);

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

    public MapLights getLights(){
        return lights;
    }

    public void setTerrainSkin(TerrainSkin terrainSkin){
        this.terrainSkin = terrainSkin;
    }

    public TerrainSkin getTerrainSkin(){
        return terrainSkin;
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

    public void setEntity(Integer entity){
        this.entity = entity;
    }

    public Integer getEntity(){
        return entity;
    }
}
