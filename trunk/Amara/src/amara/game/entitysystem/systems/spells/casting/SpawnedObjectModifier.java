/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.spells.casting;

import amara.game.entitysystem.EntityWrapper;

/**
 *
 * @author Carl
 */
public interface SpawnedObjectModifier{
    
    public abstract void modify(EntityWrapper spawnedObject, EntityWrapper spawnInformation);
}
