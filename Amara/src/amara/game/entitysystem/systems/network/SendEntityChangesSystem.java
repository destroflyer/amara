/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.game.entitysystem.systems.network;

import java.util.LinkedList;
import amara.engine.network.NetworkServer;
import amara.engine.network.messages.entitysystem.Message_EntityChanges;
import amara.game.entitysystem.*;
import amara.game.entitysystem.synchronizing.*;

/**
 *
 * @author Carl
 */
public class SendEntityChangesSystem implements EntitySystem{

    public SendEntityChangesSystem(NetworkServer networkServer){
        this.networkServer = networkServer;
    }
    private NetworkServer networkServer;
    
    @Override
    public void update(EntityWorld entityWorld, float deltaSeconds){
        LinkedList<EntityChange> entityChanges = new LinkedList<EntityChange>();
        EntityObserver entitiesObserver = entityWorld.getOrCreateEntityObserver(this);
        for(int entity : entitiesObserver.RemovedEntities())
        {
            entityChanges.add(new RemovedEntityChange(entity));
        }
        entitiesObserver.reset();
        ComponentMapObserver componentsObserver = entityWorld.getOrCreateObserver(this);
        for(int entity : componentsObserver.getNew().getEntitiesWithAll())
        {
            for(Object component : componentsObserver.getNew().getComponents(entity)){
                entityChanges.add(new NewComponentChange(entity, component));
            }
        }
        for(int entity : componentsObserver.getChanged().getEntitiesWithAll())
        {
            for(Object component : componentsObserver.getChanged().getComponents(entity)){
                entityChanges.add(new NewComponentChange(entity, component));
            }
        }
        for(int entity : componentsObserver.getRemoved().getEntitiesWithAll())
        {
            for(Object component : componentsObserver.getRemoved().getComponents(entity)){
                entityChanges.add(new RemovedComponentChange(entity, component.getClass().getName()));
            }
        }
        componentsObserver.reset();
        networkServer.broadcastMessage(new Message_EntityChanges(entityChanges.toArray(new EntityChange[0])));
    }
}
