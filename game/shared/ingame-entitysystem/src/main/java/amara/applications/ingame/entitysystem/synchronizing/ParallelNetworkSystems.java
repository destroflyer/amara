/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.synchronizing;

import amara.applications.ingame.entitysystem.systems.effects.players.*;
import amara.applications.ingame.entitysystem.systems.game.*;
import amara.applications.ingame.entitysystem.systems.physics.*;
import amara.applications.ingame.entitysystem.systems.units.*;
import amara.libraries.entitysystem.EntitySystem;

/**
 *
 * @author Carl
 */
public class ParallelNetworkSystems{
    
    public static EntitySystem[] generateSystems(){
        return new EntitySystem[]{
            new UpdateGameTimeSystem(),
            new CountdownPlayerAnnouncementsSystem(),
            new CountdownCastingSystem(),
            new TransformUpdateSystem()
        };
    }
}
