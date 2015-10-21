/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.synchronizing;

import amara.game.entitysystem.EntitySystem;
import amara.game.entitysystem.systems.game.*;
import amara.game.entitysystem.systems.physics.intersectionHelper.TransformUpdateSystem;
import amara.game.entitysystem.systems.players.*;
import amara.game.entitysystem.systems.units.*;

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
