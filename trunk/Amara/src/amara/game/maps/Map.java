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
    
    protected MapPhysicsInformation physicsInformation;
    protected int objectiveEntity;
    
    public abstract void load(EntityWorld entityWorld);

    public MapPhysicsInformation getPhysicsInformation(){
        return physicsInformation;
    }

    public int getObjectiveEntity(){
        return objectiveEntity;
    }
}
