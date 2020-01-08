/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.shared.maps;

import amara.applications.ingame.shared.maps.terrain.TerrainSkin;
import amara.libraries.entitysystem.EntityWorld;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Carl
 */
public abstract class Map{
    
    private String name;
    protected MapCamera camera;
    protected MapLights lights = new MapLights();
    protected List<MapFilter> filters = new LinkedList<>();
    protected TerrainSkin terrainSkin;
    protected MapMinimapInformation minimapInformation;
    protected MapPhysicsInformation physicsInformation;
    protected MapVisuals visuals = new MapVisuals();
    protected MapSpells[] spells = new MapSpells[0];
    protected int entity;
    
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

    public void addFilter(MapFilter filter){
        filters.add(filter);
    }

    public List<MapFilter> getFilters(){
        return filters;
    }

    public void setTerrainSkin(TerrainSkin terrainSkin){
        this.terrainSkin = terrainSkin;
    }

    public TerrainSkin getTerrainSkin(){
        return terrainSkin;
    }

    public void setMinimapInformation(MapMinimapInformation minimapInformation){
        this.minimapInformation = minimapInformation;
    }

    public MapMinimapInformation getMinimapInformation(){
        return minimapInformation;
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

    public MapSpells[] getSpells(){
        return spells;
    }

    public void setEntity(int entity){
        this.entity = entity;
    }

    public int getEntity(){
        return entity;
    }
}
