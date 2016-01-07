/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.synchronizing;

import amara.game.entitysystem.systems.players.CountdownPlayerRespawnSystem;
import amara.game.entitysystem.systems.units.CountdownCastingSystem;
import amara.game.entitysystem.systems.game.UpdateGameTimeSystem;
import amara.game.entitysystem.EntitySystem;
import amara.game.entitysystem.systems.physics.TransformUpdateSystem;

/**
 *
 * @author Carl
 */
public class ParallelNetworkSystems{
    
    public static EntitySystem[] generateSystems(){
        return new EntitySystem[]{
            new UpdateGameTimeSystem(),
            new CountdownPlayerRespawnSystem(),
            new CountdownCastingSystem(),
            new TransformUpdateSystem()
        };
    }
}
