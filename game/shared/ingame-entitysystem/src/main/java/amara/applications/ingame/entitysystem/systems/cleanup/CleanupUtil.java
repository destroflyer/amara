/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.entitysystem.systems.cleanup;

import amara.applications.ingame.entitysystem.components.audio.*;
import amara.applications.ingame.entitysystem.components.general.*;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class CleanupUtil{
    
    public static boolean tryCleanupEntity(EntityWorld entityWorld, int entity){
        boolean removeEntity = (!entityWorld.hasComponent(entity, CustomCleanupComponent.class));
        if(removeEntity){
            //These audio entities will be kept so they are sent to the client and finally removed by the RemoveAudiosAfterPlayingSystem
            if(entityWorld.hasComponent(entity, AudioRemoveAfterPlayingComponent.class)
            && entityWorld.hasComponent(entity, StartPlayingAudioComponent.class)){
                removeEntity = false;
            }
        }
        if(removeEntity){
            entityWorld.removeEntity(entity);
        }
        return removeEntity;
    }
}
