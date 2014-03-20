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
    protected MapPhysicsInformation physicsInformation;
    protected MapVisuals visuals = new MapVisuals();
    protected int objectiveEntity;
    
    public abstract void load(EntityWorld entityWorld);
    
    public abstract void spawn(EntityWorld entityWorld, int playerIndex, int playerUnitEntity);

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public MapPhysicsInformation getPhysicsInformation(){
        return physicsInformation;
    }

    public void setPhysicsInformation(MapPhysicsInformation physicsInformation){
        this.physicsInformation = physicsInformation;
    }

    public MapVisuals getVisuals(){
        return visuals;
    }

    public int getObjectiveEntity(){
        return objectiveEntity;
    }
}